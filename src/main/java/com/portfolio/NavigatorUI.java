package com.portfolio;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SpringUI
@Theme("mytheme")
public class NavigatorUI extends UI {
    Navigator navigator;
    protected static final String MAINVIEW = "main";

    @Autowired
    PortfolioService portfolioService;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Crypto Portfolio");
        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView("", new StartView());
        navigator.addView(MAINVIEW, new MainView());
    }

    /** A start view for navigating to the main view */
    public class StartView extends VerticalLayout implements View {
        public StartView() {
            setSizeFull();

            Button button = new Button("Go to Main View",
                    new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            navigator.navigateTo(MAINVIEW);
                        }
                    });
            addComponent(button);
            setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
            Notification.show("Welcome to crypto portfolio");
        }
    }

    public class MainView extends VerticalLayout implements View {
        // Menu navigation button listener
        class ButtonListener implements Button.ClickListener {
            String menuitem;

            public ButtonListener(String menuitem) {
                this.menuitem = menuitem;
            }

            @Override
            public void buttonClick(Button.ClickEvent event) {
                // Navigate to a specific state
                navigator.navigateTo(MAINVIEW + "/" + menuitem);
            }
        }

        VerticalLayout mainContent;
        VerticalLayout portfoliosContent;

        Panel equalPanel;
        Button logout;

        @Override
        public Component getViewComponent() {
            return mainContent;
        }

        public MainView() {
            setSizeFull();

            // initialize containers
            mainContent = new VerticalLayout();
            portfoliosContent = new VerticalLayout();

            // header layout
            HorizontalLayout headerContent = new HorizontalLayout();
            Label header = new Label("Crypto Portfolio");
            header.addStyleName(ValoTheme.LABEL_H1);
            headerContent.addComponent(header);

            // menu layout
            HorizontalLayout menuContent = new HorizontalLayout();

            // new portfolio
            Button addPortfolio = new Button("New portfolio");
            addPortfolio.setIcon(VaadinIcons.PLUS);
            addPortfolio.addClickListener(clickEvent -> { addNewPortfolio(); });
            menuContent.addComponent(addPortfolio);

            // logout
            logout = new Button("Logout");
            menuContent.addComponent(logout);
            logout.addClickListener(event ->
                    navigator.navigateTo(""));

            mainContent.addComponent(headerContent);
            mainContent.addComponent(menuContent);
            mainContent.addComponent(portfoliosContent);

            populateWithPortfolios();
        }

        private void addNewPortfolio() {
            portfoliosContent.removeAllComponents();
            NewPortfolioLayout newPortfolio = new NewPortfolioLayout();
            newPortfolio.config();
            portfoliosContent.addComponent(newPortfolio);

            Button done = new Button();
            done.setIcon(VaadinIcons.CHECK);
            done.addClickListener(clickEvent -> {
                portfolioService.save(new Portfolio(newPortfolio.textFieldTitle()));
                populateWithPortfolios(); });
            portfoliosContent.addComponent(done);
        }

        private void populateWithPortfolios() {
            portfoliosContent.removeAllComponents();
            portfoliosContent.addComponent(new Label("Porfolios area to be populated"));
            PortfoliosLayout portfolios = new PortfoliosLayout();
            portfolios.config(portfolioService.findAll());
            portfoliosContent.addComponent(portfolios);
        }
    }
}