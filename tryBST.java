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
        if (low > right){
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
    public double calculateAverage(long[] times){
        long sum = 0;
        for (long time : times){
            sum += time;
        }
        return (double) sum / times.length;
    }
}