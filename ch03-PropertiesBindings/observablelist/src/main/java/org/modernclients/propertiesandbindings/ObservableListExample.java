package org.modernclients.propertiesandbindings;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static javafx.collections.ListChangeListener.Change;

public class ObservableListExample {
    public static void main(String[] args) {
        ObservableList<String> strings =
                FXCollections.observableArrayList();

        strings.addListener((Observable observable) -> {
            System.out.println("\tlist invalidated");
        });

        strings.addListener((Change<? extends String> change) -> {
            System.out.println("\tstrings = " +
                    change.getList());
        });

        System.out.println("Calling add(\"First\"): ");
        strings.add("First");

        System.out.println("Calling add(0, \"Zeroth\"): ");
        strings.add(0, "Zeroth");

        System.out.println("Calling addAll(\"Second\"," +
                " \"Third\"): ");
        strings.addAll("Second", "Third");

        System.out.println("Calling set(1," +
                " \"New First\"): ");
        strings.set(1, "New First");

        final List<String> list =
                Arrays.asList("Second_1", "Second_2");
        System.out.println("Calling addAll(3, list): ");
        strings.addAll(3, list);

        System.out.println("Calling remove(2, 4): ");
        strings.remove(2, 4);

        final Iterator<String> iterator =
                strings.iterator();
        while (iterator.hasNext()) {
            final String next = iterator.next();
            if (next.contains("t")) {
                System.out.println("Calling remove()" +
                        " on iterator: ");
                iterator.remove();
            }
        }

        System.out.println("Calling removeAll(" +
                "\"Third\", \"Fourth\"): ");
        strings.removeAll("Third", "Fourth");
    }
}
