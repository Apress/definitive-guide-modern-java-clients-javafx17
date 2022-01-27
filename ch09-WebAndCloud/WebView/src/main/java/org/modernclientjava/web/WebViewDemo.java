package org.modernclientjava.web;
  
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class WebViewDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.load("https://openjfx.io");
        Scene scene = new Scene(webView, 300, 250);
        primaryStage.setTitle("JavaFX WebView Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
