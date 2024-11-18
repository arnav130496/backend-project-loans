package com.learning.loans.service;

import com.learning.loans.dto.LoansDto;

public interface ILoanService {

    void createLoan(String mobileNumber);
    LoansDto fetchLoan(String mobileNumber);
    boolean updateLoan(LoansDto loansDto);
    boolean deleteLoan(String mobileNumber);
}
