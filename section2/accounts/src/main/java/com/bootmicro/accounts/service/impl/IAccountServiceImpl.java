package com.bootmicro.accounts.service.impl;


import com.bootmicro.accounts.constants.AccountsConstants;
import com.bootmicro.accounts.dto.AccountsDto;
import com.bootmicro.accounts.dto.CustomerDto;
import com.bootmicro.accounts.entity.Accounts;
import com.bootmicro.accounts.entity.Customer;
import com.bootmicro.accounts.exception.CustomerAlreadyExistsException;
import com.bootmicro.accounts.exception.ResourceNotFoundException;
import com.bootmicro.accounts.mapper.AccountsMapper;
import com.bootmicro.accounts.mapper.CustomerMapper;
import com.bootmicro.accounts.repository.AccountRepository;
import com.bootmicro.accounts.repository.CustomerRepository;
import com.bootmicro.accounts.service.IAccountServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class IAccountServiceImpl implements IAccountServices {

//   Here not adding  @Autowired , insted of the using allArgsCustructor
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;


    /**
     * @param customerDto customerdDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto , new Customer());
        Optional<Customer> customer1 = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (customer1.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with the given mobile number"+ customerDto.getMobileNumber());
        }

        Customer savedCustomer =   customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * Using Random Account number
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerid(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }


    /**
     * @param mobileNumber mobile number to find Account details
     * return Accounts details based on given mobile number
     */
    @Override
    public  CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not found with the given mobile number " + mobileNumber));

        Accounts accounts = accountRepository.findBycustomerid(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not found with the given mobile number " + mobileNumber));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer , new CustomerDto());
        customerDto.setAccountsdto(AccountsMapper.mapToAccountsDto(accounts , new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto accepting CustomerDto object
     * @return account updated or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsdto();
        if (accountsDto != null) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account not found with the given account number " + accountsDto.getAccountNumber()));

            Accounts accounts1 = accountRepository.save(accounts);

            Long customerId = accounts1.getCustomerid();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Account not found with the given account number " + accountsDto.getAccountNumber()));
            CustomerMapper.mapToCustomer(customerDto , customer);
          customerRepository.save(customer);
            isUpdated = true;

        }

        return isUpdated;
    }

    /**
     * @param mobileNumber accepting mobile number to delete the account details;
     * @return account deleted or not ?
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not found with the given mobile number " + mobileNumber));

        customerRepository.deleteById(customer.getCustomerId());
        accountRepository.deleteByCustomerid(customer.getCustomerId());
        return true;
    }

}
