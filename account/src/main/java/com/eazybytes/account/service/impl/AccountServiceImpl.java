package com.eazybytes.account.service.impl;

import com.eazybytes.account.constants.AccountsConstants;
import com.eazybytes.account.dto.CustomerDto;
import com.eazybytes.account.entity.Accounts;
import com.eazybytes.account.entity.Customer;
import com.eazybytes.account.exception.CustomerAlreadyExistsException;
import com.eazybytes.account.mapper.CustomerMapper;
import com.eazybytes.account.repository.AccountsRepository;
import com.eazybytes.account.repository.CustomerRepository;
import com.eazybytes.account.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerinfo = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (customerinfo.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Test User");
        Customer savecustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savecustomer));
    }


    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Test User");

        return newAccount;
    }


}
