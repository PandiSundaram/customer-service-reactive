package com.pandi.projects.customer_service.entity;

import com.pandi.projects.customer_service.domain.Ticker;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class PortfolioItem {


    @Id
    private Integer id;
    private Integer customerId;
    private Integer quantity;
    private Ticker ticker;

}
