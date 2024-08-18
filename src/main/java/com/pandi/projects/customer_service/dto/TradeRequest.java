package com.pandi.projects.customer_service.dto;

import com.pandi.projects.customer_service.annotation.ValidTicker;
import com.pandi.projects.customer_service.domain.Ticker;
import com.pandi.projects.customer_service.domain.TradeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TradeRequest(@NotNull(message = "customerId cannot be blank") Integer customerId,
                           @NotNull(message = "price cannot be blank") Integer price,
                           @NotNull(message = "quantity cannot be blank")  Integer quantity,
                           @ValidTicker(message="check the tickerType") Ticker ticker, TradeType type
) {
}
