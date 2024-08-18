package com.pandi.projects.customer_service.servicetest;

import com.pandi.projects.customer_service.domain.Ticker;
import com.pandi.projects.customer_service.domain.TradeType;
import com.pandi.projects.customer_service.dto.TradeRequest;
import com.pandi.projects.customer_service.dto.TradeResponse;
import com.pandi.projects.customer_service.entity.Customer;
import com.pandi.projects.customer_service.entity.PortfolioItem;
import com.pandi.projects.customer_service.exceptions.CustomerNotFoundException;
import com.pandi.projects.customer_service.repository.CustomerRepository;
import com.pandi.projects.customer_service.repository.PortfolioRepository;
import com.pandi.projects.customer_service.service.TradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TradeServiceTest {


    @Autowired
    private TradeService tradeService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PortfolioRepository portfolioRepository;

    @Test
    public void processSell() {

        when(customerRepository.save(any())).thenReturn(Mono.just(new Customer(1, "pandi", 1000)));
        when(portfolioRepository.save(any())).thenReturn(Mono.just(new PortfolioItem(1, 1, 2, Ticker.GOOGLE)));

        Mono<TradeResponse> tradeResponseMono = tradeService.processSell(new Customer(1, "pandi", 1000), new PortfolioItem(1, 1, 2, Ticker.GOOGLE), new TradeRequest(1, 2, 2, Ticker.GOOGLE, TradeType.SELL));

        StepVerifier.create(tradeResponseMono)
                .expectNext(new TradeResponse(1, Ticker.GOOGLE, 2, 2, TradeType.SELL, 4, 1004))
                .expectComplete()
                .verify();
    }


    @Test
    public void processTrade(){

        TradeService tradeServiceSpy = spy(new TradeService(customerRepository,portfolioRepository));
        doReturn(Mono.just(new TradeResponse(1,Ticker.GOOGLE,100,2,TradeType.SELL,200,1200))).when(tradeServiceSpy).sellStock(any());
        Mono<TradeResponse> tradeResponseMono = tradeServiceSpy.processTrade(new TradeRequest(1,100,2,Ticker.GOOGLE,TradeType.SELL));

        verify(tradeServiceSpy,times(1)).sellStock(any());

        StepVerifier.create(tradeResponseMono)
                .expectNext(new TradeResponse(1,Ticker.GOOGLE,100,2,TradeType.SELL,200,1200))
                .expectComplete()
                .verify();

    }

    @Test
    public void sellStockException() {

        when(customerRepository.findById(1)).thenReturn(Mono.empty());
        when(portfolioRepository.findBycustomerIdAndTicker(any(),any())).thenReturn(Mono.just(new PortfolioItem(1,1,2,Ticker.GOOGLE)));

        TradeRequest tradeRequest = new TradeRequest(1, 100, 2, Ticker.GOOGLE, TradeType.SELL);
        StepVerifier.create(tradeService
                        .sellStock(tradeRequest))
                .expectErrorMatches(throwable -> throwable instanceof CustomerNotFoundException &&
                        throwable.getMessage().equals("Customer Not Found For1"))
                .verify();


    }

}
