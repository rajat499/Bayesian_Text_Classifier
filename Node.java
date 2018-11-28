public class Node{
	String head="";
	String words="";
	String game="";
	public Node(String line){
		int i = line.indexOf('\t');
		head=line.substring(0,i);
		words=line.substring(i+1);
		int g = head.lastIndexOf('.');
		game=head.substring(g+1);
	}
}