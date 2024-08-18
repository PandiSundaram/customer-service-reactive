package com.pandi.projects.customer_service.service;

import com.pandi.projects.customer_service.domain.Ticker;
import com.pandi.projects.customer_service.dto.CustomerInfo;
import com.pandi.projects.customer_service.dto.TradeRequest;
import com.pandi.projects.customer_service.entity.Customer;
import com.pandi.projects.customer_service.entity.PortfolioItem;
import com.pandi.projects.customer_service.exceptions.CustomerNotFoundException;
import com.pandi.projects.customer_service.mapper.EntityToCustomerInfo;
import com.pandi.projects.customer_service.repository.CustomerRepository;
import com.pandi.projects.customer_service.repository.PortfolioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PortfolioRepository portfolioRepository;

    public Mono<CustomerInfo> getCustomerInfo(Integer id) {

        return this.customerRepository.findById(id)
                 .switchIfEmpty(Mono.error(new CustomerNotFoundException(id)))
                 .flatMap(customer -> this.ConstructPortFolio(customer));

    }

    public Mono<CustomerInfo> ConstructPortFolio(Customer customer) {
        return this.portfolioRepository.findAllByCustomerId(customer.getId())
              //  .buffer(10)
                .collectList()
                .map(d -> EntityToCustomerInfo.convertToCustomerInfo(customer, d));

    }


}
