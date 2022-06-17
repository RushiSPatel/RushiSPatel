import java.util.*;
/*
 * Class for Dijkstras algorithm implementation
 * Reference: https://www.geeksforgeeks.org/java-program-for-dijkstras-shortest-path-algorithm-greedy-algo-7/ 
 */
public class DijkstrasImplementation {
   
	//Creating a general size
    static int size = TravelPlanner.cityList.size();
    //ArrayList to store the cost and time in the matrix
    ArrayList<Integer> mapping = new ArrayList<>();
  
 
    /*
     * Method to print the matrix using dijkstras
     */
    void display(int length[])
    {
        System.out.println("Cities \t\t Distance from source City");
        for (int i = 0; i < size; i++){
            System.out.println(i + " \t\t " + length[i]);
        }
        System.out.println(mapping);
    }
    
    /*
     * Method to find the shortest distance
     */
    int shortestDistance(int length[], Boolean checkSet[])
    {
        //Initialize minimum value
        int minimumValue = Integer.MAX_VALUE;
        int index = -1;
 
        for (int i = 0; i < size; i++)
            if (checkSet[i] == false && length[i] <= minimumValue) {
            	minimumValue = length[i];
            	index = i;
            }
 
        return index;
    }
 
    /*
     * Method to implement the dijkstras algorith with the use of adjacency matrix
     */
    ArrayList<Integer> dijkstraImplementation(int myGraph[][], int start)
    {
    	//Final array of distance
        int length[] = new int[size]; 
      
        //Setting check array
        Boolean checkSet[] = new Boolean[size];
 
        //Initialising elements to maximum value
        for (int i = 0; i < size; i++) {
        	length[i] = Integer.MAX_VALUE;
        	checkSet[i] = false;
        }
 
        //Source to source distance as 0
        length[start] = 0;
 
        //Finding the minimum path
        for (int i = 0; i < size - 1; i++) {
            //Setting the distance from the vertex which isnt visited yet
            int shortestDistance = shortestDistance(length, checkSet);
 
            //Set the vertex as visited
            checkSet[shortestDistance] = true;
            
            
 
            //Modifying the distance from the vertex
            for (int k = 0; k < size; k++)
            	
               //Storing in mapping list
                if (!checkSet[k] && myGraph[shortestDistance][k] != 0 && length[shortestDistance] != Integer.MAX_VALUE && length[shortestDistance] + myGraph[shortestDistance][k] < length[k]){
                	length[k] = length[shortestDistance] + myGraph[shortestDistance][k];
                	if(mapping.isEmpty()){
                		mapping.add(i);
                    	mapping.add(k);
                    	mapping.add(length[k]);
                	}
                	if(mapping.contains(k)){
                		for(int i2 = 1; i2< mapping.size(); i2 = i2+3){
                			if(mapping.get(i2) == k){
                				if(mapping.get(i2 + 1) > length[k]){
                					mapping.set(i2-1, i);
                					mapping.set(i2, k);
                					mapping.set(i2+1, length[k]);
                				}
                			}
                		}
                	}else{
                		mapping.add(i);
                    	mapping.add(k);
                    	mapping.add(length[k]);
                		
                	}
                	
                }
        }
 
        //display the shortest distance from source
        display(length);
        return mapping;
    }
    }