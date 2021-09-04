package pl.sda.exampleWithDependencyInjection;

import org.springframework.stereotype.Component;

@Component
public class BeanB {


    public BeanC beanC;

    public BeanB(BeanC beanC) {
        this.beanC = beanC;
    }

    public void beanB(BeanC beanC) {
        System.out.println("Injection to C to B");
    }
}
