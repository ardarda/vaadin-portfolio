package com.portfolio;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortfoliosLayout extends VerticalLayout {

    public void config(List<Portfolio> portfolios) {
        portfolios.forEach(portfolio -> {
            Label title = new Label(portfolio.getTitle());
            title.addStyleName(ValoTheme.LABEL_H3);
            addComponent(title);
        });
    }
}
