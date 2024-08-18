package com.pandi.projects.customer_service.service;

import com.pandi.projects.customer_service.dto.TradeRequest;
import com.pandi.projects.customer_service.dto.TradeResponse;
import com.pandi.projects.customer_service.entity.Customer;
import com.pandi.projects.customer_service.entity.PortfolioItem;
import com.pandi.projects.customer_service.exceptions.CustomerNotFoundException;
import com.pandi.projects.customer_service.exceptions.InSufficientBalanceException;
import com.pandi.projects.customer_service.exceptions.StocksNotAvailable;
import com.pandi.projects.customer_service.mapper.EntityToCustomerInfo;
import com.pandi.projects.customer_service.repository.CustomerRepository;
import com.pandi.projects.customer_service.repository.PortfolioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class TradeService {

    private final CustomerRepository customerRepository;
    private final PortfolioRepository portfolioRepository;



    public Mono<TradeResponse> processTrade(TradeRequest tradeRequest) {


        return switch (tradeRequest.type()) {
            case BUY -> buyStock(tradeRequest);

            case SELL -> sellStock(tradeRequest);

        };

    }

    public Mono<TradeResponse> sellStock(TradeRequest tradeRequest) {
        var customerMono = this.customerRepository.findById(tradeRequest.customerId())
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(tradeRequest.customerId())));

        log.info("customerMono"+ customerMono);

        var portfolioMono = this.portfolioRepository.findBycustomerIdAndTicker(tradeRequest.customerId(), tradeRequest.ticker())
                .filter(portfolioItem -> portfolioItem.getQuantity() >= tradeRequest.quantity())
                .switchIfEmpty(Mono.error(new StocksNotAvailable()));


        return Mono.zip(customerMono, portfolioMono).flatMap((tuple) -> processSell(tuple.getT1(), tuple.getT2(), tradeRequest));
    }

    public Mono<TradeResponse> buyStock(TradeRequest tradeRequest) {

        var customerMono = this.customerRepository.findById(tradeRequest.customerId())
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(tradeRequest.customerId())))
                .filter(customer -> customer.getBalance() >= tradeRequest.quantity() * tradeRequest.price())
                .switchIfEmpty(Mono.error(new InSufficientBalanceException(tradeRequest.customerId())));


        var portfolioItemMono = this.portfolioRepository.findBycustomerIdAndTicker(tradeRequest.customerId(), tradeRequest.ticker())
                .switchIfEmpty(Mono.just(new PortfolioItem(null, tradeRequest.customerId(), tradeRequest.quantity(), tradeRequest.ticker())));
        return customerMono.zipWith(portfolioItemMono).flatMap((tuple) -> processBuy(tuple.getT1(), tuple.getT2(), tradeRequest));
    }

    public Mono<TradeResponse> processBuy(Customer t1, PortfolioItem t2, TradeRequest request) {

        t1.setBalance(t1.getBalance() - request.price()*request.quantity());
        t2.setQuantity(t2.getQuantity() + request.quantity());
        return this.customerRepository.save(t1).zipWith(this.portfolioRepository.save(t2))
                .thenReturn(EntityToCustomerInfo.CovertToTradeResponse(t1, t2, request));

    }

    public Mono<TradeResponse> processSell(Customer t1, PortfolioItem t2, TradeRequest request) {

        t1.setBalance(t1.getBalance() + request.price()*request.quantity());
        t2.setQuantity(t2.getQuantity() - request.quantity());
        return this.customerRepository.save(t1).zipWith(this.portfolioRepository.save(t2))
                .thenReturn(EntityToCustomerInfo.CovertToTradeResponse(t1, t2, request));

    }


}