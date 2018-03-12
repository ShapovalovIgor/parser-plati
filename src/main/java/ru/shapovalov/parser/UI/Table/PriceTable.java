package ru.shapovalov.parser.UI.Table;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer;
import ru.shapovalov.parser.DAO.Product;

import java.util.*;
@Theme(ValoTheme.THEME_NAME)
public class PriceTable extends Grid<Product> {

    public PriceTable(Collection productCollection) {

        SparklineRenderer<Product> sparkline = new SparklineRenderer<>(200,30);

        final SparklineRenderer.SparklineConfiguration config = sparkline.getConfiguration();
        new Sparkline(config);
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

        addColumn(Product::getPrices, sparkline).setCaption("Price History");

        addColumn(Product::getCntSell, new NumberRenderer())
                .setCaption("Cnt sell");

        addColumn(Product::getCntGoodResponses, new NumberRenderer())
                .setCaption("Cnt Good Responses");

        addColumn(Product::getCntBadResponses, new NumberRenderer())
                .setCaption("Cnt Bad Responses");
//
//        addColumn(Product::getType, new NumberRenderer())
//                .setCaption("Type");
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
