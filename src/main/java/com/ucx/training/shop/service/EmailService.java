package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.entity.Order;
import com.ucx.training.shop.entity.CartItem;
import com.ucx.training.shop.entity.Product;
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
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
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
            StringBuilder message = new StringBuilder();
            final String seperator = "\n------------------------------------------------\n";
            message.append(String.format("Customer name: %s", customer.getName()));
            message.append(seperator);
            list.forEach(e -> message.append(getValues(e)));
            message.append(seperator);
            message.append(String.format("Purchase date: %s", order.getCreateDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
            message.append(seperator);
            message.append(String.format("Total: %.2f €", order.getTotal()));
            writer.write(message.toString());
        } catch (IOException e) {
            throw e;
        }
        return file;
    }

    private String getValues(CartItem e) {
        final Product cartProduct = e.getProduct();
        final Integer productQuantity = e.getQuantity();
        final BigDecimal unitPrice = cartProduct.getUnitPrice();
        return String.format("%nProduct name: %s %nProduct price: %.2f € x %d %nProduct total: %.2f€ %nLicense Codes: %s%n",
                cartProduct.getName(),
                unitPrice,
                productQuantity,
                unitPrice.multiply(BigDecimal.valueOf(productQuantity)),
                LicenseUtil.generateLicence(productQuantity));
    }

    public void send(MimeMessage msg){
        javaMailSender.send(msg);
    }
}
