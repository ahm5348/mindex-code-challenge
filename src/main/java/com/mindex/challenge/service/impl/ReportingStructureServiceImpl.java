package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mindex.challenge.data.*;
import com.mindex.challenge.service.*;

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

    @Autowired
    private EmployeeService employeeService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        LOG.debug("Obtaining Reporting Structure for employee with id [{}]", employeeId);

        try {
            Employee employee = employeeService.read(employeeId);

            // should never be null, read already returns exception if so
            assert employee != null;

            ReportingStructure reportingStructure = new ReportingStructure();
            reportingStructure.setEmployee(employee);

            // if the employee has no reports we get 0 right away
            // may be an optimization to 'skip' the call in this case
            reportingStructure.setNumberOfReports(getNumberOfReports(employee));

            return reportingStructure;
        }
        catch (Exception e) {
            // Employee read exception throws us a runtime exception with
            // Will also catch our exception for
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
    private int getNumberOfReports(Employee employee) throws RuntimeException {
        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
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

            // On the first run, this just gets our first employee again.
            // For the rest, it ensures the object exists.
            // If not, it will pass the runtime exception to the parent
            employee = employeeService.read(id);

            seenIds.add(id);

            if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
                continue;
            }

            for (Employee child : employee.getDirectReports()) {
                // ensure we add only distinct employees
                if (seenIds.contains(child.getEmployeeId())) {
                    continue;
                }

                // if a parent has the same child node twice
                // it would be in pendingIds but not seen
                if (pendingIds.contains(child.getEmployeeId())) {
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
