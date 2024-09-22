package com.bootmicro.accounts.service;

import com.bootmicro.accounts.dto.AccountsDto;
import com.bootmicro.accounts.dto.CustomerDto;

public interface IAccountServices {

    /**
     *
     * @param customerDto customerdDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber mobile number to find Account details
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDto accepting CustomerDto object
     * @return account updated or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber accepting mobile number to delete the account details;
     * @return account deleted or not ?
     */
    boolean deleteAccount(String mobileNumber);

}
