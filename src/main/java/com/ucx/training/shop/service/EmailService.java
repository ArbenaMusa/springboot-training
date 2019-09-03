package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.util.LicenseUtil;
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
    private final String LINK_ACTIVATE = "https://store.steampowered.com/account/registerkey?key=XXXXX-XXXXX-XXXXX";

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Customer customer, Order order) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(customer.getEmail());
        helper.setSubject("Invoice for your purchase!");
        helper.setText("Thank you for your purchase! Below you can find the order receipt and the activation link: " + LINK_ACTIVATE);
        helper.addAttachment("Invoice.txt",createFile(customer, order));
        send(msg);
    }

    public File createFile(Customer customer, Order order) throws IOException {
        List<CartItem> list = order.getCart();
        File file = new File("Invoice.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.write("Customer name: " + customer.getName() +
                    "\n------------------------------------------------");
            list.forEach(e ->
                    writer.write("\nProduct name: " + e.getProduct().getName() +
                            "\nProduct price: " + e.getProduct().getUnitPrice() + " €" +
                            "  x" + e.getQuantity().toString() +
                            "\nPrice: " + (e.getQuantity().intValue()* e.getProduct().getUnitPrice().intValue()) + " €"  +
                            "\nLicense Codes: " + LicenseUtil.generateLicence(e.getQuantity()) + " " ));
            writer.write("\n------------------------------------------------" +
                    "\nPurchase date: " + order.getCreateDateTime() +
                    "\n------------------------------------------------" +
                    "\nTotal: " + order.getTotal() + " €");
        } catch (IOException e) {
            throw e;
        }
        return file;
    }

    public void send(MimeMessage msg){
        javaMailSender.send(msg);
    }
}
