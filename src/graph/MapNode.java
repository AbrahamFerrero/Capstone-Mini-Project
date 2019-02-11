package graph;

import java.util.ArrayList;
import java.util.HashSet;

public class MapNode {
	int vertex;
	HashSet<MapEdge> edges;
	HashSet<String> features;
	ArrayList<Integer> matrix;
	
	public MapNode(int node) {
		vertex = node;
		edges = new HashSet<>();
		features = new HashSet<>();
	}
	
	public void addEdge(MapEdge e) {
		edges.add(e);
	}
	
	public int getVertex() {
		return vertex;
	}
	
	//Returns a list with the edges
	public HashSet<MapEdge> getNeighEdges(){
		return edges;
	}
	
	//Returns the list as integer of neighbours
	public HashSet<Integer> getNeighbors(){
		HashSet<Integer> neighbors = new HashSet<>();
		for (MapEdge i : edges) {
			neighbors.add(i.getNeighbor());
		}
		return neighbors;
	}
	
	public void addFeature(String feat) {
		features.add(feat);
		//System.out.println("adding feat : " + feat + " to node "+ vertex + " now it's size " +features.size());
	}
	
	public void addMatrix(ArrayList<Integer> key) {
		matrix= key;
		//System.out.println(" to node "+ vertex +" adding matrix : " + key +  " now it's size " +matrix.size());
	}
	
	public ArrayList<Integer> getMatrix(){
		return matrix;
	}
	
	public HashSet<String> getFeatures(){
		return features;
	}
	
	
	public String toString() {
		return Integer.toString(vertex);
	}
}
