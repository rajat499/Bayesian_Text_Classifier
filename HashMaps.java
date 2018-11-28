import java.util.*;

public class HashMaps{
	String title="";
	Vector<String>[][] llArray = new Vector[26][26];
	public HashMaps(String title){
		this.title = title;
		for(int i=0;i<26;i++){
			for(int j=0;j<26;j++){
				llArray[i][j] = new Vector<String>();
			}
		}
	}

	public void put(String word){
		word = word.toLowerCase();
		int i = word.charAt(0) - 97;
		int j = 0;
		if(word.length()>1){
			j = word.charAt(1) - 97;
		}
		llArray[i][j].add(word);
	}

	public int size(){
		int size = 0;
		for(int i=0;i<26;i++){
			for(int j=0;j<26;j++){
				size+= llArray[i][j].size();
			}
		}
		return size;
	}

	public int frequency(String word){
		int frequency=0;
		word = word.toLowerCase();
		int n = word.charAt(0) - 97;
		int m = 0;
		if(word.length()>1){
			m = word.charAt(1) - 97;
		}

		for(int i=0; i < llArray[n][m].size();i++){
			if(llArray[n][m].get(i).equals(word)){
				frequency++;
			}
		}
		return frequency;
	}
}

