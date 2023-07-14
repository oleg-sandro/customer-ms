package com.besttradebank.investment.customer.service.impl;

import static com.besttradebank.investment.customer.util.Constants.ACTIVATION_EMAIL_BODY;
import static com.besttradebank.investment.customer.util.Constants.ACTIVATION_EMAIL_SUBJECT;
import static com.besttradebank.investment.customer.util.Constants.AUTOMATIC_EMAIL;
import static com.besttradebank.investment.customer.util.Constants.CUSTOMER_EMAIL_EXISTS;
import static com.besttradebank.investment.customer.util.Constants.CUSTOMER_USERNAME_EXISTS;
import static com.besttradebank.investment.customer.util.Constants.DIFFERENT_CUSTOMERS;
import static java.lang.String.format;

import com.besttradebank.investment.customer.dto.request.SignUpCustomerRequest;
import com.besttradebank.investment.customer.dto.response.SignUpCustomerResponse;
import com.besttradebank.investment.customer.entity.Activation;
import com.besttradebank.investment.customer.entity.Customer;
import com.besttradebank.investment.customer.enums.ActivationStatusType;
import com.besttradebank.investment.customer.enums.CustomerStatusType;
import com.besttradebank.investment.customer.exception.CustomerException;
import com.besttradebank.investment.customer.mapper.CustomerMapper;
import com.besttradebank.investment.customer.messaging.command.CustomerCommand;
import com.besttradebank.investment.customer.messaging.command.SendEmailWithActivationLinkCommand;
import com.besttradebank.investment.customer.messaging.payload.SendEmailWithActivationLinkPayload;
import com.besttradebank.investment.customer.repository.ActivationRepository;
import com.besttradebank.investment.customer.repository.CustomerRepository;
import com.besttradebank.investment.customer.service.CustomerService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper;

    private final CustomerRepository customerRepository;
    private final ActivationRepository activationRepository;

    private final KafkaTemplate<String, CustomerCommand<?>> kafkaTemplate;

    @Value("${application.activation.url}")
    private String activationUrl;

    @Override
    @Transactional
    public SignUpCustomerResponse signUp(SignUpCustomerRequest request) {
        Optional<Customer> customerByUsername = customerRepository.findByUsername(request.getUsername());
        if (customerByUsername.isPresent() && customerByUsername.get().getStatus() == CustomerStatusType.ACTIVATED) {
            throw new CustomerException(format(CUSTOMER_USERNAME_EXISTS, request.getUsername()));
        }

        Optional<Customer> customerByEmail = customerRepository.findByEmail(request.getEmail());
        if (customerByEmail.isPresent() && customerByEmail.get().getStatus() == CustomerStatusType.ACTIVATED) {
            throw new CustomerException(format(CUSTOMER_EMAIL_EXISTS, request.getEmail()));
        }

        Customer customer;
        Activation activation;
        if (customerByUsername.isPresent() && customerByEmail.isEmpty()) {
            customer = customerByUsername.get();
            customer.setEmail(request.getEmail());
            customer.setPassword(request.getPassword());
            activationRepository.findAllByCustomerAndStatus(customer, ActivationStatusType.VALID)
                    .forEach(validActivation -> validActivation.setStatus(ActivationStatusType.INVALID));
        } else if (customerByEmail.isPresent() && customerByUsername.isEmpty()) {
            customer = customerByEmail.get();
            customer.setUsername(request.getUsername());
            customer.setPassword(request.getPassword());
            activationRepository.findAllByCustomerAndStatus(customer, ActivationStatusType.VALID)
                    .forEach(validActivation -> validActivation.setStatus(ActivationStatusType.INVALID));
        } else if (customerByUsername.isPresent()) {
            customer = customerByUsername.get();
            Customer anotherCustomer = customerByEmail.get();
            if (customer.equals(anotherCustomer)) {
                customer.setPassword(request.getPassword());
                activationRepository.findAllByCustomerAndStatus(customer, ActivationStatusType.VALID)
                        .forEach(validActivation -> validActivation.setStatus(ActivationStatusType.INVALID));
            } else {
                throw new CustomerException(format(DIFFERENT_CUSTOMERS, request.getUsername(), request.getEmail()));
            }
        } else {
            customer = mapper.fromSignUpCustomerRequest(request);
            customerRepository.save(customer);
        }

        activation = buildActivation(customer);
        activationRepository.save(activation);

        String emailBody = format(ACTIVATION_EMAIL_BODY, activationUrl, activation.getId());
        SendEmailWithActivationLinkPayload payload = new SendEmailWithActivationLinkPayload(
                customer.getEmail(), AUTOMATIC_EMAIL, ACTIVATION_EMAIL_SUBJECT, emailBody);
        SendEmailWithActivationLinkCommand command = new SendEmailWithActivationLinkCommand(payload);
        kafkaTemplate.send(command.getTopic(), command);

        return mapper.toSignUpCustomerResponse(customer);
    }

    private Activation buildActivation(Customer customer) {
        return Activation.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .status(ActivationStatusType.VALID)
                .expiredAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .build();
    }
}
