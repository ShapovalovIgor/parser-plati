package ru.shapovalov.parser.UI;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

public class SettingLayout extends VerticalLayout {
    private TextField facultyField;
    private Button setNickName;

    public TextField getFacultyField() {
        return facultyField;
    }

    @PostConstruct
    public void init() {
        facultyField = new TextField();

    }
}
