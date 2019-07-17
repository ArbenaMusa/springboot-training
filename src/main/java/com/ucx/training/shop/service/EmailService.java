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

    public void sendMail(Costumer costumer, Invoice invoice) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(costumer.getEmail());
        helper.setSubject("Invoice for your purchase!");
        helper.setText("<h>Here is your invoice....!</h1>", true);

        File file = new File("Invoice.txt");

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            //String[] contents = {invoice.getDescription(), invoice.getInvoiceNumber().toString(),
            //        invoice.getLineItemList().toString(), invoice.getCostumer().toString()};
            String contents = invoice.toString();
            //for (int i = 0;i<contents.length;i++){
            //writer.write(contents[i]);
            //}
            List<LineItem> list = invoice.getLineItemList();
            Product product = list.get(0).getProduct();

            writer.write("Customer name: " + costumer.getName() +
                    "\nCustomer email: " + costumer.getEmail() +
                    "\nProduct name: " + product.getName() +
                    "\nQuantity: " + list.get(0).getQuantity() +
                    "\nPurchase date: " + invoice.getCreateDateTime() +
                    "\n-------------------------------" +
                    "\nTotal: " + invoice.getTotal());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        helper.addAttachment("Invoice.txt", file);

        javaMailSender.send(msg);
    }
}
