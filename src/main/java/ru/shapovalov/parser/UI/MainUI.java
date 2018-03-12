package ru.shapovalov.parser.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.shapovalov.parser.UI.Table.PriceTable;
import ru.shapovalov.parser.Database.HibernateUtil;
import ru.shapovalov.parser.Database.DataBaseProcessor;

import java.util.Collection;

@SuppressWarnings("serial")
@Theme("vaadinbutton")
public class MainUI extends UI {
    public static HibernateUtil hibernateUtil;
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
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