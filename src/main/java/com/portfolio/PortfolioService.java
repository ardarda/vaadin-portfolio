package com.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    CryptoInvestmentRespository cryptoInvestmentRespository;

    @Autowired
    CryptoCurrencyRepository cryptoCurrencyRepository;

    @Transactional
    public Portfolio savePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> findAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public List<CryptoCurrency> findAllCryptoCurrencies() {
        return cryptoCurrencyRepository.findAll();
    }

    @Transactional
    public CryptoInvestment saveCryptoInvestment(CryptoInvestment investment) {
        return cryptoInvestmentRespository.save(investment);
    }

}
