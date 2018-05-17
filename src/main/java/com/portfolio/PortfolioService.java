package com.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PortfolioService {

    @Autowired
    PortfolioRepository repository;

    @Transactional
    public Portfolio save(Portfolio portfolio) {
        return repository.save(portfolio);
    }

    public List<Portfolio> findAll() {
        return repository.findAll();
    }
}
