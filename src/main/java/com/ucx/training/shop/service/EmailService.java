package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import com.ucx.training.shop.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.List;

@Log4j2
@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private CostumerService costumerService;
    private InvoiceService invoiceService;

    EmailService(CostumerService costumerService, InvoiceService invoiceService, JavaMailSender javaMailSender) {
        this.costumerService = costumerService;
        this.invoiceService = invoiceService;
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Costumer costumer, Invoice invoice) throws MessagingException, IOException {

        List<LineItem> list = invoice.getLineItemList();
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(costumer.getEmail());
        helper.setSubject("Invoice for your purchase!");
        helper.setText("Here is your invoice....!");

        File file = new File("Invoice.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

            writer.write("Customer name: " + costumer.getName() +
                            "\n------------------------------------------------");
            list.forEach(e ->
                writer.write("\nProduct name: " + e.getProduct().getName() +
                                "\nProduct price: " + e.getProduct().getUnitPrice() + " €" +
                                "  x" + e.getQuantity().toString() +
                                "\nPrice: " + (e.getQuantity().intValue()* e.getProduct().getUnitPrice().intValue()) + " €" ));
            writer.write("\n------------------------------------------------" +
                            "\nPurchase date: " + invoice.getCreateDateTime() +
                            "\n------------------------------------------------" +
                            "\nTotal: " + invoice.getTotal() + " €");
        } catch (IOException e) {
            throw e;
        }

        helper.addAttachment("Invoice.txt", file);

        javaMailSender.send(msg);
    }
}
