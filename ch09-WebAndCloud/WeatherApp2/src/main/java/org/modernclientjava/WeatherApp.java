package org.modernclientjava;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.modernclientjava.model.Model;
import org.modernclientjava.model.Weather;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class WeatherApp extends Application {

    private static final String API_KEY = "***************"; // TODO: Add valid API_KEY
    private static final String CITY = "London";

    private ImageView imageView;
    private Label weatherLabel;
    private Label descriptionLabel;
    private Label tempLabel;

    @Override
    public void start(Stage stage) {
        imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.setEffect(new DropShadow());

        Label label = new Label("The weather in " + CITY);

        weatherLabel = new Label();
        descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("desc");
        tempLabel = new Label();
        tempLabel.getStyleClass().add("temp");
        VBox root = new VBox(10, label, imageView, weatherLabel, descriptionLabel, tempLabel);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(WeatherApp.class.getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("The Weather App");
        stage.show();

        retrieveWeather();
    }

    private void retrieveWeather() {
        GluonObservableObject<Model> weather = getWeather();
        weather.setOnFailed(e -> System.out.println("Error: " + e));
        weather.setOnSucceeded(e -> updateModel(weather.get()));
    }

    private void updateModel(Model model) {
        if (model != null) {
            if (!model.getWeather().isEmpty()) {
                Weather w = model.getWeather().get(0);
                try {
                    imageView.setImage(new Image(new URL("http://openweathermap.org/img/wn/" + w.getIcon() + "@2x.png").toURI().toString()));
                } catch (MalformedURLException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
                weatherLabel.setText(w.getMain());
                descriptionLabel.setText(w.getDescription());
            }
            tempLabel.setText(String.format("%.2f ºC - %.1f%%", model.getMain().getTemp() - 273.15, model.getMain().getHumidity()));
        }
    }

    private GluonObservableObject<Model> getWeather() {
        RestClient client = RestClient.create()
                .method("GET")
                .host("http://api.openweathermap.org/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .path("data/2.5/weather")
                .header("accept", "application/json")
                .queryParam("appid", API_KEY)
                .queryParam("q", CITY);

        return DataProvider.retrieveObject(client.createObjectDataReader(Model.class));
    }

}
