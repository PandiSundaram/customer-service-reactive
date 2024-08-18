package com.pandi.projects.customer_service.dto;

import com.pandi.projects.customer_service.domain.Ticker;
import com.pandi.projects.customer_service.domain.TradeType;

public record TradeResponse(Integer customerId,
                            Ticker ticker,
                            Integer price,
                            Integer quantity,
                            TradeType action,
                            Integer totalPrice,
                            Integer balance) {
}
