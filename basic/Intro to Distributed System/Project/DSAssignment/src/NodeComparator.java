import java.util.Comparator;

public class NodeComparator implements Comparator<Node>{
	@Override
	public int compare(Node o1, Node o2) {
		if(o1.getValue()>o2.getValue())
			return 1;
		else if(o1.getValue()<o2.getValue())
			return -1;
		else {
			return 0;
		}
	}
	
}
