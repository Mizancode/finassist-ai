package com.finassist.transaction_service.customer;

import com.finassist.transaction_service.dto.CustomerResponse;
import com.finassist.transaction_service.entity.Customer;
import com.finassist.transaction_service.repository.CustomerRepository;
import com.finassist.transaction_service.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void checkIfCustomerIsExistByCustomerID(){
        Customer customer=new Customer();
        customer.setId(1L);
        customer.setFullName("Rahul Sharma");
        customer.setEmail("rahul.sharma@example.com");
        customer.setAccountType("PREMIUM");
        customer.setCustomerCode("CUST-1001");
        customer.setCreatedAt(LocalDateTime.now());
        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));
        CustomerResponse result=customerService.getCustomerById(1L);
        assertEquals("CUST-1001", result.getCustomerCode());

        // Verify repository was actually called
        Mockito.verify(customerRepository).findById(1L);
    }
}
