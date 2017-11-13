package ru.shapovalov.parser.UI;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;

public class FieldNickName {

    private TextField facultyField;
    private Button setNickName;

    public TextField getFacultyField() {
        return facultyField;
    }

    private void addFacultyField() {
        facultyField = new TextField();
    }
}
