package com.learning.loans.controller;

import com.learning.loans.dto.LoansDto;
import com.learning.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
@AllArgsConstructor
@Slf4j
public class LoansController {

    ILoanService loansService;

    @PostMapping
    public ResponseEntity<String> createNewLoan(@RequestParam String mobileNumber){
        loansService.createLoan(mobileNumber);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<LoansDto> getLoan(@RequestParam String mobileNumber){
        return new ResponseEntity<>(loansService.fetchLoan(mobileNumber), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateLoan(@RequestBody LoansDto loans){
        return new ResponseEntity<>(loansService.updateLoan(loans), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteLoan(@RequestParam String mobileNumber){
        return new ResponseEntity<>( loansService.deleteLoan(mobileNumber), HttpStatus.OK);
    }

}
