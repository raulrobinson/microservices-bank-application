package com.devsu.hackerearth.backend.account.service;

import com.devsu.hackerearth.backend.account.exception.GlobalException;
import com.devsu.hackerearth.backend.account.exception.NoContentException;
import com.devsu.hackerearth.backend.account.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest extends TestCase {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAccountsThrowsNoContentException() {
        lenient().when(accountRepository.findAll()).thenReturn(List.of());

        try {
            accountService.getAll();
            fail("Expected NoContentException");
        } catch (NoContentException e) {
            assertEquals("No accounts found", e.getMessage());
        }
    }

    @Test
    public void testGetByIdThrowsGlobalException() {
        lenient().when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            accountService.getById(1L);
            fail("Expected GlobalException");
        } catch (GlobalException e) {
            assertEquals("Account with ID 1 not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteByIdThrowsException() {
        lenient().when(accountRepository.existsById(1L)).thenReturn(false);

        try {
            accountService.deleteById(1L);
            fail("Expected GlobalException");
        } catch (GlobalException e) {
            assertEquals("Account with ID 1 not found", e.getMessage());
        }
    }
}
