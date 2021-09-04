package pl.sda.exampleWithDependencyInjection;

import org.springframework.stereotype.Component;
// nie możemy robić pętli zależności z poszczególnych beanów
@Component
public class AllBeans {

    BeanA beanA;
    BeanB beanB;
    BeanC beanC;

    public AllBeans(BeanA beanA, BeanB beanB, BeanC beanC) {
        this.beanA = beanA;
        this.beanB = beanB;
        this.beanC = beanC;
    }

    void print(){
        beanA.beanA(beanB);
        beanB.beanB(beanC);
        beanC.beanC(beanA);
    }
}
