package com.portfolio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String title;

    public Portfolio(String title) {
        this.title = title;
    }

    protected Portfolio() {

    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
