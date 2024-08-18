package com.pandi.projects.customer_service.dto;

import com.pandi.projects.customer_service.domain.Ticker;

public record Portfolio(Ticker ticker, Integer quantity) {
}
