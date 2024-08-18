package com.pandi.projects.customer_service.repository;

import com.pandi.projects.customer_service.domain.Ticker;
import com.pandi.projects.customer_service.entity.PortfolioItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface PortfolioRepository extends ReactiveCrudRepository<PortfolioItem,Integer> {


    Flux<PortfolioItem> findAllByCustomerId(Integer customerId);

    @Query("select * from portfolio_item where customer_id:customerId and ticker:ticker")
    Mono<PortfolioItem> findItemWithTicker(Integer customerId, Ticker ticker);

    Mono<PortfolioItem> findBycustomerIdAndTicker(Integer cutomerId,Ticker ticker);
}
