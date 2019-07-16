package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.service.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("mail")
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    CostumerService costumerService;

    @PutMapping("{id}")
    public void sendMail(@PathVariable Integer id) throws MessagingException, IOException {

        Costumer costumer = costumerService.findById(id);

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(costumer.getEmail());
        helper.setSubject("Invoice for your purchase!");
        helper.setText("<h1>Here is your invoice....!</h1>", true);

        FileSystemResource file
                = new FileSystemResource(new File("C:\\Users\\Dell\\Desktop\\dummy.pdf"));


        helper.addAttachment("dummy.pdf", file);

        javaMailSender.send(msg);
    }
}
