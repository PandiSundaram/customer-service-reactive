package com.pandi.projects.customer_service.controller;

import com.pandi.projects.customer_service.dto.CustomerInfo;
import com.pandi.projects.customer_service.dto.TradeRequest;
import com.pandi.projects.customer_service.dto.TradeResponse;
import com.pandi.projects.customer_service.service.CustomerService;
import com.pandi.projects.customer_service.service.TradeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final TradeService tradeService;
    public CustomerController(CustomerService customerService,TradeService tradeService){
        this.customerService = customerService;
        this.tradeService= tradeService;
    }

    @GetMapping("/{id}")
    public Mono<CustomerInfo> getCustomerInfo(@PathVariable Integer id){

             return customerService.getCustomerInfo(id);

    }

    @PostMapping("/trade")
    public Mono<TradeResponse> processTrade(@RequestBody  @Valid TradeRequest tradeRequest){
           log.info("traderequest"+ tradeRequest);
           return tradeService.processTrade(tradeRequest);
    }




}
