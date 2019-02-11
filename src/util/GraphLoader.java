/**
 * @author UCSD MOOC development team
 * 
 * Utility class to add vertices and edges to a graph
 *
 */
package util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class GraphLoader {
    /**
     * Loads graph with data from a file.
     * The file should consist of lines with 2 integers each, corresponding
     * to a "from" vertex and a "to" vertex.
     */ 
	
    public static void loadGraph(graph.Graph g, String filename) {
        Set<Integer> seen = new HashSet<Integer>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNextInt()) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            if (!seen.contains(v1)) {
                g.addVertex(v1);
                seen.add(v1);
            }
            if (!seen.contains(v2)) {
                g.addVertex(v2);
                seen.add(v2);
            }
            g.addEdge(v1, v2);
        }
        
        sc.close();
    }
    
    //This loader scans the txt documents with users and its matrix with features, and adds each matrix to each right vertex. 
    public static void loadFeatures(graph.Graph g,String filename) {
    	Scanner sc;
    	 try {
             sc = new Scanner(new File(filename));
         } catch (Exception e) {
             e.printStackTrace();
             return;
         }
    	 while(sc.hasNextInt()) {
    		 String line = sc.nextLine();
    		 String[] words = line.split(" ");
    		 ArrayList<Integer> map = new ArrayList<>();
    		 int key = 0;
    		 for(int i=0; i<words.length; i++) {
    			 if(words[i].equals(words[0])) {
    				 key= Integer.parseInt(words[i]);
    			 }
    			 else {
    				 map.add(Integer.parseInt(words[i]));
    			 }
    		 }
    		 if(g.getWholeVertex().containsKey(key)) {
    			 g.addFeatureToNode(map, key);
    		 }
    		 
    	 }
    	 sc.close();
    	 
   }
    
    //This loader scans the txt file containing what every index of the matrix means. It reads the number of the index in the matrix
    // and what it means (date of birth, workplace, interests, etc) and adds it to a hashmap to work with, where the key is the index
    // and the value is what the 1 in the matrix means for each index.
    
    public static void helperHashFeatures(graph.Graph g,String filename) {
    	Scanner sc;
   	 try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
   	 
   	 while(sc.hasNextLine()) {
   		String s = sc.nextLine();
   		String one = s.substring(0, s.indexOf(" "));
   		String two = s.substring((s.indexOf(" ")+1), s.length());
   		g.addFeatures(Integer.parseInt(one),two);
   		
   	 }
   	 sc.close();
    }
}
    
   

