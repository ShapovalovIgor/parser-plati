package ru.shapovalov.parser.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.shapovalov.parser.Database.DataBaseProcessor;

@SuppressWarnings("serial")
@Theme("vaadinbutton")
public class MainUI extends UI {
    private static final Log LOG = LogFactory.getLog(MainUI.class);

    @Override
    protected void init(VaadinRequest request) {
        if(LOG.isDebugEnabled())
        LOG.debug("Start UI 123 \n\n");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        Notification.show("New information:",
                "Please wait a few minutes.",
                Notification.Type.HUMANIZED_MESSAGE);


        DataBaseProcessor dataBaseProcessor = new DataBaseProcessor();
        dataBaseProcessor.processor(layout);
        TabSheet tabs = new TabSheet();
        tabs.setSizeFull(); // Make the TabSheet fill all available space. By default the height is fixed.
//        tabs.addComponent();
        tabs.addTab(layout, "Price Games");
//        tabs.addTab( tabs, "Price Changes" );
        setContent(tabs);
    }
}