import javax.swing.*;
import java.awt.*;

public class Visualizer extends JPanel {

    private int[] array;

    private int currentIndex = -1;
    private int comparingIndex = -1;

    private boolean sorting = false;
    private boolean paused = false;
    private boolean stopRequested = false;   // ⭐ NEW

    private Analyzer analyzer;

    public Visualizer(Analyzer analyzer) {
        this.analyzer = analyzer;
        generateArray();
    }

    // ===== ARRAY GENERATION =====
    public void generateArray() {

        stopRequested = true;
        sorting = false;
        paused = false;

        // ⭐ reset old highlights
        currentIndex = -1;
        comparingIndex = -1;

        array = analyzer.generateArray(50, 400);
        repaint();
    }

    // ===== SET CUSTOM ARRAY ===== ⭐ NEW
    public void setArray(int[] arr) {

        stopRequested = true;
        sorting = false;
        paused = false;

        currentIndex = -1;
        comparingIndex = -1;

        this.array = arr;
        repaint();
    }

    // ===== START SORTING =====
    public void startSorting(String algorithm, GUI gui) {

        if (sorting) return;

        sorting = true;
        paused = false;
        stopRequested = false;   // ⭐ reset stop flag

        analyzer.reset();
        analyzer.startTimer();

        currentIndex = -1;
        comparingIndex = -1;

        new Thread(() -> {

            try {
                SortingAlgorithms.sort(array, algorithm, this, analyzer, gui);
            } catch (InterruptedException e) {
                // stopped intentionally → ignore
            }

            long time = analyzer.endTimer();
            gui.getTimeLabel().setText("Time: " + time + " ms");

            currentIndex = -1;
            comparingIndex = -1;
            sorting = false;

            repaint();

        }).start();
    }

    // ===== PAUSE / RESUME =====
    public void togglePause() {
        paused = !paused;
    }

    public void checkPause() throws InterruptedException {

        while (paused) {
            Thread.sleep(50);
        }

        if (stopRequested) throw new InterruptedException(); // ⭐ critical fix
    }

    // ===== VISUAL HELPERS =====
    public void setIndices(int i, int j) {
        currentIndex = i;
        comparingIndex = j;
    }

    public void delay(GUI gui) throws InterruptedException {

        if (stopRequested) throw new InterruptedException();

        int dynamicSpeed = gui.getSpeed(); // ⭐ LIVE VALUE
        Thread.sleep(101 - dynamicSpeed);
    }

    public void update(GUI gui) {

        analyzer.updateLabels(gui.getCompLabel(), gui.getSwapLabel());

        String algo = gui.getSelectedAlgorithm();
        String complexity = "Time: " + analyzer.getTimeComplexity(algo)
                + " | Space: " + analyzer.getSpaceComplexity(algo)
                + " | Type: " + analyzer.getAlgorithmType(algo);

        gui.getComplexityLabel().setText(complexity);

        repaint();
    }

    public int[] getArray() {
        return array;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    // ===== DRAWING =====
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (array == null || array.length == 0) return;

        int width = getWidth();
        int height = getHeight();

        int barWidth = width / array.length;

        // ⭐ find max value in array
        int max = array[0];
        for (int val : array) {
            if (val > max) max = val;
        }

        for (int i = 0; i < array.length; i++) {

            // ⭐ scale height based on max value
            int scaledHeight = (int) ((double) array[i] / max * (height - 20));

            // optional: minimum visible height
            scaledHeight = Math.max(scaledHeight, 5);

            if (i == currentIndex || i == comparingIndex) {
                g.setColor(Color.RED);
            } else {
                g.setColor(new Color(90, 0, 255));
            }

            g.fillRect(
                    i * barWidth,
                    height - scaledHeight,
                    barWidth - 2,
                    scaledHeight
            );
        }
    }
}