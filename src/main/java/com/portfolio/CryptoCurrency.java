package com.portfolio;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Currency;

@Entity
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String symbol;

    private Double valueInBTC;

    protected Double valueInUSD;

    public CryptoCurrency(String symbol, Double valueInBTC, Double valueInUSD) {

        this.symbol = symbol;
        this.valueInUSD = valueInUSD;
        this.valueInBTC = valueInBTC;
    }
}
