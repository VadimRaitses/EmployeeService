package com.employeeservice.models;

import org.springframework.data.mongodb.core.query.Update;

/**
 * @author Raitses Vadim
 */

public interface Updatable {

    Update getUpdatedData();
}
