import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CanvasRandomColors extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        var canvas = new Canvas(WIDTH, HEIGHT);
        var gc = canvas.getGraphicsContext2D();
        
        canvas.setOnMouseClicked(e -> random(gc));

        stage.setScene(new Scene(new StackPane(canvas), WIDTH, HEIGHT));
        stage.setTitle("Canvas Random Colors");
        stage.show();
        random(gc);
    }

    private void random(GraphicsContext gc) {
        for (int i = 0; i < gc.getCanvas().getWidth(); i++) {
            for (int j = 0; j < gc.getCanvas().getHeight(); j++) {
                gc.getPixelWriter().setColor(i, j,
                                             Color.color(Math.random(),
                                                         Math.random(),
                                                         Math.random()));
            }
        }
    }
}
