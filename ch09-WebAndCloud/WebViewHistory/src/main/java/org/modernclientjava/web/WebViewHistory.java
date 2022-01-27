package org.modernclientjava.web;
  
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory.Entry;

public class WebViewHistory extends Application {
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
        Menu historyMenu = new Menu("History");
        engine.getHistory().getEntries().addListener((ListChangeListener.Change<? extends Entry> c) -> {
            c.next();
            for (Entry e: c.getAddedSubList()) {
                for(MenuItem i: historyMenu.getItems()){
                    if (i.getId().equals(e.getUrl())){
                        historyMenu.getItems().remove(i);
                    }
                }
            }
            for (Entry e: c.getAddedSubList()) {
                final MenuItem menuItem = new MenuItem(e.getUrl());
                menuItem.setId(e.getUrl());
                menuItem.setOnAction(a->engine.load(e.getUrl()));
                historyMenu.getItems().add(menuItem);
            }
        });
        menuBar.getMenus().addAll(navigateMenu, historyMenu);
        borderPane.setTop(menuBar);
        Scene scene = new Scene(borderPane, 640, 400);
        primaryStage.setTitle("JavaFX WebView Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
