package com.besttradebank.investment.customer.service;

import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_EMAIL;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_ID;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_PASSWORD;
import static com.besttradebank.investment.customer.util.TestConstants.CUSTOMER_USERNAME;
import static com.besttradebank.investment.customer.util.TestHelper.buildActivation;
import static com.besttradebank.investment.customer.util.TestHelper.buildCustomer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.ActivateCustomerAccountResponse;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.entity.Activation;
import com.besttradebank.investment.customer.entity.Customer;
import com.besttradebank.investment.customer.mapper.CustomerMapper;
import com.besttradebank.investment.customer.messaging.command.CustomerCommand;
import com.besttradebank.investment.customer.repository.ActivationRepository;
import com.besttradebank.investment.customer.repository.CustomerRepository;
import com.besttradebank.investment.customer.service.impl.CustomerServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerMapper mapper;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ActivationRepository activationRepository;

    @Mock
    private KafkaTemplate<String, CustomerCommand<?>> kafkaTemplate;

    @Test
    void givenSignUp_whenSignUpAndUsernameAndEmailWereNotUsedBefore_thenSuccess() {
        // given
        when(customerRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(mapper.fromSignUpCustomerRequest(any(SignUpCustomerRequest.class))).thenReturn(buildCustomer());
        Activation activation = buildActivation();
        when(customerRepository.save(any(Customer.class))).thenReturn(activation.getCustomer());
        when(activationRepository.save(any(Activation.class))).thenReturn(activation);
        when(kafkaTemplate.send(anyString(), any(CustomerCommand.class))).thenReturn(null);
        SignUpCustomerResponse response = new SignUpCustomerResponse(CUSTOMER_ID, CUSTOMER_USERNAME, CUSTOMER_EMAIL);
        when(mapper.toSignUpCustomerResponse(any(Customer.class))).thenReturn(response);
        SignUpCustomerRequest request = new SignUpCustomerRequest(CUSTOMER_USERNAME, CUSTOMER_EMAIL, CUSTOMER_PASSWORD);

        // when
        service.signUp(request);

        // then
        verify(customerRepository, times(1)).findByUsername(anyString());
        verify(customerRepository, times(1)).findByEmail(anyString());
        verify(mapper, times(1)).fromSignUpCustomerRequest(any(SignUpCustomerRequest.class));
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(activationRepository, times(1)).save(any(Activation.class));
        verify(kafkaTemplate, times(1)).send(anyString(), any(CustomerCommand.class));
        verify(mapper, times(1)).toSignUpCustomerResponse(any(Customer.class));
    }

    @Test
    void givenActivate_whenInvoke_thenSuccess() {
        // given
        Activation activation = buildActivation();
        when(activationRepository.findById(any(UUID.class))).thenReturn(Optional.of(activation));
        ActivateCustomerAccountResponse response =
                new ActivateCustomerAccountResponse(CUSTOMER_ID, CUSTOMER_USERNAME, CUSTOMER_EMAIL);
        when(mapper.toActivateCustomerAccountResponse(any(Customer.class))).thenReturn(response);

        // when
        service.activate(UUID.randomUUID());

        // then
        verify(activationRepository, times(1)).findById(any(UUID.class));
        verify(mapper, times(1)).toActivateCustomerAccountResponse(any(Customer.class));
    }
}