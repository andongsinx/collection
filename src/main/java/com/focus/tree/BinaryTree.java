package com.focus.tree;

/**
 * @description
 * @create by shadow
 * @date 2018/9/17
 * @since 1.0
 */
public class BinaryTree {


    private TreeNode root;


    public BinaryTree() {
        //初始化根节点
        root = new TreeNode(0,"rootNode(A)");
    }


    /**
     *       A
     *   B       C
     * D   E   F   G
     *
     * @param root
     */
    public void createBinaryTree(TreeNode root){
        TreeNode nodeB = new TreeNode(1, "node(B)");
        TreeNode nodeC = new TreeNode(2, "node(C)");
        TreeNode nodeD = new TreeNode(3, "node(D)");
        TreeNode nodeE = new TreeNode(4, "node(E)");
        TreeNode nodeF = new TreeNode(5, "node(F)");
        TreeNode nodeG = new TreeNode(6, "node(G)");

        root.leftChild = nodeB;
        root.rightChild= nodeC;

        nodeB.leftChild = nodeD;
        nodeB.rightChild = nodeE;

        nodeC.rightChild = nodeG;
        nodeC.leftChild = nodeF;
    }


    public boolean isEmpty(){
        return  root == null;
    }


    /**
     * 树的高度
     * @return
     */
    public int height(){
        return height(root);
    }

    private int height(TreeNode subTree){
        if(subTree==null)
            return 0;//递归结束：空树高度为0
        else{
            int i=height(subTree.leftChild)+1;
            System.out.println(subTree+":"+i);
            int j=height(subTree.rightChild)+1;
            return (i<j)?(j):(i);
        }
    }


    public int size(TreeNode subTree){
        if(subTree == null){
            return  0;
        }else{

            int right = size(subTree.rightChild);
            int left = size(subTree.leftChild);
            return  right+left+1;

        }

    }

    public TreeNode getParent(TreeNode element){

        return (root == null || root == element) ? null:getParent(root,element);

    }



    public TreeNode getParent(TreeNode subTree,TreeNode element){
        if(subTree == null){
            return null;
        }
        if(subTree.leftChild == element || subTree.rightChild == element){
            return subTree;
        }

        TreeNode p ;

        if((p=getParent(subTree.leftChild,element)) !=null){
            return p;
        }else{
            return getParent(subTree.rightChild,element);
        }


    }
    //前序遍历
    public void preOrder(TreeNode subTree){
        if(subTree!=null){
            preOrder(subTree.leftChild);
            System.out.println(subTree);
            preOrder(subTree.rightChild);
        }
    }


    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.createBinaryTree(binaryTree.root);
        binaryTree.preOrder(binaryTree.root);
        //System.out.println( binaryTree.height(binaryTree.root));
//        System.out.println(binaryTree.size(binaryTree.root));
      //  System.out.println(binaryTree.getParent(binaryTree.root.rightChild.rightChild));

    }


    private class TreeNode {
        private int key;

        private Object data;

        private boolean isVisited;

        private TreeNode leftChild;

        private TreeNode rightChild;

        public TreeNode() {
        }

        public TreeNode(int key, Object data) {
            this.key = key;
            this.data = data;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "key=" + key +
                    ", data=" + data +
                    ", isVisited=" + isVisited +
                    ", leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    '}';
        }
    }
}
