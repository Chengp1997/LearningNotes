# 剑指offer刷题指南

关于队列：https://www.cnblogs.com/be-forward-to-help-others/p/6825738.html

## 树

#### 关于树

前序遍历：中-左-右

中序遍历：左-中-右

后序遍历：左-右-中

https://www.cnblogs.com/llguanli/p/7363657.html 树的遍历

注意使用递归

https://blog.csdn.net/m0_37698652/article/details/79218014 反向建树

二叉查找树！！！

https://www.cnblogs.com/skywang12345/p/3576452.html

此处！！重点在于使用中序遍历：中序遍历中，root左侧即为左子树，右侧为右子树。

平衡二叉树！！！

​	

#### 例题1：重构树

```java
/*输入某二叉树的前序遍历和中序遍历的结果，
请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，
则重建二叉树并返回。*/

 /*解题思路：我们知道，前序遍历的第一个节点就是树的根节点，
    所以我们先根据前序遍历序列的第一个数字创建根结点，
    接下来在中序遍历序列中找到根结点的位置，根节点的左边就是左子树，
    右边就是右子树，这样就能确定左、右子树结点的数量。
    在前序遍历和中序遍历的序列中划分了左、右子树结点的值之后，
    就可以递归地去分别构建它的左右子树。*/

    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if (pre.length==0||in.length==0)
            return null;
         return reConstructBinaryTree(pre,0,pre.length-1,in,0,in.length-1);
    }    //前序遍历{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
     private TreeNode reConstructBinaryTree(int [] pre,int startPre,int endPre,int [] in,int startIn,int endIn) {
         if (startPre > endPre || startIn > endIn)
             return null;
         TreeNode root = new TreeNode(pre[startPre]);
         //根据中序遍历来切，左边就是左子树，右边就是右子树，中间为root
         for (int i = startIn; i <= endIn; i++)
             if (in[i] == pre[startPre]) {
                 root.left = reConstructBinaryTree(pre, startPre + 1, startPre + i - startIn, in, startIn, i - 1);
                 root.right = reConstructBinaryTree(pre, i - startIn + startPre + 1, endPre, in, i + 1, endIn);
                 break;
             }
         return root;
     }
```

#### 例题2：树的子结构

此题。。。不会解。。。请参考

```java
public class Solution {
    public static boolean HasSubtree(TreeNode root1, TreeNode root2) {
         boolean result = false ;
//当Tree1和Tree2都不为零的时候，才进行比较。否则直接返回false
         if (root2 !=  null && root1 != null ) {
             //如果找到了对应Tree2的根节点的点
             if (root1.val == root2.val){
              //以这个根节点为为起点判断是否包含Tree2
                 result = doesTree1HaveTree2(root1,root2);
            }
 //如果找不到，那么就再去root的左儿子当作起点，去判断时候包含Tree2
           if (!result) {
             	result = HasSubtree(root1.left,root2);
           }
            
 //如果还找不到，那么就再去root的右儿子当作起点，去判断时候包含Tree2
            if (!result) {
                 result = HasSubtree(root1.right,root2);
                }
             }
             //返回结果
         return result;
     }
      public static boolean doesTree1HaveTree2(TreeNode node1, TreeNode node2) {
      //如果Tree2已经遍历完了都能对应的上，返回true
        if (node2 ==  null ) {
             return true ;
         }
 //如果Tree2还没有遍历完，Tree1却遍历完了。返回false
         if (node1 ==  null ) {
             return false ;
         }
        //如果其中有一个点没有对应上，返回false
        if (node1.val != node2.val) {   
                return false ;
         }
        
   //如果根节点对应的上，那么就分别去子节点里面匹配
        return doesTree1HaveTree2(node1.left,node2.left) && doesTree1HaveTree2(node1.right,node2.right);
     }
```

#### 例题3：树的镜像

```java
//操作给定的二叉树，将其变换为源二叉树的镜像。
//	通过对以上两棵树的观察，我们可以总结出这两棵树的根节点相同，但它们的左、右两个子节点交换了位置。所以我们可以得出求一棵树的镜像的过程：先前序遍历这棵树的每个节点，如果遍历到的节点有子节点，就交换它的两个子节点。当交换完所有非叶节点的左、右子节点之后，就得到了树的镜像。
public class Solution {
    public void Mirror(TreeNode root) {
        //当前节点为空，直接返回
        if(root == null)
            return;
        //当前节点没有叶子节点，直接返回
        if(root.left == null && root.right == null)
            return;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        //递归交换叶子节点
        if(root.left != null)
            Mirror(root.left);
        if(root.right != null)
            Mirror(root.right);
    }
}
```

#### 例题4：从上往下打印二叉树

```java
//从上往下打印出二叉树的每个节点，同层节点从左至右打印。
//仔细一想！这不就是广度搜索么！
题目不难
```

#### 例题5：二叉搜索树的后序遍历

```java
//输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
// 如果是则输出Yes,否则输出No。
// 假设输入的数组的任意两个数字都互不相同。
这题不难，对于后序遍历，最后一个即为root
观察二叉搜索树，发现，比root小的一侧都为左子树，比root大的一侧都为右子树，因此只需要判断即可，是否左边都比root小，或者右边都比root大。
 public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence.length==0) return false;
        int root =sequence[sequence.length-1];
        //比root小的一边为左子树；另一边为右子树
        int j=0;
        for (int i=0;i<sequence.length;i++){
            if (sequence[i]<root) j++;
            else break;
        }
        boolean is=true;
        for (int i=j;i<sequence.length;i++){
            if (sequence[i]<root)return false;
        }
        return true;
    }
    
我的思路很简单，但是。。。。为啥大佬的都用递归？
```

#### 例题6：二叉树中和为某值的路径

```java
//输入一颗二叉树的跟节点和一个整数，
// 打印出二叉树中结点值的和为输入整数的所有路径。
// 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
// (注意: 在返回值的list中，数组长度大的数组靠前)
//没做出来，大佬：递归思想


//用前序遍历的方式访问到某一结点时，把该结点添加到路径上，并用目标值减去该节点的值。如果该结点为叶结点并且目标值减去该节点的值刚好为0，则当前的路径符合要求，我们把加入res数组中。如果当前结点不是叶结点，则继续访问它的子结点。当前结点访问结束后，递归函数将自动回到它的父结点。因此我们在函数退出之前要在路径上删除当前结点，以确保返回父结点时路径刚好是从根结点到父结点的路径。
  ArrayList<ArrayList<Integer> > res = new ArrayList<ArrayList<Integer> >();
    ArrayList<Integer> temp = new ArrayList<Integer>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if(root == null)
            return res;
        target -= root.val;
        temp.add(root.val);
        if(target == 0 && root.left == null && root.right == null)
            res.add(new ArrayList<Integer>(temp));
        else{
            FindPath(root.left, target);
            FindPath(root.right, target);
        }
        temp.remove(temp.size()-1);
        return res;
    }
```

#### 例题7：二叉搜索树与双向链表

该题。。虽然写出来了，然而超时！！！！！

根据观察可知，当中序遍历时，即为排序的。

```java
//输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表
// 。要求不能创建任何新的结点，只能调整树中结点指针的指向。
//我的方法

ArrayList<TreeNode> treeNodes = new ArrayList<>();
    public ArrayList<TreeNode> findNodes(TreeNode pRootOfTree){

        if (pRootOfTree!=null){
            Convert(pRootOfTree.left);
            treeNodes.add(pRootOfTree);
            Convert(pRootOfTree.right);
        }
        return treeNodes;
    }
    public TreeNode Convert(TreeNode pRootOfTree) {
        //观察可知，使用中序遍历，能够得到排序
        if (pRootOfTree==null)return null;
        ArrayList<TreeNode> treeNodes= findNodes(pRootOfTree);

        treeNodes.get(0).left=treeNodes.get(treeNodes.size()-1);
        treeNodes.get(treeNodes.size()-1).right=treeNodes.get(0).left;
        for (int i=1;i<treeNodes.size();i++){
            treeNodes.get(i-1).right=treeNodes.get(i);
        }
        for (int i=treeNodes.size()-2;i>0;i--){
            treeNodes.get(i+1).left=treeNodes.get(i);
        }

        return treeNodes.get(0);

    }
！！！GG不行！！！！

//大佬方法
public class Solution {
    TreeNode head = null;
    TreeNode end = null;
    public TreeNode Convert(TreeNode pRootOfTree) {
        ConvertSub(pRootOfTree);
        return head;
    }
    public void ConvertSub(TreeNode pRootOfTree) {
        if(pRootOfTree == null)
            return ;
        Convert(pRootOfTree.left);
        if(end == null){
            head = pRootOfTree;
            end = pRootOfTree;
        }else{
            end.right = pRootOfTree;
            pRootOfTree.left = end;
            end = pRootOfTree;
        }
        Convert(pRootOfTree.right);
    }
}

//非递归解法
import java.util.Stack;
public class Solution {
    public TreeNode Convert(TreeNode pRootOfTree) {
        TreeNode head = null;
        TreeNode pre = null;
        Stack<TreeNode> stack = new Stack<>();
        while(pRootOfTree != null || !stack.isEmpty()){
            while(pRootOfTree != null){
                stack.push(pRootOfTree);
                pRootOfTree = pRootOfTree.left;
            }
            pRootOfTree = stack.pop();
            if(head == null){
                head = pRootOfTree;
                pre = pRootOfTree;
            }else{
                pre.right = pRootOfTree;
                pRootOfTree.left = pre;
                pre = pRootOfTree;
            }
            pRootOfTree = pRootOfTree.right;
        }
        return head;
    }
}
```

#### 例题8：树的深度

输入一棵树，求出该树的深度

我的方法。。。我以为很简单了

使用层次遍历，来一层，+1，更简单的方法如下！！！

```java
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;
    public TreeNode(int val) {
        this.val = val;
    }
}
*/
public class Solution {
    public int TreeDepth(TreeNode root) {
        if(root == null)
            return 0;
        int left = TreeDepth(root.left) + 1;
        int right = TreeDepth(root.right) + 1;
        return left > right ? left : right;
    }
}
递归啊
```

#### 例题9：判断平衡二叉树

输入一棵二叉树，判断该二叉树是平衡二叉树

链接：

https://www.nowcoder.com/questionTerminal/8b3b95850edb4115918ecebdf1b4d222

来源：牛客网

这种做法有很明显的问题，在判断上层结点的时候，会多次重复遍历下层结点，增加了不必要的开销。如果改为从下往上遍历，如果子树是平衡二叉树，则返回子树的高度；如果发现子树不是平衡二叉树，则直接停止遍历，这样至多只对每个结点访问一次。 



```java
`public` `class` `Solution {``    ``public` `boolean` `IsBalanced_Solution(TreeNode root) {``        ``return` `getDepth(root) != -``1``;``    ``}``    ` `    ``private` `int` `getDepth(TreeNode root) {``        ``if` `(root == ``null``) ``return` `0``;``        ``int` `left = getDepth(root.left);``        ``if` `(left == -``1``) ``return` `-``1``;``        ``int` `right = getDepth(root.right);``        ``if` `(right == -``1``) ``return` `-``1``;``        ``return` `Math.abs(left - right) > ``1` `? -``1` `: ``1` `+ Math.max(left, right);``    ``}``}`
```

#### 例题10：二叉树的下一个结点

```java
//给定一个二叉树和其中的一个结点，
// 请找出中序遍历顺序的下一个结点并且返回。
// 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
此处应该注意，读到这里了，说明。。。左边读过了！！！
/*中序遍历：左 -> 根 -> 右
分三种情况：
如果当前节点为空，直接返回空；
如果当前节点有右子树，则返回右子树的最左子树；
如果当前节点没有右子树，再分两种情况：
看看当前节点是不是它的父节点的左子树，如果是，则返回它的父节点；
如果当前节点不是它的父节点的左子树，则把父节点赋给当前节点，
再判断当前节点是不是它的父节点的左子树，
直到当前节点是不是它的父节点的左子树，返回它的父节点。
*/
 public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if(pNode==null) return null;
        //突然意识到，好像是不用判断左子树情况，
        //如果读到这个点了，说明左子树读完了？？？
        //那么判断右子树就行
        if (pNode.right!=null){
            TreeLinkNode node=pNode.right;
            while (node.left!=null){
                node=node.left;
            }
            return node;
        }
        //如果没有右子树了！！那么需要判断！
        //向上一个结点，开始找上个节点
        while (pNode.next!=null){
            //此处！！如果pNode是左子树的话，那么它的爸爸就是下一个念的
            if (pNode==pNode.next.left){
                return pNode.next;
            }
            pNode=pNode.next;
        }
        return null;
    }
```

#### 例题11：对称的二叉树

```java
//请实现一个函数，用来判断一颗二叉树是不是对称的。
// 注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
我的想法很单纯，如果。。。可以的话，我分别存一个arraylist，一个从左开始读，一个从右开始读。。。可是失败了嘤嘤嘤
大佬方法如下
  boolean isSymmetrical(TreeNode pRoot)
    {
        if(pRoot == null)
            return true;
        Stack<TreeNode> s = new Stack<TreeNode>();
        s.push(pRoot.left);
        s.push(pRoot.right);
        while(!s.isEmpty()){
            TreeNode right = s.pop();
            TreeNode left = s.pop();
            if(right == null && left == null)
                continue;
            if(right == null || left == null)
                return false;
            if(right.val != left.val)
                return false;
            s.push(left.left);
            s.push(right.right);
            s.push(left.right);
            s.push(right.left);
            //我只会喊666了
        }
        return true;
    }


递归式
 boolean isSymmetrical(TreeNode pRoot)
    {
        if(pRoot == null)
            return true;
        return isSymmetrical(pRoot.left, pRoot.right);
    }
    boolean isSymmetrical(TreeNode left, TreeNode right){
        if(left == null && right == null)
            return true;
        if(left == null || right == null)
            return false;
        if(left.val == right.val){
            return isSymmetrical(left.left, right.right) && 
                isSymmetrical(left.right, right.left);
        }
        return false;
    }
```

#### 例题12：按之字形打印二叉树

```java
//请实现一个函数按照之字形打印二叉树，
// 即第一行按照从左到右的顺序打印，第
// 二层按照从右至左的顺序打印，
// 第三行按照从左到右的顺序打印，其他行以此类推。
此处。。不难，变种层次遍历
public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
    ArrayList<ArrayList<Integer>> all=new ArrayList<>();
    if (pRoot==null) return all;
    //如果使用stack--后进的先出！！！那就可以了
    Stack<TreeNode> leftToRight=new Stack<>();
    Stack<TreeNode> rightToLeft=new Stack<>();
    leftToRight.push(pRoot);
    int flag=1;
    while (!leftToRight.isEmpty()||!rightToLeft.isEmpty()){
        ArrayList<Integer> val=new ArrayList<>();
        //先把从左到右的读出来，然后放到从右到左的stack中
        if (flag==1){
            while (!leftToRight.isEmpty()){
                TreeNode node1=leftToRight.pop();
                val.add(node1.val);
                if (node1.left!=null){
                    rightToLeft.push(node1.left);
                }
                if (node1.right!=null){
                    rightToLeft.push(node1.right);
                }
            }
            flag=2;
        }else {
            while (!rightToLeft.isEmpty()){
                TreeNode node2=rightToLeft.pop();
                val.add(node2.val);
                if (node2.right!=null){
                    leftToRight.push(node2.right);
                }
                if (node2.left!=null){
                    leftToRight.push(node2.left);
                }
            }
            flag=1;
        }
        all.add(val);
    }
    return all;
}
```

#### 例题13：把二叉树打印成多行

类似于层次遍历，注意一下终止条件就行

#### 例题14：序列化，反序列化二叉树

```java
//请实现两个函数，分别用来序列化和反序列化二叉树
这里注意：序列化就是将其以特定的方式转化为字符串来持久化保存
反序列化就是根据序列化的值，重构成原对象！！！

此处序列化较简单，重点在反序列！
	String Serialize(TreeNode root) {
        if (root==null) return "#!";
        //这里用！作为结尾，用以反序列化时来判断
        String ans="";
        //使用前序遍历的方式来存储
        ans+=root.val+"!";
        ans+=Serialize(root.left);
        ans+=Serialize(root.right);
        return ans;
    }
    
    TreeNode Deserialize(String str) {
        //这里注意，之前犯了一个错误，应把所有传进去，之前递归时，我将str传进去，然而无法随着值变而变，因此！应穿queue
        if (str.length()==0) return null;
        String []all=str.split("!");
        Queue<String> nodes=new LinkedList<>();
        for (int i=0;i<all.length;i++){
            nodes.offer(all[i]);
        }
        TreeNode root= Des(nodes);
        return root;
    }
    TreeNode Des(Queue<String> str){
        String temp=str.poll();
        if (temp.equals("#")) return null;

        TreeNode root=new TreeNode(Integer.valueOf(temp));
        root.left=Des(str);
        root.right= Des(str);
        return root;
    }
```

#### 例题15：二叉搜索树的第k个结点

```java
//给定一棵二叉搜索树，请找出其中的第k小的结点。
// 例如， （5，3，7，2，4，6，8）    中，
// 按结点数值大小顺序第三小结点的值为4。
 int i=0;
    TreeNode KthNode(TreeNode pRoot, int k)
    {
        TreeNode ansNode=null;
        if (pRoot==null) return ansNode;
        if (k<0) return ansNode;
        //例子为前序遍历，根+左
        //二叉搜索树，则左子树都比根小，右子树都比根大
        //那就使用中序遍历！！，找到第k个就行

        ansNode=KthNode(pRoot.left,k);
        if (ansNode!=null) return ansNode;
        i++;
        if (i==k) return pRoot;
        ansNode=KthNode(pRoot.right,k);
        if (ansNode!=null) return ansNode;
        return ansNode;
    }
```

## 数组

#### 例题1：二维数组的查找

```java
//题目
/*在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
每一列都按照从上到下递增的顺序排序。
请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。*/

/*
*注意点：1. 注意空情况！！每次先排除
*       2. 思路的话，暴力求解可以，但是！尽量减少复杂度！
*/

    /*
    思路
    二维数组是有序的，从右上角来看，向左数字递减，向下数字递增。
    因此从右上角开始查找，
    当要查找数字比右上角数字大时，下移；
    当要查找数字比右上角数字小时，左移；
    如果出了边界，则说明二维数组中不存在该整数。*/

public boolean Find(int target, int [][] array) {
       if (array.length==0||array[0].length==0) return  false;
       int i=0;
       int j=array[0].length-1;
       int temp=array[i][j];
       while (temp!=target){
           if (j==0||i==array.length-1) return false;
           if (target<temp){
               j--;
           }else if (target>temp) {
               i++;
           }
           temp=array[i][j];
       }
       return true;
    }
```

#### 例题2：斐波那契数列

1 1 2 3 5 8 13

```java
/*大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
n<=39 */
简单，秩序循环即可
```

#### 例题3：跳台阶问题（经典）

```java
/*一只青蛙一次可以跳上1级台阶，也可以跳上2级。
求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。*/

/*
比较倾向于找规律的解法，
f(1) = 1, f(2) = 2, f(3) = 3, f(4) = 5，
可以总结出f(n) =f(n-1)+f(n-2)的规律，
但是为什么会出现这样的规律呢？
假设现在6个台阶，我们可以从第5跳一步到6，这样的话有多少种方案跳到5就有多少种方案跳到6，
另外我们也可以从4跳两步跳到6，跳到4有多少种方案的话，就有多少种方案跳到6，
其他的不能从3跳到6什么的啦，所以最后就是
f(6)= f(5) + f(4)；这样子也很好理解变态跳台阶的问题了。

找完规律！！！斐波那契数列！！*/

```

#### 列题4：变态跳台阶（变种斐波那契数列！）

类似于上题，只是将之前的都相加

#### 例题5：画格子问题（斐波那契数列）

```java
/*我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？*/
找规律！！！同样的思路发现是斐波那契数列
```

#### 例题6：奇数偶数重排序

```java
//输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
// 使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，
// 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
//我的方法
    public void reOrderArray(int [] array) {
        if (array.length==0) return;
        int k=0,j=array.length-1;
        List even=new LinkedList<Integer>();
        List odd=new LinkedList<Integer>();
        for (int i=0;i<array.length;i++){
            if (array[i]%2==0)even.add(array[i]);
            else odd.add(array[i]);
        }
        odd.addAll(even);
        //注意此处，add为将整个链表塞到后面
        //addAll是将内部每个元素塞到后面
        for (int i=0;i<array.length;i++){
            array[i]= (int) odd.get(i);
        }
    }

//其他方法
//如果不能开僻额外的空间，可以尝试有类似于冒泡排序的方法,如果当前的值为偶数，后一个值为奇数，则两个数对换位置：
public class Solution {
    public void reOrderArray(int [] array) {
        for(int i=0; i < array.length; i++){
            for(int j=0; j<array.length-1; j++){
                if(array[j] % 2 == 0 && array[j+1] % 2 != 0){
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }
}
```

#### 例题7：顺时针读二维数组************

```java
//输入一个矩阵，
// 按照从外向里以顺时针的顺序依次打印出每一个数字，
// 例如，如果输入如下4 X 4矩阵：
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
// 则依次打印出数字
// 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
题目没有很难，主要是，如何循环为难点！！！！！！
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        if (matrix==null)return null;
        ArrayList print=new ArrayList<Integer>();
        int left=0,right=matrix[0].length-1,up=0,down=matrix.length-1;
        /*此处注意判断条件，！！！！！！*/
        while (left <= right && up <= down) {
            //左到右
            for (int i = left; i <= right; i++) {
                print.add(matrix[up][i]);
            }
            //上到下
            for (int i = up+1; i <=down; i++) {
                print.add(matrix[i][right]);
            }
            //右到左
            //此处，防止只有一行的情况
            if (up!=down){
                for (int i = right-1; i >= left; i--) {
                    print.add(matrix[down][i]);
                }
            }

            //从下到上
            if (left!=right){
                for (int i = down-1; i > up; i--) {
                    print.add(matrix[i][left]);
                }
            }

            left++;right--;up++;down--;

        }

        return print;
    }
    
//初次之外， 大佬有一种旋转魔方的方式
哇，秒！
/*
可以模拟魔方逆时针旋转的方法，一直做取出第一行的操作 
  例如  
  1 2 3 
  4 5 6 
  7 8 9 
  输出并删除第一行后，再进行一次逆时针旋转，就变成： 
  6 9 
  5 8 
  4 7 
  继续重复上述操作即可。 
  */
public ArrayList<Integer> printMatrix_2(int[][] matrix) {           
    ArrayList<Integer> al = new ArrayList<>();             int row = matrix.length;    
    while (row != 0) {
        for (int i = 0; i < matrix[0].length; i++) {  
            al.add(matrix[0][i]);                
        }
        if (row == 1)  
            break;    
        matrix = turn(matrix); 
        row = matrix.length;   
    }     
    return al;  
}
private int[][] turn(int[][] matrix) {  
    // TODO 自动生成的方法存根        
    int col = matrix[0].length;  
    int row = matrix.length;  
    int[][] newMatrix = new int[col][row - 1];            for (int j = col - 1; j >= 0; j--) {               
      for (int i = 1; i < row; i++) {               
         newMatrix[col - 1 - j][i - 1] = matrix[i][j];
        }
    }
    return newMatrix;        
}
```

#### 例题8：字符串的排列

```java
//输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
//输入描述:输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
。。。没有写出来，看看大佬想法，牛客写得很清楚！	
//刚看题目的时候，可能会觉得这个问题很复杂，不能一下子想出解决方案。那我们就要学会把复杂的问题分解成小问题。我们求整个字符串的排列，其实可以看成两步：
	//第一步求所有可能出现在第一个位置的字符（即把第一个字符和后面的所有字符交换[相同字符不交换]）；
	//第二步固定第一个字符，求后面所有字符的排列。这时候又可以把后面的所有字符拆成两部分（第一个字符以及剩下的所有字符），依此类推。这样，我们就可以用递归的方法来解决。
	
```

https://www.nowcoder.com/questionTerminal/fe6b651b66ae47d7acce78ffdd9a96c7

#### 例题9：数组中出现次数超过一半的数字

```java
//数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
//该题我的思路很简单，使用map来搜集
三种解法：
//法1：借助hashmap存储数组中每个数出现的次数，最后看是否有数字出现次数超过数组长度的一半；
//法2：排序。数组排序后，如果某个数字出现次数超过数组的长度的一半，则一定会数组中间的位置。所以我们取出排序后中间位置的数，统计一下它的出现次数是否大于数组长度的一半；
//法3：某个数字出现的次数大于数组长度的一半，意思就是它出现的次数比其他所有数字出现的次数和还要多。因此我们可以在遍历数组的时候记录两个值：1. 数组中的数字;2. 次数。遍历下一个数字时，若它与之前保存的数字相同，则次数加1，否则次数减1；若次数为0，则保存下一个数字，并将次数置为1。遍历结束后，所保存的数字即为所求。最后再判断它是否符合条件。

我的方法：
public int MoreThanHalfNum_Solution(int [] array) {
        if (array.length==0||array==null)return 0;
        if (array.length==1)return array[0];
        int length=array.length;
        Map<Integer, Integer> numbers=new HashMap();
        for (int i=0;i<array.length;i++){
            if (numbers.containsKey(array[i])){
                int times=numbers.get(array[i]);
                numbers.put(array[i],times+1);
            }else {
                numbers.put(array[i],1);
            }
        }

        Collection<Integer> keys=numbers.keySet();
        Iterator<Integer> iterator=keys.iterator();
        while (iterator.hasNext()){
            int key=iterator.next();
            if (numbers.get(key)>length/2) {
                return key;
            }
        }
        return 0;

    }
    
    方法二：
    public class Solution {
    public int MoreThanHalfNum_Solution(int [] array) {
        Arrays.sort(array);
        int half = array.length/2;
        int count = 0;
        for(int i=0; i<array.length; i++){
            if(array[i] == array[half])
                count ++;
        }
        if(count > half)
            return array[half];
        else
            return 0;
    }
}

方法三：
public class Solution {
    public int MoreThanHalfNum_Solution(int [] array) {
        int res = array[0], count = 1;
        for(int i=1; i<array.length; i++){
            if(array[i] == res)
                count++;
            else{
                count--;
            }
            if(count == 0){
                res = array[i];
                count = 1;
            }
        }
        count = 0;
        for(int i=0; i<array.length; i++){
            if(array[i] == res)
                count++;
        }
        return count > array.length/2 ? res : 0;
    }
}
```

#### 例题10：最小的k个数

```java
//输入n个整数，找出其中最小的K个数。
// 例如输入4,5,1,6,2,7,3,8这8个数字，
// 则最小的4个数字是1,2,3,4,。
方法一：很简单（我的方法，排序后，找前几个）
方法二：利用最大堆保存这k个数，每次只和堆顶比，如果比堆顶小，删除堆顶，新数入堆。！！！！
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
public class Solution {
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(input == null || k ==0 || k > input.length)
            return res;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, new Comparator<Integer>() { 
            public int compare(Integer e1, Integer e2) {
                return e2 - e1;
            }
        });
        for(int i=0; i<input.length; i++){
            if(maxHeap.size() != k)
                maxHeap.offer(input[i]);
            else{
                if(maxHeap.peek() > input[i]){
                    maxHeap.poll();
                    maxHeap.offer(input[i]);
                }
            }
        }
        for(Integer i: maxHeap){
            res.add(i);
        }
        return res;
    }
}
```

#### 例题11：连续子数组的最大和

```java
//在古老的一维模式识别中,常常需要计算连续子向量的最大和,
// 当向量全为正数的时候,问题很好解决。
// 但是,如果向量中包含负数,是否应该包含某个负数,
// 并期望旁边的正数会弥补它呢？
// 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8
// (从第0个开始,到第3个为止)。给一个数组，返回它的最大连续子序列的和，
// 你会不会被他忽悠住？(子向量的长度至少是1)

此题不难，但是自己被忽悠了嘤嘤嘤
 public int FindGreatestSumOfSubArray(int[] array) {
        if(array.length==0||array==null) return -0;
        int max=array[0];
        int temp=0;
        for(int i=0;i<array.length;i++){
          //此处注意如果之和<0,就从头开始计算！！！
          //原先理解错了，这里写错了
          //连续是连续，但是不一定是从头开始
          if (temp<0){
                temp=array[i];
            }else {
                temp+=array[i];
            }
            if (temp>max) {
                max=temp;
            }
        }
        return max;
```

#### 例题12：丑数

//把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。

> 链接：
>
> https://www.nowcoder.com/questionTerminal/6aa9e04fc3794f68acf8778237ba065b
>
> 来源：牛客网
>
> **通俗易懂的解释：**  
>
>    **首先从丑数的定义我们知道，一个丑数的因子只有2,3,5，那么丑数p = 2 ^ x \* 3 ^ y \* 5 ^ z，换句话说一个丑数一定由另一个丑数乘以2或者乘以3或者乘以5得到，那么我们从1开始乘以2,3,5，就得到2,3,5三个丑数，在从这三个丑数出发乘以2,3,5就得到4，6,10,6，9,15,10,15,25九个丑数，我们发现这种方法会得到重复的丑数，而且我们题目要求第N个丑数，这样的方法得到的丑数也是无序的。那么我们可以维护三个队列：**  
>
>    **（1）丑数数组： 1**  
>
>    **乘以2的队列：2**  
>
>    **乘以3的队列：3**  
>
>    **乘以5的队列：5**  
>
>    **选择三个队列头最小的数2加入丑数数组，同时将该最小的数乘以****2,3,5****放入三个队列；**  
>
>    **（2）丑数数组：1,2**  
>
> ​    **乘以2的队列：4**   
>
> ​    **乘以3的队列：3，6**   
>
> ​    **乘以5的队列：5，10**   
>
> ​    **选择三个队列头最小的数3加入丑数数组，同时将该最小的数乘以****2,3,5****放入三个队列；**   
>
> ​    **（3）丑数数组：1,2,3**   
>
> ​    **乘以2的队列：4,6**   
>
> ​    **乘以3的队列：6,9**   
>
> ​    **乘以5的队列：5,10,15**   
>
> ​    **选择三个队列头里最小的数4加入丑数数组，同时将该最小的数乘以****2,3,5****放入三个队列；**   
>
> ​    **（4）丑数数组：1,2,3,4**   
>
> ​    **乘以2的队列：6，8**   
>
> ​    **乘以3的队列：6,9,12**   
>
> ​    **乘以5的队列：5,10,15,20**   
>
> ​    **选择三个队列头里最小的数5加入丑数数组，同时将该最小的数乘以****2,3,5****放入三个队列；**   
>
> ​    **（5）丑数数组：1,2,3,4,5**   
>
> ​    **乘以2的队列：6,8,10，**   
>
> ​    **乘以3的队列：6,9,12,15**   
>
> ​    **乘以5的队列：10,15,20,25**   
>
> ​    **选择三个队列头里最小的数6加入丑数数组，但我们发现，有两个队列头都为6，所以我们弹出两个队列头，同时将12,18,30放入三个队列；**   
>
> ​    **……………………**   
>
> ​    **疑问：**   
>
> ​    **1.为什么分三个队列？**   
>
> ​    **丑数数组里的数一定是有序的，因为我们是从丑数数组里的数乘以2,3,5选出的最小数，一定比以前未乘以2,3,5大，同时对于三个队列内部，按先后顺序乘以2,3,5分别放入，所以同一个队列内部也是有序的；**   
>
> ​    **2.为什么比较三个队列头部最小的数放入丑数数组？**   
>
> ​    **因为三个队列是有序的，所以取出三个头中最小的，等同于找到了三个队列所有数中最小的。**   
>
> ​    **实现思路：**   
>
> ​    **我们没有必要维护三个队列，只需要记录三个指针显示到达哪一步；“|”表示指针,arr表示丑数数组；**   
>
> ​    **（1）1**   
>
> ​    **|2**   
>
> ​    **|3**   
>
> ​    **|5**   
>
> ​    **目前指针指向0,0,0，队列头arr[0] \* 2 = 2,  arr[0] \* 3 = 3,  arr[0] \* 5 = 5**   
>
> ​    **（2）1 2**   
>
> ​    **2 |4**   
>
> ​    **|3 6**   
>
> ​    **|5 10**   
>
> ​    **目前指针指向1,0,0，队列头arr[1] \* 2 = 4,  arr[0] \* 3 = 3, arr[0] \* 5 = 5**   
>
> ​    **（3）1 2 3**   
>
> ​    **2| 4 6**   
>
> ​    **3 |6 9**    
>
> ​    **|5 10 15**   
>
>    **目前指针指向1,1,0，队列头arr[1] \* 2 = 4,  arr[1] \* 3 = 6, arr[0] \* 5 = 5**  
>
>    **………………** 

```java
//别人的代码就是好。。。
public class Solution {
    public int GetUglyNumber_Solution(int index) {
        if(index <= 0)
            return 0;
        if(index == 1)
            return 1;
        int t2 = 0, t3 = 0, t5 = 0;
        int [] res = new int[index];
        res[0] = 1;
        for(int i = 1; i<index; i++){
            res[i] = Math.min(res[t2]*2, Math.min(res[t3]*3, res[t5]*5));
            if(res[i] == res[t2]*2) t2++;
            if(res[i] == res[t3]*3) t3++;
            if(res[i] == res[t5]*5) t5++;
        }
        return res[index-1];
    }
}
```

#### 例题13：数组中的逆序对

在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007
 输入描述:
 题目保证输入的数组中没有的相同的数字

 ![img](https://ws1.sinaimg.cn/large/006tKfTcgy1ftay375qnij30h607waae.jpg)

```java
/*解题思路
很容易想到的方法就是遍历每一个元素，让其与后面的元素对比，如果大于则count++，但是这样的时间复杂度是O(n^2)，因此，我们可以用归并排序思路。

例如7,5,4,6可以划分为两段7,5和4,6两个子数组
	在7,5中求出逆序对，因为7大于5所以有1对
	在6,4中求出逆序对，因为6大于4所以逆序对再加1，为2
	对7,5和6,4进行排序，结果为5,7,和4,6
	设置两个指针分别指向两个子数组中的最大值，p1指向7，p2指向6
	比较p1和p2指向的值，如果大于p2，因为p2指向的是最大值，所以第二个子数组中有几个元素就有几对逆序对(当前有两个元素，逆序对加	2,2+2=4)，7>6,比较完之后将p1指向的值放入辅助数组里，辅助数组里现在有一个数字7，然后将p1向前移动一位指向5
	再次判断p1和p2指向的值，p1小于p2，因为p1指向的是第一个子数组中最大值，所以子数组中没有能和当前p2指向的6构成逆序对的数，将p2指向的值放入辅助数组，并向前移动一位指向4，此时辅助数组内为6,7
	继续判断p1(指向5)和p2(指向4)，5>4,第二个子数组中只有一个数字，逆序对加1，4+1=5，为5对，然后将5放入辅助数组，第一个子数组遍历完毕，只剩下第二个子数组，当前只有一个4，将4也放入辅助数组，函数结束。辅助数组此时为4,5,6,7.逆序对为5.*/
	
	public class Solution {
    public int InversePairs(int [] array) {
        int len = array.length;
        if(array== null || len <= 0){
            return 0;
        }
        return mergeSort(array, 0, len-1);
    }
    public int mergeSort(int [] array, int start, int end){
        if(start == end)
            return 0;
        int mid = (start + end) / 2;
        int left_count = mergeSort(array, start, mid);
        int right_count = mergeSort(array, mid + 1, end);
        int i = mid, j = end;
        int [] copy = new int[end - start + 1];
        int copy_index = end - start;
        int count = 0;
        while(i >= start && j >= mid + 1){
            if(array[i] > array[j]){
                copy[copy_index--] = array[i--];
                count += j - mid;
                if(count > 1000000007){
                    count %= 1000000007;
                }
            }else{
                copy[copy_index--] = array[j--];
            }
        }
        while(i >= start){
            copy[copy_index--] = array[i--];
        }
        while(j >= mid + 1){
            copy[copy_index--] = array[j--];
        }
        i = 0;
        while(start <= end) {
            array[start++] = copy[i++];
        }
        return (left_count+right_count+count)%1000000007;
    }
}


```

数据范围：

```
对于%50的数据,size<=10^4

对于%75的数据,size<=10^5

对于%100的数据,size<=2*10^5
```

#### 例题14：数字在排序数组中出现的次数

统计一个数字在排序数组中出现的次数

这个。。。我的方法简单粗暴，看到别人的想法。。。。

！！！！！排序数组排序数组排序数组！！！！！！！

再麻烦也给我用二分法T.T

```java
//解题思路
//正常的思路就是二分查找了，我们用递归的方法实现了查找k第一次出现的下标，用循环的方法实现了查找k最后一次出现的下标。
//除此之外，还有另一种奇妙的思路，因为data中都是整数，所以我们不用搜索k的两个位置，而是直接搜索k-0.5和k+0.5这两个数应该插入的位置，然后相减即可

法2
public class Solution {
    public int GetNumberOfK(int [] array , int k) {
        return biSearch(array, k+0.5) - biSearch(array, k-0.5);
    }
    public int biSearch(int [] array, double k){
        int start  = 0, end = array.length - 1;
        while(start <= end){
            int mid = start + (end - start)/2;
            if(array[mid] > k){
                end = mid - 1;
            }else{
                start = mid + 1;
            }
        }
        return start;
    }
}
```

#### 例题15：数组中只出现一次的数字

一个整型数组里除了两个数字之外，其他的数字都出现了偶数次。请写程序找出这两个只出现一次的数字。

方法一：hashmap大法（我的方法）

方法二：异或法！

 

>    任何一个数字异或它自己都等于0。 

 

如果数组中只一个数字是只出现一次的，其他数字都是成双成对出现的，那么我们从头到尾依次异或数组中的每个数字，最终的结果刚好就是那个只出现一次的数字，因为那些成对出现两次的数字全部在异或中抵消了。

 

那么回到我们的题目，因为有两个只出现一次的数字，所以我们可以试着把原数组分成两个子数组，使得每个数组包含一个只出现一次的数字，而其他数字都成对出现两次。如果这样拆分成两个数组，那么我们就可以按照之前的办法分别对两个数组进行异或运算找出两个只出现一次的数字。

 

问题来了，如何进行分组呢？
 我们还是从头到尾依次异或数组中的每个数字，那么最终得到的结果就是两个只出现一次的数字异或的结果。由于这两个数字不一样，所以异或的结果至少有一位为1，我们在结果数字中找到第一个为1的位置，记为index位，现在我们以第index位是不是1为标准把原数组拆分成两个子数组，第一个子数组中的数组第index位都为1，第二个子数组中的数组第index位都为0，那么只出现一次的数字将被分配到两个子数组中去，于是每个子数组中只包含一个出现一次的数字，而其他数字都出现两次。这样我们就可以用之前的方法找到数组中只出现一次的数字了。

```java
public class Solution {
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        num1[0] = 0;
        num2[0] = 0;
        if(array.length == 0)
            return;
        int num = 0;
        for(int i = 0; i < array.length; i++){
            num ^= array[i];
        }
        int index = 0;
        while((num & 1) == 0 && index < 8){
            num = num >> 1;
            index ++;
        }

        for(int i = 0; i < array.length; i++){
            if(isa1(array[i], index))
                num1[0] ^= array[i];
            else
                num2[0] ^= array[i];
        }
    }

    public boolean isa1(int i, int index){
        i = i >> index;
        return (i & 1) == 1;
    }
}
```

#### 例题16：和为S的连续正数序列

别人的想法真好。。。

```java
//小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,
// 他马上就写出了正确答案是100。但是他并不满足于此,
// 他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。
// 没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。
// 现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        //看了大佬的思路，好活。。。我好菜嘤嘤嘤
        ArrayList<ArrayList<Integer>> all=new ArrayList<>();
        if (sum<=0) return all;
        int start=1;
        int end=2;
        //此时开始累加，如果比sum小，就增加end，如果比sum大就去掉start
        int temp=0;
        int i=start;
        int mid=(1+sum)/2;
        //此处，要一直循环直到到达中间
        while (start<mid){
            temp=sum(start,end);
            if (temp==sum){
                all.add(sequence(start,end));
                end++;
            }else if (temp<sum){
                end++;
            }else if (temp>sum){
                start++;
            }
        }
        return all;
    }

    public int sum(int start,int end){
        int sum=0;
        for (int i=start;i<=end;i++){
            sum+=i;
        }
        return sum;
    }

    public ArrayList<Integer> sequence(int start,int end){
        ArrayList<Integer> sequence=new ArrayList<>();
        for (int i=start;i<=end;i++){
            sequence.add(i);
        }
        return sequence;
    }
```

#### 例题17：和为S的两个数字

```java
import java.util.ArrayList;
//输入一个递增排序的数组和一个数字S，在数组中查找两个数，
// 使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
我的方法如下！！！为上一题的变种
public class OffernewCoder41 {
    public ArrayList<Integer> FindNumbersWithSum(int [] array, int sum) {
        ArrayList<Integer> ans=new ArrayList<>();
        if (array.length<2||array==null) return ans;

        int left=0;
        int right=array.length-1;
        int times=0;
        while (left<right){
            if (sum(array,left,right)==sum){
                ans.add(array[left]);
                ans.add(array[right]);
                //此处！！！！！根据分析，第一组一定是最小的，倒抛物线！！！！
                //x+y=s   xy=(s-x)x
                return ans;
            }else if (sum(array,left,right)<sum){
                left++;
            }else if (sum(array,left,right)>sum){
                right--;
            }
        }
        return ans;
    }

    public int sum(int array[],int left, int right){
        return (array[left]+array[right]);
    }
}

法一：哈希法。用一个HashMap，它的 key 存储数S与数组中每个数的差，value 存储当前的数字，比较S=15, 当前的数为 4，则往 hashmap 中插入(key=11, value=4)。我们遍历数组，判断hashmap 中的 key 是否存在当前的数字，如果存在，说明存在着另一个数与当前的数相加和为 S，我们就可以判断它们的乘积是否小于之前的乘积，如果小的话就替换之前的找到的数字，如果大就放弃当前找到的。如果hashmap 中的 key 不存在当前的数字，说明还没有找到相加和为 S 的两个数，那就把S与当前数字的差作为 key，当前数字作为 value 插入到 hashmap 中，继续遍历。
import java.util.ArrayList;
import java.util.HashMap;
public class Solution {
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(array.length < 2)
            return res;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int less = Integer.MAX_VALUE;
        for(int i = 0; i < array.length; i++){
            if(map.containsKey(array[i])){
                if(array[i] * map.get(array[i]) < less){
                    less = array[i] * map.get(array[i]);
                    res.clear();
                    res.add(map.get(array[i]));
                    res.add(array[i]);
                }
            }else{
                int key = sum - array[i];
                map.put(key, array[i]);
            }
        }
        return res;
    }
}
```

#### 例题18：数组中重复的数字

```java
/*在一个长度为n的数组里的所有数字都在0到n-1的范围内。
数组中某些数字是重复的，但不知道有几个数字是重复的。
也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，
那么对应的输出是第一个重复的数字2。*/
我的方法，很简单，即实用hashmap，开拓了空间，但是可行
  public boolean duplicate(int numbers[],int length,int [] duplication) {
        if (numbers==null||length<=1) return false;
        Map<Integer,Integer> dup= new HashMap<>();
        for (int i=0;i<length;i++){
            if (!dup.containsKey(numbers[i])){
                dup.put(numbers[i],1);
            }else {
                duplication[0]=numbers[i];
                return true;
            }
        }
        return false;
    }
    

大佬方法：
如果题目要求不能开辟额外的空间，那我们可以用如下的方法：
因为数组中的数字都在0~n-1的范围内，所以，如果数组中没有重复的数，那当数组排序后，数字i将出现在下标为i的位置。现在我们重排这个数组，从头到尾扫描每个数字，当扫描到下标为i的数字时，首先比较这个数字(记为m)是不是等于i。如果是，则接着扫描下一个数字；如果不是，则再拿它和m 位置上的数字进行比较，如果它们相等，就找到了一个重复的数字（该数字在下标为i和m的位置都出现了），返回true；如果它和m位置上的数字不相等，就把第i个数字和第m个数字交换，把m放到属于它的位置。接下来再继续循环，直到最后还没找到认为没找到重复元素，返回false。
 public boolean duplicate(int numbers[],int length,int [] duplication) {
        if(length == 0)
            return false;
        for(int i = 0; i < length; i++){
            while(i != numbers[i]){
                if(numbers[i] == numbers[numbers[i]]){
                    duplication[0] = numbers[i];
                    return true;
                }else{
                    int temp = numbers[numbers[i]];
                    numbers[numbers[i]] = numbers[i];
                    numbers[i] = temp;
                }
            }
        }
        return false;
    }
。。。我觉得这个方法一般，看如下
下面这个是C代码
```

```c
链接：https://www.nowcoder.com/questionTerminal/623a5ac0ea5b4e5f95552655361ae0a8
来源：牛客网

不需要额外的数组或者hash table来保存，题目里写了数组里数字的范围保证在0 ~ n-1
  之间，所以可以利用现有数组设置标志，当一个数字被访问过后，可以设置对应位上的数 +
  n，之后再遇到相同的数时，会发现对应位上的数已经大于等于n了，那么直接返回这个数即可。 
  代码是C：
  int   find_dup(  int   numbers[], int   length) { 
  	for   (  int   i=0  ; i<length; i++) {     
  		int   index = numbers[i];      
 		if   (index >= length) {
              index -= length;
        }           
  		if (numbers[index] >= length) {      
 			  return   index;
   		}   
   		numbers[index] = numbers[index] + length;
   }      
   return -1 ; 
}
```

#### 例题19：构建乘积数组

```java
//给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],
// 其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。
// 不能使用除法。
我是辣鸡。。。
 public int[] multiply(int[] A) {
        if (A.length<=1)return A;
        int []B=new int[A.length];
        B[0]=1;
        //先左边一半
        for (int i=1;i<A.length;i++){
            B[i]=B[i-1]*A[i-1];
        }

        int temp = 1;
        for(int j = A.length - 2; j>=0; j--){
            temp *= A[j+1];
            B[j] *= temp;
        }
        return B;

    }
    思路看这 https://www.weiweiblog.cn/multiply/?spm=a2c4e.11153940.blogcont642728.105.64bb6815A7GNEN
```

#### 例题20：正则表达式匹配

```java
//请实现一个函数用来匹配包括'.'和'*'的正则表达式。
// 模式中的字符'.'表示任意一个字符，
// 而'*'表示它前面的字符可以出现任意次（包含0次）。
// 在本题中，匹配是指字符串的所有字符匹配整个模式。
// 例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，
// 但是与"aa.a"和"ab*a"均不匹配
我好菜啊。。。
public boolean match(char[] str, char[] pattern)
{
    int sindex = 0, pindex = 0;
    return matchCore(str, sindex, pindex, pattern);
}
    public boolean matchCore(char[] str, int sindex, int pindex, char[] pattern){
        if(sindex >= str.length && pindex == pattern.length)
            return true;
        if(pindex >= pattern.length && sindex < str.length)
            return false;
        if(pindex+1 < pattern.length && pattern[pindex+1] == '*'){
            if(sindex < str.length && (str[sindex] == pattern[pindex] || pattern[pindex] == '.') ){
                return matchCore(str, sindex, pindex+2, pattern) ||
                        matchCore(str, sindex+1, pindex+2, pattern ) ||
                        matchCore(str, sindex+1, pindex, pattern);
            }else{
                return matchCore(str, sindex, pindex+2, pattern);
            }
        }
        if(sindex < str.length && (str[sindex] == pattern[pindex] || pattern[pindex] == '.'))
            return matchCore(str, sindex+1, pindex+1, pattern);
        return false;
    }
思路见此：  	
当模式中的第二个字符不是“*”时：
1、如果字符串第一个字符和模式中的第一个字符相匹配，那么字符串和模式都后移一个字符，然后匹配剩余的。
2、如果 字符串第一个字符和模式中的第一个字符相不匹配，直接返回false。
而当模式中的第二个字符是“*”时：
如果字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配。如果字符串第一个字符跟模式第一个字符匹配，可以有3种匹配方式：
1、模式后移2字符，相当于x*被忽略；
2、字符串后移1字符，模式后移2字符；
3、字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位。
```

#### 例题21：滑动窗口的最大值

```java
//给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
// 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
// 那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
// 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
// {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}，
// {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}，
// {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
public ArrayList<Integer> maxInWindows(int [] num, int size)
    {
        ArrayList<Integer> ans=new ArrayList<>();
        if(size>num.length|| size == 0) return ans;
        int j=size;
        for (int i=0;i<num.length-size+1;i++){
            ans.add(max(num,i,size));
        }

        return ans;
    }

    public int max(int numbers[],int start,int size){
        int max=numbers[start];
        for (int i=start+1;i<size+start;i++){
            if (numbers[i]>max) max=numbers[i];
        }
        return max;
    }
我的方法如上，暴力法！！

大佬方法如下
法二：双向队列
用一个双向队列，队列第一个位置保存当前窗口的最大值，当窗口滑动一次，判断当前最大值是否过期（当前最大值的位置是不是在窗口之外），新增加的值从队尾开始比较，把所有比他小的值丢掉。这样时间复杂度为O(n)。

public class Solution {
    public ArrayList<Integer> maxInWindows(int [] num, int size){
        ArrayList<Integer> res = new ArrayList<Integer>();
        LinkedList<Integer> deque = new LinkedList<Integer>();
        if(num.length == 0 || size == 0)
            return res;
        for(int i = 0; i < num.length; i++){
            if(!deque.isEmpty() && deque.peekFirst() <= i - size)
                deque.poll();
            while(!deque.isEmpty() && num[deque.peekLast()] < num[i])
                deque.removeLast();
            deque.offerLast(i);
            if(i + 1 >= size)
                res.add(num[deque.peekFirst()]);
        }
        return res;
    }
}
```

## 查找

https://www.cnblogs.com/yw09041432/p/5908444.html  7种常见排序法

#### 例题1：旋转数组的最小数字（二分法）

```java
/*把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
输入一个非减排序的数组的一个旋转，输出旋转数组的最小元素。
 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，
该数组的最小值为1。 NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。*/

//方法二，使用变种二分法
    public int minNumberInRotateArray(int [] array) {
        int len = array.length;
        if(len == 0)
            return 0;
        int low = 0, high = len - 1;
        while(low < high){
            int mid = low + (high - low) / 2;
            if(array[mid] > array[high]){
                low = mid + 1;
            }else if(array[mid] == array[high]){
                high = high - 1;
            }else{
                high = mid;
            }
        }
        return array[low];
    }
```

```
//在一个字符串(0<=字符串长度<=10000，
// 全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置,
// 如果没有则返回 -1（需要区分大小写）
```

## 字符串

#### 关于字符串

```java
/*StringBuffer是个类！！！！
* 使用的时候请new
* 它和StringBuilder的区别在于---StringBuffer是线程安全的
*                          ---StringBuilder速度快，单线程不安全*/
```

#### 例题1：第一个只出现一次的字符

```java
//在一个字符串(0<=字符串长度<=10000，
// 全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置,
// 如果没有则返回 -1（需要区分大小写）

方法不难，使用hashmap来统计
```

#### 例题2：左旋转字符串

```java
//汇编语言中有一种移位指令叫做循环左移（ROL），
// 现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
// 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
// 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，
// 即“XYZdefabc”。是不是很简单？OK，搞定它！
此处注意subString的应用
```

### 例题3：反转单词的序列

此处注意split的应用

#### 例题4：扑克牌顺子

找顺子，一副牌，但是有2个大王2两个小王，大小王可以当任何值，为方便当为0，模拟抽5张牌，是否为顺子

注意！！！！看起来简单，但是坑很多！！！！！！，跑了好几次才过

```java
  public boolean isContinuous(int [] numbers) {
        if (numbers.length>5||numbers==null) return false;
        Arrays.sort(numbers);//先排序，后判断
        int count0=0;
        int sum=0;
        for (int i=0;i<numbers.length-1;i++){
            if (numbers[i]==0){
                count0++;
            }else {
                if (numbers[i]==numbers[i+1]) return false;//如果抽到相同的，肯定不行
                if((numbers[i+1]-numbers[i])>1){
                  
                    sum+=numbers[i+1]-numbers[i]-1;
                }
            }
        }
        if (sum>=count0){
            return false;
        }

        return true;
    }
```

#### 例题5：字符流中第一个不重复的字符

​	

```java
//请实现一个函数用来找出字符流中第一个只出现一次的字符。
// 例如，当从字符流中只读出前两个字符"go"时，
// 第一个只出现一次的字符是"g"。
// 当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
//如果当前字符流没有存在出现一次的字符，返回#字符。

//我的老方法，依然是实用map

 //Insert one char from stringstream
    Map<Character,Integer> all=new HashMap<>();
    ArrayList<Character>  list=new ArrayList<>();
    public void Insert(char ch)
    {
        if (all.containsKey(ch)){
            all.put(ch,all.get(ch)+1);
        }else {
            all.put(ch,1);
        }
        list.add(ch);
    }
    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce()
    {
        char ans='#';
        for (int i=0;i<list.size();i++){
            if (all.get(list.get(i))==1){
                ans=list.get(i);
                break;
            }
        }
        return ans;
    }
```

## 链表

#### 关于链表

```java
//大致结构
public static class ListNode {
     int val;
     ListNode next = null;
     ListNode(int val) {
         this.val = val;
     }
}
```

#### 例题1：反转链表

不难，但是要注意链表结点之间的连接！

```java
/*输入一个链表，按链表值从尾到头的顺序返回一个ArrayList*/
public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
            if (listNode==null) return new ArrayList<Integer>();
            ArrayList tailToHead=new ArrayList<Integer>();
            //此处，两个node来进行移动
            ListNode head=listNode;
            ListNode move=listNode.next;
            head.next=null;
            while (move!=null){//注意了！！！！每次要这么换下一个node！！！
                ListNode temp=move.next;
                move.next=head;
                head=move;
                move=temp;
                //将所有的转换方向后，向前移动一步！
            }

            //此处！！！智障宝宝嘤嘤嘤
            //第一次忘记了写成head.next了，此处要注意，使用head，移动head！
            while (head!=null){
                tailToHead.add(head.val);
                head=head.next;
            }
            return tailToHead;
        }
```

#### 例题2：找出倒数第k个结点

```java
//输入一个链表，输出该链表中倒数第k个结点。
我的思路：反转链表后---取出第k个----然而不知为何错了？？？我觉得很对啊
   public ListNode FindKthToTail(ListNode head, int k) {
            if (k <= 0) return null;
            if (head == null) return null;
            ListNode tail = head;
            ListNode move = head.next;
            head.next = null;
            //反转链表
            while (move != null) {
                ListNode temp = move.next;
                move.next = tail;
                tail = move;
                move = temp;
            }
            //开始找第k个
            int i = 1;
            ListNode temp = tail;
            while (i != k) {
                temp = temp.next;
                i++;
            }
            return temp;
        }
        
//大神思路：这个思路很ok，使用双指针方法：经典的双指针法。定义两个指针，第一个指针从链表的头指针开始遍历向前走k-1步，第二个指针保持不动，从第k步开始，第二个指针也开始从链表的头指针开始遍历，由于两个指针的距离保持在k-1，当第一个指针到达链表的尾节点时，第二个指针刚好指向倒数第k个节点。
//关注要点
//1. 链表头指针是否为空，若为空则直接返回回null
//2. k是否为0，k为0也就是要查找倒数第0个节点，由于计数一般是从1开始的，所有输入0没有实际意义，返回null
//3. k是否超出链表的长度，如果链表的节点个数少于k，则在指针后移的过程中会出现next指向空指针的错误，所以程序中要加一个判断

public class Solution {
    public ListNode FindKthToTail(ListNode head,int k) {
        if(head == null || k == 0)
            return null;
        ListNode temp = head;
        //判断k是否超过链表节点的个数，注意是 i < k - 1
        for(int i=0; i < k-1; i++){
            if(temp.next != null)
                temp = temp.next;
            else
                return null;
        }
        ListNode pA = head;
        ListNode pB = head;
        for(int i=0; i<k-1; i++)
            pA = pA.next;
        while(pA.next != null){
            pA = pA.next;
            pB = pB.next;
        }
        return pB;
    }
}


//方法三：使用stack，推进后，取出第k个
```

#### 例题3：合并链表（******）

```java
//该题。。。没想出来
//输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
//递归解法
public class Solution {
    public ListNode Merge(ListNode list1,ListNode list2) {
        if(list1 == null)
            return list2;
        else if(list2 == null)
            return list1;
        ListNode mergehead = null;
        if(list1.val <= list2.val){
            mergehead = list1;
            mergehead.next = Merge(list1.next,list2);
        }else{
            mergehead = list2;
            mergehead.next = Merge(list1, list2.next);
        }
        return mergehead;
    }
}
//非递归解法
public class Solution {
    public ListNode Merge(ListNode list1,ListNode list2) {
        if(list1 == null)
            return list2;
        else if(list2 == null)
            return list1;
        ListNode mergehead = null;
        if(list1.val <= list2.val){
            mergehead = list1;
            list1 = list1.next;
        }else{
            mergehead = list2;
            list2 = list2.next;
        }
        ListNode cur = mergehead;
        while(list1 != null && list2 != null){
            if(list1.val <= list2.val){
                cur.next = list1;
                list1 = list1.next;
            }else{
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        if(list1 == null)
            cur.next = list2;
        else if(list2 == null)
            cur.next = list1;
        return mergehead;
    }
}
//递归想明白，很ok！
```

#### 例题4：复杂链表的复制

此题。。。没想出来，按照网上思路写了一下，写出来了，但是超市，唯一区别就在于  0？1：0

```java
    public RandomListNode Clone(RandomListNode pHead)
    {
        if(pHead == null)
            return null;
        //复制节点 A->B->C 变成 A->A'->B->B'->C->C'
        RandomListNode head = pHead;
        while(head != null){
            RandomListNode node = new RandomListNode(head.label);
            node.next = head.next;
            head.next = node;
            head = node.next;
        }
        //复制random
        head = pHead;
        while(head != null){
            head.next.random = head.random == null ? null : head.random.next;
            head = head.next.next;
        }
        //折分
        head = pHead;
        RandomListNode chead = head.next;
        while(head != null){
            RandomListNode node = head.next;
            head.next = node.next;
            node.next = node.next == null ? null : node.next.next;
            head = head.next;
        }
        return chead;
    }
```

#### 例题5：两个链表的第一个结点

输入两个链表，找出它们的第一个公共结点

//方法没有很难，如果两个链表有公共结点，说明它们后面的部分都是相同的，因此可以使用stack，然后一个一个退出比较

#### 例题6：小朋友领奖品（环形链表）

```java
//小朋友围城一个圈，指定数m，编号为0的开始报数，直到报到m-1，出来要表演节目，
//并且不用回圈，它的下一个小朋友继续报数，直到只剩一个人，求是哪个人？
//小朋友编号为0到n-1

  /*该方法虽然可行。。。但是超时了*/
//    public int LastRemaining_Solution(int n, int m) {
//        if (m<1) return -1;
//        int end=0;//记录结束的孩子数
//        int i=0;//记录报数
//        int j=0;//记录下标
//        int children[]=new int[n];
//        int len=children.length;
//        while (end!=n-1) {
//            if (j==len-1)j=0;
//            if (children[j]==-1)continue;
//            if (i==m-1){//当喊道这个小朋友的时候
//                i=0;
//                children[j]=-1;
//                end++;
//            }
//
//            i++;
//            j++;
//        }
//        int ans=0;
//        for (int k=0;k<children.length;k++){
//            if (children[k]!=-1){
//                ans=k;
//                break;
//            }
//        }
//        return ans;
//    }

    public int LastRemaining_Solution(int n, int m) {
        if(n < 1 || m < 1)
            return -1;
        LinkedList<Integer> link = new LinkedList<Integer>();
        for(int i = 0; i < n; i++)
            link.add(i);
        int index = -1;   //起步是 -1 不是 0
        while(link.size() > 1){
            index = (index + m) % link.size();  //对 link的长度求余不是对 n
            link.remove(index);
            index --;
        }
        return link.get(0);
    }

```

#### 例题7：链表的环结点

```java
//给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
我的方法。。依然很简单。。实用hashmap

其他方法：两个指针法
假设环长度为n，进入环之前结点个数为x,slow在环内走了k个结点,fast绕环走了m圈,则有2(x+k)=x+mn+k 可以得出x = mn - k。此时slow距入口结点还剩 n-k个结点,x=(m−1)n+n−k，即一个指针从链表头节点走到环入口的长度等于另一个指针从相遇的位置走 m-1圈后再走n-k的长度，也就是说两个指针相遇后，让一个指针回到头节点，另一个指针不动，然后他们同时往前每次走一步，当他们相遇时，相遇的节点即为环入口节点。
所以，我们设置两个指针，一个是快指针fast，一个是慢指针slow，fast一次走两步，slow一次走一步，如果单链表有环那么当两个指针相遇时一定在环内。此时将一个指针指到链表头部，另一个不变，二者同时每次向前移一格,当两个指针再次相遇时即为环的入口节点。如果fast走到null则无环。
public ListNode EntryNodeOfLoop(ListNode pHead)
    {
        if(pHead.next == null || pHead.next.next == null)
            return null;
        ListNode slow = pHead.next;
        ListNode fast = pHead.next.next;
        while(fast != null){
            if(fast == slow){
                fast = pHead;
                while(fast != slow){
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return null;
    }

```

#### 例题8：删除链表中重复的结点



```java
//在一个排序的链表中，存在重复的结点，
// 请删除该链表中重复的结点，重复的结点不保留，
// 返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
 
public ListNode deleteDuplication(ListNode pHead) {
        //放两个指针，一个在当前位置，一个不停挪动，知道找到比较大的那个
    //这里自己注意！有可能第一个就是重复的，
        if(pHead == null || pHead.next == null)
            return pHead;
        ListNode head = new ListNode(-1);
        head.next = pHead;

        ListNode now=head, move=head.next;
        while (move!=null){
            if (move.next!=null&&move.next.val==move.val){
                while (move.next!=null&&move.next.val==move.val){
                    move=move.next;//找不到不同的，就动
                }
                now.next=move.next;
                //找到了就连
            }else {
                now=now.next;
            }
            //开始移动继续
            move=move.next;
        }
        return head.next;
    }
```



## 栈

#### 关于栈

FILO

#### 例题1：使用栈实现队列

不难

#### 例题2：包含min函数的栈

我太辣鸡了。。。此题不难，重点理解

定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。

```java

//大佬思路，自我完善后，只觉得秒啊！！！	
//用一个栈stack保存数据，用另外一个栈temp保存依次入栈最小的数
//比如，stack中依次入栈
//5, 3, 4, 10, 2, 12, 1, 8
//则temp依次入栈
//5, 3, 3，3, 2, 2, 1, 1
//每次入栈的时候，如果入栈的元素比min中的栈顶元素小或等于则入栈，否则用最小元素入栈。
Stack<Integer> mystack=new Stack();
Stack<Integer> minStack=new Stack<>();
int min;
public void push(int node) {
    mystack.push(node);
    if (minStack.isEmpty()) min=node;
    if (node<=min) min=node;
    minStack.push(min);
}

public void pop() {
    int temp=mystack.peek();
    mystack.pop();
    minStack.pop();
    if (temp==min){
        min=minStack.peek();
    }
}

public int top() {
    return mystack.peek();
}

public int min() {
    return min;
}
```

#### 例题3：栈的压入与弹出顺序

```java
//输入两个整数序列，第一个序列表示栈的压入顺序，
// 请判断第二个序列是否可能为该栈的弹出顺序。
// 假设压入栈的所有数字均不相等。
// 例如序列1,2,3,4,5是某栈的压入顺序，
// 序列4,5,3,2,1是该压栈序列对应的一个弹出序列，
// 但4,3,5,1,2就不可能是该压栈序列的弹出序列。
// （注意：这两个序列的长度是相等的）
此题不难，重点是怎么理解
原先。。。我通过找规律，发现，能pop出来的，是index递增，递减的关系。。然而。。本智障不会
因此！！！
///可使用两个数组模拟栈的压入弹出
代码简单，注意理解
```



## 二进制

关于负数补码

-4 --- 4 --- 100 --- 1001 --- 1010 

反转---二进制---+1----负数1开头



#### 例题1：二进制中的1

输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。

解题思路：

如果一个整数不为0，那么这个整数至少有一位是1。如果我们把这个整数减1，那么原来处在整数最右边的1就会变为0，原来在1后面的所有的0都会变成1(如果最右边的1后面还有0的话)。其余所有位将不会受到影响。
举个例子：一个二进制数1100，从右边数起第三位是处于最右边的一个1。减去1后，第三位变成0，它后面的两位0变成了1，而前面的1保持不变，因此得到的结果是1011.我们发现减1的结果是把最右边的一个1开始的所有位都取反了。这个时候如果我们再把原来的整数和减去1之后的结果做与运算，从原来整数最右边一个1那一位开始所有位都会变成0。如1100&1011=1000.也就是说，把一个整数减去1，再和原整数做与运算，会把该整数最右边一个1变成0.那么一个整数的二进制有多少个1，就可以进行多少次这样的操作。

```java
public class Solution {
    public int NumberOf1(int n) {
        int count = 0;
        while(n!=0){
            count += 1;
            n &= (n-1);
        }
        return count;
    }
}
```



## 数值

#### 例题1：数值的整数次方

不难，但是要注意代码的完整性，要考虑到所有情况

```java
//给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
//我的方法  O(N)
//大佬思路如下
```

太长了，见连接  https://www.weiweiblog.cn/power/?spm=a2c4e.11153940.blogcont642728.29.64bb6815IsWxhV

#### 例题2：整数中1的次数（从1到n整数中1出现的次数）

```java
//求出1~13的整数中1出现的次数,
// 并算出100~1300的整数中1出现的次数？
// 为此他特别数了一下1~13中包含1的数字有1、10、11、12、13
// 因此共出现6次,但是对于后面问题他就没辙了。
// ACMer希望你们帮帮他,并把问题更加普遍化,
// 可以很快的求出任意非负整数区间中1出现的次数
// （从1 到 n 中1出现的次数）。


//我的方法：将其转换成String，然后数
public int NumberOf1Between1AndN_Solution(int n) {
        if(n<=0) return 0;
//        /*根据观察，0~9  1   10~19 10+1  之后每个10区间+1*/
//        /*100~109 10+1 110~119 10+10+1 ……*/
//        /*200~209 1    210~219 10+1*/
        int ans=0;
    //注意，此处请使用Stringbuffer，不然会超时
        StringBuffer s=new StringBuffer();
        for (int i=1;i<=n;i++){
            s.append(i);
        }
        String all=s.toString();
        for (int i=0;i<all.length();i++){
            if (all.charAt(i)=='1'){
                ans++;
            }
        }
        return ans;
    }
法三：归纳法
设N = abcde ,其中abcde分别为十进制中各位上的数字。
如果要计算百位上1出现的次数，它要受到3方面的影响：百位上的数字，百位以下（低位）的数字，百位以上（高位）的数字。
① 如果百位上数字为0，百位上可能出现1的次数由更高位决定。比如：12013，则可以知道百位出现1的情况可能是：100~199，1100~1199,2100~2199，，…，11100~11199，一共1200个。可以看出是由更高位数字（12）决定，并且等于更高位数字（12）乘以 当前位数（100）。
② 如果百位上数字为1，百位上可能出现1的次数不仅受更高位影响还受低位影响。比如：12113，则可以知道百位受高位影响出现的情况是：100~199，1100~1199,2100~2199，，….，11100~11199，一共1200个。和上面情况一样，并且等于更高位数字（12）乘以 当前位数（100）。但同时它还受低位影响，百位出现1的情况是：12100~12113,一共114个，等于低位数字（113）+1。
③ 如果百位上数字大于1（2~9），则百位上出现1的情况仅由更高位决定，比如12213，则百位出现1的情况是：100~199,1100~1199，2100~2199，…，11100~11199,12100~12199,一共有1300个，并且等于更高位数字+1（12+1）乘以当前位数（100）。
 public int NumberOf1Between1AndN_Solution(int n) {
        int res = 0;
        int cur = 0, before = 0, after = 0;
        int i = 1;
        while(i<=n){
            before = n/(i*10);
            cur = (n/i)%10;
            after = n - n/i*i;
            if(cur == 0){
                // 如果为0,出现1的次数由高位决定,等于高位数字 * 当前位数
                res += before * i;
            }else if(cur == 1){
                // 如果为1, 出现1的次数由高位和低位决定,高位*当前位+低位+1
                res += before * i + after + 1;
            }else{
                // 如果大于1, 出现1的次数由高位决定,（高位数字+1）* 当前位数
                res += (before + 1) * i;
            }
            i *= 10;
        }
        return res;
    }
```

#### 例题3：把数组拍成最小的数

输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。

解题思路

先将数组转换成字符串数组，然后对字符串数组按照规则排序，最后将排好序的字符串数组拼接出来。
 关键就是制定排序规则：

 

- 若ab > ba 则 a > b
- 若ab < ba 则 a < b
- 若ab = ba 则 a = b

 

>    解释说明：
>    a = 21
>    b = 2
>    因为 212 < 221, 即 ab < ba ，所以 a < b
>    所以我们通过对ab和ba比较大小，来判断a在前或者b在前的。 



```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
public class Solution {
    public String PrintMinNumber(int [] numbers) {
        int len = numbers.length;
        if(len == 0)
            return "";
        if(len == 1)
            return String.valueOf(numbers[0]);
        StringBuffer res = new StringBuffer();
        String [] str = new String[len];
        for(int i=0; i<len; i++)
            str[i] = String.valueOf(numbers[i]);
        Arrays.sort(str, new Comparator<String>(){
            public int compare(String s1, String s2) {
                String c1 = s1 + s2;
                String c2 = s2 + s1;
                return c1.compareTo(c2);
            }
        });
        for(int i=0; i<len; i++)
            res.append(str[i]);
        return res.toString();
    }
}
```

关于此处比较器，有一个比较好的介绍

> 作者：fantasy_
>
> 链接：
>
> https://www.nowcoder.com/questionTerminal/8fecd3f8ba334add803bf2a06af1b993
>
> 来源：牛客网
>
> 
>
> 关于比较器，比如例题中的{3，32，321} 数组中先放入3，而后3和32比较，因为332>323 所以3>32 数组此时为[32,3]; 再往数组中加入321，先与32比较，32132<32321 故 321<32  故321应排在32前面，再与3比较 3213<3321 故321<3 数组最终排序[321，32，3]

#### 例题4：求1+2+3+++++n

不能用乘除，if else while for switch

那就递归啊！！！！

#### 例题5：不用加减乘除做加法

> 链接：<https://www.nowcoder.com/questionTerminal/59ac416b4b944300b617d4f7f111b215>
> 来源：牛客网
>
> 首先看十进制是如何做的： 5+7=12，三步走 第一步：相加各位的值，不算进位，得到2。 第二步：计算进位值，得到10. 如果这一步的进位值为0，那么第一步得到的值就是最终结果。  第三步：重复上述两步，只是相加的值变成上述两步的得到的结果2和10，得到12。  同样我们可以用三步走的方式计算二进制值相加： 5-101，7-111 第一步：相加各位的值，不算进位，得到010，二进制每位相加就相当于各位做异或操作，101^111。  第二步：计算进位值，得到1010，相当于各位做与操作得到101，再向左移一位得到1010，(101&111)<<1。  第三步重复上述两步， 各位相加 010^1010=1000，进位值为100=(010&1010)<<1。      继续重复上述两步：1000^100 = 1100，进位值为0，跳出循环，1100为最终结果。 

```java
public int Add(int num1, int num2) {
        if(num2 == 0)
            return num1;
        int sum = 0, carry = 0;
        while(num2 != 0){
            sum = num1 ^ num2;
            carry = (num1 & num2) << 1;
            num1 = sum;
            num2 = carry;
        }
        return num1;
    }
```

#### 例题6：表示数值的字符串

此处，注意判断条件

```java
//请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
// 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
// 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
  public boolean isNumeric(char[] str) {
        if(str.length==0)return false;
        boolean pOrM=false,hasE=false,hasDot=false;
        for (int i=0;i<str.length;i++){
            //如果是+/-
            if(str[i]=='+'||str[i]=='-'){
                //不是第一个的话，必须前面是E或e
                if (i>0&&str[i-1]!='E'&&str[i-1]!='e'){
                    return false;
                }
                pOrM=true;
            }else if (str[i]=='E'||str[i]=='e'){
                if (hasE) return false;//不能有两个
                if (i==str.length-1) return false;//e后面必须要有数字
                    hasE=true;
            }else if (str[i]=='.'){
                if (hasDot) return false;
                if (hasE) return false;//
                hasDot=true;
            }else if (str[i]>'9'||str[i]<0) {//不合法
                return false;
            }
        }
        return true;
    }
```

#### 例题7：数据流的中位数

如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。



。。。不会做。这么做吧

我们可以将数据排序后分为两部分，左边部分的数据总是比右边的数据小。那么，我们就可以用最大堆和最小堆来装载这些数据：

 

- 最大堆装左边的数据，取出堆顶（最大的数）的时间复杂度是O(1)
- 最小堆装右边的数据，同样，取出堆顶（最小的数）的时间复杂度是O(1)

 

从数据流中拿到一个数后，先按顺序插入堆中：如果左边的最大堆是否为空或者该数小于等于最大堆顶的数，则把它插入最大堆，否则插入最小堆。然后，**我们要保证左边的最大堆的size等于右边的最小堆的size或者最大堆的size比最小堆的size大1。**
 要获取中位数的话，直接判断最大堆和最小堆的size，如果相等，则分别取出两个堆的堆顶除以2得到中位数，不然，就是最大堆的size要比最小堆的size大，这时直接取出最大堆的堆顶就是我们要的中位数。

```java
import java.util.PriorityQueue;
import java.util.Comparator;
public class Solution {
    // 最小堆（右）
    private PriorityQueue<Integer> rHeap = new PriorityQueue<>(); 
    // 最大堆（左）
    private PriorityQueue<Integer> lHeap = new PriorityQueue<Integer>(15, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
    // 保证lHeap.size()>=rHeap.size()
    public void Insert(Integer num) {
        // 先按大小插入，再调整
        if(lHeap.isEmpty() || num <= lHeap.peek())
            lHeap.offer(num);
        else
            rHeap.offer(num);

        if(lHeap.size() < rHeap.size()){
            lHeap.offer(rHeap.peek());
            rHeap.poll();
        }else if(lHeap.size() - rHeap.size() == 2){
            rHeap.offer(lHeap.peek());
            lHeap.poll();
        }
    }
    public Double GetMedian() {
        if(lHeap.size() > rHeap.size())
            return new Double(lHeap.peek());
        else
            return new Double(lHeap.peek() + rHeap.peek())/2;
    }
}
```

