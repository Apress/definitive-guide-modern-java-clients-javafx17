package org.modernclients.propertiesandbindings;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TriangleAreaExample {
    public static void main(String[] args) {
        IntegerProperty x1 = new SimpleIntegerProperty(0);
        IntegerProperty y1 = new SimpleIntegerProperty(0);
        IntegerProperty x2 = new SimpleIntegerProperty(0);
        IntegerProperty y2 = new SimpleIntegerProperty(0);
        IntegerProperty x3 = new SimpleIntegerProperty(0);
        IntegerProperty y3 = new SimpleIntegerProperty(0);

        final NumberBinding x1y2 = Bindings.multiply(x1, y2);
        final NumberBinding x2y3 = Bindings.multiply(x2, y3);
        final NumberBinding x3y1 = Bindings.multiply(x3, y1);
        final NumberBinding x1y3 = Bindings.multiply(x1, y3);
        final NumberBinding x2y1 = Bindings.multiply(x2, y1);
        final NumberBinding x3y2 = Bindings.multiply(x3, y2);

        final NumberBinding sum1 = Bindings.add(x1y2, x2y3);
        final NumberBinding sum2 = Bindings.add(sum1, x3y1);
        final NumberBinding diff1 =
                Bindings.subtract(sum2, x1y3);
        final NumberBinding diff2 =
                Bindings.subtract(diff1, x2y1);
        final NumberBinding determinant =
                Bindings.subtract(diff2, x3y2);
        final NumberBinding area =
                Bindings.divide(determinant, 2.0D);

        x1.set(0); y1.set(0);
        x2.set(6); y2.set(0);
        x3.set(4); y3.set(3);

        printResult(x1, y1, x2, y2, x3, y3, area);

        x1.set(1); y1.set(0);
        x2.set(2); y2.set(2);
        x3.set(0); y3.set(1);

        printResult(x1, y1, x2, y2, x3, y3, area);
    }

    private static void printResult(IntegerProperty x1,
                                    IntegerProperty y1,
                                    IntegerProperty x2,
                                    IntegerProperty y2,
                                    IntegerProperty x3,
                                    IntegerProperty y3,
                                    NumberBinding area) {
        System.out.println("For A(" +
                x1.get() + "," + y1.get() + "), B(" +
                x2.get() + "," + y2.get() + "), C(" +
                x3.get() + "," + y3.get() +
                "), the area of triangle ABC is " +
                area.getValue());
    }
}
