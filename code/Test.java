class Edge{	//edge class
	public int sVert;
	public int eVert;
	public int weight;
	
	public Edge(int sv, int ev, int w) {
		// TODO Auto-generated constructor stub
		sVert = sv;
		eVert = ev;
		weight = w;
	}
}

class PriorityQueue{	//array based heap class
	private final int SIZE = 20;
	private Edge[] EdgeList;
	private int size;
	
	public PriorityQueue() {
		// TODO Auto-generated constructor stub
		EdgeList = new Edge[SIZE];
		size = 0;
	}
	
	public void insert(Edge edge) {	//insert operation
		int j;
		for (j = 0; j < size; j++) {
			if (edge.weight >= EdgeList[j].weight) {
				break;
			}
		}
		
		for (int k = size-1; k >= j; k--) {
			EdgeList[k+1] = EdgeList[k];
		}
		
		EdgeList[j] = edge;
		size++;
	}
	
	public Edge popMin() {	//remove the minimum item in the heap
		return EdgeList[--size];
	}
	
	public void popN(int n) {	//remove the Nth item in the heap
		for (int i = n; i < size-1; i++) {
			EdgeList[i] = EdgeList[i+1];
		}
		size--;
	}
	
	public Edge peekMin() {	//get the minimum item in the heap
		return EdgeList[size-1];
	}
	
	public Edge peekN(int n) {	//get the Nth item in the heap
		return EdgeList[n];
	}
	
	public int size() {	//get the size of the heap
		return size;
	}
	
	public boolean isEmpty() {	//check if the heap is empty
		return (size == 0);
	}
	
	public int find(int index) {	//find the special item with a given index
		for (int i = 0; i < size; i++) {
			if (EdgeList[i].eVert == index) {
				return i;
			}
		}
		return -1;
	}
}

class Vertex{	//vertex class
	public char label;
	public boolean isInTree;
	
	public Vertex(char lab) {
		// TODO Auto-generated constructor stub
		label = lab;
		isInTree = false;
	}
}

class Graph{	//graph class
	private final int MAX_VERTS = 20;
	private final int INFINITY = 1000000;
	private Vertex vertexList[];
	private int adjMat[][];
	private int nVerts;
	private int currentVert;
	private PriorityQueue thePQ;
	private int nTree;
	
	public Graph() {	//initialize the graph
		// TODO Auto-generated constructor stub
		vertexList = new Vertex[MAX_VERTS];
		
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for (int i = 0; i < MAX_VERTS; i++) {
			for (int j = 0; j < MAX_VERTS; j++) {
				adjMat[i][j] = INFINITY;
			}
		}
		thePQ = new PriorityQueue();
	}
	
	public void addVertex(char lab) {	//add vertex to the graph
		vertexList[nVerts++] = new Vertex(lab);
	}
	
	public void addEdge(int start, int end, int weight) {	//add edge to the graph
		adjMat[start][end] = weight;
		adjMat[end][start] = weight;
	}
	
	public void displayVertex(int index) {	//display the vertex in the graph
		System.out.print(vertexList[index].label);
	}
	
	public void MST() {	//prime method for the MST problem
		currentVert = 0;
		
		while (nTree < nVerts-1) {
			vertexList[currentVert].isInTree = true;
			nTree++;
			
			for (int i = 0; i < nVerts; i++) {
				if (i == currentVert) {
					continue;
				}
				if (vertexList[i].isInTree) {
					continue;
				}
				int weight = adjMat[currentVert][i];
				if (weight == INFINITY) {
					continue;
				}
				putInPQ(i, weight);
			}
			
			if (thePQ.size() == 0) {
				System.out.println("Graph is not connected!");
				return;
			}
			
			Edge theEdge = thePQ.popMin();
			int sVert = theEdge.sVert;
			currentVert = theEdge.eVert;
			
			
			System.out.print(vertexList[sVert].label);
			System.out.print(vertexList[currentVert].label);
			System.out.print(" ");
		}
		
		for (int i = 0; i < nVerts; i++) {
			vertexList[i].isInTree = false;
		}
	}

	private void putInPQ(int newVert, int newWeight) {	//put the weight into the heap
		// TODO Auto-generated method stub
		int queueIndex = thePQ.find(newVert);
		if (queueIndex != -1) {
			Edge tmp = thePQ.peekN(queueIndex);
			int oldWeight = tmp.weight;
			if (oldWeight > newWeight) {
				thePQ.popN(queueIndex);
				Edge theEdge = new Edge(currentVert, newVert, newWeight);
				thePQ.insert(theEdge);
			}
		}
		else {
			Edge theEdge = new Edge(currentVert, newVert, newWeight);
			thePQ.insert(theEdge);
		}
	}
}

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph theGraph = new Graph();
		theGraph.addVertex('A');	//#0
		theGraph.addVertex('B');	//#1
		theGraph.addVertex('C');	//#2
		theGraph.addVertex('D');	//#3
		theGraph.addVertex('E');	//#4
		theGraph.addVertex('F');	//#5
		theGraph.addVertex('G');	//#6
		theGraph.addVertex('H');	//#7
		theGraph.addVertex('I');	//#8
		theGraph.addVertex('J');	//#9
		
		theGraph.addEdge(0, 1, 8);	//#0 AB
		theGraph.addEdge(0, 5, 10);	//#1 AF
		theGraph.addEdge(0, 7, 4);	//#2 AH
		theGraph.addEdge(1, 2, 4);	//#3 BC
		theGraph.addEdge(1, 4, 10);	//#4 BE
		theGraph.addEdge(1, 5, 7);	//#5 BF
		theGraph.addEdge(1, 7, 9);	//#6 BH
		theGraph.addEdge(2, 3, 3);	//#7 CD
		theGraph.addEdge(2, 5, 3);	//#8 CF
		theGraph.addEdge(3, 4, 25);	//#9 DE
		theGraph.addEdge(3, 5, 18);	//#10 DF
		theGraph.addEdge(3, 6, 2);	//#11 DG
		theGraph.addEdge(4, 5, 2);	//#12 EF
		theGraph.addEdge(4, 6, 7);	//#13 EG
		theGraph.addEdge(6, 7, 3);	//#14 GH
		theGraph.addEdge(0, 8, 5);	//#15 AI
		theGraph.addEdge(7, 9, 6);	//#16 HJ
		
		System.out.println("The Minimum Spanning Tree is: ");
		theGraph.MST();
	}

}
