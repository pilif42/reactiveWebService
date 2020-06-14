package com.sample.service;

import com.sample.db.entity.Account;
import com.sample.dto.AccountDto;
import com.sample.dto.InternalCaseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ExternalApiServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalApiServiceImpl.class);

    private final RetryTemplate apiRetryTemplate;
    private final RestTemplate externalServiceRestTemplate;

    public ExternalApiServiceImpl(RetryTemplate apiRetryTemplate, RestTemplate externalServiceRestTemplate) {
        this.apiRetryTemplate = apiRetryTemplate;
        this.externalServiceRestTemplate = externalServiceRestTemplate;
    }

    public CompletableFuture<InternalCaseDto> createInternalIssue(Account account) {
        LOGGER.debug("Entering createInternalIssue with account {}", account);
        final AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setEmail(account.getEmail());
        accountDto.setPassword(account.getPassword());
        accountDto.setRole(account.getRole());
        final HttpEntity<AccountDto> request = new HttpEntity<>(accountDto);

        final InternalCaseDto internalCaseDto = apiRetryTemplate.execute(context ->
                externalServiceRestTemplate.postForObject("/somePath", request, InternalCaseDto.class));

        return CompletableFuture.completedFuture(internalCaseDto);
    }
}
