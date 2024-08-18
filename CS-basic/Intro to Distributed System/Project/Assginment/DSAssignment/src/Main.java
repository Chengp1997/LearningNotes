import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("1. using antiEntropy: ");
		List<Node> nodes1 = Alogrithms.initialize();
		Alogrithms.AntiEntropy(nodes1);
		
		System.out.println();
		System.out.println("2. using gossiping: ");
		List<Node> nodes2 = Alogrithms.initialize();
		Alogrithms.Gossiping(nodes2);
		
		
	}

}
