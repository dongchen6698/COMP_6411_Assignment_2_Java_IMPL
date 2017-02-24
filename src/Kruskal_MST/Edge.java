package Kruskal_MST;

public class Edge{  
    public int startPoint;  
    public int endPoint;  
    public int weight;
     
    public Edge(int n_startPoint, int n_endPoint, int n_weight){
        this.startPoint = n_startPoint;
        this.endPoint = n_endPoint;  
        this.weight = n_weight;  
    }  

    public String toString(){
        return "("+ (startPoint+1) +", "+ (endPoint+1) +") edge cost: " + weight;
    }
}
