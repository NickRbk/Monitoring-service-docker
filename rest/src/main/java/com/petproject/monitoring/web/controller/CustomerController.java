package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.Customer;
import com.petproject.monitoring.service.IAuthService;
import com.petproject.monitoring.service.ICustomerService;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.web.dto.request.CustomerReqDTO;
import com.petproject.monitoring.web.dto.response.CustomerResDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.petproject.monitoring.security.constants.SecurityConstants.HEADER_STRING;

@Validated
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class CustomerController {
    private ICustomerService customerService;
    private IEntityAdapterService entityAdapterService;
    private IAuthService authService;

    @GetMapping()
    public CustomerResDTO getCustomer(@RequestHeader(HEADER_STRING) String token) {
        Customer customer = customerService.getById(authService.getIdFromToken(token));
        return entityAdapterService.getCustomerResDTOFromEntity(customer);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping()
    public void updateCustomer(@RequestBody @NotNull @Valid CustomerReqDTO customerReqDTO,
                               @RequestHeader(HEADER_STRING) String token) {
        customerService.update(authService.getIdFromToken(token), customerReqDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping()
    public void deleteCustomer(@RequestHeader(HEADER_STRING) String token) {
        customerService.delete(authService.getIdFromToken(token));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @NotNull @Valid CustomerReqDTO customerReqDTO) {
        customerService.signUp(customerReqDTO);
    }
}
