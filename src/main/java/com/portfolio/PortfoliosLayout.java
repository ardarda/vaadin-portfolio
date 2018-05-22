package com.portfolio;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PortfoliosLayout extends VerticalLayout {

    protected static final String PORTFOLIOVIEW = "portfolio";
    protected HorizontalLayout horizontalLayout;
    protected VerticalLayout portfoliosLayout;
    protected VerticalLayout portfolioDetailLayout;
    protected VerticalLayout addNewInvestmentLayout;

    protected PortfolioService service;

    protected NativeSelect<String> investedSelect;
    protected NativeSelect<String> investmentSelect;
    protected TextField investedAmount;
    protected TextField investmentAmount;

    protected Portfolio selectedPortfolio;

    public PortfoliosLayout() {
        horizontalLayout = new HorizontalLayout();
        addComponent(horizontalLayout);

        portfoliosLayout = new VerticalLayout();
        horizontalLayout.addComponent(portfoliosLayout);

        portfolioDetailLayout = new VerticalLayout();
        horizontalLayout.addComponent(portfolioDetailLayout);

        addNewInvestmentLayout = new VerticalLayout();
        horizontalLayout.addComponent(addNewInvestmentLayout);
    }

    public void config(List<Portfolio> portfolios, PortfolioService service) {
        this.service = service;
        portfolios.forEach(portfolio -> {
            Button portfolioButton = new Button(portfolio.getTitle());
            portfolioButton.addStyleName(ValoTheme.BUTTON_LINK);
            portfoliosLayout.addComponent(portfolioButton);

            portfolioButton.addClickListener(clickEvent -> {
                addNewInvestmentLayout.removeAllComponents();
                updatePortfolioDetailLayout(portfolio);
            });

        });
    }

    protected void updatePortfolioDetailLayout(Portfolio portfolio) {
        this.service = service;
        selectedPortfolio = portfolio;

        portfolioDetailLayout.removeAllComponents();

        Button addNewInvestment = new Button("Add");
        addNewInvestment.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addNewInvestment.setIcon(VaadinIcons.PLUS);

        addNewInvestment.addClickListener(clickEvent -> {
            configAddNewInvestmentLayout(portfolio);
        });
        portfolioDetailLayout.addComponents(addNewInvestment);

        portfolio.getCryptoInvestments().forEach(cryptoInvestment -> {
            String investedCoinSym = cryptoInvestment.getInvestedCoin().getSymbol();
            String investmentCoinSym = cryptoInvestment.getInvestmentCoin().getSymbol();
            String investedCoinAmount = cryptoInvestment.getInvestedCoinAmount().toString();
            String investmentCoinAmount = cryptoInvestment.getInvestmentCoinAmount().toString();

            Label investmentTitle = new Label(investedCoinSym + investmentCoinSym);
            Label amountsSentence = new Label("invested " + investedCoinAmount + " " + investedCoinSym + " in " + investmentCoinAmount + " " + investmentCoinSym);

            investmentTitle.addStyleName(ValoTheme.LABEL_COLORED);

            portfolioDetailLayout.addComponents(investmentTitle, amountsSentence);

        });
    }


    protected void configAddNewInvestmentLayout(Portfolio portfolio) {
        addNewInvestmentLayout.removeAllComponents();

        // invested coin
        HorizontalLayout investedContainer = new HorizontalLayout();
        Label invest = new Label("Invested");
        investedContainer.addComponent(invest);
        investedSelect =
                new NativeSelect<>("Pick a coin");
        List<CryptoCurrency> currencies = service.findAllCryptoCurrencies();
        List<String> symbols = currencies.stream().map(cryptoCurrency -> cryptoCurrency.getSymbol()).collect(Collectors.toList());
        investedSelect.setItems(symbols);
        investedContainer.addComponent(investedSelect);

        investedContainer.addComponent(new Label("Amount"));
        investedAmount = new TextField("Enter amount");
        investedContainer.addComponent(investedAmount);

        // investment coin
        HorizontalLayout investmentContainer = new HorizontalLayout();
        Label investment = new Label("Investment");
        investmentContainer.addComponent(investment);
        investmentSelect =
                new NativeSelect<>("Pick a coin");
        investmentSelect.setItems(symbols);
        investmentContainer.addComponent(investmentSelect);

        investmentContainer.addComponent(new Label("Amount"));
        investmentAmount = new TextField("Enter amount");
        investmentContainer.addComponent(investmentAmount);

        Button done = new Button();
        done.setIcon(VaadinIcons.CHECK);
        done.addClickListener(clickEvent -> {
            if (validateNewInvestmentForm()) {
                commitNewInvestment();
            }
        });

        addNewInvestmentLayout.addComponents(investedContainer, investmentContainer, done);
    }

    protected Boolean validateNewInvestmentForm() {
        Boolean didSelectInvestedCoin = investedSelect.getSelectedItem().isPresent();
        Boolean didSelectInvestmentCoin = investmentSelect.getSelectedItem().isPresent();
        Boolean didEnterInvestedAmount = !investedAmount.getValue().isEmpty();
        Boolean didEnterInvestmentAmount = !investmentAmount.getValue().isEmpty();

        return didSelectInvestedCoin && didSelectInvestmentCoin && didEnterInvestedAmount && didEnterInvestmentAmount;
    }

    protected void commitNewInvestment() {
        List<CryptoCurrency> currencies = service.findAllCryptoCurrencies();

        Optional<String> investedCoinSymbol = investedSelect.getSelectedItem();
        CryptoCurrency investedCoin = currencies.stream()
                                                .filter(cryptoCurrency -> investedCoinSymbol.get().equals(cryptoCurrency.getSymbol()))
                                                .findFirst()
                                                .orElse(null);

        Optional<String>  investmentCoinSymbol = investmentSelect.getSelectedItem();
        CryptoCurrency investmentCoin = currencies.stream()
                .filter(cryptoCurrency -> investmentCoinSymbol.get().equals(cryptoCurrency.getSymbol()))
                .findFirst()
                .orElse(null);

        if (investedCoin != null && investmentCoin != null) {
            CryptoInvestment investment = new CryptoInvestment(investedCoin, Float.parseFloat(investedAmount.getValue()),  investmentCoin, Float.parseFloat(investmentAmount.getValue()), selectedPortfolio);
            if (service.saveCryptoInvestment(investment) != null) {
                Notification.show("Investment created");
                addNewInvestmentLayout.removeAllComponents();
                List<CryptoInvestment> investments = selectedPortfolio.getCryptoInvestments();
                investments.add(investment);
                updatePortfolioDetailLayout(selectedPortfolio);
            }
        }
    }
}
