package com.robinsonir.fittrack.api;

import com.robinsonir.fittrack.data.repository.customer.Customer;
import com.robinsonir.fittrack.data.service.customer.CustomerRegistrationRequest;
import com.robinsonir.fittrack.data.service.customer.CustomerService;
import com.robinsonir.fittrack.data.service.customer.CustomerUpdateRequest;
import com.robinsonir.fittrack.security.jwt.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final JwtTokenUtil jwtUtil;

    public CustomerController(CustomerService customerService,
                              JwtTokenUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
        String jwtToken = jwtUtil.generateToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }


    @PutMapping("update/{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId,
            @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId, updateRequest);
    }

    @PutMapping(
            value = "{customerId}/profile-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadCustomerProfileImage(@PathVariable("customerId") Long customerId,
            @RequestParam("file") MultipartFile file) {
        customerService.uploadCustomerProfilePicture(customerId, file);
    }

    @GetMapping(
            value = "{customerId}/profile-image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getCustomerProfileImage(@PathVariable("customerId") Long customerId) {
        return customerService.getProfilePicture(customerId);
    }


}
