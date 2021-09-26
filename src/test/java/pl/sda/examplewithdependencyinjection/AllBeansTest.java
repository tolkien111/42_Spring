package pl.sda.examplewithdependencyinjection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AllBeansTest {

    @Autowired

    public AllBeans allBeans;

    @Test
    public void testBean(){
        allBeans.print();
    }

}