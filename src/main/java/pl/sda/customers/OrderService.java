package pl.sda.customers;

import org.springframework.stereotype.Component;


@Component
public class OrderService {

   private final OrderRepository orderRepository;
   private final InvoiceService invoiceService;

    public OrderService(OrderRepository orderRepository, InvoiceService invoiceService) {
        this.orderRepository = orderRepository;
        this.invoiceService = invoiceService;
    }

    public void makeOrder(String customerName, String invoiceNumber) {
        System.out.println("making order...");
        invoiceService.createInvoice(customerName, invoiceNumber);
        orderRepository.save(customerName, invoiceNumber);
        System.out.println("order created");

    }
}
