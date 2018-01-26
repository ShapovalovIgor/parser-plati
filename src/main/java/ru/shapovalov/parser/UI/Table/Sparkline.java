package ru.shapovalov.parser.UI.Table;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.grid.cellrenderers.view.SparklineRenderer;
import ru.shapovalov.parser.dao.Product;

public class Sparkline {
    public Sparkline(SparklineRenderer.SparklineConfiguration config) {

        config.setPathWidth(2);
        config.setMinMaxColor("#f96");
        config.setNormalRangeVisible(!config.isNormalRangeVisible());
        config.setAverageVisible(!config.isAverageVisible());
        config.setMinMaxVisible(!config.isMinMaxVisible());
        config.setValueVisible(!config.isValueVisible());
        config.setValueDotVisible(!config.isValueDotVisible());
        config.setMinMaxDotsVisible(!config.isMinMaxDotsVisible());
    }
}
