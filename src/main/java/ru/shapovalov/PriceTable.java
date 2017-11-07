package ru.shapovalov;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import elemental.json.JsonValue;
import ru.shapovalov.dao.Product;

public class PriceTable {
    public VerticalLayout getTablePrice(){
        Grid<Product> grid = new Grid<>();
        grid.setCaption("Double click to edit");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        TextField nameEditor = new TextField();
        Slider progressEditor = new Slider();
        progressEditor.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        progressEditor.setMax(150.0);
        progressEditor.setMin(1.0);

        grid.addColumn(Product::getId, new NumberRenderer("%02d"))
                .setCaption("##")
                .setExpandRatio(0);

        grid.addColumn(Product::getName, new BoldLastNameRenderer())
                .setEditorComponent(nameEditor, Product::setName)
                .setCaption("Name")
                .setExpandRatio(2);

        grid.addColumn(Product::getProgress, new ProgressBarRenderer() {
            @Override
            public JsonValue encode(Double value) {
                if (value != null) {
                    value = (value - 1) / 149.0;
                }
                return super.encode(value);
            }
        }).setEditorComponent(progressEditor, Product::setProgress)
                .setCaption("Progress")
                .setExpandRatio(2);

        grid.addColumn(Product::getWeightHistory, new SparklineRenderer())
                .setCaption("Weight")
                .setExpandRatio(4);

        grid.getEditor().setEnabled(true);
         return new VerticalLayout();
    }
}
