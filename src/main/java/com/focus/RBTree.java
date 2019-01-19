package com.focus;

/**
 * @description
 * @create by shadow
 * @date 2018/9/18
 * @since 1.0
 */
public class RBTree<T extends Comparable<T>> {


    private RBTNode<T> mRoot; //根节点

    private static final boolean RED = false;

    private static final boolean BLACK = true;

    public class RBTNode<T extends Comparable<T>> {

        boolean color; //颜色

        T key;//关键字（键值）


        RBTNode<T> left;//左孩子

        RBTNode<T> right;//右孩子

        RBTNode<T> parent;//父节点


        public RBTNode(boolean color, T key, RBTNode<T> left, RBTNode<T> right, RBTNode<T> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }


        public T getKey() {
            return key;
        }

        public String toString() {

            return "" + key + (this.color == RED ? "(R)" : "(B)");
        }

    }

    public RBTree() {
        mRoot = null;
    }

    private RBTNode<T> parentOf(RBTNode<T> node) {
        return node != null ? node.parent : null;
    }


    private boolean colorOf(RBTNode<T> node) {
        return node != null ? node.color : BLACK;
    }

    private boolean isRed(RBTNode<T> node) {
        return (node != null && node.color == RED) ? true : false;
    }


    private void setBlack(RBTNode<T> node) {
        if (node != null) {
            node.color = BLACK;
        }
    }


    private void setRed(RBTNode<T> node) {
        if (node != null)
            node.color = RED;

    }


    private void setParent(RBTNode<T> node, RBTNode<T> parent) {
        if (node != null) {
            node.parent = parent;
        }
    }

    private void setColor(RBTNode<T> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }


    public void preOrder(RBTNode<T> node) {
        if (node != null) {
            System.out.println(node.key);
            preOrder(node.left);
            preOrder(node.right);
        }

    }

    private RBTNode<T> search(RBTNode<T> x, T key) {
        if (x == null) {
            return x;
        }

        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            return search(x.left, key);
        } else if (cmp < 0) {
            return search(x.right, key);
        } else {
            return x;
        }
    }


    public RBTNode<T> search(T key) {
        return search(mRoot, key);
    }


    private RBTNode<T> getminimun(RBTNode<T> node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public RBTNode<T> getMinimun() {
        return getminimun(mRoot);
    }

    private RBTNode<T> getMaximun(RBTNode<T> node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }

        return node;
    }


    public RBTNode<T> getMaximun() {
        return getMaximun(mRoot);
    }

    /*
    * 对红黑树的节点(x)进行左旋转
    *
    * 左旋示意图(对节点x进行左旋)：
    *      px                              px
    *     /                               /
    *    x                               y
    *   /  \      --(左旋)-.           / \                #
    *  lx   y                          x  ry
    *     /   \                       /  \
    *    ly   ry                     lx  ly
    *
    *
    */
    private void leftRotate(RBTNode<T> x) {
        RBTNode<T> y = x.right;
        x.right = y.left;

        y.parent = x.parent;

        if (x.parent == null) {
            mRoot = y;
        } else {
            if (x.parent.left == x) {
                y.parent.left = y;
            } else {
                y.parent.right = y;
            }
        }

        y.left = x;
        x.parent = y;

    }


    /*
    * 对红黑树的节点(y)进行右旋转
    *
    * 右旋示意图(对节点y进行左旋)：
    *            py                               py
    *           /                                /
    *          y                                x
    *         /  \      --(右旋)-.            /  \                     #
    *        x   ry                           lx   y
    *       / \                                   / \                   #
    *      lx  rx                                rx  ry
    *
    */
    private void rightRotate(RBTNode<T> y) {
        RBTNode<T> x = y.left;

        y.left = x.right;
        x.parent = y.parent;

        if (y.parent == null) {
            mRoot = x;
        } else {
            if (y.parent.left == y) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
        }

        x.right = y;
        y.parent = x;

    }

    /*
    红黑树插入修正函数


    在向红黑树中插入节点后（失去平衡）再调用该函数
    目的是将它重新塑造成一颗红黑树
    参数说明：
       node 插入的节点
     */
    private void insertFixUp(RBTNode<T> node) {
        RBTNode<T> parent, gparent;


        while ((parent = parentOf(node)) != null && isRed(parent)) {
            gparent = parentOf(parent);//获取祖父节点

            //如果父节点是祖父节点的左节点，反之是右节点
            if (parent == gparent.left) {
                RBTNode<T> uncle = gparent.right;//叔父节点
                //case1 :叔叔节点是红色
                if (uncle != null && isRed(uncle)) {
                    //把父节点与叔叔节点涂黑
                    setBlack(uncle);
                    setBlack(parent);
                    //将祖父节点涂红
                    setRed(gparent);
                    node = gparent;//将位置放到祖父节点处
                    continue;
                }
                //case2: 叔叔节点是黑色，且当前节点是右子节点
                if (parent.right == node) {
                    //左旋
                    RBTNode<T> temp ;
                    leftRotate(parent);
                    temp = parent;
                    parent = node;
                    node = temp;
                }
                //case 3: 叔叔节点是黑色，且当前节点是左子节点
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);

            } else {//同理
                RBTNode<T> uncle = gparent.left;
                if (uncle != null && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }
                if (parent.left == node) {
                    RBTNode<T> temp ;
                    rightRotate(parent);
                    temp = parent;
                    parent = node;
                    node = temp;

                }
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);

            }
        }

        setBlack(this.mRoot);
    }

    private void insert(RBTNode<T> node) {
        int cmp;
        RBTNode<T> y = null;
        RBTNode<T> x = this.mRoot;
        //1、将红黑树当做一颗二插查找树，将节点添加到二插树中
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if(y!=null){
            cmp = node.key.compareTo(y.key);
            if(cmp<0){
                y.left = node;
            }else{
                y.right = node;
            }
        }else{
            this.mRoot = node;
        }

        //2、设置节点的颜色为红色
        node.color = RED;
        //3、将它重新修正为一颗红黑树

        insertFixUp(node);
    }

    public void insert(T key){
        RBTNode<T> node = new RBTNode<>(BLACK,key,null,null,null);
        if(node !=null){
            insert(node);
        }

    }


    /*
     * 打印"红黑树"
     *
     * key        -- 节点的键值
     * direction  --  0，表示该节点是根节点;
     *               -1，表示该节点是它的父结点的左孩子;
     *                1，表示该节点是它的父结点的右孩子。
     */
    private void print(RBTNode<T> tree, T key, int direction) {

        if(tree != null) {

            if(direction==0)    // tree是根节点
                System.out.printf("%2d(B) is root\n", tree.key);
            else                // tree是分支节点
                System.out.printf("%2d(%s) is %2d's %6s child\n", tree.key, isRed(tree)?"R":"B", key, direction==1?"right" : "left");

            print(tree.left, tree.key, -1);
            print(tree.right,tree.key,  1);
        }
    }

    public void print() {
        if (mRoot != null)
            print(mRoot, mRoot.key, 0);
    }

    public static void main(String[] args) {
        final int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        final boolean mDebugInsert = false;    // "插入"动作的检测开关(false，关闭；true，打开)
        final boolean mDebugDelete = false;    // "删除"动作的检测开关(false，关闭；true，打开)

        int i, ilen = a.length;
        RBTree<Integer> tree=new RBTree<Integer>();

        System.out.printf("== 原始数据: ");
        for(i=0; i<ilen; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        for(i=0; i<ilen; i++) {
            tree.insert(a[i]);
            // 设置mDebugInsert=true,测试"添加函数"
            if (mDebugInsert) {
                System.out.printf("== 添加节点: %d\n", a[i]);
                System.out.printf("== 树的详细信息: \n");
                tree.print();
                System.out.printf("\n");
            }
        }

        tree.print();


    }


}
