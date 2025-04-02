package com.mindex.challenge.data;

public class ReportingStructure {

    // recursive solution seems simplest to work with.
    // using dynamic programming would be nice but
    // values would not be persisted
    // So, for the same input, they would have the same runtime

    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {
    }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    public int getNumberOfReports() { return numberOfReports; }

    public void setNumberOfReports(int numberOfReports) { this.numberOfReports = numberOfReports; }
}
