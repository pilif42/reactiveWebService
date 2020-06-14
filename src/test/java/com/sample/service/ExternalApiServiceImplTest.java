package com.sample.service;

import com.sample.db.entity.Account;
import com.sample.dto.AccountDto;
import com.sample.dto.InternalCaseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.sample.util.DataUtil.buildAccount;
import static com.sample.util.RetryUtil.MAX_ATTEMPTS;
import static com.sample.util.RetryUtil.buildApiRetryTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExternalApiServiceImplTest {
    private static final String CASE_ID = "3019ae27-cc49-4d89-ab38-75fadb6aedfa";
    private static final String POST_PATH = "/somePath";
    private static final String ERROR_MSG = "Some error occurred";

    @InjectMocks
    private ExternalApiServiceImpl externalApiService;

    @Mock
    private DummyServiceImpl dummyService;

    private RetryTemplate apiRetryTemplate;

    @BeforeEach
    public void setup() {
        apiRetryTemplate = buildApiRetryTemplate();
        ReflectionTestUtils.setField(externalApiService, "apiRetryTemplate", apiRetryTemplate);
    }

    @Test
    public void createInternalIssue_happyPath() throws InterruptedException, ExecutionException {
        // GIVEN
        final Account account = buildAccount();

        final AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setEmail(account.getEmail());
        accountDto.setPassword(account.getPassword());
        accountDto.setRole(account.getRole());
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto);

        final InternalCaseDto internalCaseDto = new InternalCaseDto();
        internalCaseDto.setCaseId(CASE_ID);
        when(dummyService.postForObject(POST_PATH, request, InternalCaseDto.class)).thenReturn(internalCaseDto);

        // WHEN
        CompletableFuture<InternalCaseDto> internalCaseDtoFuture = externalApiService.createInternalIssue(account);

        // THEN
        assertEquals(internalCaseDto, internalCaseDtoFuture.get());
        verify(dummyService, times(1)).postForObject(POST_PATH, request, InternalCaseDto.class);
    }

    @Test
    public void createInternalIssue_errorPath() {
        // GIVEN
        final Account account = buildAccount();

        final AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setEmail(account.getEmail());
        accountDto.setPassword(account.getPassword());
        accountDto.setRole(account.getRole());
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto);

        when(dummyService.postForObject(POST_PATH, request, InternalCaseDto.class)).thenThrow(new RestClientException(ERROR_MSG));

        // WHEN
        Exception exception = assertThrows(RestClientException.class, () -> externalApiService.createInternalIssue(account));
        assertEquals(ERROR_MSG, exception.getMessage());

        // THEN
        verify(dummyService, times(MAX_ATTEMPTS)).postForObject(POST_PATH, request, InternalCaseDto.class);
    }
}
