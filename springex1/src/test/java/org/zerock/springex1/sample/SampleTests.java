package org.zerock.springex1.sample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SampleTests {

    @Autowired
    private SampleService sampleService;

    @Test
    public void testService1(){
        Assertions.assertNotNull(sampleService);
    }
}
