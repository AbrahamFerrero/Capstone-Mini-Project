package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import util.GraphLoader;

public class Graph {

	HashMap<Integer,MapNode> VertexMap;
	HashSet<MapEdge> edges;
	HashMap<Integer, String> Features = new HashMap<>();

	public Graph() {
		VertexMap = new HashMap<>();
		edges = new HashSet<>();
	}

	/**
	 * Get a list of vertexes for the graph
	 * @return A HashSet with a list of vertexes for this graph
	 */
	public HashSet<MapNode> getVertex(){
		HashSet<MapNode> vertexList = new HashSet<>();
		for (MapNode node: VertexMap.values()) {
			vertexList.add(node);
		}
		return vertexList;
	}

	public HashMap<Integer,MapNode> getWholeVertex(){
		return VertexMap;
	}

	public void addFeatureToNode(ArrayList<Integer> map, int key) {
		MapNode a = VertexMap.get(key);
		a.addMatrix(map);
	}

	/** Adds a new vertex in the graph
	 *
	 * @param integer, a Vertice to add, it will be converted to a MapNode
	 */
	public void addVertex(int v) {
		MapNode newVertex = new MapNode(v);
		VertexMap.put(v, newVertex);
	}

	/** Adds an edge to both the vertexes and the list of edges
	 *
	 * @param int of the vertex from where the edge starts
	 * @param int of the vertex from where the edge ends
	 */
	public void addEdge(int from, int to) {
		if(!VertexMap.containsKey(from) || !VertexMap.containsKey(to)) {
			throw new IllegalArgumentException("Edge cannot be added as the vertex are not in the graph");
		}
		MapNode start = VertexMap.get(from);
		MapNode end = VertexMap.get(to);
		MapEdge newEdge = new MapEdge(start,end);
		edges.add(newEdge);
		start.addEdge(newEdge);
	}

	/** Compares the list of friends of two given users and returns a list with friends in common
	 *
	 * @param id of the first user to compare
	 * @param id of the other user to compare
	 * @return HashSet with a list of friends in common
	 */
	private HashSet<Integer> friendsOfTwo(int id1, int id2){
		HashSet<Integer> friendList = new HashSet<>();
		if(!VertexMap.containsKey(id1) ||  !VertexMap.containsKey(id2)) {
			throw new IllegalArgumentException("User " + id1 + " or " + id2 + " do not exist");
		}
		MapNode friend1 = VertexMap.get(id1);
		MapNode friend2 = VertexMap.get(id2);
		HashSet<Integer> listFriends1 = friend1.getNeighbors();
		HashSet<Integer> listFriends2 = friend2.getNeighbors();
		for(int i : listFriends1) {
			if(listFriends2.contains(i)) {
				friendList.add(i);
			}
		}
		return friendList;
	}

	/** Method to arrange a print statement for testing purposes, it prints the solution with the friends in common
	 *
	 * @param id of the first user to compare
	 * @param id of the other user to compare
	 */
	public void friendsInCommon(int id1, int id2){
		HashSet<Integer> listOfFriends = friendsOfTwo(id1,id2);
		if(listOfFriends.isEmpty()) {
			System.out.println(id1 + " and " + id2 + " have no friends in common");
		}
		else {
			System.out.println(id1 + " and " + id2 + " have " + listOfFriends.size() + " friends in common:");
			for(int i: listOfFriends) {
				System.out.println(i);
			}
		}
	}

	/** Method used in Graphloader. It adds features to a hashmap: The key will be the index number for the matrix,
	 * and the value the feature as a String (date of birth, residence, where it worked, etc)
	 *
	 * @param index in the matrix
	 * @param String, description of the feature.
	 */
	public void addFeatures(int index, String feature) {
		Features.put(index, feature);
	}

	/** Private helper method to add the right feature to each vertex (facebook user). It scans the matrix and the Features map.
	 * If the index in the matrix where the vertex/user has info (value 1 in the matrix), it adds it as a feature
	 * for that vertex as an String. The more 1s the matrix for that user has, the more features it adds to the user.
	 */
	private void addFeaturesToNode() {
		for(MapNode node : VertexMap.values()) {
			ArrayList<Integer> matrix = node.getMatrix();
			for(int i=0; i< matrix.size();i++) {
				if(Features.containsKey(i)) {
					if(matrix.get(i) == 1) {
						node.addFeature(Features.get(i));
					}
				}
			}
		}
	}

	/** Helper method, returns false if they are not friends and true if they are already
	 * friends. Helpful not to check affinities if they are already friends, as it would make
	 * no sense to recommend a friend who is already your friend.
	 *
	 * @param MapNode, the user we are trying to give new friends in our main method
	 * @param MapNode, the friend we try to check whether or not is already a friend
	 */
	private boolean isFriend(MapNode user, MapNode friend) {
		if(user.getNeighbors().contains(friend.getVertex())) {
			//For testing purposes, prints the statement if the accounts are already friends.
			//System.out.println(user + " and " + friend + " are already friends");
			return true;
		}
		return false;
	}

	/** Helper method. Depending on the kind of concurrence between two accounts, it adds
	 * a different amount of affinity to the final count, depending on how important is the
	 * concurrence for the affinity (it creates mor affinity to work at the same place than
	 * for example, the gender or the birthday)
	 *
	 * @param int count to get and return once added the sum
	 * @param String, description of the feature.
	 */
	private int featCounting(int count, String s) {
		if(s.contains("education")|| s.contains("location")||
		s.contains("locale")|| s.contains("languages") ||
		(s.contains("work") && s.contains("position")) ||
		(s.contains("work") && s.contains("projects"))) {
			// For testing purposes, to check that the right sum is being executed:
			//System.out.println("adding 5 to " + count + " because of "  + s);
			count = count + 5;
			return count;
		}
		if(s.contains("hometown") || s.contains("political")) {
			count = count + 7;
			return count;
		}
		if(s.contains("gender")){
			count = count + 0;
			return count;
		}
		if(s.contains("last_name")){
			count = count +3;
			return count;
		}
		else {
			count = count +1;
			return count;
		}
	}

	/** Helper method. We will check every HashMap to find a friendship to recommend. We will get the 5 with the highest
	 * affinity, so the recommender will be really accurate. If there are less than 5 is because the account has not enough
	 * features in common with ANY of the other users in the entire social network or egonet we are checking who obviously are
	 * not already friends.
	 *
	 * @param HashSet<MapNode>, Final list we want to return, now empty but we will fill it with 0 to 5 recommended friendships.
	 * @param HashMap<MapNode, Integer>, A HashMap with all the accounts with features in common, and the counter of the affinity.
	 */
	private HashSet<MapNode> getListOfFiveMax(HashSet<MapNode> ListOfAffinities, HashMap<MapNode, Integer>mapAndCount){
		while(ListOfAffinities.size()<5 && !mapAndCount.isEmpty()) {
			int max = 0;
			int counter = 0;
			MapNode nodeToAdd= null;
			for(MapNode node : mapAndCount.keySet()) {
				counter = mapAndCount.get(node);
				if (counter> max) {
					max = counter;
					nodeToAdd = node;
				}
			}
			if (max == mapAndCount.get(nodeToAdd)) {
				ListOfAffinities.add(nodeToAdd);
				//For testing purposes, to check that we are adding the 5 accounts with the highest affinity coefficient:
				//System.out.println("Adding " + nodeToAdd.toString() + " Because its count is " +  mapAndCount.get(nodeToAdd) + " and max " + max);
				mapAndCount.remove(nodeToAdd);
			}
		}
		return ListOfAffinities;
	}

	/** The final method which returns a HashSet<MapNode> with the 5 accounts recommended for a given user, according to
	 * its features.
	 *
	 * It has 3 helper methos that we will explain in the comments.
	 *
	 * @param int, the id of the user we want to check. If we had real data with real names, we could use a method to
	 * insert the name and get the id number, but due to privacy issues, we are working just with id numbers.
	 */

	public HashSet<MapNode> getAffinity(int id) {
		HashSet<MapNode> ListOfAffinities = new HashSet<>();
		HashMap<MapNode, Integer> mapAndCount = new HashMap<>();
		//1.- We'll fill every vertex (user) with it's different features (work location, date of birth, gender, etc):
		addFeaturesToNode();
		MapNode user = VertexMap.get(id);
		int count = 0;
		/* 2.- We check the nodes in our graph, and for each vertex, we get the features.
		 * If the features between our user and its friend match, we execute the helper method featCounting(count,s)
		 * This method will add the value of each feature (depending on how important this feature is)
		 * to the final counter, which is an affinity coefficient between nodes in the graph.
		 * We'll fill in the HashMap with the candidates to be recommended and the value.
		 */
		for(MapNode node : VertexMap.values()) {
			//We must also check that accounts are not already friends:
			if(!user.equals(node) && !isFriend(user,node)) {
				for(String s : user.features) {
					if(node.getFeatures().contains(s)) {
						//For testing purposes, to check what feature they have in common:
						//System.out.println("the feature between " + id + " and " + node.toString() +" is the same: " + s);
						count = featCounting(count,s);
					}
				}
			}
			if(count>0) {
				//If there's affinity between our account and the one we are checking, add it to the
				//HashMap, with the node as a key, and the coefficient as a value.
				mapAndCount.put(node, count);
			}
			count = 0;
		}
		/*
		 *3.- With the helper method getListOfFiveMax(ListOfAffinities,mapAndCount), we will take the 5 candidates with
		 * higher coefficient of affinity and return it as a MapNode.
		 */
		return getListOfFiveMax(ListOfAffinities,mapAndCount);
	}


	public static void main(String[] args)
	{
		System.out.println("Loading graph:");
		Graph newGraph = new Graph();
		GraphLoader.loadGraph(newGraph,"data/3980Edges.txt");
		GraphLoader.loadFeatures(newGraph,"data/3980Feat.txt");
		int friend1= 4030;
		int friend2= 4020;
		newGraph.friendsInCommon(friend1, friend2);
		//System.out.println(newGraph.getWholeVertex().size());
		GraphLoader.helperHashFeatures(newGraph,"data/3980featnames.txt");
		int user_id = 4030;
		HashSet<MapNode> affinities = newGraph.getAffinity(user_id);
		System.out.println("Recommended friends for user " + user_id + " : " + affinities);
	}
}
