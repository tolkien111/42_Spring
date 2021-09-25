package pl.sda.customers.entity;

import lombok.Value;


@Value // generuje konstruktor z danymi polami + gettery ale bez setterów
public class CompanyZipCodeView {

    String name;
    String vat;
    String zipCode;


}
