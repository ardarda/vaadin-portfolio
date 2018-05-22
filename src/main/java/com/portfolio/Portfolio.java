package com.portfolio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long portfolio_id;

    private  String title;

    public Portfolio( String title) {
        this.title = title;
    }

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true ,
            fetch = FetchType.EAGER,
            targetEntity = CryptoInvestment.class,
            mappedBy = "portfolio")
    private List<CryptoInvestment> cryptoInvestments = new ArrayList<>();

    protected Portfolio() {

    }

    public Long getId() {
        return portfolio_id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public List<CryptoInvestment> getCryptoInvestments() {
        return cryptoInvestments;
    }

    public void setCryptoInvestments(List<CryptoInvestment> cryptoInvestments) {
        this.cryptoInvestments = cryptoInvestments;
    }
}
