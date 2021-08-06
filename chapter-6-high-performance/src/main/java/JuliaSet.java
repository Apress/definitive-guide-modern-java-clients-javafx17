import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class JuliaSet extends GraphicApp {

    private final static int MAX_ITERATIONS = 60;
    private final static int TOTAL_FRAMES = 10;
    private double zx, zy, tmp;
    int i;
    private JuliaSet.JuliaSetConf conf;
    private BooleanProperty running = new SimpleBooleanProperty(false);
    private int totalIterations;
    private Runnable updateConf;
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void setup() {
        width = 1200;
        height = 800;
        conf = new JuliaSetConf();
        totalIterations = MAX_ITERATIONS;
        setBottom(createConfPanel());
        frames(0);
    }

    @Override
    public void draw() {
        running.set(true);
        totalIterations++;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                zx = zy = 0;
                zx = 1.5 * (x - width / 2) / (0.5 * width);
                zy = (y - height / 2) / (0.5 * height);
                i = 0;
                while (zx * zx + zy * zy < 4 && i < totalIterations) {
                    tmp = zx * zx - zy * zy + conf.cx;
                    zy = 2.0 * zx * zy + conf.ci;
                    zx = tmp;
                    i++;
                }
                Color c = conf.infinityColor;
                if (i < totalIterations) {
                    double newC = ((double) i) / ((double) totalIterations);
                    c = getColor(newC);
                }
                graphicContext.getPixelWriter().setColor(x, y, c);
            }
        }
        if (totalIterations > conf.maxIterations) {
            running.set(false);
            frames(0);
        }
    }

    private Color getColor(double newC) {
        double r = newC, g = newC, b = newC;
        if (newC > conf.threshold) {
            if (!conf.computedLighterR)
                r = conf.lighterR;
            if (!conf.computedLighterG)
                g = conf.lighterG;
            if (!conf.computedLighterB)
                b = conf.lighterB;
        } else {
            if (!conf.computedDarkerR)
                r = conf.darkerR;
            if (!conf.computedDarkerG)
                g = conf.darkerG;
            if (!conf.computedDarkerB)
                b = conf.darkerB;
        }
        return Color.color(r, g, b);
    }

    public static class JuliaSetConf {

        public double threshold = 0.8;
        public double lighterR = 0.7;
        public double lighterG = 0.7;
        public double lighterB = 0.7;
        public double darkerR = 0.3;
        public double darkerG = 0.3;
        public double darkerB = 0.3;
        public double cx = -0.70176;
        public double ci = -0.3842;
        public boolean computedLighterR = true;
        public boolean computedLighterG = true;
        public boolean computedLighterB = true;
        public boolean computedDarkerR = true;
        public boolean computedDarkerG = true;
        public boolean computedDarkerB = true;
        public Color infinityColor = Color.GOLDENROD;
        public int maxIterations = MAX_ITERATIONS / 2;
    }

    private Node createConfPanel() {
        VBox vbConf = new VBox(5);
        Slider spLigherR = slider(conf.lighterR);
        Slider spLigherG = slider(conf.lighterG);
        Slider spLigherB = slider(conf.lighterB);
        CheckBox chkUseComputedLighterR = checkBox();
        CheckBox chkUseComputedLighterG = checkBox();
        CheckBox chkUseComputedLighterB = checkBox();
        vbConf.getChildren().add(new HBox(10, new Label("Lighter Colors"),
                                          spLigherR, chkUseComputedLighterR, spLigherG,
                                          chkUseComputedLighterG, spLigherB, chkUseComputedLighterB));
        Slider spDarkerR = slider(conf.darkerR);
        Slider spDarkerG = slider(conf.darkerG);
        Slider spDarkerB = slider(conf.darkerB);
        CheckBox chkUseComputedDarkerR = checkBox();
        CheckBox chkUseComputedDarkerG = checkBox();
        CheckBox chkUseComputedDarkerB = checkBox();
        vbConf.getChildren().add(new HBox(10, new Label("Darker Colors"),
                                          spDarkerR, chkUseComputedDarkerR, spDarkerG,
                                          chkUseComputedDarkerG, spDarkerB, chkUseComputedDarkerB));
        Slider sldThreshold = slider(conf.threshold);
        Spinner<Integer> spMaxIterations = new Spinner<>(10, MAX_ITERATIONS, MAX_ITERATIONS / 2);
        spMaxIterations.valueProperty().addListener(c -> updateConf.run());
        ColorPicker clInifinity = new ColorPicker(conf.infinityColor);
        clInifinity.valueProperty().addListener(c -> updateConf.run());
        HBox hbGeneral = new HBox(5, new Label("Threshold"), sldThreshold,
                                  new Label("Inner Color"), clInifinity,
                                  new Label("Iterations"), spMaxIterations);
        hbGeneral.setAlignment(Pos.CENTER_LEFT);
        vbConf.getChildren().add(hbGeneral);
        Slider sldX = slider(-1, 1.0, conf.cx);
        sldX.setMinSize(300, 10);
        Slider sldI = slider(-1, 1.0, conf.ci);
        sldI.setMinSize(300, 10);
        Button btnRun = new Button("Animate");
        updateConf = () -> {
            conf.lighterR = spLigherR.getValue();
            conf.lighterG = spLigherG.getValue();
            conf.lighterB = spLigherB.getValue();
            conf.darkerR = spDarkerR.getValue();
            conf.darkerG = spDarkerG.getValue();
            conf.darkerB = spDarkerB.getValue();
            conf.threshold = sldThreshold.getValue();
            conf.computedLighterR = chkUseComputedLighterR.isSelected();
            conf.computedLighterG = chkUseComputedLighterG.isSelected();
            conf.computedLighterB = chkUseComputedLighterB.isSelected();
            conf.computedDarkerR =
                    conf.computedDarkerG = chkUseComputedDarkerG.isSelected();
            conf.computedDarkerB = chkUseComputedDarkerB.isSelected();
            conf.cx = sldX.getValue();
            conf.ci = sldI.getValue();
            conf.infinityColor = clInifinity.getValue();
            conf.maxIterations = spMaxIterations.getValue();
            totalIterations = conf.maxIterations;
            frames(TOTAL_FRAMES);
        };
        btnRun.setOnAction(e -> {
            updateConf.run();
            totalIterations = 1;
        });
        HBox hbSet = new HBox(5, new Label("cX"), sldX, new Label("cI"), sldI, btnRun);
        vbConf.getChildren().add(hbSet);
        TitledPane pnConf = new TitledPane("Configuration", vbConf);
        pnConf.setExpanded(true);
        pnConf.setCollapsible(false);
        pnConf.disableProperty().bind(running);
        return pnConf;
    }

    private CheckBox checkBox() {
        CheckBox checkBox = new CheckBox("Auto");
        checkBox.setSelected(true);
        checkBox.selectedProperty().addListener(c -> updateConf.run());
        return checkBox;
    }

    private Slider slider(double d) {
        return slider(0.0, 1.0, d);
    }

    private Slider slider(double min, double max, double d) {
        Slider slider = new Slider(min, max, d);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.1);
        slider.valueProperty().addListener(c -> updateConf.run());
        return slider;
    }

}
