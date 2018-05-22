package com.portfolio;

import javax.persistence.*;

@Entity
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String symbol;

    private Float valueInBtc;

    protected Float valueInUsd;

    public CryptoCurrency(String symbol, Float valueInBTC, Float valueInUSD) {

        this.symbol = symbol;
        this.valueInUsd = valueInUSD;
        this.valueInBtc = valueInBTC;
    }

    public CryptoCurrency() {}

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getValueInBtc() {
        return valueInBtc;
    }

    public void setValueInBtc(Float valueInBtc) {
        this.valueInBtc = valueInBtc;
    }

    public float getValueInUsd() {
        return valueInUsd;
    }

    public void setValueInUsd(Float valueInUsd) {
        this.valueInUsd = valueInUsd;
    }
}
