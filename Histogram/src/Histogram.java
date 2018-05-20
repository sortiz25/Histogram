import java.io.*;
import java.util.*;

//Project: Histogram
//Name: Santiago Ortiz
//Description:This program will read a paragraph from a file, and generate a sorted histogram of the words used. The histogram will then be output to a text file
//with the words sorted in order from most common, to least common.

public class Histogram {

	public static void main(String[] args) throws IOException {
		
		String inName= "input.txt";
		String outName= "output.txt";
		int longest= 1;
		
		Map<String,Integer> wordList= new HashMap<String,Integer>();	//This HashMap is used to easily update the count of each word we have found
		
		Scanner in_reader= new Scanner(new File(inName));		
		
		while(in_reader.hasNextLine()){									//First we will count how many times each word appears.
			String line= in_reader.nextLine().toLowerCase();									
			StringTokenizer single_words= new StringTokenizer(line.replaceAll("[^a-z]", " "));	
			
			while(single_words.hasMoreTokens()){
				String cur_word= single_words.nextToken();	
				if(!wordList.containsKey(cur_word)){					//If a word is not found in the HashMap, it is added,
					wordList.put(cur_word, 1);
					if(cur_word.length() > longest){
						longest= cur_word.length();
					}
				}
				else{													//otherwise the count of the word is updated.
					wordList.put(cur_word, wordList.get(cur_word) +1);
				}	
			}
		}
		
		in_reader.close();												//We still need to arrange the words in descending order based on their counts.
		
		ArrayList<String> sortedList= new ArrayList<String>();		
		
		for (Map.Entry<String, Integer> entry : wordList.entrySet()){	
			int cur_count= entry.getValue();
													
			boolean added= false;
			for(int i=0; i<sortedList.size(); i++){						//We will traverse the ArrayList until we find a word that the current word's count can surpass.
				if(cur_count >= wordList.get(sortedList.get(i)) ){
					sortedList.add(i, entry.getKey());
					added= true;
					break;
				}
			}
			if(!added){											
				sortedList.add(entry.getKey());							 
			}
			
		}
		
		FileWriter outFile= new FileWriter(outName);					//Finally we output our findings to the "output" text file.
		PrintWriter output= new PrintWriter(outFile);
		
		for(int i=0; i<sortedList.size(); i++){
			String word=sortedList.get(i);
			int count= wordList.get(word);
			output.printf("%" + longest + "s", word);
			output.print(" | ");
			for(int j=0; j< count;j++){
				output.print("=");
			}
			output.println(" (" + count + ")");
		}
		
		output.close();
		outFile.close();
	}

}