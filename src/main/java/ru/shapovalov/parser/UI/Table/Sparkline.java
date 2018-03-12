package ru.shapovalov.parser.UI.Table;

import org.vaadin.grid.cellrenderers.view.SparklineRenderer;

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
