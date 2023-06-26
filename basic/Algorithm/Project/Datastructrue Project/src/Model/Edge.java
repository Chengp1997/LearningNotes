package Model;

public class Edge {
    private Vertex startSpot;
    private Vertex destinationSpot;
    private int weight=0;

    public Edge(Vertex startSpot,Vertex destinationSpot,int weight){
        this.startSpot=startSpot;
        this.destinationSpot=destinationSpot;
        this.weight=weight;
    }

    public Vertex getDestinationSpot() {
        return destinationSpot;
    }

    public Vertex getStartSpot() {
        return startSpot;
    }

    public int getWeight() {
        return weight;
    }

    public void setDestinationSpot(Vertex destinationSpot) {
        this.destinationSpot = destinationSpot;
    }

    public void setStartSpot(Vertex startSpot) {
        this.startSpot = startSpot;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }



}
