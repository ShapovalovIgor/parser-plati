package ru.shapovalov.parser.UI.Table;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;
import ru.shapovalov.parser.dao.Product;
import ru.shapovalov.parser.parsing.ParserStrings;

import javax.annotation.PostConstruct;

@Theme(ValoTheme.THEME_NAME)
public class PriceTable extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private Grid<Product> grid = new Grid<>(Product.class);


    public PriceTable() {
    }

    @PostConstruct
    public void init() {
        setSizeFull();

        ParserStrings parserStrings = new ParserStrings();
        try {
            parserStrings.parserGoods();
        } catch (Exception e) {
            e.printStackTrace();
        }
        grid.setItems(parserStrings.productCollection);
        grid.setCaption("Double click to edit");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        Slider progressEditor = new Slider();
        progressEditor.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        progressEditor.setMax(150.0);
        progressEditor.setMin(1.0);

        grid.addColumn(Product::getIdGoods, new NumberRenderer())
                .setCaption("ID")
                .setExpandRatio(1);

        grid.addColumn(Product::getNameGoods, new TextRenderer())
                .setCaption("Name")
                .setExpandRatio(2);

        grid.addColumn(Product::getPrice, new NumberRenderer())
                .setCaption("Old Price")
                .setExpandRatio(3);

        grid.addColumn(Product::getCntSell, new NumberRenderer())
                .setCaption("Cnt sell")
                .setExpandRatio(4);

        grid.addColumn(Product::getCntGoodResponses, new NumberRenderer())
                .setCaption("Cnt Good Responses")
                .setExpandRatio(5);

        grid.addColumn(Product::getCntBadResponses, new NumberRenderer())
                .setCaption("Cnt Bad Responses")
                .setExpandRatio(6);

        grid.addColumn(Product::getType, new NumberRenderer())
                .setCaption("Type")
                .setExpandRatio(7);

        grid.getEditor().setEnabled(false);

//        grid.setStyleGenerator(t -> {
//            if (t.getPriceOld() == t.getPriceNew()) {
//                return "green";
//            } else if (t.getPriceOld() < t.getPriceNew()) {
//                return "red";
//            }
//            return null;
//        });


// Create a header row to hold column filters
        HeaderRow filterRow = grid.appendHeaderRow();

        addComponent(grid);
    }
}
