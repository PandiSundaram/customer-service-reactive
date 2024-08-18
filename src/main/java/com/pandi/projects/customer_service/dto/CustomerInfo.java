package com.pandi.projects.customer_service.dto;

import java.util.List;

public record CustomerInfo(Integer id,
                           String name,
                           Integer balance,
                           List<Portfolio> porfolioList) {
}
