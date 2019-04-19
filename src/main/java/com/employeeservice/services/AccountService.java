package com.employeeservice.services;

import com.employeeservice.models.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {

    Account getAccount(String email);

    void addAccount(Account account);

    UserDetails loadAccountByEmail(String email);


}
