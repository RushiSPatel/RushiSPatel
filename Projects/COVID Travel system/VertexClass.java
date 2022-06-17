import java.util.Comparator;

public class VertexClass implements Comparator<VertexClass>{
	
public VertexClass(String startCity,String destinationCity,int cost,int time, String modeOfTransport){
	this.startCity=startCity;
	this.destinationCity=destinationCity;
	this.cost=cost;
	this.time=time;
	this.modeOfTransport=modeOfTransport;
}
	
public VertexClass(VertexClass instance, int time){
	this.instance = instance;
	this.time=time;
}
	
public VertexClass(){
	
}

VertexClass instance;

String startCity;
/**
 * @return the startCity
 */
public String getStartCity() {
	return startCity;
}
/**
 * @param startCity the startCity to set
 */
public void setStartCity(String startCity) {
	this.startCity = startCity;
}
	String destinationCity;
	int cost;
	int time;
/**
 * @return the destinationCity
 */
public String getDestinationCity() {
	return destinationCity;
}
/**
 * @param destinationCity the destinationCity to set
 */
public void setDestinationCity(String destinationCity) {
	this.destinationCity = destinationCity;
}
/**
 * @return the cost
 */
public int getCost() {
	return cost;
}
/**
 * @param cost the cost to set
 */
public void setCost(int cost) {
	this.cost = cost;
}
/**
 * @return the time
 */
public int getTime() {
	return time;
}
/**
 * @param time the time to set
 */
public void setTime(int time) {
	this.time = time;
}
/**
 * @return the modeOfTransport
 */
public String getModeOfTransport() {
	return modeOfTransport;
}
/**
 * @param modeOfTransport the modeOfTransport to set
 */
public void setModeOfTransport(String modeOfTransport) {
	this.modeOfTransport = modeOfTransport;
}
	String modeOfTransport;


 @Override public int compare(VertexClass vertex1, VertexClass vertex2){
  if (vertex1.time < vertex2.time)
	            return -1;
  if (vertex1.time > vertex2.time)
	            return 1;
	  
	        return 0;
	    }

}
