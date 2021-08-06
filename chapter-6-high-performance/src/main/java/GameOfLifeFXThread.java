
import javafx.scene.paint.Color;

public class GameOfLifeFXThread extends GraphicApp {

    final int WIDTH = 2500;
    final int HEIGHT = 2500;
    final int CELL_SIZE = 5;
    boolean currentGeneration[][];
    int columns = WIDTH / CELL_SIZE;
    int rows = HEIGHT / CELL_SIZE;
    private GameOfLife gameOfLife;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void setup() {
        width = WIDTH;
        height = HEIGHT;
        gameOfLife = new GameOfLife(columns, rows, CELL_SIZE);
        currentGeneration = gameOfLife.newCells();
        background(Color.DARKGRAY);
        title("Game of Life");
        frames(5);
    }

    @Override
    public void draw() {
        long initial = System.currentTimeMillis();
        gameOfLife.drawCells(currentGeneration, graphicContext);
        System.out.println("Time to render " +
                           (System.currentTimeMillis() - initial));
        initial = System.currentTimeMillis();
        currentGeneration = gameOfLife.newGeneration(currentGeneration);
        // test with parallel stream
//        currentGeneration = gameOfLife.newGenerationParallel(currentGeneration);
        System.out.println("Time to calculate new generation: " + (System.currentTimeMillis() - initial));
    }
}
