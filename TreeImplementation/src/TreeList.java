import java.util.List;
public class TreeList<E> {

    private int logBase(int b, int a){
        return (int) Math.ceil(Math.log(a)/Math.log(b));
    }

    private class TreeNode<E> {
        private E value;
        private TreeNode<E> left;
        private TreeNode<E> right;
    
        public TreeNode(E value){this(value, null, null);}
        public TreeNode(){this(null, null, null);}
        public TreeNode(E value, TreeNode<E> left, TreeNode<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    
        public TreeNode<E> getLeft() {return left;}
        public TreeNode<E> getRight() {return right;}
        public E getData() {return value;}
    
        public void setLeft(TreeNode<E> left) {this.left = left;}
        public void setRight(TreeNode<E> right) {this.right = right;}
        public void setData(E value) {this.value = value;}
    
        /*
         * public void traversePreorder() {
         * System.out.print(value);
         * if(left != null) left.traversePreorder();
         * if(right != null) right.traversePreorder();
         * }
         * 
         * public void traverseInorder() {
         * if(left != null) left.traverseInorder();
         * System.out.print(value);
         * if(right != null) right.traverseInorder();
         * }
         * 
         * public void traversePostorder() {
         * if(left != null) left.traversePostorder();
         * if(right != null) right.traversePostorder();
         * System.out.print(value);
         * }
         */
    }
    
    TreeNode<E> root;
    int numElements;
    int depth;

    public TreeList() {
        root = null;
        numElements = 0;
        depth = 0;
    }

    public TreeList(E value) {
        root = new TreeNode<E>(value);
        numElements = 1;
        depth = 1;
    }
    
    public TreeList<E> listToTreeList(List<E> orig){
        return null;
    }

    
}
