package ru.shapovalov.parser.UI.Table;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;
import ru.shapovalov.parser.dao.Product;
import ru.shapovalov.parser.parsing.ParserStrings;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Theme(ValoTheme.THEME_NAME)
public class PriceTable extends Grid<Product> {

    private static final long serialVersionUID = 1L;
    private Grid<Product> grid = new Grid<>(Product.class);


    public PriceTable(Collection productCollection) {
        setSizeFull();
        setItems(productCollection);
        setCaption("Price:");
        setSizeFull();
        getEditor().setEnabled(false);

        setSelectionMode(Grid.SelectionMode.SINGLE);

        addColumn(Product::getIdGoods, new NumberRenderer())
                .setId("ID")
                .setCaption("ID");

        addColumn(Product::getNameGoods, new TextRenderer())
                .setCaption("Name");

        addColumn(Product::getPrice, new NumberRenderer())
                .setCaption("Old Price");

        addColumn(product -> {
            Double priceNew = product.getPrice();
            Double priceOld = product.getPrice();
            return getColorColumnPrice(priceOld, priceNew);
        }, new HtmlRenderer())
                .setCaption("New Price");

        addColumn(Product::getCntSell, new NumberRenderer())
                .setCaption("Cnt sell");

        addColumn(Product::getCntGoodResponses, new NumberRenderer())
                .setCaption("Cnt Good Responses");

        addColumn(Product::getCntBadResponses, new NumberRenderer())
                .setCaption("Cnt Bad Responses");

        addColumn(Product::getType, new NumberRenderer())
                .setCaption("Type");
    }

    private String getColorColumnPrice(Double priceOld, Double priceNew) {
        String color = "";
        String status = "";
        int checkPrice = priceOld.compareTo(priceNew);
        System.out.println("Old price" + priceOld + " New price" + priceNew);
        System.out.println("Check" + checkPrice);
        switch (checkPrice) {
            case 0:
                color = "#000000";
                break;
            case -1:
                color = "#ff0000";
                status = "+";
                break;
            case 1:
                color = "#00ff00";
                status = "-";
                break;
        }
        return "<span class=\"v-icon\" style=\"font-family: "
                + VaadinIcons.WALLET.getFontFamily() + ";color:" + color
                + "\">&#x"
                + Integer.toHexString(VaadinIcons.WALLET.getCodepoint())
                + ";</span><font color =\"" + color + "\"> " + status + priceNew + "</font>";
    }
}
