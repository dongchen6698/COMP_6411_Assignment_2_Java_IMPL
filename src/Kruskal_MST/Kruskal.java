package Kruskal_MST;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Kruskal {
	public Kruskal(String n_nodesCount, String n_filePath) {
		// Get the Vertex number and file path from args
		int nodesCount = Integer.parseInt(n_nodesCount);
		String filePath = n_filePath;
		// Generate the Nodes list and Edge list
		ArrayList<Integer> nodesList = getNodesList(nodesCount);
		ArrayList<ArrayList<Integer>> edgesList = getEdgeList(nodesCount, filePath);

		//Sort the Edges based on weight
		ArrayList<ArrayList<Integer>> sortedEdges = sortEdges(edgesList);

		// Execute the Kruskal function and measure the time
		long startTime = System.currentTimeMillis();
		ArrayList<ArrayList<Integer>> result_mst = Kruskal(nodesList, sortedEdges);
		long endTime = System.currentTimeMillis();
		long durationTime = endTime - startTime; //milliseconds

		// Measure the memory consumption
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		long memory = runtime.totalMemory() - runtime.freeMemory();

		// Generate the EdgesList based on Edge class.
		ArrayList<Edge> edges = generateEdgeList(result_mst);

		// Output and format result to file
		fromResultToFile(nodesCount,edges,durationTime,memory);
		System.out.println("Finish calculate");
	}
	public static ArrayList<Integer> getNodesList(int nodesCount){
		ArrayList<Integer> nodesList = new ArrayList<Integer>();
		for(int i=0;i<nodesCount;i++){
			nodesList.add(i);
		}
		return nodesList;
	}
	
	public static ArrayList<ArrayList<Integer>> getEdgeList(int nodesCount, String filePath){
		try {
    		File file = new File(filePath);
        	Scanner loadScanner = new Scanner(file);
        	int graph[][] = new int[nodesCount][nodesCount];
        	while(loadScanner.hasNext()) {
                for(int x=0;x<nodesCount;x++) {
                    for(int y=0;y<nodesCount;y++) {
                        int val = loadScanner.nextInt();
                        graph[x][y] = val;
                    }
                }
             }
             loadScanner.close();
    
             ArrayList<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();
             
             for(int x=0;x<nodesCount;x++){
            	 for(int y=x;y<nodesCount;y++){
            		 if(graph[x][y] == 0){
            			 continue;
            		 }else{
            			 ArrayList<Integer> edge = new ArrayList<Integer>(Arrays.asList(x,y,graph[x][y]));
            			 edgesList.add(edge);
            		 }
            	 }
             }
             return edgesList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<ArrayList<Integer>> sortEdges(ArrayList<ArrayList<Integer>> edges){
		Collections.sort(edges, new Comparator<ArrayList<Integer>>() {
			@Override
			public int compare(ArrayList<Integer> arg0, ArrayList<Integer> arg1) {
				return arg0.get(2).compareTo(arg1.get(2));
			}
		});	
		return edges;
	}
	
	
	public static ArrayList<ArrayList<Integer>> Kruskal(ArrayList<Integer> nodesList, ArrayList<ArrayList<Integer>> edgesList){
		DisjointSets forest = new DisjointSets();
		ArrayList<ArrayList<Integer>> mst = new ArrayList<ArrayList<Integer>>();
		
		for(Integer n: nodesList){
			forest.add(n);
		}
		
		for(ArrayList<Integer> edge : edgesList){
			int n_1 = edge.get(0);
			int n_2 = edge.get(1);
			int t_1 = forest.find(n_1);
			int t_2 = forest.find(n_2);
			
			if(t_1 != t_2){
				mst.add(edge);
				forest.union(t_1, t_2);	
			}
		}
		return mst;
	}
	
	public static ArrayList<Edge> generateEdgeList(ArrayList<ArrayList<Integer>> result_mst){
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		for(ArrayList<Integer> edge : result_mst){
			edgeList.add(new Edge(edge.get(0),edge.get(1),edge.get(2)));
		}
		return edgeList;	
	}
	
	public static void fromResultToFile(int nodesCount, ArrayList<Edge> edgeList, long durationTime, long memory) {
    	try {
    		String sumStr = "";
        	int sumValue = 0;
    		File file = new File("Result_of_Graph_G_N_"+nodesCount+".txt");
        	if(file.exists()) {
    			file.delete();
    			file.createNewFile();
    		}else{
    			file.createNewFile();
    		}
        	FileWriter out = new FileWriter(file);
        	out.write("Total number of nodes = " + nodesCount + "\n");
        	out.write("Total number of edges in the minimum spanning three = " + edgeList.size() + "\n");
        	out.write("List of edges & their costs:" + "\n");
            for(Edge e:edgeList) {
                out.write(e + "\n");
                sumStr += e.weight + " + ";
                sumValue += e.weight;
            }
            sumStr = sumStr.substring(0, sumStr.length()-3);
            out.write("Total cost of minimum spanning tree is = Sum of ("+sumStr+") = "+sumValue+"\n");
            out.write("Total execution time is = " + durationTime + " milliseconds" + "\n");
            out.write("Total memory consumption is = " + memory + " bytes" + "\n");
            out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }
}
