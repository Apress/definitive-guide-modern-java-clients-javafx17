package org.modernclients.propertiesandbindings;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

public class SelectBindingExample {
    public static void main(String[] args) {
        ObjectProperty<Lighting> root =
                new SimpleObjectProperty<>();

        final ObjectBinding<Color> colorBinding =
                Bindings.select(root, "light", "color");

        colorBinding.addListener((o, oldValue, newValue) ->
                System.out.println("\tThe color changed:\n" +
                        "\t\told color = " + oldValue +
                        ",\n\t\tnew color = " + newValue));

        System.out.println("firstLight is black.");
        Light firstLight = new Light.Point();
        firstLight.setColor(Color.BLACK);

        System.out.println("secondLight is white.");
        Light secondLight = new Light.Point();
        secondLight.setColor(Color.WHITE);

        System.out.println("firstLighting has firstLight.");
        Lighting firstLighting = new Lighting();
        firstLighting.setLight(firstLight);

        System.out.println("secondLighting has secondLight.");
        Lighting secondLighting = new Lighting();
        secondLighting.setLight(secondLight);

        System.out.println("Making root observe" +
                " firstLighting.");
        root.set(firstLighting);

        System.out.println("Making root observe" +
                " secondLighting.");
        root.set(secondLighting);

        System.out.println("Changing secondLighting's" +
                " light to firstLight");
        secondLighting.setLight(firstLight);

        System.out.println("Changing firstLight's" +
                " color to red");
        firstLight.setColor(Color.RED);
    }
}
