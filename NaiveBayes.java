import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NaiveBayes
{
	static Vector<Vector<Node>> vectorArray = new Vector<Vector<Node>>();

	static Vector<Node> v1= new Vector<Node>();
	static Vector<Node> v2= new Vector<Node>();
	static Vector<Node> v3= new Vector<Node>();
	static Vector<Node> v4= new Vector<Node>();
	static Vector<Node> v5= new Vector<Node>();

	public static void main ( String args [])
	{
		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader("20ng-sports.txt"));

			while ((line = br.readLine()) != null) {
				Node n = new Node(line);
				int random = 1 + (int)(Math.random()*5);
				add(n,random);
			}

			vectorArray.add(v1);
			vectorArray.add(v2);
			vectorArray.add(v3);
			vectorArray.add(v4);
			vectorArray.add(v5);
			double finalAccuracy = 0.0;
			for(int i = 1;i<=5;i++){
				double accuracyCurrent = classify(i);
				System.out.println("Accuracy Test case "+i+" - "+accuracyCurrent+" %");
				finalAccuracy += accuracyCurrent;
			}
			System.out.println("Total Accuracy - "+(finalAccuracy/5.0)+" %");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void add(Node n, int i){
		if(v1.size()+v2.size()+v3.size()+v4.size()+v5.size()>=2000){
			System.out.println("Error- Too much of data.");
		}

		switch(i){
			case 1:
				if(v1.size()>=399){
					add(n,2);
				}
				else{
					v1.add(n);
				}
			break;
			case 2:
				if(v2.size()>=399){
					add(n,3);
				}
				else{
					v2.add(n);
				}
			break;		
			case 3:
				if(v3.size()>=399){
					add(n,4);
				}
				else{
					v3.add(n);
				}
			break;	
			case 4:
				if(v4.size()>=399){
					add(n,5);
				}
				else{
					v4.add(n);
				}
			break;	
			case 5:
				if(v5.size()>=399){
					add(n,1);
				}
				else{
					v5.add(n);
				}
			break;	
			default:
				System.out.println("Error- Don't know where to add.");
			break;	
		}
	}

	public static double classify(int i){
		HashMaps hockey = new HashMaps("hockey");
		HashMaps baseball = new HashMaps("baseball");
		int baseballFrequency = 0;
		int hockeyFrequency = 0;
		for(int x=0;x<vectorArray.size();x++){
			if(x == (i-1))
				continue;
			int n=0;
			while(n<vectorArray.get(x).size()){
				Node element = vectorArray.get(x).get(n);
				
				if(element.game.equals("hockey"))
					hockeyFrequency++;
				else if(element.game.equals("baseball"))
					baseballFrequency++;

				String[] arrayOfWords = element.words.split(" ");
				for(int j=0; j<arrayOfWords.length ;j++){
					if(element.game.equals("hockey"))
						hockey.put(arrayOfWords[j]);
					else if(element.game.equals("baseball"))
						baseball.put(arrayOfWords[j]);
				}
				n++;
			}
		}

		System.out.println("Words in Hockey HashMaps-"+hockey.size()+". Words in BaseBall HashMaps-"+baseball.size());
		System.out.println("Hockey training cases- "+hockeyFrequency+". BaseBall training Cases-"+baseballFrequency);
		int n = 0;
		double hockeyWords = (double)hockey.size();
		double baseballWords = (double)baseball.size();
		int accuracy = 0;
		while(n<vectorArray.get(i-1).size()){
			Node element = vectorArray.get(i-1).get(n);
				String[] arrayOfWords = element.words.split(" ");
				double finalPh = 0.0;
				double finalPb = 0.0;
				for(int j=0; j<arrayOfWords.length ;j++){
					double wordFrequencyInHockey =1 + hockey.frequency(arrayOfWords[j]);
					double wordFrequencyInBaseball =1 + baseball.frequency(arrayOfWords[j]);
					double phw = Math.log(wordFrequencyInHockey/hockeyWords)+Math.log(hockeyFrequency*1.0/(hockeyFrequency+baseballFrequency));
					double pbw = Math.log(wordFrequencyInBaseball/baseballWords)+Math.log(baseballFrequency*1.0/(hockeyFrequency+baseballFrequency));
					double total = Math.log((wordFrequencyInHockey/hockeyWords)*hockeyFrequency*1.0/(hockeyFrequency+baseballFrequency) + (wordFrequencyInBaseball/baseballWords)*baseballFrequency*1.0/(hockeyFrequency+baseballFrequency));
					phw = phw - total;
					pbw = pbw - total;
					finalPh += phw;
					finalPb += pbw;
				}
				String game = "";
				if(finalPh>finalPb){
					game = "hockey";
				}
				if(finalPb>finalPh){
					game = "baseball";
				}
				if(element.game.equals(game)){
					//System.out.println("decided"+" "+element.game);
					accuracy++;
				}
				else{
					//System.out.println("Couldn't decide "+element.game);
				}
			n++;
		}
		return accuracy*100.0/(vectorArray.get(i-1).size());
	}
}

