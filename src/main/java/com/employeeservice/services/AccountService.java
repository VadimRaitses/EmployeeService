package com.employeeservice.services;

import com.employeeservice.models.Account;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * @author Raitses Vadim
 */


public interface AccountService extends UserDetailsService {

    Account getAccount(String email);

    void addAccount(Account account);


}
