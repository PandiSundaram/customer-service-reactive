package com.pandi.projects.customer_service.domaintest;

import com.pandi.projects.customer_service.domain.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class TickerTest {


    @Test
    public void ticker(){

      Ticker ticker = Ticker.valueOf("AMAZON");
      Assertions.assertEquals(Ticker.AMAZON,ticker);

    }


    @Test
    public void tickerInvalid(){

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,()->{
            Ticker.valueOf("amazon");
        });
        Assertions.assertEquals(exception.getMessage(),"No enum constant com.pandi.projects.customer_service.domain.Ticker.amazon");

    }


}
