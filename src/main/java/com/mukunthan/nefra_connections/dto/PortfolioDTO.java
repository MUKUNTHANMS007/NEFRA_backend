package com.mukunthan.nefra_connections.dto;

import java.math.BigDecimal;

public record PortfolioDTO(
        Integer investmentYear,
        String investedCompanyName,
        BigDecimal investmentAmount
) {}