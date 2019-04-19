package com.employeeservice.services;

public interface QueueService<E> {

    void sendMessage(String eventName, E message);

}
