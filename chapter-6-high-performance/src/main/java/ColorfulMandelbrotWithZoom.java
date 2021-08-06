import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ColorfulMandelbrotWithZoom extends GraphicApp {

    private final int MAX_ITERATIONS = 200;
    private double zx, zy, cX, cY, tmp;
    int i;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void setup() {
        width = 1200;
        height = 800;
        var canvas = graphicContext.getCanvas();
        var bp = (BorderPane) canvas.getParent();
        var p = new StackPane(canvas);
        var sp = new ScrollPane(p);

        p.setMinSize(20000, 20000);
        bp.setCenter(null);
        sp.setPrefSize(1200, 800);
        sp.setVvalue(0.5);
        sp.setHvalue(0.5);
        bp.setCenter(sp);
        sp.setOnMouseClicked(e -> {
            double zoom = 0.2;
            double scaleX = canvas.getScaleX();
            double scaleY = canvas.getScaleY();
            if (e.getButton() == MouseButton.SECONDARY && (canvas.getScaleX() > 0.5)) {
                canvas.setScaleX(scaleX - zoom);
                canvas.setScaleY(scaleY - zoom);
            } else if (e.getButton() == MouseButton.PRIMARY) {
                canvas.setScaleX(scaleX + zoom);
                canvas.setScaleY(scaleY + zoom);
            } else if (e.getButton() == MouseButton.MIDDLE) {
                sp.setVvalue(0.5);
                sp.setHvalue(0.5);
                canvas.setScaleY(1);
                canvas.setScaleX(1);
            }
        });
        canvas.setOnMousePressed(canvas.getOnMouseClicked());
        frames(0);
        title("Mandelbrot with color and zoom");
    }

    @Override
    public void draw() {
        long start = System.currentTimeMillis();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                zx = zy = 0;
                // the known range of accepted values for cx and cy
                cX = map(x, 0, width, -2.5, 1.0);
                cY = map(y, 0, height, -1, 1.0);
                i = 0;
                while (zx * zx + zy * zy < 4 && i < MAX_ITERATIONS) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    i++;
                }
                // if it is not exploding to infinite
                if (i < MAX_ITERATIONS) {
                    double newC = ((double) i) / ((double) MAX_ITERATIONS);
                    Color c;
                    if (newC > 0.4)
                        c = Color.color(newC, 0.8, newC);
                    else
                        c = Color.color(0.2, newC, 0.2);
                    graphicContext.getPixelWriter().setColor(x, y, c);
                } else {
                    graphicContext.getPixelWriter().setColor(x, y, Color.BLACK);
                }

            }
        }
        System.out.println("Generating mandelbrot took " + (System.currentTimeMillis() - start) + " ms");
    }

}
