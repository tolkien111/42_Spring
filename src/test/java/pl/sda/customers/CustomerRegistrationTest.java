package pl.sda.customers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerRegistrationTest {

    @Autowired //oznacza że string wstrzyknie nam bean CustomerRegistration, nie musimy inicjalizować objektu w springu
    private CustomerRegistration customerRegistration;

    @Test
    void testRegisterCustomer(){
        customerRegistration.register("jan@wp.pl", "Jan Kowalski");

    }

}