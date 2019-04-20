package com.employeeservice.services.impl;

import com.employeeservice.dao.UserRepository;
import com.employeeservice.models.Account;
import com.employeeservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * @author Raitses Vadim
 */


@Service
public class AccountServiceImpl implements AccountService {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository repository;

    @Autowired
    public AccountServiceImpl(UserRepository repository,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Account getAccount(String mail) {
        return repository.findByEmail(mail);
    }

    public UserDetails loadAccountByEmail(String email) {
        return getUserDetails(email);

    }

    private UserDetails getUserDetails(String email) {
        Account applicationUser = repository.findByEmail(email);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());
    }

    @Override
    public void addAccount(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        repository.save(account);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUserDetails(email);

    }
}
