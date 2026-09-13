package com.finassist.transaction_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotBlank(message = "Customer Code is Required")
    @Size(max = 50, message = "Customer code must not exceed 50 characters")
    private String customerCode;
    @NotBlank(message = "Full Name is Required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Format")
    private String email;
    @NotBlank(message = "Account Type is required")
    private String accountType;
}
