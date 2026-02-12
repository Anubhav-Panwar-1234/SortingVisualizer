import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Sorting Algorithm Visualizer - DAA Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);

        SortingPanel panel = new SortingPanel();
        frame.add(panel, BorderLayout.CENTER);

        JPanel controls = new JPanel();

        String[] algorithms = {
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort"
        };

        JComboBox<String> algoBox = new JComboBox<>(algorithms);
        JButton generateBtn = new JButton("Generate");
        JButton startBtn = new JButton("Start");

        JLabel comparisonLabel = new JLabel("Comparisons: 0");
        JLabel swapLabel = new JLabel("Swaps: 0");

        controls.add(new JLabel("Algorithm: "));
        controls.add(algoBox);
        controls.add(generateBtn);
        controls.add(startBtn);
        controls.add(comparisonLabel);
        controls.add(swapLabel);

        frame.add(controls, BorderLayout.SOUTH);

        generateBtn.addActionListener(e -> panel.generateArray());

        startBtn.addActionListener(e -> {
            String selected = (String) algoBox.getSelectedItem();
            panel.startSorting(selected, comparisonLabel, swapLabel);
        });

        frame.setVisible(true);
    }
}
