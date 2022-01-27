package org.modernclients.propertiesandbindings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HeronsFormulaExample {
    public static void main(String[] args) {
        DoubleProperty a = new SimpleDoubleProperty(0);
        DoubleProperty b = new SimpleDoubleProperty(0);
        DoubleProperty c = new SimpleDoubleProperty(0);

        DoubleBinding s = a.add(b).add(c).divide(2.0d);

        final DoubleBinding areaSquared = new When(
                a.add(b).greaterThan(c)
                        .and(b.add(c).greaterThan(a))
                        .and(c.add(a).greaterThan(b)))
                .then(s.multiply(s.subtract(a))
                        .multiply(s.subtract(b))
                        .multiply(s.subtract(c)))
                .otherwise(0.0D);

        a.set(3);
        b.set(4);
        c.set(5);
        System.out.printf("Given sides a = %1.0f," +
                        " b = %1.0f, and c = %1.0f," +
                        " the area of the triangle is" +
                        " %3.2f\n", a.get(), b.get(), c.get(),
                Math.sqrt(areaSquared.get()));

        a.set(2);
        b.set(2);
        c.set(2);
        System.out.printf("Given sides a = %1.0f," +
                        " b = %1.0f, and c = %1.0f," +
                        " the area of the triangle is" +
                        " %3.2f\n", a.get(), b.get(), c.get(),
                Math.sqrt(areaSquared.get()));
    }
}
