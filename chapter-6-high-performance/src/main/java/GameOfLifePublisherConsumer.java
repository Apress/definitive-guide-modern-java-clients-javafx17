
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;

public class GameOfLifePublisherConsumer extends GraphicApp {

    final int WIDTH = 2500;
    final int HEIGHT = 2500;
    final int CELL_SIZE = 1;
    boolean currentGeneration[][];
    int columns = WIDTH / CELL_SIZE;
    int rows = HEIGHT / CELL_SIZE;
    // this is the desired number of frames
    int numberOfFramesPerSecond = 0;
    private GameOfLife gameOfLife;
    ConcurrentLinkedQueue<boolean[][]> cellsQueue;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void setup() {
        cellsQueue = new ConcurrentLinkedQueue<>();
        width = WIDTH;
        height = HEIGHT;
        gameOfLife = new GameOfLife(columns, rows, CELL_SIZE);
        currentGeneration = gameOfLife.newCells();
        Task<Void> producerTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (true) {
                    cellsQueue.add(currentGeneration);
                    currentGeneration = gameOfLife.newGeneration(currentGeneration);
                }
            }
        };
        Task<Void> consumerTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (true) {
                    while (!cellsQueue.isEmpty()) {
                        boolean[][] data = cellsQueue.poll();
                        Platform.runLater(() -> {
                            // we need to draw the background because we are not using draw loop anymore
                            graphicContext.setFill(Color.LIGHTGRAY);
                            graphicContext.fillRect(0, 0,
                                                    width, height);
                            gameOfLife.drawCells(data, graphicContext);
                        });
                        if (numberOfFramesPerSecond > 0) {
                            Thread.sleep(1000 / numberOfFramesPerSecond);
                        }
                    }
                }
            }
        };
        Thread producerThread = new Thread(producerTask);
        producerThread.setDaemon(true);
        Thread consumerThread = new Thread(consumerTask);
        consumerThread.setDaemon(true);
        producerThread.start();
        consumerThread.start();
        frames(0);
        title("Game of Life Using High-Density Data Pattern");
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }
}