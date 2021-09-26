package pl.sda.examplewithdependencyinjection;

import org.springframework.stereotype.Component;

@Component
public class BeanC {

    BeanA beanA;

    public BeanC(BeanA beanA) {
        this.beanA = beanA;
    }

    public void beanC(BeanA beanA){
        System.out.println("Injection A to C" + beanA);
    }
}
