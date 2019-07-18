package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.entity.Invoice;
import com.ucx.training.shop.entity.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@Async
public class EmailService {

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Costumer costumer, Invoice invoice) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(costumer.getEmail());
        helper.setSubject("Invoice for your purchase!");
        helper.setText("Here is your invoice....!");
        helper.addAttachment("Invoice.txt",createFile(costumer, invoice));
        send(msg);
    }

    public File createFile(Costumer costumer, Invoice invoice) throws IOException {
        List<LineItem> list = invoice.getLineItemList();
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
        return file;
    }

    public void send(MimeMessage msg){
        javaMailSender.send(msg);
    }
}
