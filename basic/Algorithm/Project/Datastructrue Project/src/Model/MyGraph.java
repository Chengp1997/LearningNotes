package Model;

import java.util.LinkedList;

public class MyGraph {
    private LinkedList<Vertex> allVertex=new LinkedList<>();
    private Vertex root;

    /**
     * 加顶点
     * @param vertex  顶点
     * @throws Exception
     */
    public void addVertex(Vertex vertex) throws Exception {
        allVertex.add(vertex);
    }

    /**
     * 获得所有顶点
     * @return  返回顶点链表
     */
    public LinkedList<Vertex> getAllVertex() {
        return allVertex;
    }

    /**
     * 形成邻接表  adjacency list
     */
    public void createGraph(MyGraph initialGraph) {
        for (int i = 0; i < initialGraph.getAllVertex().size(); i++) {
            System.out.print(initialGraph.getAllVertex().get(i).getSpotsName());
            for (int j = 0; j < initialGraph.getAllVertex().get(i).getAllEdge().size(); j++) {
                System.out.print(" -- " + initialGraph.getAllVertex().get(i).getAllEdge().get(j).getWeight() + " -- "
                        + initialGraph.getAllVertex().get(i).getAllEdge().get(j).getDestinationSpot().getSpotsName());
            }
            System.out.println();
        }
    }

    /**
     * 生成邻接矩阵
     *
     * @param initialGraph
     */
    public void outputGraph(MyGraph initialGraph) {
        int size = initialGraph.getAllVertex().size();
        String adjacencyMatrix[][] = new String[size + 1][size + 1];//为方便输出，存储到数组中

        //初始化景区名字
        for (int i = 1; i < size + 1; i++) {
            adjacencyMatrix[0][i] = initialGraph.getAllVertex().get(i - 1).getSpotsName();
            adjacencyMatrix[i][0] = initialGraph.getAllVertex().get(i - 1).getSpotsName();
            adjacencyMatrix[0][0] = "    ";
        }

        //初始化所有点
        for (int i = 1; i < size + 1; i++) {
            for (int j = 1; j < size + 1; j++) {
                adjacencyMatrix[i][j] = Integer.toString(32767);
                if (i == j) {
                    adjacencyMatrix[i][j] = Integer.toString(0) + "\t";
                }
            }
        }
        //初始化所有连接点
        for (int k = 0; k < size; k++) {//竖排
            for (int j = 0; j < initialGraph.getAllVertex().get(k).getAllEdge().size(); j++) {//边
                for (int i = 0; i < size; i++) {//横排
                    if (initialGraph.getAllVertex().get(k).getAllEdge().get(j).getDestinationSpot().getSpotsName().equals(adjacencyMatrix[0][i + 1])) {
                        adjacencyMatrix[k + 1][i + 1] = Integer.toString(initialGraph.getAllVertex().get(k).getAllEdge().get(j).getWeight()) + "\t";
                    }
                }
            }
        }
        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {
                System.out.print(adjacencyMatrix[i][j] + "\t");
                if (j == size) System.out.println();
            }
        }

    }

    /**
     * 获得相应图的邻接矩阵
     * @param initialGraph  传入的图
     * @return  返回相应的邻接矩阵
     */
    public int[][]  getAdjacencyMatrix(MyGraph initialGraph){
        int size = this.getAllVertex().size();
        int returnMatrix[][]=new int[size][size];
        //初始化所有点
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                returnMatrix[i][j] = 32767;
                if (i == j) {
                    returnMatrix[i][j] = 0;
                }
            }
        }
        //初始化所有连接点
        for (int k = 0; k < size; k++) {//竖排
            for (int j = 0; j < this.getAllVertex().get(k).getAllEdge().size(); j++) {//边
                for (int i = 0; i < size; i++) {//横排
                    if (this.getAllVertex().get(k).getAllEdge().get(j).getDestinationSpot().getSpotsName().equals(this.getAllVertex().get(i).getSpotsName())){
                        returnMatrix[k][i] = this.getAllVertex().get(k).getAllEdge().get(j).getWeight();
                    }
                }
            }
        }
        return  returnMatrix;
    }


    /**
     * 判断graph中是否有相同名称的点
     *
     * @param name 输入的名称
     * @return 返回判断
     */
    public boolean contains(String name) {
        for (int i = 0; i < this.getAllVertex().size(); i++) {
            if (this.getAllVertex().get(i).getSpotsName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回所有点中具有相同名称的点
     *
     * @param name 相同的名称
     * @return 具有相同名称的点
     */
    public Vertex getVertex(String name) {
        for (int i = 0; i < this.getAllVertex().size(); i++) {
            if (this.getAllVertex().get(i).getSpotsName().equals(name)) {
                return this.getAllVertex().get(i);
            }
        }
        return null;
    }

    /**
     * 计算最短路径的dijistra算法
     * @param root  路径的起点
     * @param destinationVertex  路径的终点
     * @return  返回最小路程的路径列表
     * @throws Exception
     */
    public Vertex[] Dijkstra(Vertex root,Vertex destinationVertex) throws Exception {
        int adjacencyMatrix[][]=getAdjacencyMatrix(this);//我的邻接矩阵
        int distance[]=new int[this.getAllVertex().size()];
        boolean isflag[]=new boolean[distance.length];//用来标记
        int shortestPath[]=new int[distance.length];
        for (int i=0;i<isflag.length;i++){
            isflag[i]=false;

        }
        int begin=this.allVertex.indexOf(root);
        int end=this.allVertex.indexOf(destinationVertex);
        //从第一行开始
        distance = adjacencyMatrix[begin];
        for (int i=0;i<distance.length;i++){
            if(i!=begin&&distance[i]<32767) {
                shortestPath[i] = begin;
            } else{
                shortestPath[i]=-1;
            }
        }
        isflag[begin]=true;
        while (true){
            //首先找到distance中的最短路径
            int min=32767;
            int flag=-1;
            for (int i=0;i<distance.length;i++){
                if (!isflag[i]){//若这行未被标记过，则进行寻找最小值
                    if (distance[i]<min){
                        min=distance[i];//找到这行中最小的
                        flag=i;
                    }
                }
            }//找完未更新flag说明没有通路
            if (flag==-1){
                break;
            }
            //标记这行
            isflag[flag]=true;
            //更新最短的路径
            for (int i=0;i<distance.length;i++){
                if (!isflag[i]&&adjacencyMatrix[flag][i]!=32767&&(distance[flag]+adjacencyMatrix[flag][i])<distance[i]){//未被标记并且是相连的
                    distance[i]=distance[flag]+adjacencyMatrix[flag][i];
                    shortestPath[i]=flag;
                }
            }

        }
//        System.out.println("最短路程为：  "+(distance[end]-distance[begin]));
        int place=end;
        LinkedList<String> path=new LinkedList<>();//用来记录正序
        while (place!=-1){
            path.add(this.getAllVertex().get(place).getSpotsName());
            place=shortestPath[place];
        }
        Vertex road[]=new Vertex[path.size()];//为了方便后期处理，将路径正序安放
        int j=0;
        for (int i=path.size()-1;i>=0;i--){
            road[j++]=this.getVertex(path.get(i));

        }
        return road;
    }

    /**
     * 打出最短路径推荐
     * @param road
     */
    public void printOutShortestPath(Vertex road[]){
        System.out.println("最短路径推荐为： ");
        for (int i=0;i<road.length;i++){
            System.out.print(road[i].getSpotsName());
            if (i!=road.length-1){
                System.out.print("  ->  ");
            }
        }
        System.out.println();
    }

    /**
     * 计算最短路径的floyd算法
     * @param start  起始位置
     * @param end  路径的终点
     * @return  返回总图
     */
    public Vertex[] Floyd(Vertex start,Vertex end) throws Exception {
        int begin=this.allVertex.indexOf(start);
        int theEnd=this.allVertex.indexOf(end);
        int adjacencyMatrix[][] = getAdjacencyMatrix(this);//我的邻接矩阵
        int shortestPath[][] = new int[this.allVertex.size()][this.allVertex.size()];

        //将shortestPath数组初始化
        for (int i = 0; i < shortestPath.length; i++) {
            for (int j = 0; j < shortestPath.length; j++) {
                shortestPath[i][j] = j;
            }
        }

        for (int k = 0; k < adjacencyMatrix.length; k++) {
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    int temp;
                    if(adjacencyMatrix[i][k]==32767||adjacencyMatrix[k][j]==32767){
                        temp=32767;
                    }else {
                        temp=adjacencyMatrix[i][k]+adjacencyMatrix[k][j];
                    }
                    if (adjacencyMatrix[i][j] > temp) {
                        adjacencyMatrix[i][j] = temp;
                        shortestPath[i][j] = shortestPath[i][k];
                    }
                }
            }
        }
        System.out.println("最短路程为：  "+(adjacencyMatrix[begin][theEnd]-adjacencyMatrix[begin][begin]));
        int place=theEnd;
        LinkedList<String> path=new LinkedList<>();//用来记录正序
        do {
            path.add(this.getAllVertex().get(place).getSpotsName());
            place=shortestPath[place][begin];
        }while (place!=begin);

        path.add(this.getAllVertex().get(begin).getSpotsName());
        Vertex road[]=new Vertex[path.size()];//为了方便后期处理，将路径正序安放
        int j=0;
        for (int i=path.size()-1;i>=0;i--){
                road[j++]=this.getVertex(path.get(i));

        }
        return road;
    }


    /**
     * 获得图的所有边（不重复）
     * @return  返回边链表
     * @throws Exception
     */
    public LinkedList<Edge> getAllEdge() throws Exception {
        LinkedList<Edge>  allEdge = new LinkedList<>();
        //获得所有边，首先将所有点的各个边加入。
        for (int i=0;i<this.allVertex.size();i++){
            for (int j=0;j<this.getAllVertex().get(i).getAllEdge().size();j++){
                allEdge.add( this.getAllVertex().get(i).getAllEdge().get(j));
            }
        }
        for (int i=0;i<allEdge.size();i++){//遍历所有的边，查找重复的边
            for (int j=0;j<allEdge.size();j++){
                if (allEdge.get(i).getDestinationSpot().equals(allEdge.get(j).getStartSpot())&&allEdge.get(i).getStartSpot().equals(allEdge.get(j).getDestinationSpot())){
                    allEdge.remove(allEdge.get(j));
                }
            }
        }
        return allEdge;
    }

    /**
     *
     * 求最小生成树的prim算法
     * @param root  树的根节点
     * @return  返回最小生成树的数组
     */
    public int[] prim(Vertex root){
        int start=this.getAllVertex().indexOf(root);
        int adjacencyMatrix[][]=this.getAdjacencyMatrix(this);
        int tree[]=new int[this.allVertex.size()];//用来存储最小生成树
        int weights[]=new int[this.allVertex.size()];//用来存储各权值
        int count=0;
        //进行初始化
        if (this.getAllVertex().indexOf(root)!=-1) {
            tree[count++] = this.getAllVertex().indexOf(root);//树的第一个结点为开始的结点

            for (int i = 0; i < this.allVertex.size(); i++) {
                weights[i] = adjacencyMatrix[start][i];//将权值赋为所在行的权值，即代表开始的结点到各个结点的距离
            }

            for (int j = 0; j < this.allVertex.size(); j++) {
                if (start == j)
                    continue;

                int positon = 0;
                int min = 32767;
                //选出权值最小的结点
                for (int i = 0; i < this.allVertex.size(); i++) {
                    if (weights[i] < min && weights[i] != 0) {
                        min = weights[i];
                        positon = i;//获得权值最小的点的位置
                    }
                }

                //将获得的点键入结果集中
                tree[count++] = positon;
                //并将标记的位置重置为0，表明已经被标记过了
                weights[positon] = 0;
                //更新权值数组
                for (int i = 0; i < this.allVertex.size(); i++) {
                    if (weights[i] != 0 && adjacencyMatrix[positon][i] < weights[i]) {
                        weights[i] = adjacencyMatrix[positon][i];
                    }
                }

            }

            System.out.print("最小生成树为： ");
            for (int i = 0; i < tree.length; i++) {
                System.out.print(this.getAllVertex().get(tree[i]).getSpotsName() + "  ");
            }
            System.out.println();
            return tree;
        }else {
            return null;
        }

    }

//    public int[] kruskal(Vertex root) throws Exception {
//        LinkedList<Edge> allEdge=this.getAllEdge();
//
//    }
    /**
     * 生成导游图
     * @param root  所在的位置
     */
    public void printOutGuidanceRoute(Vertex root,Vertex end) throws Exception {
        int tree[]= prim(root);//获得我的最小生成树
        if (tree!=null){
            LinkedList<Vertex> path=new LinkedList<Vertex>();//用来存储走的路径
            //遍历最小生成树
            for (int i=1;i<tree.length;i++){
                if (!this.getAllVertex().get(tree[i-1]).isConnected(this.getAllVertex().get(tree[i]))){
                    //通过dijkstra算法，求出不连通点的最短路径
                    Vertex dijkstra[]=this.Dijkstra(this.getAllVertex().get(tree[i-1]),this.getAllVertex().get(tree[i]));
                    for (int j=0;j<dijkstra.length;j++){//将求出的路径加入到总路径集中
                        path.add(dijkstra[j]);
                    }
                }else {
                    if (path.contains(this.getAllVertex().get(tree[i-1]))){
                        continue;
                    }
                    path.add(this.getAllVertex().get(tree[i-1]));
                }
            }
            //最后将初始点加回路径
            Vertex back[]=this.Dijkstra(this.getAllVertex().get((tree.length-1)),end);
            for (int j=0;j<back.length;j++){//将求出的路径加入到总路径集中
                path.add(back[j]);
            }
            for (int i=0;i<path.size();i++){
                System.out.print(path.get(i).getSpotsName());
                if (i!=path.size()-1){
                    System.out.print("  ->  ");
                }
            }
            System.out.println();
        }else {
            System.out.println("输入错误，请重新输入");
        }


    }

    /**
     * 给对应两点连边
     * @param firstSpot  第一个点
     * @param destination  第二个点
     * @param weight  权重
     * @return 返回是否添加成功
     * @throws Exception
     */
    public boolean addEdge(String firstSpot,String destination,int weight) throws Exception {
        if (this.contains(firstSpot)&&this.contains(destination)&&!(firstSpot.equals(destination))){
            Vertex firstVertex=this.getVertex(firstSpot);
            Vertex secondVertex=this.getVertex(destination);
            Edge firstEdge=new Edge(firstVertex,secondVertex,weight);
            firstVertex.addEdge(firstEdge);
            Edge secondEdge=new Edge(secondVertex,firstVertex,weight);
            secondVertex.addEdge(secondEdge);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除边
     * @param first  连通的第一个点
     * @param second  连通的第二个点
     * @return  返回是否删除成功
     */
    public boolean deleteEdge(String first,String second){
        if (this.contains(first)&&this.contains(second)&&!(first.equals(second))){
            Vertex firstVertex=this.getVertex(first);
            Vertex secondVertex=this.getVertex(second);
            Edge firstEdge=firstVertex.getLinkedEdge(secondVertex);
            Edge secondEdge=secondVertex.getLinkedEdge(firstVertex);
            firstVertex.getAllEdge().remove(firstEdge);
            secondVertex.getAllEdge().remove(secondEdge);
            return true;
        }else {
            return false;
        }
    }
}