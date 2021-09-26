package pl.sda.customers.demo;

import org.springframework.stereotype.Component;

@Component
public class InvoiceService {

    public void createInvoice(String customerName, String invoiceNumber) {
        System.out.println("preparing invoice (number: " + invoiceNumber + ")" + " for customer: " + customerName);
    }
}
