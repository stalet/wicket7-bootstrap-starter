package org.tomten.web.components;


import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.tomten.web.services.DateService;
import org.tomten.web.template.TemplatePage;

public class HomePage extends TemplatePage {

    @SpringBean
    private DateService dateService;

    public HomePage(final PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        setDefaultModel(new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                return dateService.getDate();
            }
        });
        final Label date = new Label("date", getModel());
        add(date);
        date.add(new AjaxSelfUpdatingTimerBehavior(Duration.milliseconds(100)));
    }

    @Override
    protected IModel<?> getTitle() {
        return Model.of("Home");
    }

}
