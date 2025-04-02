package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ReportingStructureController
 * holds the request to create a new report
 * for the employeeId given.
 */
@RestController
public class ReportingStructureController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    /**
     * getNumberOfReports gets the number of unique
     * reports that employee has. It looks into each
     * direct report of that employee, and then into those
     * employee's direct reports, taking each unique report
     * to our original employee's count.
     * @param id EmployeeId of the Employee we want to find reports for
     * @return ReportingStructure object holding the employee and their reports
     */
    @GetMapping("/reports/{id}")
    public ReportingStructure getNumberOfReports(@PathVariable String id) {
        LOG.debug("Received getNumberOfReports request for id [{}]", id);

        return reportingStructureService.getReportingStructure(id);
    }
}
