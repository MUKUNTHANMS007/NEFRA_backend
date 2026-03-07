package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.InvestorPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvestorPortfolioRepository extends JpaRepository<InvestorPortfolio, Long> {
    List<InvestorPortfolio> findByInvestorIdOrderByInvestmentYearDesc(Long investorId);
}