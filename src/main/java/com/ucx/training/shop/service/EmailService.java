package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;

public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    CostumerService costumerService;
    InvoiceService invoiceService;

    EmailService(CostumerService costumerService, InvoiceService invoiceService)
    {
        this.costumerService = costumerService;
        this.invoiceService = invoiceService;
    }

    public void sendMail(Integer costumerId, Integer invoiceId) throws MessagingException, IOException {

        Costumer costumer = costumerService.findById(costumerId);
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(costumer.getEmail());
        helper.setSubject("Invoice for your purchase!");
        helper.setText("<h>Here is your invoice....!</h1>", true);

        File file = new File("Invoice.txt");

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            String contents = invoiceService.print(invoiceId).toString();
            writer.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }

        helper.addAttachment("Invoice.txt", file);

        javaMailSender.send(msg);
    }
}
