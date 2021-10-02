package pl.sda.customers.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.entity.Person;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisterPersonForm;
import pl.sda.customers.service.dto.RegisteredCustomerId;
import pl.sda.customers.service.exception.EmailAlreadyExistException;
import pl.sda.customers.service.exception.PeselAlreadyExistException;
import pl.sda.customers.service.exception.VatAlreadyExistsException;

import javax.transaction.Transactional;

@Service // usługa/serwis, mówi nam czym ta klasa jest
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    @NonNull
    private final CustomerRepository repository;

//    @RequiredArgsConstructor + @NonNull przed polem są równoznaczne z zakomentowanym konstruktorem
//    public CustomerService(@NonNull CustomerRepository repository) {
//        this.repository = repository;
//    }


    //ZOSTALO założone że nieważne czy email się powtarza w Company czy Person, sprawdza maile z obu zbiorów
    public RegisteredCustomerId registerCompany (@NonNull RegisterCompanyForm form) {
        if (repository.emailExists(form.getEmail())) {
            throw new EmailAlreadyExistException("email exists: " + form.getEmail());
        }
        if (repository.vatExists(form.getVat())) {
            throw new VatAlreadyExistsException("vat exists: " + form.getVat());
        }
        final var company = new Company(form.getEmail(), form.getName(), form.getVat());
        repository.save(company);
        return new RegisteredCustomerId(company.getId());
    }

    public RegisteredCustomerId registerPerson (@NonNull RegisterPersonForm form){
        if (repository.emailExists(form.getEmail())) {
            throw new EmailAlreadyExistException("email exists: " + form.getEmail());
        }
        if (repository.peselExist(form.getPesel())){
            throw new PeselAlreadyExistException("pesel exists: " + form.getPesel());
        }
        final var person = new Person(form.getEmail(),
                form.getFirstName(),
                form.getLastName(),
                form.getPesel());
        repository.save(person);
        return new RegisteredCustomerId(person.getId());
    }

}
