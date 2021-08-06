import javafx.scene.paint.Color;

public class SimplestMandelbrot extends GraphicApp {

    private final int MAX_ITERATIONS = 100;
    private double zx, zy, cX, cY, tmp;
    int i;
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void setup() {
        width = 1200;
        height = 800;
        frames(0);
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
                    graphicContext.getPixelWriter().setColor(x, y, Color.WHITE);
                } else {
                    graphicContext.getPixelWriter().setColor(x, y, Color.BLACK);
                }
            }
        }
        System.out.println("Generating mandelbrot took " + (System.currentTimeMillis() - start) + " ms");
    }

}
