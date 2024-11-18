package com.learning.loans.service.impl;

import com.learning.loans.dto.LoansDto;
import com.learning.loans.entity.Loans;
import com.learning.loans.repository.LoansRepository;
import com.learning.loans.service.ILoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class LoansServiceImpl implements ILoanService {

    @Autowired
    private LoansRepository loansRepository;


    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optional = loansRepository.findByMobileNumber(mobileNumber);
        if(optional.isPresent()){
            log.error("Loan already exists for mobile number : {}", mobileNumber);
            throw new RuntimeException("Loan already exists for mobile number : " + mobileNumber);
        }
        createLoansEntity(mobileNumber);
    }

    private void createLoansEntity(String mobileNumber){
        Loans loans = new Loans();
        loans.setMobileNumber(mobileNumber);
        loans.setLoanType("House Loan");
        loans.setTotalLoan(100);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(100);
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loans.setLoanNumber(String.valueOf(randomLoanNumber));
        loansRepository.save(loans);
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans dbLoanInfo = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new RuntimeException("Loan not exists with given Mobile number: "+mobileNumber));
        LoansDto loansDto = new LoansDto();
        BeanUtils.copyProperties(dbLoanInfo,loansDto);
        return loansDto;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans dbLoanInfo = loansRepository.findByLoanNumber(loansDto.getLoanNumber())
                .orElseThrow(()->new RuntimeException("Loan number not exits in Db "+loansDto.getLoanNumber()));
        BeanUtils.copyProperties(loansDto,dbLoanInfo);
        loansRepository.save(dbLoanInfo);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans dbLoanInfo = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new RuntimeException("Loan not exists with given Mobile number: "+mobileNumber));
        loansRepository.deleteById(dbLoanInfo.getLoanId());
        return true;
    }
}
