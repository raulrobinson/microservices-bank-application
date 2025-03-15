package com.devsu.hackerearth.backend.account;

import com.devsu.hackerearth.backend.account.infrastructure.persistence.entity.AccountEntity;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.enumeration.AccountType;
import com.devsu.hackerearth.backend.account.infrastructure.persistence.SpringDataAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class integrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SpringDataAccountRepository springDataAccountRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		springDataAccountRepository.deleteAll();

		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setId(1L);
		accountEntity.setInitialAmount(BigDecimal.valueOf(1000.0));
		accountEntity.setNumber("1234567890");
		accountEntity.setType(AccountType.CREDIT);
		accountEntity.setClientCode("CLI-1712345678901");
		accountEntity.setActive(true);
		springDataAccountRepository.save(accountEntity);
	}

	@Test
	void testGetAllAccounts() throws Exception {
		String response = mockMvc.perform(get("/api/accounts")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		var accounts = objectMapper.readValue(response, List.class);

		assertThat(accounts).isNotEmpty();
	}

}

