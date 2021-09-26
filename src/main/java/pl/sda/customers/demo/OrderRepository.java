package pl.sda.customers.demo;

import org.springframework.stereotype.Component;

@Component
public class OrderRepository {

    public void save(String customerName, String invoiceNumber) {
        System.out.println("saving invoice (number: " + invoiceNumber + ")" + " for customer: " + customerName);
    }


}
