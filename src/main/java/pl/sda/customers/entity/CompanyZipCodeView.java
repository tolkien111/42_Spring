package pl.sda.customers.entity;

import lombok.Value;

//III Metoda (na wyciągnięcie czegoś) dla poprzedniego Query
@Value // generuje konstruktor z danymi polami + gettery ale bez setterów
public class CompanyZipCodeView {

    String name;
    String vat;
    String zipCode;


}
