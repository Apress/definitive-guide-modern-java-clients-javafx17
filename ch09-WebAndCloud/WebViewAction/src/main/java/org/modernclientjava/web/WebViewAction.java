package org.modernclientjava.web;
  
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class WebViewAction extends Application {
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        WebEngine engine = webView.getEngine();
        engine.load("https://openjfx.io ");
        BorderPane borderPane= new BorderPane(webView);
        MenuBar menuBar = new MenuBar();
        final Menu navigateMenu = new Menu("Navigate");
        MenuItem home = new MenuItem("Home");
        navigateMenu.getItems().addAll(home);
        home.setOnAction(e -> engine.load("https://github.com/openjdk/jfx"));
        menuBar.getMenus().add(navigateMenu);
        borderPane.setTop(menuBar);
        Scene scene = new Scene(borderPane, 640, 400);
        primaryStage.setTitle("JavaFX WebView Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
