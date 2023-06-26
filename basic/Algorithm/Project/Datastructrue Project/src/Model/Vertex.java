package Model;

import java.util.LinkedList;

//顶点
public class Vertex {
    private String spotsName;//景点名称
    private String intro;
    private String welcomePoint;
    private String relaxPlace;
    private String restRoom;
    private LinkedList<Edge> allEdge=new LinkedList<>();

    public Vertex(String spotsName){
        this.setSpotsName(spotsName);
    }
    public String getSpotsName() {
        return spotsName;
    }

    public void setSpotsName(String spotsName) {
        this.spotsName = spotsName;
    }

    public void addEdge(Edge edge) throws Exception {
        allEdge.add(edge);
    }

    public LinkedList<Edge> getAllEdge() {
        return allEdge;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getRelaxPlace() {
        return relaxPlace;
    }

    public String getRestRoom() {
        return restRoom;
    }

    public String getWelcomePoint() {
        return welcomePoint;
    }

    public void setRelaxPlace(String relaxPlace) {
        this.relaxPlace = relaxPlace;
    }

    public void setRestRoom(String restRoom) {
        this.restRoom = restRoom;
    }

    public void setWelcomePoint(String welcomePoint) {
        this.welcomePoint = welcomePoint;
    }

    public boolean equals(Vertex obj) {
        if (this.getSpotsName()==obj.getSpotsName()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 通过另一个点获得两点连接的边
     * @param vertex  另一点
     * @return  返回相连的边
     */
    public Edge getLinkedEdge(Vertex vertex) {
        Edge linkedEdge=null;
        for (int i=0;i<this.getAllEdge().size();i++){
            if (this.getAllEdge().get(i).getDestinationSpot().equals(vertex)){
                linkedEdge= this.getAllEdge().get(i);
            }
        }
        return  linkedEdge;
    }

    /**
     * 返回输入点的权重
     * @return  返回输入点的权重
     */
    public int getLinkedWeight(Vertex vertex){
        for (int i=0;i<this.getAllEdge().size();i++){//遍历所有相连的边，是否有自己的边
            if (this.getAllEdge().get(i).getDestinationSpot().equals(vertex)){
                return this.getAllEdge().get(i).getWeight();
            }
        }
        return 32767;
    }

    /**
     * 对于一个点的所有连接的边，返回最短的边
     * @return  返回权重最小的边
     */
    public Edge getShortestEdge() {
        int shortest=this.allEdge.get(0).getWeight();//获得第一个点的权重
        Edge shortestEdge=null;
        for (int i=0;i<this.allEdge.size();i++){//对于所有点集
            if (shortest>=this.allEdge.get(i).getWeight()){//若找到更短边
                shortest=this.allEdge.get(i).getWeight();
                shortestEdge=this.allEdge.get(i);
            }
            System.out.println(shortest);
            System.out.println(shortestEdge.getWeight());
        }
        return shortestEdge;
    }
    /**
     * 输入另一个点，判断两个点是否连通
     * @param root  输入的另一个点
     * @return  返回判断
     */
    public boolean isConnected(Vertex root){
        for (int i=0;i<this.allEdge.size();i++){
            if (this.allEdge.get(i).getDestinationSpot().getSpotsName().equals(root.getSpotsName())){
                return true;
            }
        }
        return false;
    }
}
