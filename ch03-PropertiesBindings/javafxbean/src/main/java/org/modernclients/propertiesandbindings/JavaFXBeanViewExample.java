package org.modernclients.propertiesandbindings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

public class JavaFXBeanViewExample {
    private JavaFXBeanModelExample model;

    public JavaFXBeanViewExample(JavaFXBeanModelExample model) {
        this.model = model;
        hookupChangeListeners();
    }

    private void hookupChangeListeners() {
        model.iProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                System.out.println("Property i changed: old value = " + oldValue + ", new value = " + newValue);
            }
        });

        model.strProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                System.out.println("Property str changed: old value = " + oldValue + ", new value = " + newValue);
            }
        });

        model.colorProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color oldValue, Color newValue) {
                System.out.println("Property color changed: old value = " + oldValue + ", new value = " + newValue);
            }
        });
    }
}
