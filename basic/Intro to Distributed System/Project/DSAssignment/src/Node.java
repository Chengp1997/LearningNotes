import java.util.Random;

public class Node{
	private double value;
	private int index;
	private double possibility;//of not being removed

	public Node(int index, double value) {
		this.index = index;
		this.value = value;
		this.possibility = 1;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public double getPossibility() {
		return this.possibility;
	}
	
	public void exchange(Node otherNode) {
		double avrage = (this.getValue()+otherNode.getValue())/2;
		this.setValue(avrage);
		otherNode.setValue(avrage);
	}
	
	
	public void decreasePossibility(double k) {
		Random random = new Random();
		double tmp = random.nextDouble();
		if(tmp<=this.possibility) {
			this.possibility = possibility/k;
		}
		else {
			this.possibility=0;
		}
	}
	
}
