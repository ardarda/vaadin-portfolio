package com.portfolio;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;

@Entity
public class CryptoInvestment implements Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CryptoCurrency investedCoin;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CryptoCurrency investmentCoin;

    public CryptoInvestment(CryptoCurrency investedCoin, CryptoCurrency investmentCoin) {

        this.investedCoin = investedCoin;
        this.investmentCoin = investmentCoin;
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
}