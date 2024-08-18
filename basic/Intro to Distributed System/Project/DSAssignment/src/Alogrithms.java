import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class Alogrithms {

	private static double epsilon = 0.00000001;
	private static double k = 4;
	private static int size = 1000;
	private static int max = 1000;
	private static Random random = new Random();
	
	static public List<Node> initialize() {
		List<Node> nodes = new ArrayList<Node>(size);
		double sum=0;
		for(int h=0;h<size;h++) {
			nodes.add(new Node(h, random.nextInt(max)));
			sum+=nodes.get(h).getValue();
		}
		System.out.println("expected average: "+sum/size);
		return nodes;
	}
	
	static public void AntiEntropy(List<Node> nodes) {
		int counter=0;
		while(true) {
			for(Node node:nodes) {
				//choose a node to infect
				 int index = chooseNode(node.getIndex());
				//update
				node.exchange(nodes.get(index));
			}
			counter++;
			
			//check end condition
			List<Node> tmp = nodes;
			Collections.sort(tmp, new NodeComparator());
			if(Math.abs(tmp.get(0).getValue()-tmp.get(nodes.size()-1).getValue())<epsilon) {
				System.out.println("Done. Counter="+counter);
				break;
			}
		}
		
		System.out.println("actural value of first node: "+nodes.get(0).getValue());
	}
	
	static public void Gossiping(List<Node> nodes) {
		int counter=0;
		while(true) {
			boolean endCondition = true;
			for(Node node:nodes) {
				if(node.getPossibility()!=0) {
					//set condition to false
					endCondition = false;
					//choose a node to infect
					int index = chooseNode(node.getIndex());
					
					//update and modify possibility
					if(Math.abs(node.getValue()-nodes.get(index).getValue())<epsilon) {
						node.decreasePossibility(k);
					
					}
					else {
						node.exchange(nodes.get(index));
					}
				}
			}
			counter++;
			//check end condition
			if(endCondition)
				break;
		}
		
		
		System.out.println("Done. Counter="+counter);
		System.out.println("actural value of first node: "+nodes.get(0).getValue());
	}
	
	static private int chooseNode(int currentNodeIndex) {
		int index;
		while(true) {
			index = random.nextInt(size-1);
			if(index!=currentNodeIndex) {
				break;
			}
		}
		return index;
	}

}
