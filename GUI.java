import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {

    private JComboBox<String> algoBox;
    private JButton generateBtn;
    private JButton startBtn;
    private JButton pauseBtn;
    private JButton inputBtn;

    private JSlider speedSlider;

    private JLabel compLabel;
    private JLabel swapLabel;
    private JLabel timeLabel;
    private JLabel complexityLabel;

    public GUI() {

        // ⭐ CHANGE: 2 ROW LAYOUT
        setLayout(new GridLayout(2, 1));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        // ===== ALGORITHM DROPDOWN =====
        String[] algorithms = {
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Merge Sort",
                "Quick Sort",
                "Heap Sort",
                "Counting Sort",
                "Radix Sort",
                "Bucket Sort"
        };

        algoBox = new JComboBox<>(algorithms);

        // ===== BUTTONS =====
        generateBtn = new JButton("Generate");
        startBtn = new JButton("Start");
        pauseBtn = new JButton("Pause");
        inputBtn = new JButton("Custom Input");

        // ===== SPEED SLIDER =====
        speedSlider = new JSlider(1, 100, 40);
        speedSlider.setPreferredSize(new Dimension(120, 40));

        // ===== LABELS =====
        compLabel = new JLabel("Comparisons: 0");
        swapLabel = new JLabel("Swaps: 0");
        timeLabel = new JLabel("Time: 0 ms");
        complexityLabel = new JLabel("Complexity: -");

        // ===== TOP ROW =====
        topPanel.add(new JLabel("Algorithm: "));
        topPanel.add(algoBox);
        topPanel.add(generateBtn);
        topPanel.add(inputBtn);
        topPanel.add(startBtn);
        topPanel.add(pauseBtn);

        // ===== BOTTOM ROW =====
        bottomPanel.add(new JLabel("Speed: "));
        bottomPanel.add(speedSlider);
        bottomPanel.add(compLabel);
        bottomPanel.add(swapLabel);
        bottomPanel.add(timeLabel);
        bottomPanel.add(complexityLabel);

        // ===== ADD TO MAIN PANEL =====
        add(topPanel);
        add(bottomPanel);
    }

    // ===== GET ALGO BOX =====
    public JComboBox<String> getAlgorithmBox() {
        return algoBox;
    }

    // ===== TOGGLE PAUSE TEXT =====
    public void togglePauseText(boolean isPaused) {
        pauseBtn.setText(isPaused ? "Resume" : "Pause");
    }

    // ===== GETTERS =====
    public String getSelectedAlgorithm() {
        return (String) algoBox.getSelectedItem();
    }

    public int getSpeed() {
        return speedSlider.getValue();
    }

    public JButton getGenerateButton() {
        return generateBtn;
    }

    public JButton getStartButton() {
        return startBtn;
    }

    public JButton getPauseButton() {
        return pauseBtn;
    }

    public JButton getInputButton() {
        return inputBtn;
    }

    public JLabel getCompLabel() {
        return compLabel;
    }

    public JLabel getSwapLabel() {
        return swapLabel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JLabel getComplexityLabel() {
        return complexityLabel;
    }
}