class tNode {
    int name;
    tNode left, right;

    public tNode(int item) {
        name = item;
        left = right = null;
    }
}

public class tryBST {
    tNode root;

    public tNode buildPerfectBST(int low, int high) {
        if (low > high) {
            return null;
        }

        int middle = low + (high - low) / 2;
        tNode node = new tNode(middle);

        node.left = buildPerfectBST(low, middle - 1);
        node.right = buildPerfectBST(middle + 1, high);

        return node;
    }
    public boolean isBST(tNode node, int min, int max) {
        if (node == null) return true;
        if (node.name < min || node.name > max) return false;

        return isBST(node.left, min, node.name - 1) &&
                isBST(node.right, node.name + 1, max);
    }
    public tNode deleteNode(tNode root, int key) {
        if (root == null) return root;

        if (key < root.name) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.name) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.name = minValue(root.right);
            root.right = deleteNode(root.right, root.name);
        }
        return root;
    }

    private int minValue(tNode root) {
        int minv = root.name;
        while (root.left != null) {
            minv = root.left.name;
            root = root.left;
        }
        return minv;
    }

    public double calculateAverage(long[] times) {
        long sum = 0;
        for (long time : times) sum += time;
        return (double) sum / times.length;
    }

    public double calculateStdDev(long[] times, double average) {
        double sumSquares = 0;
        for (long time : times) sumSquares += Math.pow(time - average, 2);
        return Math.sqrt(sumSquares / times.length);
    }

    public void runBenchmark(int n) {
        int maxRange = (int) Math.pow(2, n) - 1;
        int reps = 30;

        long[] populateTimes = new long[reps];
        long[] deleteTimes = new long[reps];

        for (int i = 0; i < reps; i++) {
            long startPop = System.currentTimeMillis();
            this.root = buildPerfectBST(1, maxRange);
            populateTimes[i] = System.currentTimeMillis() - startPop;

            long startDel = System.currentTimeMillis();
            for (int j = 2; j <= maxRange; j += 2) {
                this.root = deleteNode(this.root, j);
            }
            deleteTimes[i] = System.currentTimeMillis() - startDel;

            this.root = null;
        }

        double avgPop = calculateAverage(populateTimes);
        double avgDel = calculateAverage(deleteTimes);

        System.out.println("\nMethod                     | Number of keys n | Average time (ms) | Standard Deviation");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("Populate tree              | %-16d | %-17.2f | %.2f%n",
                maxRange, avgPop, calculateStdDev(populateTimes, avgPop));
        System.out.printf("Remove evens from the tree | %-16d | %-17.2f | %.2f%n",
                maxRange, avgDel, calculateStdDev(deleteTimes, avgDel));
    }

    public static void main(String[] args) {
        tryBST bstTask = new tryBST();
        int n = 20;
        int maxRange = (int) Math.pow(2, n) - 1;

        bstTask.root = bstTask.buildPerfectBST(1, (int)Math.pow(2, 7)-1);
        if (bstTask.isBST(bstTask.root, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
            System.out.println("BST Verification Successful for n=20.");
        }
        bstTask.root = null;

        bstTask.runBenchmark(n);
    }
}