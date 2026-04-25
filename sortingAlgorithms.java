public class SortingAlgorithms {

    public static void sort(int[] array,
                            String algorithm,
                            Visualizer v,
                            Analyzer a,
                            GUI gui)
            throws InterruptedException {

        switch (algorithm) {
            case "Bubble Sort":
                bubbleSort(array, v, a, gui);
                break;
            case "Selection Sort":
                selectionSort(array, v, a, gui);
                break;
            case "Insertion Sort":
                insertionSort(array, v, a, gui);
                break;
            case "Merge Sort":
                mergeSort(array, 0, array.length - 1, v, a, gui);
                break;
            case "Quick Sort":
                quickSort(array, 0, array.length - 1, v, a, gui);
                break;
            case "Heap Sort":
                heapSort(array, v, a, gui);
                break;
            case "Counting Sort":
                countingSort(array, v, a, gui);
                break;
            case "Radix Sort":
                radixSort(array, v, a, gui);
                break;
            case "Bucket Sort":
                bucketSort(array, v, a, gui);
                break;
        }
    }

    // ===== BUBBLE =====
    private static void bubbleSort(int[] arr, Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {

                v.checkPause();
                v.setIndices(j, j + 1);
                a.incComparisons();

                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    a.incSwaps();
                }

                v.update(gui);
                v.delay(gui);
            }
        }
    }

    // ===== SELECTION =====
    private static void selectionSort(int[] arr, Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        for (int i = 0; i < arr.length - 1; i++) {

            int min = i;

            for (int j = i + 1; j < arr.length; j++) {

                v.checkPause();
                v.setIndices(min, j);
                a.incComparisons();

                if (arr[j] < arr[min]) min = j;

                v.update(gui);
                v.delay(gui);
            }

            swap(arr, i, min);
            a.incSwaps();
        }
    }

    // ===== INSERTION =====
    private static void insertionSort(int[] arr, Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        for (int i = 1; i < arr.length; i++) {

            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {

                v.checkPause();
                v.setIndices(j, i);
                a.incComparisons();

                arr[j + 1] = arr[j];
                j--;
                a.incSwaps();

                v.update(gui);
                v.delay(gui);
            }

            arr[j + 1] = key;
        }
    }

    // ===== MERGE =====
    private static void mergeSort(int[] arr, int l, int r,
                                 Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        if (l < r) {
            int m = (l + r) / 2;

            mergeSort(arr, l, m, v, a, gui);
            mergeSort(arr, m + 1, r, v, a, gui);

            merge(arr, l, m, r, v, a, gui);
        }
    }

    private static void merge(int[] arr, int l, int m, int r,
                              Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        int[] temp = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;

        while (i <= m && j <= r) {

            v.checkPause();
            v.setIndices(i, j);
            a.incComparisons();

            temp[k++] = (arr[i] <= arr[j]) ? arr[i++] : arr[j++];

            v.update(gui);
            v.delay(gui);
        }

        while (i <= m) temp[k++] = arr[i++];
        while (j <= r) temp[k++] = arr[j++];

        for (i = l, k = 0; i <= r; i++, k++) {
            arr[i] = temp[k];
            a.incSwaps();
        }
    }

    // ===== QUICK =====
    private static void quickSort(int[] arr, int low, int high,
                                 Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        if (low < high) {
            int pi = partition(arr, low, high, v, a, gui);
            quickSort(arr, low, pi - 1, v, a, gui);
            quickSort(arr, pi + 1, high, v, a, gui);
        }
    }

    private static int partition(int[] arr, int low, int high,
                                 Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {

            v.checkPause();
            v.setIndices(j, high);
            a.incComparisons();

            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                a.incSwaps();
            }

            v.update(gui);
            v.delay(gui);
        }

        swap(arr, i + 1, high);
        a.incSwaps();

        return i + 1;
    }

    // ===== HEAP =====
    private static void heapSort(int[] arr, Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i, v, a, gui);

        for (int i = n - 1; i > 0; i--) {

            swap(arr, 0, i);
            a.incSwaps();

            v.update(gui);
            v.delay(gui);

            heapify(arr, i, 0, v, a, gui);
        }
    }

    private static void heapify(int[] arr, int n, int i,
                                Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
            a.incComparisons();
        }

        if (r < n && arr[r] > arr[largest]) {
            largest = r;
            a.incComparisons();
        }

        if (largest != i) {
            swap(arr, i, largest);
            a.incSwaps();

            v.setIndices(i, largest);
            v.update(gui);
            v.delay(gui);

            heapify(arr, n, largest, v, a, gui);
        }
    }

    // ===== COUNTING =====
    private static void countingSort(int[] arr, Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        int max = arr[0];
        for (int val : arr) if (val > max) max = val;

        int[] count = new int[max + 1];

        for (int val : arr) count[val]++;

        int index = 0;

        for (int i = 0; i <= max; i++) {
            while (count[i]-- > 0) {

                v.setIndices(index, -1);
                arr[index++] = i;
                a.incSwaps();

                v.update(gui);
                v.delay(gui);
            }
        }
    }

    // ===== RADIX =====
    private static void radixSort(int[] arr, Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        int max = arr[0];
        for (int val : arr) if (val > max) max = val;

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp, v, a, gui);
        }
    }

    private static void countSort(int[] arr, int exp,
                                 Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) {

            v.setIndices(i, -1);
            arr[i] = output[i];
            a.incSwaps();

            v.update(gui);
            v.delay(gui);
        }
    }

    // ===== BUCKET (simplified) =====
    private static void bucketSort(int[] arr, Visualizer v, Analyzer a, GUI gui)
            throws InterruptedException {

        int n = arr.length;
        int max = arr[0];

        for (int val : arr) if (val > max) max = val;

        int[] bucket = new int[n];

        for (int val : arr) {
            int idx = (val * n) / (max + 1);
            bucket[idx]++;
        }

        int k = 0;
        for (int i = 0; i < n; i++) {
            while (bucket[i]-- > 0) {

                v.setIndices(k, -1);
                arr[k++] = (i * (max + 1)) / n;
                a.incSwaps();

                v.update(gui);
                v.delay(gui);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}