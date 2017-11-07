package ru.shapovalov.parser;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer;
import ru.shapovalov.parser.dao.Product;
import ru.shapovalov.parser.parsing.ParserStrings;

public class PriceTable {
    public VerticalLayout getTablePrice(){
        ParserStrings parserStrings = new ParserStrings();
        try {
            parserStrings.parserGoods();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Container container = new BeanItemContainer<>(Product.class, new Product());
        Grid<Product> grid = new Grid<Product>((DataProvider<Product, ?>) ParserStrings.oldIdList);
        grid.setCaption("Double click to edit");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        TextField nameEditor = new TextField();
        Slider progressEditor = new Slider();
        progressEditor.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        progressEditor.setMax(150.0);
        progressEditor.setMin(1.0);

        grid.addColumn(Product::getId_goods, new NumberRenderer())
                .setCaption("ID")
                .setExpandRatio(0);

        grid.addColumn(Product::getName_goods, new TextRenderer())
                .setCaption("Name")
                .setExpandRatio(1);

        grid.addColumn(Product::getPriceOld, new SparklineRenderer())
                .setCaption("price")
                .setExpandRatio(4);

        grid.getEditor().setEnabled(true);
         return new VerticalLayout();
    }
}
