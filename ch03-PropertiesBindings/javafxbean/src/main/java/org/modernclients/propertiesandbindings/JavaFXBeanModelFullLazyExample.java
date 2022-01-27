package org.modernclients.propertiesandbindings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JavaFXBeanModelFullLazyExample {
    private static final String DEFAULT_STR = "Hello";
    private StringProperty str;
    private String _str = DEFAULT_STR;

    public final String getStr() {
        if (str != null) {
            return str.get();
        } else {
            return _str;
        }
    }

    public final void setStr(String str) {
        if (this.str != null) {
            this.str.set(str);
        } else {
            _str = str;
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
