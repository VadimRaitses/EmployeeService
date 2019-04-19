package com.employeeservice.models;

/**
 * @author Raitses Vadim
 */

public enum EmployeeState {
    CREATED("created"),
    DELETED("deleted"),
    UPDATED("updated");

    private final String state;

    EmployeeState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
