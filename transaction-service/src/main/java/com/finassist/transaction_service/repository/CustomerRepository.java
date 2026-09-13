package com.finassist.transaction_service.repository;

import com.finassist.transaction_service.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByCustomerCode(@NotBlank(message = "Customer Code is Required") @Size(max = 50, message = "Customer code must not exceed 50 characters") String customerCode);

    boolean existsByEmail(@NotBlank(message = "Email is Required") @Email(message = "Invalid Email Format") String email);

}
