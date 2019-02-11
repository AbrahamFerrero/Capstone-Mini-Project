package graph;

public class MapEdge {
	MapNode start;
	MapNode end;
	
	public MapEdge(MapNode first, MapNode second){
		start = first;
		end = second;
	}
	
	public int getNeighbor(){
		return end.getVertex();
	}
	
	public int getVertexID() {
		return start.getVertex();
	}
	
	
}
