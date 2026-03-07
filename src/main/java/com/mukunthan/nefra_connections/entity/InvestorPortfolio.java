package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "investor_portfolios")
@Getter @Setter @NoArgsConstructor
public class InvestorPortfolio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_id", nullable = false)
    private User investor;

    @Column(name = "investment_year", nullable = false)
    private Integer investmentYear;

    @Column(name = "invested_company_name", nullable = false)
    private String investedCompanyName;

    @Column(name = "investment_amount")
    private BigDecimal investmentAmount;
}