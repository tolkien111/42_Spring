package pl.sda.examplewithdependencyinjection;

import org.springframework.stereotype.Component;

@Component
public class BeanA {

public BeanB beanB;

    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }

    public void beanA(BeanB beanB){
        System.out.println("Injection B to A" + beanB);
    }
}
