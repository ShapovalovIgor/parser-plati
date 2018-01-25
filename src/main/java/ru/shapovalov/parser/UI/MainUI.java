package ru.shapovalov.parser.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.shapovalov.parser.UI.Table.PriceTable;
import ru.shapovalov.parser.database.HibernateUtil;
import ru.shapovalov.parser.parsing.ParserStrings;

@SuppressWarnings("serial")
@Theme("vaadinbutton")
public class MainUI extends UI {
public static HibernateUtil hibernateUtil;
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        hibernateUtil = new HibernateUtil();

        ParserStrings parserStrings = new ParserStrings();
        try {
            parserStrings.parserGoods();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PriceTable priceTable = new PriceTable(parserStrings.productCollection);

        layout.addComponent(priceTable);

        TabSheet tabs = new TabSheet();
        tabs.setSizeFull(); // Make the TabSheet fill all available space. By default the height is fixed.
//        tabs.addComponent();
        tabs.addTab( layout, "Price Games" );
//        tabs.addTab( tabs, "Price Changes" );
        setContent(tabs);
    }
}