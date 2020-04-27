import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) {
		try {
			Scanner scan = new Scanner(System.in);
			List<Vertex> districtList  = new ArrayList<>();
			districtList = enterDistrict(districtList, scan);

			int vCount = districtList.size();
			Graph g = new Graph(vCount);
		
			List<Edge> edgeList  = new ArrayList<>();
			edgeList = enterRoad(edgeList, districtList, scan);

			for (Edge edge : edgeList) {
				Vertex source = edge.getSource();
				Vertex dest = edge.getDestination();
				g.addEdge(source.getId(), dest.getId(), edge.getWeight());
		   }

			System.out.println("Graph:");
		
			// print Graph
			g.printGraph();

			// Floyd-Warshall All Pair Shortest Path Algorithm
			System.out.println("Floyd-Warshall All Pair Shortest Path Matrix:");
			FloydWarshall(g);

			System.out.println("Good Bye");

		 	scan.close();
		 
		 } catch(Exception e) {
			 System.out.println(e.getMessage());
		 }
	}
	
	public static void FloydWarshall(Graph g) {
		int V = g.getvCount();
		
		// to store the calculated distances
		float dist[][] = new float[V][V];

		// initialize with adjacency matrix weight values
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				dist[i][j] = g.getAdj()[i][j];
			}
		}

		// loop through all vertices one by one
		for (int k = 0; k < V; k++) {
			// pick all as source
			for (int i = 0; i < V; i++) {
				// pick all as destination
				for (int j = 0; j < V; j++) {
					// If k is on the shortest path from i to j
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						// update the value of dist[i][j]
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}
		
		// shortest path matrix
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				// if value is infinity
				if (dist[i][j] == Float.MAX_VALUE)
					System.out.print("INF ");
				else
					{
						int val = (int)dist[i][j];
						System.out.print(val + "   ");

						// System.out.print(dist[i][j] + "   ");
					}
				
			}
			System.out.println();
		}
		
	}


	static Float[][] addEdge(List<Edge> edgeList, Float[][] adj) {

		for (Edge edge : edgeList) {
			 Vertex source = edge.getSource();
			 Vertex dest = edge.getDestination();
			 adj[source.getId()][dest.getId()] = edge.getWeight();
        }

		return adj;
	}

	static List<Vertex> enterDistrict(List<Vertex> existingList, Scanner scan){
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Enter the name of district ... [N = No]");
		System.out.println("------------------------------------------------------------------------");

		String name = scan.next();
		name = name.toLowerCase();
		if(!name.equals("n")){
			if(isExist(name, existingList)){
				System.out.println("This place is already exists ...");
				enterDistrict(existingList, scan);
			}
			else{
				Integer count = existingList.size();
				Vertex dist = new Vertex(count, name);
				existingList.add(dist);
				enterDistrict(existingList, scan);
			}
		}

		return existingList;
	}
	
	static boolean isExist(String name, List<Vertex> districts ){

		if(districts.size() == 0){
			return false;
		}
		for (Vertex dist : districts) {
			if(dist != null){
				String nn = dist.getName();
				if (nn.equals(name)) {
					return true;
				}
			}
			
		}
		//throw new RuntimeException("Should not happen");
		return false;
	}
		
	static List<Edge> enterRoad(List<Edge> existingRoads, List<Vertex> districts, Scanner scan){
		Integer counter = existingRoads.size() + 1;
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Enter Edge#"+ counter +": start, end, distance   [N = No] ...");
		System.out.println("------------------------------------------------------------------------");

		String roadDetails = scan.next();
		roadDetails = roadDetails.toLowerCase();
		if(!roadDetails.equals("n")){
				String[] inputArray = roadDetails.split(",");
				
				if(inputArray.length < 3){
					enterRoad(existingRoads, districts, scan);
				}

				String origin = inputArray[0];

				if(origin == ""){
					enterRoad(existingRoads, districts, scan);
				}

				if(!isExist(origin, districts)){
				   System.out.println("This vertex does not exist");
				   enterRoad(existingRoads, districts, scan);
				}
		   
				Vertex originDistrict = find(origin, districts);

				String  destination = inputArray[1];
				if(destination == ""){
					enterRoad(existingRoads, districts, scan);
				}
				if(!isExist(destination, districts)){
					System.out.println("This vertex does not exist");
					enterRoad(existingRoads, districts, scan);
				 }
			
				Vertex destinationDistrict = find(destination, districts);

			
				Float distnance = Float.parseFloat(inputArray[2]);

				Edge newRoad = new Edge(counter, originDistrict, destinationDistrict, distnance);
				existingRoads.add(newRoad);
				enterRoad(existingRoads, districts, scan);
		}

		return existingRoads;
	}

	static Vertex find(String name, List<Vertex> districts ){
		for (Vertex dist : districts) {
            String nn = dist.getName();
				if (nn.equals(name)) {
					return dist;
				}
        }
		throw new RuntimeException("Should not happen");		
	}


}
