import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // ===== FRAME =====
        JFrame frame = new JFrame("Sorting Visualizer");

        // ===== ICON SETUP =====
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");

        // Windows/Linux
        frame.setIconImage(icon);

        // macOS Dock Icon ⭐ IMPORTANT
        try {
            Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(icon);
        } catch (Exception e) {
            System.out.println("Taskbar not supported");
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // ===== OBJECT CREATION =====
        Analyzer analyzer = new Analyzer();
        GUI gui = new GUI();
        Visualizer visualizer = new Visualizer(analyzer);

        // ===== ADD COMPONENTS =====
        frame.add(visualizer, BorderLayout.CENTER);
        frame.add(gui, BorderLayout.SOUTH);

        // ===== INITIAL COMPLEXITY DISPLAY =====
        String initialAlgo = gui.getSelectedAlgorithm();
        String initialComplexity = "Time: " + analyzer.getTimeComplexity(initialAlgo)
                + " | Space: " + analyzer.getSpaceComplexity(initialAlgo)
                + " | Type: " + analyzer.getAlgorithmType(initialAlgo);

        gui.getComplexityLabel().setText(initialComplexity);

        // ===== ALGORITHM CHANGE LISTENER =====
        gui.getAlgorithmBox().addActionListener(e -> {

            String algo = gui.getSelectedAlgorithm();

            String complexity = "Time: " + analyzer.getTimeComplexity(algo)
                    + " | Space: " + analyzer.getSpaceComplexity(algo)
                    + " | Type: " + analyzer.getAlgorithmType(algo);

            gui.getComplexityLabel().setText(complexity);
        });

        // ===== BUTTON ACTIONS =====

        gui.getGenerateButton().addActionListener(e -> {
            visualizer.generateArray();
        });

        gui.getInputButton().addActionListener(e -> {
            int[] arr = analyzer.getUserInputArray();
            visualizer.setArray(arr);
        });

        gui.getStartButton().addActionListener(e -> {
            String algo = gui.getSelectedAlgorithm();
            visualizer.startSorting(algo, gui);
            gui.togglePauseText(false);
        });

        gui.getPauseButton().addActionListener(e -> {

            visualizer.togglePause();

            if (gui.getPauseButton().getText().equals("Pause")) {
                gui.togglePauseText(true);
            } else {
                gui.togglePauseText(false);
            }
        });

        // ===== SHOW FRAME =====
        frame.setVisible(true);
    }
}