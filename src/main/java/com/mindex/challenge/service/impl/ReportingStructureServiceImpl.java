package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.mindex.challenge.data.*;
import com.mindex.challenge.service.*;
import org.springframework.web.client.RestTemplate;

// for creating our queue
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Handles the REST request for finding the number of reports
 * of a given Employee.
 *
 * @author Aidan Mayes Poduslo
 */

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    /**
     * Finds the proper number of reports of the given {@linkplain Employee employee}
     *
     * @param employeeId The id of the {@link Employee employee}
     * @return  A {@link ReportingStructure object} for that {@link Employee employee}
     */
    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        LOG.debug("Obtaining Reporting Structure for employee with id [{}]", employeeId);

        try {
            ResponseEntity<Employee> employeeResponseEntity =
                    new RestTemplate().getForEntity(employeeId, Employee.class);
            Employee employee = employeeResponseEntity.getBody();

            ReportingStructure reportingStructure = new ReportingStructure();
            reportingStructure.setEmployee(employee);

            // should never be null, load already returns exception if so
            assert employee != null;
            reportingStructure.setNumberOfReports(getNumberOfReports(employee));

            return reportingStructure;
        }
        catch (Exception e) {
            // Employee read exception throws us a runtime exception with
            // an error message already
            LOG.error(e.getMessage());
        }

        return null;
    }

    /**
     * Helper function in getting the reporting structure. Uses
     * an algorithm similar to breadth-first search to obtain
     * the number of reports the {@link Employee employee} has.
     *
     * @param employee {@link Employee employee} object of our target
     * @return integer representing the total number of reports
     */
    private int getNumberOfReports(Employee employee) {
        if (employee.getDirectReports().isEmpty()) {
            return 0;
        }

        // use BFS algorithm
        // track seenIds so we don't have repeats
        ArrayList<String> seenIds = new ArrayList<>();

        // track any pendingIds
        // LinkedList has quick getFirst
        Queue<String> pendingIds = new LinkedList<>();

        // holds total number of reports of that employee
        int numberOfReports = 0;

        pendingIds.add(employee.getEmployeeId());

        while (!pendingIds.isEmpty()) {

            String id = pendingIds.poll();

            seenIds.add(id);

            for (Employee child : employee.getDirectReports()) {
                // ensure we add only distinct employees
                if (seenIds.contains(child.getEmployeeId())) {
                    continue;
                }

                // each new child is one new report
                numberOfReports++;
                pendingIds.add(child.getEmployeeId());

            }

        }

        return numberOfReports;
    }
}
