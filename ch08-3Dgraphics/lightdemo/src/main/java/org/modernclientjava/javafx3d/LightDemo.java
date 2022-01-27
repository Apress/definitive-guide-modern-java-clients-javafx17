package org.modernclientjava.javafx3d;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class LightDemo extends Application {
    private final Model model;

    public LightDemo() {
        model = new Model();
    }

    @Override
    public void start(Stage stage) {
        View view = new View(model);
        stage.setTitle("Light Example");
        stage.setScene(view.scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class Model {
        private final DoubleProperty redLightX = new SimpleDoubleProperty(this, "redLightX", 20.0d);
        private final DoubleProperty redLightY = new SimpleDoubleProperty(this, "redLightY", -15.0d);
        private final DoubleProperty redLightZ = new SimpleDoubleProperty(this, "redLightZ", -20.0d);
        private final DoubleProperty blueLightX = new SimpleDoubleProperty(this, "blueLightX", 15.0d);
        private final DoubleProperty blueLightY = new SimpleDoubleProperty(this, "blueLightY", -15.0d);
        private final DoubleProperty blueLightZ = new SimpleDoubleProperty(this, "blueLightZ", -5.0d);

        public DoubleProperty redLightXProperty() {
            return redLightX;
        }

        public DoubleProperty redLightYProperty() {
            return redLightY;
        }

        public DoubleProperty redLightZProperty() {
            return redLightZ;
        }

        public DoubleProperty blueLightXProperty() {
            return blueLightX;
        }

        public DoubleProperty blueLightYProperty() {
            return blueLightY;
        }

        public DoubleProperty blueLightZProperty() {
            return blueLightZ;
        }
    }

    private static class View {
        public Scene scene;

        public Box box1;
        public Box box2;
        public Box box3;
        public PerspectiveCamera camera;
        public PointLight redLight;
        public PointLight blueLight;

        private View(Model model) {
            box1 = new Box(10, 10, 10);
            box1.setId("Box1");
            box1.getTransforms().add(new Translate(-15, 0, 0));
            box2 = new Box(10, 10, 10);
            box2.setId("Box2");
            box3 = new Box(10, 10, 10);
            box3.setId("Box3");
            box3.getTransforms().add(new Translate(15, 0, 0));

            camera = new PerspectiveCamera(true);

            Rotate rotateX = new Rotate(-20, Rotate.X_AXIS);
            Rotate rotateY = new Rotate(-20, Rotate.Y_AXIS);
            Rotate rotateZ = new Rotate(-20, Rotate.Z_AXIS);
            Translate translateZ = new Translate(0, 0, -60);

            camera.getTransforms().addAll(rotateX, rotateY, rotateZ, translateZ);

            redLight = new PointLight(Color.RED);
            redLight.translateXProperty().bind(model.redLightXProperty());
            redLight.translateYProperty().bind(model.redLightYProperty());
            redLight.translateZProperty().bind(model.redLightZProperty());

            blueLight = new PointLight(Color.BLUE);
            blueLight.translateXProperty().bind(model.blueLightXProperty());
            blueLight.translateYProperty().bind(model.blueLightYProperty());
            blueLight.translateZProperty().bind(model.blueLightZProperty());

            Group group = new Group(new Group(box1, box2, box3),
                    camera, redLight, blueLight);
            SubScene subScene = new SubScene(group, 640, 400, true, SceneAntialiasing.BALANCED);
            subScene.setCamera(camera);

            // Red Light
            Tab redTab = new Tab("Red Light");
            redTab.setClosable(false);
            Rectangle red = new Rectangle(10, 10);
            red.fillProperty().bind(Bindings.when(redLight.lightOnProperty()).then(Color.RED).otherwise(Color.DARKGREY));
            redTab.setGraphic(red);

            CheckBox redLightOn = new CheckBox("Light On/Off");
            redLightOn.setSelected(true);
            redLight.lightOnProperty().bind(redLightOn.selectedProperty());

            Slider redLightXSlider = createSlider(20);
            Slider redLightYSlider = createSlider(-20);
            Slider redLightZSlider = createSlider(-20);
            redLightXSlider.valueProperty().bindBidirectional(model.redLightXProperty());
            redLightYSlider.valueProperty().bindBidirectional(model.redLightYProperty());
            redLightZSlider.valueProperty().bindBidirectional(model.redLightZProperty());

            HBox hbox1 = new HBox(10, new Label("x:"), redLightXSlider,
                    new Label("y:"), redLightYSlider,
                    new Label("z:"), redLightZSlider);
            hbox1.setPadding(new Insets(10, 10, 10, 10));
            hbox1.setAlignment(Pos.CENTER);

            HBox hbox2 = new HBox(10, createScopeToggles(box1), createScopeToggles(box2), createScopeToggles(box3));
            hbox2.setPadding(new Insets(10, 10, 10, 10));
            hbox2.setAlignment(Pos.CENTER);

            VBox redControlPanel = new VBox(10, redLightOn, hbox1, hbox2);
            redControlPanel.setPadding(new Insets(10, 10, 10, 10));
            redControlPanel.setAlignment(Pos.CENTER);
            redTab.setContent(redControlPanel);

            // Blue Light
            Tab blueTab = new Tab("Blue Light");
            blueTab.setClosable(false);
            Rectangle blue = new Rectangle(10, 10);
            blue.fillProperty().bind(Bindings.when(blueLight.lightOnProperty()).then(Color.BLUE).otherwise(Color.DARKGREY));
            blueTab.setGraphic(blue);


            CheckBox blueLightOn = new CheckBox("Light On/Off");
            blueLightOn.setSelected(true);
            blueLight.lightOnProperty().bind(blueLightOn.selectedProperty());

            Slider blueLightXSlider = createSlider(15);
            Slider blueLightYSlider = createSlider(-15);
            Slider blueLightZSlider = createSlider(-15);
            blueLightXSlider.valueProperty().bindBidirectional(model.blueLightXProperty());
            blueLightYSlider.valueProperty().bindBidirectional(model.blueLightYProperty());
            blueLightZSlider.valueProperty().bindBidirectional(model.blueLightZProperty());

            HBox hbox3 = new HBox(10, new Label("x:"), blueLightXSlider,
                    new Label("y:"), blueLightYSlider,
                    new Label("z:"), blueLightZSlider);
            hbox3.setPadding(new Insets(10, 10, 10, 10));
            hbox3.setAlignment(Pos.CENTER);

            HBox hbox4 = new HBox(50, addLightControls(blueLight));
            hbox4.setPadding(new Insets(10, 10, 10, 10));
            hbox4.setAlignment(Pos.CENTER);

            VBox blueControlPanel = new VBox(10, blueLightOn, hbox3, hbox4);
            blueControlPanel.setPadding(new Insets(10, 10, 10, 10));
            blueControlPanel.setAlignment(Pos.CENTER);
            blueTab.setContent(blueControlPanel);

            TabPane tabPane = new TabPane(redTab, blueTab);
            BorderPane borderPane = new BorderPane(subScene, null, null, tabPane, null);
            scene = new Scene(borderPane);
        }

        private Slider createSlider(double value) {
            Slider slider = new Slider(-40, 40, value);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            return slider;
        }

        // since JavaFX 13 -->
        private Pane createScopeToggles(Node node) {
            RadioButton none = new RadioButton("none");
            none.setOnAction(a -> {
                redLight.getScope().remove(node);
                redLight.getExclusionScope().remove(node);
            });

            RadioButton scoped = new RadioButton("scoped");
            scoped.setOnAction(a -> redLight.getScope().add(node));

            RadioButton excluded = new RadioButton("excluded");
            excluded.setOnAction(a -> redLight.getExclusionScope().add(node));
            none.setSelected(true);

            ToggleGroup tg = new ToggleGroup();
            tg.getToggles().addAll(none, scoped, excluded);
            var vBox = new VBox(5, none, scoped, excluded);
            return new HBox(10, new Label(node.getId()), vBox);
        }

        // since JavaFX 16 -->
        private HBox addLightControls(PointLight light) {
            VBox range = createSliderControl("range", light.maxRangeProperty(), 0, 100, light.getMaxRange());
            VBox c = createSliderControl("constant", light.constantAttenuationProperty(), -1, 1, light.getConstantAttenuation());
            VBox lc = createSliderControl("linear", light.linearAttenuationProperty(), -1, 1, light.getLinearAttenuation());
            VBox qc = createSliderControl("quadratic", light.quadraticAttenuationProperty(), -1, 1, light.getQuadraticAttenuation());
            return new HBox(10, range, c, lc, qc);
        }

        private VBox createSliderControl(String name, DoubleProperty property, double min, double max, double start) {
            Slider slider = new Slider(min, max, start);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            property.bindBidirectional(slider.valueProperty());
            TextField tf = new TextField();
            tf.textProperty().bindBidirectional(slider.valueProperty(), new NumberStringConverter());
            tf.setMaxWidth(40);
            return new VBox(5, new Label(name), new HBox(slider, tf));
        }
    }
}
