package com.ucx.training.shop.service;

import com.ucx.training.shop.dto.ContactFormDTO;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.Role;
import com.ucx.training.shop.entity.User;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.type.RecordStatus;
import com.ucx.training.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private UserService userService;
    private CustomerService customerService;
    private EmailService emailService;
    private RoleService roleService;
    private JavaMailSender javaMailSender;


    private final String UCX_EMAIL = "ucxemailtest@gmail.com";

    public AuthenticationService(UserService userService, CustomerService customerService, EmailService emailService, RoleService roleService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.customerService = customerService;
        this.emailService = emailService;
        this.roleService = roleService;
        this.javaMailSender = javaMailSender;
    }

    //LOGIN
    public Map<String, String> login(String email, String password) throws NotFoundException {
        if (email == null || password == null) {
            throw new IllegalArgumentException("One of the arguments is null!");
        }

        User foundUser = userService.findByEmail(email);
        Customer foundCustomer = customerService.findByEmail(email);

        if (foundUser == null) {
            throw new RuntimeException("This user does not exist");
        }

        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        if (!encodedPassword.equals(foundUser.getPassword())) {
            throw new RuntimeException("The password is incorrect!");
        }

        if (foundUser.getRecordStatus() == RecordStatus.INACTIVE) {
            throw new RuntimeException("This user is deactivated");
        }

        Map<String, String> resultUser = new HashMap<>();
        resultUser.put("userId", foundUser.getId().toString());
        resultUser.put("customerId", foundCustomer.getId().toString());
        resultUser.put("accessToken", JwtUtil.getAccessToken(foundUser));
        resultUser.put("refreshToken", JwtUtil.getRefreshToken(foundUser));

        return resultUser;
    }

    //REGISTER
    public Customer save(Customer customer) {
        if (customer.getAddresses() != null && !customer.getAddresses().isEmpty()) {
            customer.getAddresses().forEach(e -> e.setCustomer(customer));
        }
        final Integer CUSTOMER_ROLE_ID = 2;
        final Role role = roleService.findById(CUSTOMER_ROLE_ID);

        if (customer.getUser() != null) {
            if (role != null) customer.getUser().setRole(role);
            if (customer.getEmail() != null) customer.getUser().setEmail(customer.getEmail());
            String password = customer.getUser().getPassword();
            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
            customer.getUser().setPassword(encodedPassword);
        }
        return customerService.save(customer);
    }

    public void sendMail(ContactFormDTO contactForm) throws MessagingException, IOException {
        if (contactForm == null) {
            throw new IllegalArgumentException("Given Contact form is null");
        }
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(UCX_EMAIL);
        helper.setSubject(String.format("Contact us - %s", contactForm.getName()));
        String message = String.format("<html><body><strong>Contact name:</strong> %s <br><strong>Contact email:</strong> %s <br><strong>Message:</strong> %s</body></html>",
                contactForm.getName(),
                contactForm.getEmail(),
                contactForm.getMessage());
        helper.setText(message, true);
        send(msg);
    }

    public void send(MimeMessage msg){
        javaMailSender.send(msg);
    }
}
