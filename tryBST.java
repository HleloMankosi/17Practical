
class tNode{
        int name;
        tNode left, right;

        public tNode(int item){
            name = item;
            left = right = null;
        }
    }
public class tryBST{
    tNode root;

    public tNode buildPerfectBST(int low, int high){
        if (low > high){
            return null;
        }
        int middle = low + (high - low) / 2;
        tNode node = new tNode(middle);

        node.left = buildPerfectBST(low, middle - 1);
        node.right = buildPerfectBST(middle + 1, high);
        return node;
    }
    public boolean isBST(tNode node, int min, int max){
        if (node == null){
            return true;
        }
        if (node.name < min || node.name > max){
            return false;
        }
        return isBST(node.left, min, node.name - 1) && isBST(node.right, node.name + 1, max);
    }
    public static void main(String[] args){
        tryBST tree = new tryBST();
        int n = 7;
        int maxRange = (int) Math.pow(2, n) - 1;

        tree.root = tree.buildPerfectBST(1, maxRange);

        if (tree.isBST(tree.root, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE)){
            System.out.println("Tree is a valid Binary Search Tree.");
        }
    }
    public tNode deleteNode(tNode root, int key){
        if (root == null) return root;

        if (key < root.name){
            root.left = deleteNode(root.left, key);
        }else if (key > root.name){
            root.right = deleteNode(root.right, key);
        }else{
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.name = minValue(root.right);
            root.right = deleteNode(root.right, root.name);
        }
        return root;
    }
    public int minValue(tNode root) {
        int minv = root.name;
        while (root.left != null) {
            minv = root.left.name;
            root = root.left;
        }
        return minv;
    }
    public void removeAllEvens(int maxRange) {
        for (int i = 2; i <= maxRange; i += 2) {
            this.root = deleteNode(this.root, i);
        }
    }
    public void runBenchmark(int n) {
        int maxRange = (int) Math.pow(2, n) - 1;
        int repetitions = 30;

        long[] populateTimes = new long[repetitions];
        long[] deleteTimes = new long[repetitions];

        for (int i = 0; i < repetitions; i++) {

            long startPopulate = System.currentTimeMillis();
            this.root = buildPerfectBST(1, maxRange);
            long endPopulate = System.currentTimeMillis();
            populateTimes[i] = (endPopulate - startPopulate);

            long startDelete = System.currentTimeMillis();
            removeAllEvens(maxRange);
            long endDelete = System.currentTimeMillis();
            deleteTimes[i] = (endDelete - startDelete);

            this.root = null;
        }
        double avgPopulate = calculateAverage(populateTimes);
        double stdDevPopulate = calculateStdDev(populateTimes, avgPopulate);

        double avgDelete = calculateAverage(deleteTimes);
        double stdDevDelete = calculateStdDev(deleteTimes, avgDelete);

        System.out.printf("%-30s | %-15s | %-15s | %-15s%n", "Method", "Number of keys n", "Average time in ms.", "Standard Deviation");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-30s | %-15d | %-15.2f | %-15.2f%n", "Populate tree", maxRange, avgPopulate, stdDevPopulate);
        System.out.printf("%-30s | %-15d | %-15.2f | %-15.2f%n", "Remove evens from the tree", maxRange, avgDelete, stdDevDelete);
    }

    public double calculateAverage(long[] times){
        long sum = 0;
        for (long time : times){
            sum += time;
        }
        return (double) sum / times.length;
    }
    public double calculateStdDev(long[] times, double average){
        double sumOfSquaredDifferences = 0;
        for (long time : times){
            sumOfSquaredDifferences += Math.pow(time - average, 2);
        }
        return Math.sqrt(sumOfSquaredDifferences / times.length);
    }
}