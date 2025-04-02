package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * References the structure for {@linkplain EmployeeServiceImplTest}
 * in creating the tests for the {@linkplain ReportingStructureServiceImpl} class.
 * It uses the {@link EmployeeService} calls and relies on its functionality.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    // holds shared base url
    private String reportsUrl;
    private String employeeUrl;

    // testEmployees
    private List<Employee> employees = new ArrayList<Employee>();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportingStructureService reportingStructureService; // using the interface


    /**
     * setup creates the urls according to our random port.
     *
     * It then creates a few test employees.
     * The first has no reports, the second has 1 report (the first).
     * These two test the basic functionality.
     *
     * The third holds two duplicates of the child with no reports.
     * The fourth holds layers of reports, testing the ability to look through children.
     */
    @Before
    public void setup() {
        // we need the port to be initialized so we must
        // wait for that before setting our reportsUrl and employeeUrl
        employeeUrl = "http://localhost:" + port + "/employee";
        reportsUrl = "http://localhost:" + port + "/reports/{id}";

        // create employee with no reports
        Employee testEmployeeNoReports = new Employee();
        Employee createdEmployeeNoReports = restTemplate.postForEntity(
                employeeUrl, testEmployeeNoReports, Employee.class).getBody();
        employees.add(createdEmployeeNoReports);


        // create employee with 1 report
        Employee testEmployee = new Employee();
        testEmployee.setDirectReports(employees.subList(0, 1));

        Employee createdEmployeeOneChild = restTemplate.postForEntity(
                employeeUrl, testEmployee, Employee.class).getBody();

        employees.add(createdEmployeeOneChild);


        // create employee with 2 duplicate ids in the reports
        Employee testEmployeeDupReports = new Employee();

        List<Employee> dupList = new ArrayList<>();
        dupList.add(createdEmployeeNoReports);
        dupList.add(createdEmployeeNoReports);

        testEmployeeDupReports.setDirectReports(dupList);

        Employee createdEmployeeDupReports = restTemplate.postForEntity(
                employeeUrl, testEmployeeDupReports, Employee.class).getBody();

        employees.add(createdEmployeeDupReports);


        // create employee with 2 distinct children
        Employee testEmployeeTwoChildren = new Employee();
        // holds employee with child, and employee with duplicate children
        testEmployeeTwoChildren.setDirectReports(employees.subList(1, 3));

        Employee createdEmployeeTwoChildren = restTemplate.postForEntity(
                employeeUrl, testEmployeeTwoChildren, Employee.class).getBody();

        // this employee has two different ids in its DirectReports
        // and the child is shared among those two
        // should expect 3
        employees.add(createdEmployeeTwoChildren);

    }

    /**
     * This test checks if we can detect an {@link Employee} with no direct reports
     * It also checks if it can read direct reports if they exist
     */
    @Test
    public void testGetReportingStructureEmpty() {

        Employee testEmployeeNoReports = employees.get(0);

        ReportingStructure structure = restTemplate.getForEntity(
                reportsUrl, ReportingStructure.class, testEmployeeNoReports.getEmployeeId()).getBody();

        assertNotNull(structure);

        // ensure employee data is maintained
        assertEquals(testEmployeeNoReports.getEmployeeId(), structure.getEmployee().getEmployeeId());

        // this employee has no reports
        assertEquals(0, structure.getNumberOfReports());

    }

    /**
     * This test checks if we can detect an {@link Employee} with 1 direct report
     */
    @Test
    public void testGetReportingStructure() {

        Employee testEmployee = employees.get(1);


        ReportingStructure structure = restTemplate.getForEntity(
                reportsUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();

        assertNotNull(structure);

        // ensure employee data is maintained
        assertEquals(testEmployee.getEmployeeId(), structure.getEmployee().getEmployeeId());

        // this employee has one report
        assertEquals(1, structure.getNumberOfReports());

    }

    /**
     * This test checks if we can detect an {@link Employee} with two duplicate reports
     */
    @Test
    public void testGetReportingStructureDuplicates() {

        Employee testEmployee = employees.get(2);


        ReportingStructure structure = restTemplate.getForEntity(
                reportsUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();

        assertNotNull(structure);

        // ensure employee data is maintained
        assertEquals(testEmployee.getEmployeeId(), structure.getEmployee().getEmployeeId());

        // this employee has one report still
        assertEquals(1, structure.getNumberOfReports());

    }

    /**
     * This test checks if we can detect an {@link Employee} with layers of reports
     */
    @Test
    public void testGetReportingStructureLayers() {

        Employee testEmployee = employees.get(3);


        ReportingStructure structure = restTemplate.getForEntity(
                reportsUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();

        assertNotNull(structure);

        // ensure employee data is maintained
        assertEquals(testEmployee.getEmployeeId(), structure.getEmployee().getEmployeeId());

        // this employee has 3 reports
        assertEquals(3, structure.getNumberOfReports());

    }


}
