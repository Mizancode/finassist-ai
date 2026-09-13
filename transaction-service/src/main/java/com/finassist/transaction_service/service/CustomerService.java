package com.finassist.transaction_service.service;

import com.finassist.transaction_service.dto.CustomerRequest;
import com.finassist.transaction_service.dto.CustomerResponse;
import com.finassist.transaction_service.entity.Customer;
import com.finassist.transaction_service.exception.DuplicateResourceException;
import com.finassist.transaction_service.exception.ResourceNotFoundException;
import com.finassist.transaction_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        if(customerRepository.existsByCustomerCode(customerRequest.getCustomerCode())){
            throw new DuplicateResourceException("Customer Code already exists: "+customerRequest.getCustomerCode());
        }

        if(customerRepository.existsByEmail(customerRequest.getEmail())){
            throw new DuplicateResourceException("Customer email already exists: "+customerRequest.getEmail());
        }

        Customer customer=new Customer();
        customer.setCustomerCode(customerRequest.getCustomerCode());
        customer.setAccountType(customerRequest.getAccountType());
        customer.setEmail(customerRequest.getEmail());
        customer.setFullName(customerRequest.getFullName());
        Customer savedCustomer=customerRepository.save(customer);
        return mapToResponse(savedCustomer);
    }

    private CustomerResponse mapToResponse(Customer savedCustomer) {
        CustomerResponse customerResponse=new CustomerResponse();
        customerResponse.setId(savedCustomer.getId());
        customerResponse.setAccountType(savedCustomer.getAccountType());
        customerResponse.setCreatedAt(savedCustomer.getCreatedAt());
        customerResponse.setCustomerCode(savedCustomer.getCustomerCode());
        customerResponse.setEmail(savedCustomer.getEmail());
        customerResponse.setFullName(savedCustomer.getFullName());
        return customerResponse;
    }

    public CustomerResponse getCustomerById(Long customerId) {
        Customer customer= customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer not found with ID: "+customerId));
        return mapToResponse(customer);
    }

    public Customer getCustomerEntityById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer not found with ID: "+customerId));
    }

    public boolean exists(Long id){
        return customerRepository.existsById(id);
    }
}
