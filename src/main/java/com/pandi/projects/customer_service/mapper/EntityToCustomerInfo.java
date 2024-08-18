package com.pandi.projects.customer_service.mapper;

import com.pandi.projects.customer_service.dto.CustomerInfo;
import com.pandi.projects.customer_service.dto.Portfolio;
import com.pandi.projects.customer_service.dto.TradeRequest;
import com.pandi.projects.customer_service.dto.TradeResponse;
import com.pandi.projects.customer_service.entity.Customer;
import com.pandi.projects.customer_service.entity.PortfolioItem;
import reactor.core.publisher.Flux;

import java.util.List;

public class EntityToCustomerInfo {



    public static CustomerInfo convertToCustomerInfo(Customer customer, List<PortfolioItem> portfolioItemFlux){
         var portFolioList = portfolioItemFlux.stream().map(d -> new Portfolio(d.getTicker(),d.getQuantity())).toList();

         return new CustomerInfo(customer.getId(), customer.getName(),customer.getBalance(),portFolioList);

    }

    public static TradeResponse CovertToTradeResponse(Customer customer, PortfolioItem portfolioItem, TradeRequest tradeRequest){

        return new TradeResponse(customer.getId(), tradeRequest.ticker(), tradeRequest.price(), tradeRequest.quantity(),tradeRequest.type(),tradeRequest.quantity()* tradeRequest.price(),customer.getBalance());
    }

}
