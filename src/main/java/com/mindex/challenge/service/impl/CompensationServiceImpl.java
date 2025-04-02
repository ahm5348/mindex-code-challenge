package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.mindex.challenge.data.*;
import com.mindex.challenge.dao.*;
import com.mindex.challenge.service.*;
import org.springframework.stereotype.Service;


/**
 * Handles the REST request for creating and deleting the
 * {@linkplain Compensation} of a given {@linkplain Employee}.
 *
 * @author Aidan Mayes Poduslo
 */
@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Compensation create(Compensation compensation) throws RuntimeException {
        LOG.debug("Creating compensation [{}]", compensation);

        if (employeeRepository.findByEmployeeId(compensation.getCompensationId()) != null) {
            compensationRepository.insert(compensation);
        }
        else {
            throw new RuntimeException("Creating compensation failed as the Employee "
                    + compensation.getCompensationId() + " is not in our system");
        }

        return compensation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Compensation read(String id) throws RuntimeException {
        LOG.debug("Reading compensation from employee [{}]", id);

        Compensation compensation = compensationRepository.findByCompensationId(id);

        // use same structure as EmployeeService
        if (compensation == null) {
            throw new RuntimeException("Invalid employee id: " + id);
        }

        return compensation;
    }

}
