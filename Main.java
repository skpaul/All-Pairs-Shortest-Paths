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

			List<Edge> roadList  = new ArrayList<>();
			roadList = enterRoad(roadList, districtList, scan);

			System.out.println("Good Bye");
			System.out.println("Good Bye");

			scan.close();
		 
		 } catch(Exception e) {
			 System.out.println(e.getMessage());
		 }
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
		System.out.println("Enter road#"+ counter +": start, end, distance   [N = No] ...");
		System.out.println("i.e. dhaka, rajshahi, 3");
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
				   System.out.println("This district does not exist");
				   enterRoad(existingRoads, districts, scan);
				}
		   
				Vertex originDistrict = find(origin, districts);

				String  destination = inputArray[1];
				if(destination == ""){
					enterRoad(existingRoads, districts, scan);
				}
				if(!isExist(destination, districts)){
					System.out.println("This district does not exist");
					enterRoad(existingRoads, districts, scan);
				 }
			
				Vertex destinationDistrict = find(destination, districts);

			
				Integer distnance = Integer.parseInt(inputArray[2]);

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
