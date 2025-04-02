package com.mindex.challenge.data;

import java.util.Date;

/**
 * Compensation has a key of the compensationId.
 * This represents the employee this compensation is for.
 * It holds the salary and date this compensation was created for
 * that employee.
 */
public class Compensation {

    // 1-1 with an employeeId
    private String compensationId;

    private int salary;
    private Date effectiveDate;

    /**
     * Empty Compensation constructor for JSON
     */
    public Compensation() {}

    /**
     * getCompensationId returns the employeeId of this compensation
     * @return the compensationId of the Compensation
     */
    public String getCompensationId() { return compensationId; }

    /**
     * setCompensationId updates the compensationId
     * @param compensationId new compensationId of the Compensation
     */
    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
    }

    /**
     * getSalary returns the salary of the employee
     * @return integer salary
     */
    public int getSalary() { return salary; }

    /**
     * setSalary updates the salary of the employee
     * @param salary new salary as an integer
     */
    public void setSalary(int salary) { this.salary = salary; }

    /**
     * getEffectiveDate returns the date of the Compensation
     * @return Date instance
     */
    public Date getEffectiveDate() { return effectiveDate; }

    /**
     * setEffectiveDate updates the date of the Compensation
     * @param effectiveDate new desired Date
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
