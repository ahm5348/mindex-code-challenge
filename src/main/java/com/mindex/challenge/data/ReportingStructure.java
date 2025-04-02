package com.mindex.challenge.data;

/**
 * ReportingStructure holds an employee
 * and the numberOfReports it has.
 * The number of reports is the number of unique direct
 * reports that employee or any of its 'children' have
 * repeatedly.
 */
public class ReportingStructure {

    private Employee employee;
    private int numberOfReports;

    /**
     * ReportingStructure is an empty constructor
     */
    public ReportingStructure() {
    }

    /**
     * getEmployee returns the Employee object stored inside the ReportingStructure
     * @return Employee object in this ReportingStructure
     */
    public Employee getEmployee() { return employee; }

    /**
     * setEmployee updates the ReportingStructure's Employee object.
     * This can be done at the first initialization (null -> Employee)
     * or an update.
     * @param employee Employee object we want to set
     */
    public void setEmployee(Employee employee) { this.employee = employee; }

    /**
     * getNumberOfReports obtains the number of unique reports
     * of the Employee part of the ReportingStructure object
     * and any of their recursive reports
     * @return integer number of reports
     */
    public int getNumberOfReports() { return numberOfReports; }

    /**
     * setNumberOfReports changes the number of unique reports
     * of this ReportingStructure object.
     * @param numberOfReports integer holding our new number of reports
     */
    public void setNumberOfReports(int numberOfReports) { this.numberOfReports = numberOfReports; }
}
