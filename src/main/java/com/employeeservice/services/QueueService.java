package com.employeeservice.services;

/**
 * @author Raitses Vadim
 */

public interface QueueService<E> {

    void sendMessage(String eventName, E message);

}
