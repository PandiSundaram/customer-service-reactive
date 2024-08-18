package com.pandi.projects.customer_service.servicetest;

import com.pandi.projects.customer_service.domain.Ticker;
import com.pandi.projects.customer_service.dto.CustomerInfo;
import com.pandi.projects.customer_service.dto.Portfolio;
import com.pandi.projects.customer_service.entity.Customer;
import com.pandi.projects.customer_service.entity.PortfolioItem;
import com.pandi.projects.customer_service.repository.CustomerRepository;
import com.pandi.projects.customer_service.repository.PortfolioRepository;
import com.pandi.projects.customer_service.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PortfolioRepository portfolioRepository;


    @Test
    public void getCustomerInfo(){

       Customer customer = new Customer(1,"pandi",1000);
       when(customerRepository.findById(1)).thenReturn(Mono.just(customer));


        var mockCustomerService = new CustomerService(customerRepository,portfolioRepository);
        CustomerService customerServiceSpy = spy(mockCustomerService);
        doReturn(Mono.just(new CustomerInfo(1,"pandi",1000, Collections.emptyList()))).when(customerServiceSpy).ConstructPortFolio(any());

        Mono<CustomerInfo> monoResponse = customerServiceSpy.getCustomerInfo(1);


        verify(customerServiceSpy,times(1)).getCustomerInfo(1);

        StepVerifier.create(monoResponse)
                .expectNext(new CustomerInfo(1,"pandi",1000, Collections.emptyList()))
                .expectComplete()
                .verify();


    }

    @Test
    public void ConstructPortFolio(){

        PortfolioItem portfolioItem = new PortfolioItem(1,1,2,Ticker.GOOGLE);
        when(portfolioRepository.findAllByCustomerId(1)).thenReturn(Flux.just(portfolioItem));

       Mono<CustomerInfo> customerInfoMono = customerService.ConstructPortFolio(new Customer(1,"pandi",1000));

       StepVerifier.create(customerInfoMono)
               .expectNext(new CustomerInfo(1,"pandi",1000, Arrays.asList(new Portfolio(Ticker.GOOGLE,2))))
               .expectComplete()
               .verify();

    }



}















