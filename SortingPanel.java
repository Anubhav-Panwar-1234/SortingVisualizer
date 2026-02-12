import java.awt.*;
import javax.swing.*;

class SortingPanel extends JPanel {

    private int[] array;
    private boolean sorting = false;
    private boolean isSorted = false;

    private int currentIndex = -1;
    private int comparingIndex = -1;

    private int comparisons = 0;
    private int swaps = 0;

    public SortingPanel() {
        generateArray();
    }

    public void generateArray() {
        if (sorting) return;

        isSorted = false;
        comparisons = 0;
        swaps = 0;

        array = new int[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 400);
        }

        repaint();
    }

    public void startSorting(String algorithm,
                             JLabel compLabel,
                             JLabel swapLabel) {

        if (sorting) return;

        sorting = true;
        isSorted = false;
        comparisons = 0;
        swaps = 0;

        new Thread(() -> {

            long startTime = System.nanoTime();

            try {

                switch (algorithm) {
                    case "Bubble Sort":
                        bubbleSort(compLabel, swapLabel);
                        break;
                    case "Selection Sort":
                        selectionSort(compLabel, swapLabel);
                        break;
                    case "Insertion Sort":
                        insertionSort(compLabel, swapLabel);
                        break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime) / 1_000_000;

            System.out.println("Execution Time: " + timeTaken + " ms");

            currentIndex = -1;
            comparingIndex = -1;
            sorting = false;
            isSorted = true;

            repaint();

        }).start();
    }

    private void bubbleSort(JLabel compLabel,
                            JLabel swapLabel)
            throws InterruptedException {

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {

                currentIndex = j;
                comparingIndex = j + 1;

                comparisons++;

                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swaps++;
                }

                updateLabels(compLabel, swapLabel);
                repaint();
                Thread.sleep(40);
            }
        }
    }

    private void selectionSort(JLabel compLabel,
                               JLabel swapLabel)
            throws InterruptedException {

        for (int i = 0; i < array.length - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {

                currentIndex = minIndex;
                comparingIndex = j;

                comparisons++;

                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }

                updateLabels(compLabel, swapLabel);
                repaint();
                Thread.sleep(40);
            }

            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
            swaps++;
        }
    }

    private void insertionSort(JLabel compLabel,
                               JLabel swapLabel)
            throws InterruptedException {

        for (int i = 1; i < array.length; i++) {

            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {

                currentIndex = j;
                comparingIndex = i;

                comparisons++;

                array[j + 1] = array[j];
                j--;
                swaps++;

                updateLabels(compLabel, swapLabel);
                repaint();
                Thread.sleep(40);
            }

            array[j + 1] = key;
        }
    }

    private void updateLabels(JLabel compLabel,
                              JLabel swapLabel) {

        SwingUtilities.invokeLater(() -> {
            compLabel.setText("Comparisons: " + comparisons);
            swapLabel.setText("Swaps: " + swaps);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int barWidth = width / array.length;

        for (int i = 0; i < array.length; i++) {

            if (isSorted) {
                g.setColor(Color.GREEN);
            }
            else if (i == currentIndex || i == comparingIndex) {
                g.setColor(Color.RED);
            }
            else {
                g.setColor(new Color(90, 0, 255));
            }

            g.fillRect(i * barWidth,
                    getHeight() - array[i],
                    barWidth - 2,
                    array[i]);
        }
    }
}
