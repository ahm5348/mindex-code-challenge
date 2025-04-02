package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * CompensationController holds two REST calls:
 * to create a {@linkplain Compensation} and to
 * read the {@link Compensation} from the database.
 */
@RestController
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /**
     * create takes the {@linkplain Compensation} request,
     * sets in the LOG to display when debugging, and then
     * calls the corresponding service method.
     * @param compensation {@link Compensation} object
     * @return Written {@link Compensation} object
     */
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        return compensationService.create(compensation);
    }

    /**
     * read takes the id given, which would
     * represent a {@linkplain Compensation compensation} object, and makes
     * the call to the corresponding service method.
     * @param id An employeeId that we want the {@link Compensation compensation} for
     * @return Read {@link Compensation} object
     */
    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);

        return compensationService.read(id);
    }
}
