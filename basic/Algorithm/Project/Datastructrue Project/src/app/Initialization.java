package app;

import Model.Edge;
import Model.MyGraph;
import Model.Notice;
import Model.Vertex;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Initialization {
   private   String edgeFileName = "src/file/spots.txt";
   private String infoFileName="src/file/spotsInfo.txt";
   private List<Notice> notices=new LinkedList<>();
    public Initialization() throws Exception {
        MyGraph initialGraph=new MyGraph();
        this.readFromEdgeFile(edgeFileName,infoFileName,initialGraph);
        chooseMode(initialGraph);
    }

    /**
     * 读取文件信息，初始化景区图
     * @param edgeFileName  景区通路
     * @param infoFileName  景区景点介绍文件
     */
    private void readFromEdgeFile(String edgeFileName, String infoFileName,MyGraph initialGraph) throws Exception {
        try {
            InputStreamReader edgeIn = null;
            InputStreamReader infoIn=null;
            try {
                edgeIn = new InputStreamReader(new FileInputStream(edgeFileName), "GBK");
                infoIn=new InputStreamReader(new FileInputStream(infoFileName), "GBK");
            } catch (UnsupportedEncodingException | FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader edgeRead = new BufferedReader(edgeIn);
            BufferedReader InfoRead = new BufferedReader(infoIn);
            String spotsInfo;
            while ((spotsInfo = InfoRead.readLine()) != null) {
                String a[]=spotsInfo.split("  ");
                Vertex vertex=new Vertex(a[0]);
                initialGraph.addVertex(vertex);
                vertex.setIntro(a[1]);
                vertex.setWelcomePoint(a[2]);
                vertex.setRelaxPlace(a[3]);
                vertex.setRestRoom(a[4]);
            }
            String edgeInfo;
            while ((edgeInfo = edgeRead.readLine()) != null) {
                String a[]=edgeInfo.split("  ");
                Vertex second = initialGraph.getVertex(a[1]);
                Vertex first = initialGraph.getVertex(a[0]);
                for(int i=0;i<initialGraph.getAllVertex().size();i++){
                    if(initialGraph.getAllVertex().get(i).getSpotsName().equals(a[0])) {
                        Edge linkedEdge=new Edge(first,second,Integer.parseInt(a[2]));
                        initialGraph.getAllVertex().get(i).addEdge(linkedEdge);
                    }else if(initialGraph.getAllVertex().get(i).getSpotsName().equals(a[1])){
                        Edge linkedEdge=new Edge(second,first,Integer.parseInt(a[2]));
                        initialGraph.getAllVertex().get(i).addEdge(linkedEdge);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将元素写入文件
     * @param fileName  写入的文件名
     * @param initialGraph  写入的图
     */
    public void writeInTheFile(String fileName,MyGraph initialGraph)  {
        OutputStreamWriter outInfo = null;
        try {
            outInfo = new OutputStreamWriter(new FileOutputStream(fileName), "GBK");

        if (fileName.equals(infoFileName)){//若要写入的为景点信息文件
               for (int i=0;i<initialGraph.getAllVertex().size();i++){
                   String inputInfo=initialGraph.getAllVertex().get(i).getSpotsName()+"  "+initialGraph.getAllVertex().get(i).getIntro()+"  "+
                           initialGraph.getAllVertex().get(i).getWelcomePoint()+"  "+initialGraph.getAllVertex().get(i).getRelaxPlace()+"  "+
                           initialGraph.getAllVertex().get(i).getRestRoom()+"\n";
                   outInfo.write(inputInfo);
               }
            }else {
                for (int i=0;i<initialGraph.getAllEdge().size();i++){
                    String edgeInfo= initialGraph.getAllEdge().get(i).getStartSpot().getSpotsName()+"  "
                            +initialGraph.getAllEdge().get(i).getDestinationSpot().getSpotsName()+"  "
                            +initialGraph.getAllEdge().get(i).getWeight()+"\n";
                    outInfo.write(edgeInfo);
                }
            }
        outInfo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立主程序应用菜单选项
     */
    public void printMenu(){
        System.out.println("\t\t\t\t      欢迎使用景区管理系统   ");
        System.out.println("\t\t\t\t******************************");
        System.out.println("\t\t\t\t\t1.景区景点分布图");//输出景区景点分布图
        System.out.println("\t\t\t\t\t2.查找景点");//查找景点
        System.out.println("\t\t\t\t\t3.景点排序榜");//景点排序
        System.out.println("\t\t\t\t\t4.最快到达景点");//最短路径&最短距离
        System.out.println("\t\t\t\t\t5.景区导游线路图");
        System.out.println("\t\t\t\t\t6.停车场记录");
        System.out.println("\t\t\t\t\t7.管理员登录");
        System.out.println("\t\t\t\t\t8.查看发布的公告");
        System.out.println("\t\t\t\t\t9.退出");
        System.out.println("\t\t\t\t*******************************");
        System.out.println("\t\t\t\t请输入你的选择： ");
    }

    /**
     * 选择菜单
     * @param initialGraph  景区地图
     * @throws Exception
     */
    public void chooseMode(MyGraph initialGraph) throws Exception {
        Scanner input=new Scanner(System.in);
        while (true){
            printMenu();
            int choose=input.nextInt();
            if(choose==1){//景区景点分布图
                initialGraph.createGraph(initialGraph);
                initialGraph.outputGraph(initialGraph);
            }else if (choose==2){//查找景点
                searchForTheSpot(initialGraph);
            }else if(choose==3){
                sortSpots(initialGraph);
            }else if (choose==4){
               shortestPath(initialGraph);
            }else if (choose==5){
               guidancePath(initialGraph);
            }else if (choose==6){//停车场记录
                parkingManagement();
            }else if (choose==7){
                management(initialGraph);
            }else if (choose==8){
                System.out.println("\t\t\t\t**********公告********");
                for (int i=0;i<notices.size();i++){
                    System.out.println(notices.get(i).getNotice());
                }
                System.out.println("\t\t\t\t**********************");
            } else if (choose==9){
                System.out.println("\t\t\t\t欢迎使用我们的景区管理系统，我们下次再会。");
                writeInTheFile(edgeFileName,initialGraph);
                writeInTheFile(infoFileName,initialGraph);
                System.exit(0);
            }else{
                System.out.println("\t\t\t\t输入错误，请重新输入");
            }

        }
    }

    /**
     * 查找景点，输出景点信息。
     */
    public void searchForTheSpot(MyGraph initialGraph){
        Scanner inputSpots = new Scanner(System.in);
        while(true) {
            System.out.println("\t\t\t\t请输入你要查找的景点名称/景点信息(输入E返回菜单）");
            String spotName = inputSpots.next();
            if (!spotName.equals("E")) {
                for (int i = 0; i < initialGraph.getAllVertex().size(); i++) {
                    if (spotName.equals(initialGraph.getAllVertex().get(i).getSpotsName())||initialGraph.getAllVertex().get(i).getIntro().contains(spotName)) {
                        System.out.println("\t\t\t\t****************************************");
                        System.out.println("\t\t\t\t\t景点名称： " + initialGraph.getAllVertex().get(i).getSpotsName());
                        System.out.println("\t\t\t\t\t景点介绍： " + initialGraph.getAllVertex().get(i).getIntro());
                        System.out.println("\t\t\t\t\t热度： " + initialGraph.getAllVertex().get(i).getWelcomePoint());
                        System.out.println("\t\t\t\t\t休息区： " + initialGraph.getAllVertex().get(i).getRelaxPlace());
                        System.out.println("\t\t\t\t\t公测： " + initialGraph.getAllVertex().get(i).getRestRoom());
                        System.out.println("\t\t\t\t\t连接的景点："+initialGraph.getAllVertex().get(i).getAllEdge().size());
                        break;
                    }
                    if(i==initialGraph.getAllVertex().size()-1){System.out.println("\t\t\t\t对不起，景区没有此景点");}
                }
            }else {
                break;
            }
        }
    }

    /**
     * 使用冒泡排序法排序景点信息
     * @param initialGraph  传入的需要排序的经典信息
     */
    public void sortSpots(MyGraph initialGraph){
        Scanner chooseWay=new Scanner(System.in);
        while (true){
            System.out.println("\t\t\t\t***********************");
            System.out.println("\t\t\t\t\t请输入你的排序方式：");
            System.out.println("\t\t\t\t\t1.按欢迎度排序");
            System.out.println("\t\t\t\t\t2.按人流量排序");//岔路数
            System.out.println("\t\t\t\t\t3.返回菜单");
            int way=chooseWay.nextInt();
            Vertex sort[]=new Vertex[initialGraph.getAllVertex().size()];
            for (int i=0;i<initialGraph.getAllVertex().size();i++){
                sort[i]=initialGraph.getAllVertex().get(i);
            }
            if (way==1){
                //bubble sort
//                for (int i=0;i<sort.length;i++){
//                    for (int j=1;j<sort.length-i;j++){
//                        if (Integer.parseInt(sort[j-1].getWelcomePoint())<Integer.parseInt(sort[j].getWelcomePoint())){
//                            Vertex temp=sort[j-1];
//                            sort[j-1]=sort[j];
//                            sort[j]=temp;
//                        }
//                    }
//                }
                //快速排序法
                for (int i=1;i<sort.length;i++){
                    int j=i-1;
                    Vertex temp=sort[i];
                    while (j>=0&&Integer.parseInt(temp.getWelcomePoint())>Integer.parseInt(sort[j].getWelcomePoint())){
                        sort[j+1]=sort[j];
                        j--;
                    }
                    sort[j+1]=temp;
                }

                for (int i=0;i<sort.length;i++){
                    System.out.println("\t\t\t\t"+sort[i].getSpotsName()+"  热度： "+sort[i].getWelcomePoint());
                }
            }else if (way==2){
                //bubble sort
                for (int i=0;i<sort.length;i++){
                    for (int j=1;j<sort.length-i;j++){
                        if (sort[j-1].getAllEdge().size()<sort[j].getAllEdge().size()){
                            Vertex temp=sort[j-1];
                            sort[j-1]=sort[j];
                            sort[j]=temp;
                        }
                    }
                }
                for (int i=0;i<sort.length;i++){
                    System.out.println("\t\t\t\t"+sort[i].getSpotsName()+"  岔路口数： "+sort[i].getAllEdge().size());
                }
            }else if (way==3){
                break;
            }
        }
    }

    /**
     * 判断管理员用户名密码是否输入正确
     * @param username  管理员用户名
     * @param password  管理员密码
     * @return  返回判断结果
     */
    public boolean login(String username,String password){
        if (username.equals("123")&&password.equals("123")){
            return  true;
        }else {
            return false;
        }
    }

    /**
     * 景区管理员模式
     * @param initialGraph 景区地图
     */
    public void management(MyGraph initialGraph) throws Exception {
        Scanner inputInfo=new Scanner(System.in);
        System.out.println("\t\t\t\t请输入你的用户名：");
        String username= inputInfo.next();
        System.out.println("\t\t\t\t请输入你的密码：");
        String passWord= inputInfo.next();
        if(login(username,passWord)){
            System.out.println("\t\t\t\t登录成功,请输入你的操作");
            while (true){
            System.out.println("\t\t\t\t**********************");
            System.out.println("\t\t\t\t      1.景点维护");
            System.out.println("\t\t\t\t      2.道路维护");
            System.out.println("\t\t\t\t      3.退出");
            System.out.println("\t\t\t\t**********************");
            int choosemode=inputInfo.nextInt();
                if (choosemode==1){
                    while (true){
                        System.out.println("\t\t\t\t*****************");
                        System.out.println("\t\t\t\t   1.增加景点");
                        System.out.println("\t\t\t\t   2.删除景点");
                        System.out.println("\t\t\t\t   3.返回");
                        int mode=inputInfo.nextInt();
                        if (mode==1) {
                            addSpot(initialGraph);
                        }else if (mode==2){
                            deleteSpot(initialGraph);
                        }else if (mode==3){
                            break;
                        }else {
                            System.out.println("\t\t\t\t输入错误");
                        }
                    }
                }else if (choosemode==2){
                    while (true){
                        System.out.println("\t\t\t\t*****************");
                        System.out.println("\t\t\t\t   1.增加道路");
                        System.out.println("\t\t\t\t   2.删除道路");
                        System.out.println("\t\t\t\t   3.返回");
                        int mode=inputInfo.nextInt();
                        if (mode==1){
                            addRoad(initialGraph);
                        }else if (mode==2){
                            deleteRoad(initialGraph);
                        }else if (mode==3){
                            break;
                        }else {
                            System.out.println("\t\t\t\t输入错误，请重新选择");
                        }
                    }
                }else if (choosemode==3){
                        return;
                }else{
                    System.out.println("\t\t\t\t输入错误，请重新输入");
                }
            }

        }else{
            System.out.println("\t\t\t\t用户名或密码输入错误");
        }
    }

    /**
     * 增加景点
     * @param initialGraph
     */
    public void addSpot(MyGraph initialGraph) throws Exception {
        Scanner inputInfo=new Scanner(System.in);
        while (true){
            System.out.println("\t\t\t\t请输入你要增加的景点的名字");
            String spotName = inputInfo.next();
            if(!initialGraph.contains(spotName)) {
                Vertex newSpot = new Vertex(spotName);
                initialGraph.addVertex(newSpot);
                newSpot.setWelcomePoint("50");
                System.out.println("\t\t\t\t请输入景区介绍：");
                newSpot.setIntro(inputInfo.next());
                System.out.println("\t\t\t\t是否有休息区？（有/无）");
                newSpot.setRelaxPlace(inputInfo.next());
                System.out.println("\t\t\t\t是否有厕所？（有/无）");
                newSpot.setRestRoom(inputInfo.next());
                Notice newNotice = new Notice();
                newNotice.setNotice("\t\t\t\t景区新增景点：" + spotName);
                notices.add(newNotice);
                System.out.println("\t\t\t\t发布公告！");
                while (true) {
                    System.out.println("\t\t\t\t请输入与新景区连通的景点：");
                    String destination = inputInfo.next();
                    System.out.println("\t\t\t\t两个景点的距离");
                    int weight = inputInfo.nextInt();
                    if (!initialGraph.addEdge(spotName, destination, weight)) {
                        System.out.println("\t\t\t\t输入错误，请重新输入");
                        continue;
                    } else {
                        Notice newEdgeNotice = new Notice();
                        newEdgeNotice.setNotice("新景点" + spotName + "与" + destination + "连通，他们相距" + weight);
                        notices.add(newEdgeNotice);
                        System.out.println("\t\t\t\t继续输入？（Y 继续 E 退出）");
                        String choose = inputInfo.next();
                        if (choose.equals("E")) return;
                        else continue;
                    }
                }
            }else{
                System.out.println("\t\t\t\t该景点已经存在，请重新输入");
            }
        }
    }


    /**
     * 删除景点
     * @param initialGraph  传入的地图
     */
    public void deleteSpot(MyGraph initialGraph) throws IOException {
        Scanner inputInfo=new Scanner(System.in);
        System.out.println("请输入你要删除的景点的名字");
        String spotName=inputInfo.next();
        for (int i=0;i<initialGraph.getAllVertex().size();i++){
            if (initialGraph.getAllVertex().get(i).getSpotsName().equals(spotName)){//含有此点，删除
                for (int j=0;j<initialGraph.getAllVertex().get(i).getAllEdge().size();j++){//获得每条与改点相连的边
                    //获得连接边下一个结点，删除此边。
                    Vertex nextSpot=initialGraph.getAllVertex().get(i).getAllEdge().get(j).getDestinationSpot();
                    nextSpot.getAllEdge().remove(nextSpot.getLinkedEdge(initialGraph.getAllVertex().get(i)));
                }
                initialGraph.getAllVertex().remove(initialGraph.getAllVertex().get(i));
                System.out.println("删除成功！");
                    Notice newNotice=new Notice();
                    newNotice.setNotice("景区删除："+spotName);
                    notices.add(newNotice);
                    System.out.println("发布公告！");
                return;
            }
            if(i==initialGraph.getAllVertex().size()-1){
                System.out.println("输入错误，无此景点");
            }
        }
        Notice newNotice=new Notice();
        newNotice.setNotice("景区撤销的景点："+spotName);
        notices.add(newNotice);
        System.out.println("发布公告！");
    }


    /**
     * 增加两个景点间道路
     * @param initialGraph  景区地图
     */
    public void addRoad(MyGraph initialGraph) throws Exception {
        String first;
        String second;
        int weight;
        while (true){
            Scanner inputInfo=new Scanner(System.in);
            System.out.println("请输入你要增加的道路");
            System.out.println("相连的第一个景点为： ");
            first=inputInfo.next();
            System.out.println("相连的第二个景点名称为：");
            second=inputInfo.next();
            System.out.println("两个景点间的距离为： ");
            weight=inputInfo.nextInt();
            if (!initialGraph.addEdge(first,second,weight)){
                System.out.println("输入错误，请重新输入");
            }else break;
        }
        Notice newNotice=new Notice();
        newNotice.setNotice("景区新增道路："+first+" 与 "+second+" 已连通,相距 "+weight);
        notices.add(newNotice);
        System.out.println("发布公告！");
    }

    /**
     * 删除两个景点简的道路
     * @param initialGraph  景区地图
     */
    public void deleteRoad(MyGraph initialGraph) throws IOException {
        Scanner inputInfo=new Scanner(System.in);
        System.out.println("请输入你要删除的道路");
        System.out.println("相连的第一个景点为： ");
        String first=inputInfo.next();
        System.out.println("相连的第二个景点名称为：");
        String second=inputInfo.next();
        if (initialGraph.deleteEdge(first,second)){
            Notice newNotice=new Notice();
            newNotice.setNotice("景区删除道路："+first+" 与 "+second+" 已不能通行 ");
            notices.add(newNotice);
            System.out.println("发布公告！");
        }else {
            System.out.println("输入错误，请重新输入");
        }


    }

    /**
     * 停车场管理
     * @throws Exception
     */
    public void parkingManagement() throws Exception {
        ParkingAlgorithm parkingAlgorithm=new ParkingAlgorithm();
        while (true){
            System.out.println("\t\t\t\t******************");
            System.out.println("\t\t\t\t1.管理汽车");
            System.out.println("\t\t\t\t2.查看记录");
            System.out.println("\t\t\t\t3.退出");
            Scanner inputInfo=new Scanner(System.in);
            int choose=inputInfo.nextInt();
                if (choose==3){
                    break;
                }else if (choose==1){
                    while (true) {System.out.println("\t\t\t\t********************");
                        System.out.println("\t\t\t\t1.停车\n\t\t\t\t2.离开车库\n\t\t\t\t3.退出");
                        int chooseWay=inputInfo.nextInt();
                        if (chooseWay==3) {
                            break;
                        }else if (chooseWay==1){
                            parkingAlgorithm.printParkingSlot();
                            System.out.println("\t\t\t\t输入你的车牌号");
                            String number=inputInfo.next();
                            System.out.println("\t\t\t\t输入时间");
                            String time=inputInfo.next();
                            parkingAlgorithm.parkCar(number,time);
                        }else if (chooseWay==2){
                            parkingAlgorithm.printParkingSlot();
                            System.out.println("\t\t\t\t输入你的车牌号");
                            String number=inputInfo.next();
                            System.out.println("\t\t\t\t输入时间");
                            String time=inputInfo.next();
                            parkingAlgorithm.leave(number,time);
                        }
                    }
                }else if (choose==2){
                    parkingAlgorithm.printStatus();
                }
            }
    }

    /**
     * 计算最短路径，并给出最短路径
     * @param initialGraph  计算路径的图
     * @throws Exception
     */
    public void shortestPath(MyGraph initialGraph) throws Exception {
        Scanner input=new Scanner(System.in);
        System.out.println("\t\t\t\t请输入你的起点");
        Vertex begin=initialGraph.getVertex(input.next());
        int start=initialGraph.getAllVertex().indexOf(begin);
        System.out.println("\t\t\t\t请输入你的终点");
        Vertex end=initialGraph.getVertex(input.next());
        int finish=initialGraph.getAllVertex().indexOf(end);
        if (start==-1||finish==-1){
            System.out.println("\t\t\t\t输入错误，请重新输入");
        }else {
            System.out.println("\t\t\t\tDijkstra");
            Vertex road[]= initialGraph.Dijkstra(begin,end);
            initialGraph.printOutShortestPath(road);
            System.out.println("\t\t\t\tFloyd");
            Vertex road2[]=initialGraph.Floyd(begin,end);
            initialGraph.printOutShortestPath(road2);
        }
    }

    /**
     * 导游路线图
     * @param initialGraph  景区地图
     * @throws Exception
     */
    public void guidancePath(MyGraph initialGraph) throws Exception {
        Scanner input=new Scanner(System.in);
        System.out.println("\t\t\t\t为你列出导游推荐路线，请输入你所在的位置，我们为你安排最佳旅游线路");
        System.out.println("\t\t\t\t你的起点： ");
        Vertex begin=initialGraph.getVertex(input.next());
        System.out.println("\t\t\t\t是否回到原点? 1.是 2. 否");
        int choose=input.nextInt();
        if (choose==1) {
            initialGraph.printOutGuidanceRoute(begin,begin);
        }else if (choose==2){
            System.out.println("\t\t\t\t请输入你最后想到达的地方");
            Vertex end=initialGraph.getVertex(input.next());
            initialGraph.printOutGuidanceRoute(begin,end);
        }
    }
}
