package org.modernclients.propertiesandbindings;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class ListChangeEventExample {
    public static void main(String[] args) {
        ObservableList<String> strings =
                FXCollections.observableArrayList();
        strings.addListener(new MyListener());

        System.out.println("Calling addAll(\"Zero\"," +
                " \"One\", \"Two\", \"Three\"): ");
        strings.addAll("Zero", "One", "Two", "Three");

        System.out.println("Calling" +
                " FXCollections.sort(strings): ");
        FXCollections.sort(strings);

        System.out.println("Calling set(1, \"Three_1\"): ");
        strings.set(1, "Three_1");

        System.out.println("Calling setAll(\"One_1\"," +
                " \"Three_1\", \"Two_1\", \"Zero_1\"): ");
        strings.setAll("One_1", "Three_1", "Two_1", "Zero_1");

        System.out.println("Calling removeAll(\"One_1\"," +
                " \"Two_1\", \"Zero_1\"): ");
        strings.removeAll("One_1", "Two_1", "Zero_1");
    }

    private static class MyListener implements
            ListChangeListener<String> {
        @Override
        public void onChanged(
                Change<? extends String> change) {
            System.out.println("\tlist = " +
                    change.getList());
            System.out.println(prettyPrint(change));
        }

        private String prettyPrint(
                Change<? extends String> change) {
            StringBuilder sb =
                    new StringBuilder("\tChange event data:\n");
            int i = 0;
            while (change.next()) {
                sb.append("\t\tcursor = ")
                        .append(i++)
                        .append("\n");

                final String kind =
                        change.wasPermutated() ? "permutated" :
                        change.wasReplaced() ? "replaced" :
                        change.wasRemoved() ? "removed" :
                        change.wasAdded() ? "added" :
                        "none";

                sb.append("\t\tKind of change: ")
                        .append(kind)
                        .append("\n");

                sb.append("\t\tAffected range: [")
                        .append(change.getFrom())
                        .append(", ")
                        .append(change.getTo())
                        .append("]\n");

                if (kind.equals("added") ||
                        kind.equals("replaced")) {
                    sb.append("\t\tAdded size: ")
                            .append(change.getAddedSize())
                            .append("\n");
                    sb.append("\t\tAdded sublist: ")
                            .append(change.getAddedSubList())
                            .append("\n");
                }

                if (kind.equals("removed") ||
                        kind.equals("replaced")) {
                    sb.append("\t\tRemoved size: ")
                            .append(change.getRemovedSize())
                            .append("\n");
                    sb.append("\t\tRemoved: ")
                            .append(change.getRemoved())
                            .append("\n");
                }

                if (kind.equals("permutated")) {
                    StringBuilder permutationSB =
                            new StringBuilder("[");
                    int from = change.getFrom();
                    int to = change.getTo();
                    for (int k = from; k < to; k++) {
                        int permutation =
                                change.getPermutation(k);
                        permutationSB.append(k)
                                .append("->")
                                .append(permutation);
                        if (k < change.getTo() - 1) {
                            permutationSB.append(", ");
                        }
                    }
                    permutationSB.append("]");
                    String permutation =
                            permutationSB.toString();
                    sb.append("\t\tPermutation: ")
                            .append(permutation).append("\n");
                }
            }
            return sb.toString();
        }
    }
}
