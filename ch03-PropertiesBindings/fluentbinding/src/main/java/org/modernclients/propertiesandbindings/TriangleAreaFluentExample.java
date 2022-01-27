package org.modernclients.propertiesandbindings;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TriangleAreaFluentExample {
    public static void main(String[] args) {
        IntegerProperty x1 = new SimpleIntegerProperty(0);
        IntegerProperty y1 = new SimpleIntegerProperty(0);
        IntegerProperty x2 = new SimpleIntegerProperty(0);
        IntegerProperty y2 = new SimpleIntegerProperty(0);
        IntegerProperty x3 = new SimpleIntegerProperty(0);
        IntegerProperty y3 = new SimpleIntegerProperty(0);

        final NumberBinding area = x1.multiply(y2)
                .add(x2.multiply(y3))
                .add(x3.multiply(y1))
                .subtract(x1.multiply(y3))
                .subtract(x2.multiply(y1))
                .subtract(x3.multiply(y2))
                .divide(2.0D);

        StringExpression output = Bindings.format(
                "For A(%d,%d), B(%d,%d), C(%d,%d)," +
                        " the area of triangle ABC is %3.1f",
                x1, y1, x2, y2, x3, y3, area);

        x1.set(0); y1.set(0);
        x2.set(6); y2.set(0);
        x3.set(4); y3.set(3);

        System.out.println(output.get());

        x1.set(1); y1.set(0);
        x2.set(2); y2.set(2);
        x3.set(0); y3.set(1);

        System.out.println(output.get());
    }
}
