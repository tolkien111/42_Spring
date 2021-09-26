package pl.sda.customers.demo;


import org.springframework.stereotype.Component;

@Component
public class CustomerRegistration {

//    @Autowired -raczej używany w testach, nie zalecane/rekomendowane w kodzie, w klasach tworzymy konstruktor
    private final CustomerDatabase repository;


    //    @Autowired - nie jest wymagane, dodatkowo nie może być kilku konstruktorów
    //    bo Spring sobie nie poradzi, chyba że mamy adnotacje @Autowired przy jednym z nich
    public CustomerRegistration(CustomerDatabase repository) {
        this.repository = repository;
    }

    public void register(String email, String name) {
        System.out.println("registering customer: " + email);
        repository.save(email, name);
        System.out.println("registered customer: " + email);
    }

//    @Autowired - nie zalecane/rekomendowane w kodzie, w klasach tworzymy konstruktor (ten wykomentowany)
//    public void setRepository(CustomerRepository repository) {
//        this.repository = repository;
//    }

}
