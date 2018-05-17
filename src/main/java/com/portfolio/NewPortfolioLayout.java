package com.portfolio;


import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewPortfolioLayout extends VerticalLayout {

    private TextField title;

    @Autowired
    PortfolioService service;

    public void config() {
        // initialize ui components
        title = new TextField();
        title.setPlaceholder("Enter a title");
        title.addStyleName(ValoTheme.TEXTFIELD_LARGE);
        addComponent(title);
    }

    public String textFieldTitle() {
        return title.getValue();
    }
}
