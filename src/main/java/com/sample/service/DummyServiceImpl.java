package com.sample.service;

import com.sample.dto.AccountDto;
import com.sample.dto.InternalCaseDto;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public class DummyServiceImpl {
    public InternalCaseDto postForObject(String urlPath, HttpEntity<AccountDto> request, Class resultingClass) {
        return null;
    }
}
