package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * CompensationServiceTest is a small test class
 * that tests the create and read calls in our
 * {@linkplain CompensationService}
 * RuntimeExceptions not tested at this moment.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceTest {

    // holds shared base url
    private String compensationUrl;
    private String compensationUrlId;
    private String employeeUrl;

    // employee object used by both tests
    private Employee employee;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationService compensationService;


    /**
     * setup creates the urls according to our random port.
     *
     * It then creates a test employee to store compensation for.
     */
    @Before
    @Order(1)
    public void setup() {
        // we need the port to be initialized so we must
        // wait for that before setting our compensationUrl and employeeUrl
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationUrlId = "http://localhost:" + port + "/compensation/{id}";
        employeeUrl = "http://localhost:" + port + "/employee";

        // create employee
        Employee testEmployee = new Employee();

        this.employee = restTemplate.postForEntity(
                employeeUrl, testEmployee, Employee.class).getBody();

    }

    /**
     * testCompensationCreate creates a new compensation using
     * our test employee created in the setup method.
     * It checks if the creation was correct, with the
     * id, salary, and date being compared.
     */
    @Test
    @Order(2)
    public void testCompensationCreate() {
        // get our employee
        Employee testEmployee = this.employee;

        // create our compensation object
        Compensation compensation = new Compensation();

        // set our compensation fields
        compensation.setCompensationId(testEmployee.getEmployeeId());

        int salary = 100;

        compensation.setSalary(salary);

        Date currentdate = new Date();
        compensation.setEffectiveDate(currentdate);

        Compensation createdCompensation = restTemplate.postForEntity(
                compensationUrl, compensation, Compensation.class).getBody();

        // does it exist?
        assertNotNull(createdCompensation);

        // does it have the same id?
        assertEquals(compensation.getCompensationId(), createdCompensation.getCompensationId());

        // does it have the correct salary?
        assertEquals(salary, createdCompensation.getSalary());

        // does it have the correct date?
        assertEquals(currentdate, createdCompensation.getEffectiveDate());
    }

    /**
     * testCompensationRead is directly set after creating the {@linkplain Compensation}
     * in the testCompensationCreate test.
     * It reads that object.
     */
    @Test
    @Order(3)
    public void testCompensationRead() {
        // get our employee
        Employee testEmployee = this.employee;

        Compensation readCompensation = restTemplate.getForEntity(compensationUrlId, Compensation.class, testEmployee.getEmployeeId()).getBody();

        // does it exist?
        assertNotNull(readCompensation);
    }

}
