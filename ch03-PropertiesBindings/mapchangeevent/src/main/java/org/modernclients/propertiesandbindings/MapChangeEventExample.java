package org.modernclients.propertiesandbindings;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapChangeEventExample {
    public static void main(String[] args) {
        ObservableMap<String, Integer> map =
                FXCollections.observableHashMap();
        map.addListener(new MyListener());

        System.out.println("Calling put(\"First\", 1): ");
        map.put("First", 1);

        System.out.println("Calling put(\"First\", 100): ");
        map.put("First", 100);

        Map<String, Integer> anotherMap = new HashMap<>();
        anotherMap.put("Second", 2);
        anotherMap.put("Third", 3);
        System.out.println("Calling putAll(anotherMap): ");
        map.putAll(anotherMap);

        Iterator<Map.Entry<String, Integer>> entryIterator =
                map.entrySet().iterator();
        while (entryIterator.hasNext()) {
            final Map.Entry<String, Integer> next =
                    entryIterator.next();
            if (next.getKey().equals("Second")) {
                System.out.println("Calling remove on" +
                        " entryIterator: ");
                entryIterator.remove();
            }
        }

        final Iterator<Integer> valueIterator =
                map.values().iterator();
        while (valueIterator.hasNext()) {
            final Integer next = valueIterator.next();
            if (next == 3) {
                System.out.println("Calling remove on" +
                        " valueIterator: ");
                valueIterator.remove();
            }
        }
    }

    private static class MyListener implements
            MapChangeListener<String, Integer> {
        @Override
        public void onChanged(
                Change<? extends String, ? extends Integer>
                        change) {
            System.out.println("\tmap = " + change.getMap());
            System.out.println(prettyPrint(change));
        }

        private String prettyPrint(
                Change<? extends String, ? extends Integer>
                        change) {
            StringBuilder sb =
                    new StringBuilder("\tChange event" +
                            " data:\n");
            sb.append("\t\tWas added: ")
                    .append(change.wasAdded())
                    .append("\n");
            sb.append("\t\tWas removed: ")
                    .append(change.wasRemoved())
                    .append("\n");
            sb.append("\t\tKey: ")
                    .append(change.getKey())
                    .append("\n");
            sb.append("\t\tValue added: ")
                    .append(change.getValueAdded())
                    .append("\n");
            sb.append("\t\tValue removed: ")
                    .append(change.getValueRemoved())
                    .append("\n");
            return sb.toString();
        }
    }
}