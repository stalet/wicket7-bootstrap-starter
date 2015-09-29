package org.tomten.web;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.less.BootstrapLess;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import org.tomten.web.components.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableScheduling
public class WicketApplication extends WebApplication {
    private final static Logger LOG = LoggerFactory.getLogger(WicketApplication.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * provides page for default request
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }


    @Override
    protected void init() {
        super.init();

        final IBootstrapSettings settings = new BootstrapSettings();
        final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Readable);

        settings.setJsResourceFilterName("footer-container").setThemeProvider(themeProvider);

        Bootstrap.install(this, settings);
        BootstrapLess.install(this);

        getComponentInstantiationListeners().add(
                new SpringComponentInjector(this, applicationContext));
        mountPage("/index.html", HomePage.class);

    }

    public static void main(String[] args) {
        SpringApplication.run(WicketApplication.class, args);
    }

}
