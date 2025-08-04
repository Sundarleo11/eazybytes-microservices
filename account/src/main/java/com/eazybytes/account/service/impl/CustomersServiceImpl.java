package com.eazybytes.account.service.impl;


import com.eazybytes.account.dto.AccountsDto;
import com.eazybytes.account.dto.CardsDto;
import com.eazybytes.account.dto.CustomerDetailsDto;
import com.eazybytes.account.dto.LoansDto;
import com.eazybytes.account.entity.Accounts;
import com.eazybytes.account.entity.Customer;
import com.eazybytes.account.exception.ResourceNotFoundException;
import com.eazybytes.account.mapper.AccountsMapper;
import com.eazybytes.account.mapper.CustomerMapper;
import com.eazybytes.account.repository.AccountsRepository;
import com.eazybytes.account.repository.CustomerRepository;
import com.eazybytes.account.service.ICustomersService;
import com.eazybytes.account.service.client.CardsFeignClient;
import com.eazybytes.account.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}