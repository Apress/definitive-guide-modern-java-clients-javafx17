import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BouncingBalls extends GraphicApp {

    private static final int TOTAL_BALLS = 20;
    List<Ball> balls = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void setup() {
        Random random = new Random();
        for (int i = 0; i < TOTAL_BALLS; i++) {
            Ball ball = new Ball();
            ball.circ = random.nextInt(100) + 10;
            ball.x = random.nextInt(width - ball.circ);
            ball.y = random.nextInt(height - ball.circ);
            ball.xDir = random.nextBoolean() ? 1 : -1;
            ball.yDir = random.nextBoolean() ? 1 : -1;
            ball.color = Color.color(Math.random(),
                                     Math.random(), Math.random());
            balls.add(ball);
        }
        background(Color.DARKCYAN);
    }

    @Override
    public void draw() {
        for (Ball ball : balls) {
            ball.update();
            ball.draw(graphicContext);
        }
    }

    public class Ball {

        int x, y, xDir = 1, yDir = 1, circ;
        Color color;

        public void update() {
            if (x + circ > width || x < 0) {
                xDir *= -1;
            }
            if (y + circ > height || y < 0) {
                yDir *= -1;
            }
            x += 5 * xDir;
            y += 5 * yDir;
        }

        public void draw(GraphicsContext gc) {
            gc.setLineWidth(10);
            gc.setFill(color);
            gc.fillOval(x, y, circ, circ);
            gc.setStroke(Color.BLACK);
            gc.strokeOval(x, y, circ, circ);
        }
    }
}
