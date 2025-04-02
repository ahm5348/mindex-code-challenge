package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

/**
 * CompensationService holds the two calls made
 * by the CompensationController.
 */
public interface CompensationService {

    /**
     * create takes the {@linkplain Compensation compensation}
     * object passed and writes it into our repository
     * @param compensation {@link Compensation} object to write
     * @throws RuntimeException when id cannot be found
     * @return {@link Compensation} object that was written
     */
    Compensation create(Compensation compensation) throws RuntimeException;

    /**
     * read takes the id of the {@linkplain Compensation compensation}
     * and uses it to find our {@link Compensation compensation}
     * in our repository.
     * @param id String id of the Employee the compensation is for.
     * @throws RuntimeException when id cannot be found
     * @return {@link Compensation} object
     */
    Compensation read (String id) throws RuntimeException;
}
