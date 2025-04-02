package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

/**
 * ReportingStructureService is the interface
 * for the REST endpoints for the ReportingStructure
 */
public interface ReportingStructureService {

    /**
     * getReportingStructure finds the proper number of
     * reports of the given {@linkplain Employee employee}
     *
     * @param employeeId The id of the {@link Employee employee}
     * @return  A {@link ReportingStructure object} for that {@link Employee employee}
     */
    ReportingStructure getReportingStructure(String employeeId);
}
