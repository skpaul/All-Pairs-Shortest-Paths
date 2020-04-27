//package floyd_warshall;

import java.util.List;
import java.util.ArrayList;

public class Graph {
	private int vCount;
	private float[][] adj;

	public int getvCount() {
		return vCount;
	}

	public float[][] getAdj() {
		return adj;
	}

	public Graph(int vCount) {
		this.vCount = vCount;
		adj = new float[vCount][vCount];
		for (int i = 0; i < vCount; i++) {
			for (int j = 0; j < vCount; j++) {
				if (i != j) {
					adj[i][j] = Float.MAX_VALUE;
				}

			}
		}
	}

	public void addEdge(int i, int j, float weight) {
		adj[i][j] = weight;
	}

	public void removeEdge(int i, int j) {
		adj[i][j] = Float.MAX_VALUE;
	}

	public boolean hasEdge(int i, int j) {
		if (adj[i][j] != Float.MAX_VALUE && adj[i][j] != 0) {
			return true;
		}
		return false;
	}

	public List<Integer> neighbours(int vertex) {
		List<Integer> edges = new ArrayList<Integer>();
		for (int i = 0; i < vCount; i++)
			if (hasEdge(vertex, i))
				edges.add(i);
		return edges;
	}

	public void printGraph() {
		for (int i = 0; i < vCount; i++) {
			List<Integer> edges = neighbours(i);
			System.out.print(i + ": ");
			for (int j = 0; j < edges.size(); j++) {
				System.out.print(edges.get(j) + " ");
			}
			System.out.println();
		}
	}

	public void printGraph(List<Vertex> vertexes) {
		for (int i = 0; i < vCount; i++) {
			List<Integer> edges = neighbours(i);
			Vertex startVertex = find(i, vertexes);
			System.out.print(startVertex.getName() + ": ");

			// System.out.print(i + ": ");
			for (int j = 0; j < edges.size(); j++) {
				Vertex destVertex = find(edges.get(j), vertexes);
				System.out.print(destVertex.getName() + " ");
				
				//System.out.print(edges.get(j) + " ");
			}
			System.out.println();
		}
	}

	private Vertex find(Integer id, List<Vertex> districts ){
		for (Vertex dist : districts) {
            Integer nn = dist.getId();
				if (nn == id) {
					return dist;
				}
        }
		throw new RuntimeException("Should not happen");		
	}

	// Recursive Function to print path of given
	// vertex u from source vertex v
	private void printPath(float[][] path, int v, int u)
	{
		if (path[v][u] == v)
			return;

		printPath(path, v, (int) path[v][u]);
		System.out.print((int)path[v][u] + " ");
	}

	// Function to print the shortest cost with path
	// information between all pairs of vertices
	private void printSolution(float[][] cost, float[][] path, int N)
	{
		for (int v = 0; v < N; v++)
		{
			for (int u = 0; u < N; u++)
			{
				if (u != v && path[v][u] != -1)
				{
					System.out.print("Shortest Path from vertex " + v +
							" to vertex " + u + " is (" + v + " ");
					printPath(path, v, u);
					System.out.println(u + ")");
				}
			}
		}
	}

	// Function to run Floyd-Warshell algorithm
	public void FloydWarshell(float[][] adjMatrix, int N)
	{
		// cost[] and parent[] stores shortest-path
		// (shortest-cost/shortest route) information
		float[][] cost = new float[N][N];
		float[][] path = new float[N][N];

		// initialize cost[] and parent[]
		for (int v = 0; v < N; v++)
		{
			for (int u = 0; u < N; u++)
			{
				// initally cost would be same as weight
				// of the edge
				cost[v][u] = adjMatrix[v][u];

			//	float ff = float.m
				if (v == u)
					path[v][u] = 0;
				else if (cost[v][u] != Float.MAX_VALUE)
					path[v][u] = v;
				else
					path[v][u] = -1;
			}
		}

		// run Floyd-Warshell
		for (int k = 0; k < N; k++)
		{
			for (int v = 0; v < N; v++)
			{
				for (int u = 0; u < N; u++)
				{
					// If vertex k is on the shortest path from v to u,
					// then update the value of cost[v][u], path[v][u]

					if (cost[v][k] != Float.MAX_VALUE
							&& cost[k][u] != Float.MAX_VALUE
							&& (cost[v][k] + cost[k][u] < cost[v][u]))
					{
						cost[v][u] = cost[v][k] + cost[k][u];
						path[v][u] = path[k][u];
					}
				}

				// if diagonal elements become negative, the
				// graph contains a negative weight cycle
				if (cost[v][v] < 0)
				{
					System.out.println("Negative Weight Cycle Found!!");
					return;
				}
			}
		}

		// Print the shortest path between all pairs of vertices
		printSolution(cost, path, N);
	}


}