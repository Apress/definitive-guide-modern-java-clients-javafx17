package org.modernclients.propertiesandbindings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HeronsFormulaDirectExtensionExample {
    public static void main(String[] args) {
        final DoubleProperty a = new SimpleDoubleProperty(0);
        final DoubleProperty b = new SimpleDoubleProperty(0);
        final DoubleProperty c = new SimpleDoubleProperty(0);

        DoubleBinding area = new DoubleBinding() {
            {
                super.bind(a, b, c);
            }

            @Override
            protected double computeValue() {
                double a0 = a.get();
                double b0 = b.get();
                double c0 = c.get();

                if ((a0 + b0 > c0) && (b0 + c0 > a0) &&
                        (c0 + a0 > b0)) {
                    double s = (a0 + b0 + c0) / 2.0D;
                    return Math.sqrt(s * (s - a0) *
                            (s - b0) * (s - c0));
                } else {
                    return 0.0D;
                }
            }
        };

        a.set(3);
        b.set(4);
        c.set(5);
        System.out.printf("Given sides a = %1.0f," +
                        " b = %1.0f, and c = %1.0f," +
                        " the area of the triangle" +
                        " is %3.2f\n", a.get(), b.get(),
                c.get(), area.get());

        a.set(2);
        b.set(2);
        c.set(2);
        System.out.printf("Given sides a = %1.0f," +
                        " b = %1.0f, and c = %1.0f," +
                        " the area of the triangle" +
                        " is %3.2f\n", a.get(), b.get(),
                c.get(), area.get());
    }
}
