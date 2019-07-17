package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;

@Service
public class EmailService{

    JavaMailSender javaMailSender;
    CostumerService costumerService;
    InvoiceService invoiceService;

    EmailService(CostumerService costumerService, InvoiceService invoiceService, JavaMailSender javaMailSender)
    {
        this.costumerService = costumerService;
        this.invoiceService = invoiceService;
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Costumer costumer, Invoice invoice) throws MessagingException{

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(costumer.getEmail());
        helper.setSubject("Invoice for your purchase!");
        helper.setText("<h>Here is your invoice....!</h1>", true);

        File file = new File("Invoice.txt");

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            String contents = invoice.toString();
            writer.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }

        helper.addAttachment("Invoice.txt", file);

        javaMailSender.send(msg);
    }
}
