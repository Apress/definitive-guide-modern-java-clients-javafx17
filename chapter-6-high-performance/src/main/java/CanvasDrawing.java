import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CanvasDrawing extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        ctx.setLineWidth(10);
        canvas.setOnMousePressed(e -> ctx.beginPath());
        canvas.setOnMouseDragged(e -> {
            ctx.lineTo(e.getX(), e.getY());
            ctx.stroke();
        });
        canvas.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                clear(ctx);
            }
        });
        stage.setTitle("Drawing on Canvas");
        stage.setScene(new Scene(new StackPane(canvas), WIDTH, HEIGHT));
        stage.show();
        clear(ctx);
    }

    public void clear(GraphicsContext ctx) {
        ctx.setFill(Color.DARKBLUE);
        ctx.fillRect(0, 0, WIDTH, HEIGHT);
        ctx.setStroke(Color.ALICEBLUE);
    }
}
