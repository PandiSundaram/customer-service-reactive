package com.pandi.projects.customer_service.controllertest;

import com.pandi.projects.customer_service.dto.CustomerInfo;
import com.pandi.projects.customer_service.exceptions.CustomerNotFoundException;
import com.pandi.projects.customer_service.service.CustomerService;
import com.pandi.projects.customer_service.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
public class CustomerControllerTest {


    @Autowired
    WebTestClient webTestClient;

    @MockBean
    CustomerService customerService;



    @Test
    public void getCustomerInfo(){

        //assert
        CustomerInfo customerInfo = new CustomerInfo(1,"pandi",1000, Collections.emptyList());

        //arrange
        when(customerService.getCustomerInfo(any())).thenReturn(Mono.just(customerInfo));

        //verify
        this.webTestClient.get()
                .uri("/customer/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerInfo.class)
                .value(d -> Assertions.assertEquals("pandi",d.name()));
    }

    @Test
    public void getCustomerInfoNotFound(){


        //arrange
        when(customerService.getCustomerInfo(any())).thenReturn(Mono.error(new CustomerNotFoundException(2)));

        //verify
        this.webTestClient.get()
                .uri("/customer/2")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .value(d -> log.info("response"+ d))
                .value(d -> Assertions.assertEquals("CUSTOMER_NOT_FOUND",d.getTitle()));

    }

}
