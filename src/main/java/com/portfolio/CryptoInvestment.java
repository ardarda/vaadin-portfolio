package com.portfolio;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.sound.sampled.Port;

@Entity
public class CryptoInvestment implements Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", referencedColumnName = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invested_coin_id", referencedColumnName = "id")
    private CryptoCurrency investedCoin;
//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "investment_coin_id", referencedColumnName = "id")
    private CryptoCurrency investmentCoin;

    private Float investedCoinAmount;

    private Float investmentCoinAmount;

    //

    public CryptoInvestment(CryptoCurrency investedCoin, Float investedAmount, CryptoCurrency investmentCoin, Float investmentAmount, Portfolio portfolio) {

        this.investedCoin = investedCoin;
        this.investedCoinAmount = investedAmount;
        this.investmentCoin = investmentCoin;
        this.investmentCoinAmount = investmentAmount;
        this.portfolio = portfolio;
    }

    protected CryptoInvestment() {}

    @Override
    public Boolean isProfitable() {
        return true;
    }

    @Override
    public Double profitInUSD() {
        return 100.0;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public CryptoCurrency getInvestedCoin() {
        return investedCoin;
    }

    public void setInvestedCoin(CryptoCurrency investedCoin) {
        this.investedCoin = investedCoin;
    }

    public  CryptoCurrency getInvestmentCoin() {
        return investmentCoin;
    }

    public void setInvestmentCoin(CryptoCurrency investmentCoin) {
        this.investmentCoin = investmentCoin;
    }

    public Float getInvestedCoinAmount() {
        return investedCoinAmount;
    }

    public void setInvestedCoinAmount(Float investedCoinAmount) {
        this.investedCoinAmount = investedCoinAmount;
    }

    public Float getInvestmentCoinAmount() {
        return investmentCoinAmount;
    }

    public void setInvestmentCoinAmount(Float investmentCoinAmount) {
        this.investmentCoinAmount = investmentCoinAmount;
    }
}