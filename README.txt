Hi! The breakdown for how the program works is as follows:


1. NAVIGATING TO THE FILE
	- The working file of interest is the "Graph.java" file using the directory: "/awu0626_HW1/src/main/Graph.java"

2. BREAKDOWN
	- In "Graph.java" there are a total of 7 methods:
		1. public static void main(String[] args);
		2. public Graph()
		3. public void searchWithBFS(int src)
		4. public boolean searchWithBFS(int src, int tgt)
		5. public void searchWithDFS(int src)
		6. public boolean searchWithDFS(int src, int tgt)
		7 private boolean getPath(int tgt, Integer[] parent)

	- The methods of interest are ONLY the BFS and DFS searches, namely the methods (3), (4), (5), (6) which actually does the graph searches.
	
	- Methods (3) and (5) take as input (int src), the source node and searches 
	  the entire graph starting from the input source node and prints out the
	  number of times that searches(BFS/DFS) needed to be reinitiated in order to discover all the graph.

	- Methods (4) and (6) take as inputs (int src, int tgt), the source node and
	  the target node respectively and does a single iteration of graph search. 
	  If the search, which begins on node (src), is able to reach the target (tgt), 
	  then the graph terminate on reaching the target node and calls on 
	  the helper method (7), to print out the path from (src -> tgt).
		- Note that the return type of (4) and (6) denotes whether if the 							
		  (tgt) is reachable from (src). If (tgt) is reachable from (src),
		  then the methods return true, otherwise false. 

	- The main method (1), allows user to specify which search to run based on command-line input.

3. INPUTS
	- Initialization: 
		- Open up the terminal from IDE of choice.
			- In IntelliJ, navigate to: "View -> Tool Windows -> Terminal" 
			- In VSCode, navigate to :  "View -> Terminal"

	- Navigate to the relative file path "/src/main/" which houses the "Graph.java" file.
		
	- From terminal, run the following command to compile Graph.java: "javac Graph.java"
		
	- After compilation, user can run the graph search algorithms by:
		- searchWithBFS(int src) : "java Graph searchWithBFS x"
			The code above runs BFS search to see how many searches are needed to discover all graph using BFS starting from source node x

		- searchWithDFS(int src) : "java Graph searchWithDFS x"
			The code above runs BFS search to see how many searches are needed to discover all graph using DFS starting from source node x

		- searchWithBFS(int src, int tgt) : "java Graph searchWithBFS x y"
			The code above runs BFS search to see if target node y is reachable from source node x. If it is, the method will print out the path from (x -> y)
		
		- searchWithDFS(int src, int tgt) : "java Graph searchWithDFS x y"
			The code above runs DFS search to see if target node y is reachable from source node x. If it is, the method will print out the path from (x -> y)
				




	
	