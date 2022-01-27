package org.modernclients.propertiesandbindings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JavaFXBeanModelHalfLazyExample {
    private static final String DEFAULT_STR = "Hello";
    private StringProperty str;

    public final String getStr() {
        if (str != null) {
            return str.get();
        } else {
            return DEFAULT_STR;
        }
    }

    public final void setStr(String str) {
        if ((this.str != null) ||
                !(str.equals(DEFAULT_STR))) {
            strProperty().set(str);
        }
    }

    public StringProperty strProperty() {
        if (str == null) {
            str = new SimpleStringProperty(this,
                    "str", DEFAULT_STR);
        }
        return str;
    }
}
