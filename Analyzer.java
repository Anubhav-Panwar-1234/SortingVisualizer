import javax.swing.*;
import java.util.Random;

public class Analyzer {

    private int comparisons;
    private int swaps;
    private long startTime;

    // ===== RESET =====
    public void reset() {
        comparisons = 0;
        swaps = 0;
    }

    // ===== RANDOM DATA GENERATION =====
    public int[] generateArray(int size, int maxValue) {
        Random rand = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(maxValue);
        }

        return array;
    }

    // ===== USER INPUT FEATURE =====
    public int[] getUserInputArray() {

        String input = JOptionPane.showInputDialog(
                null,
                "Enter numbers separated by space:",
                "Custom Input",
                JOptionPane.PLAIN_MESSAGE
        );

        if (input == null || input.trim().isEmpty()) {
            return generateArray(50, 400);
        }

        String[] parts = input.trim().split("\\s+");
        int[] array = new int[parts.length];

        try {
            for (int i = 0; i < parts.length; i++) {
                array[i] = Integer.parseInt(parts[i]);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid input! Using random array.");
            return generateArray(50, 400);
        }

        return array;
    }

    // ===== TIMER =====
    public void startTimer() {
        startTime = System.nanoTime();
    }

    public long endTimer() {
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
    }

    // ===== COUNTERS =====
    public void incComparisons() {
        comparisons++;
    }

    public void incSwaps() {
        swaps++;
    }

    public int getComparisons() {
        return comparisons;
    }

    public int getSwaps() {
        return swaps;
    }

    // ===== UPDATE LABELS =====
    public void updateLabels(JLabel compLabel, JLabel swapLabel) {
        SwingUtilities.invokeLater(() -> {
            compLabel.setText("Comparisons: " + comparisons);
            swapLabel.setText("Swaps: " + swaps);
        });
    }

    // ===== TIME COMPLEXITY =====
    public String getTimeComplexity(String algorithm) {
        switch (algorithm) {

            case "Bubble Sort":
            case "Selection Sort":
                return "O(n²)";

            case "Insertion Sort":
                return "Best: O(n), Worst: O(n²)";

            case "Merge Sort":
                return "O(n log n)";

            case "Quick Sort":
                return "Best: O(n log n), Worst: O(n²)";

            case "Heap Sort":
                return "O(n log n)";

            case "Counting Sort":
                return "O(n + k)";

            case "Radix Sort":
                return "O(nk)";

            case "Bucket Sort":
                return "Best: O(n + k), Worst: O(n²)";

            default:
                return "-";
        }
    }

    // ===== SPACE COMPLEXITY =====
    public String getSpaceComplexity(String algorithm) {
        switch (algorithm) {

            case "Merge Sort":
                return "O(n)";

            case "Quick Sort":
                return "O(log n)";

            case "Heap Sort":
                return "O(1)";

            case "Counting Sort":
                return "O(k)";

            case "Radix Sort":
                return "O(n + k)";

            case "Bucket Sort":
                return "O(n + k)";

            default:
                return "O(1)";
        }
    }

    // ===== ALGORITHM TYPE ===== ⭐ NEW
    public String getAlgorithmType(String algorithm) {
        switch (algorithm) {

            case "Bubble Sort":
            case "Selection Sort":
            case "Insertion Sort":
                return "Comparison-Based";

            case "Merge Sort":
            case "Quick Sort":
                return "Divide & Conquer";

            case "Heap Sort":
                return "Heap-Based";

            case "Counting Sort":
            case "Radix Sort":
                return "Non-Comparison";

            case "Bucket Sort":
                return "Distribution Sort";

            default:
                return "-";
        }
    }
}