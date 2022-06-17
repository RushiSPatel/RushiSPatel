import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonIdentityUtil implements Person{
	
	static List<PersonIdentity> peopleList = new ArrayList<>();

	public PersonIdentity addPerson(String name){
		
		if(name == null || name.isEmpty()){
			System.out.println("Name cannot be null or empty!");
			return null;
		}
		
		PersonIdentity person = new PersonIdentity();
		
		String sqlQuery = String.format("insert into FamilyTree (individualName) values(\"%s\")",name);
		
		try {
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if(checkStatus){
				System.out.println(name + " added as person in the system!");
				person.setName(name);
				peopleList.add(person);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return person;
	}
	
	public Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes){
		Boolean check = false;
		
		if(person == null){
			System.out.println("Person object cannot be null!");
			return null;
		}
		if(attributes==null){
			System.out.println("Attributes cannot be null!");
			return null;
		}
		
		
		List<String> peopleNameList = new ArrayList<>();
		
		for (PersonIdentity personIdentity : peopleList) {
			peopleNameList.add(personIdentity.getName());
		}
		
		if(!peopleNameList.contains(person.getName())){
			System.out.println("This person is not added in the database.");
			return false;
		}
		
		String sqlQuery = new String();
		sqlQuery = "update FamilyTree set ";
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    sqlQuery = sqlQuery + String.format("%s = \"%s\",", key,value);   
		    
		    for (PersonIdentity personIdentity : peopleList) {
				if(personIdentity.getName().equals(person.getName())){
					if(key.equals("dateOfBirth")){
						personIdentity.setDateOfBirth(value);
					}
					if(key.equals("locationOfBirth")){
						personIdentity.setLocationOfBirth(value);
					}
					if(key.equals("dateOfDeath")){
						personIdentity.setDateOfDeath(value);
					}
					if(key.equals("locationOfDeath")){
						personIdentity.setLocationOfDeath(value);
					}
					if(key.equals("gender")){
						personIdentity.setGender(value);
					}
					if(key.equals("occupation")){
						personIdentity.setOccupation(value);
					}
					if(key.equals("references")){
						personIdentity.getReferences().add(value);
						//personIdentity.setReferences(value);
					}
					if(key.equals("notes")){
						personIdentity.getNotes().add(value);
						//personIdentity.setNotes(value);
					}
				}
			}
		    
		}
		sqlQuery = removeLastChar(sqlQuery);
		
		sqlQuery += String.format("where individualName = \"%s\"", person.getName());
		
		try{
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if(checkStatus){
				System.out.println("Attributes added for : " + person.getName());
				return true;
			}
			
		} catch(SQLException exception){
			exception.printStackTrace();
		}
		
		return check;
	}
	
	public Boolean recordReference(PersonIdentity person, String reference){
		Boolean check = false;
		
		if(person == null){
			System.out.println("Person object cannot be null!");
			return null;
		}
		if(reference==null){
			System.out.println("Reference cannot be null!");
			return null;
		}
		
		List<String> peopleNameList = new ArrayList<>();
		
		for (PersonIdentity personIdentity : peopleList) {
			peopleNameList.add(personIdentity.getName());
		}
		
		if(!peopleNameList.contains(person.getName())){
			System.out.println("This person is not added in the database.");
			return false;
		}

		
		String sqlQuery = new String();

		sqlQuery = String.format("insert into ReferencesOfIndividual (individualName,referencesToSourceMaterial) values (\"%s\", \"%s\")",person.getName(), reference);
		
		try{
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if(checkStatus){
				System.out.println("References added for : " + person.getName());
				if(person.getReferences()==null){
					List<String> newReference = new ArrayList<String>();
					newReference.add(reference);
					person.setReferences(newReference);
				}
				else{
				person.getReferences().add(reference);
				}
				return true;
			}
			
		} catch(SQLException exception){
			exception.printStackTrace();
		}
		
		return check;
	}
	
	public Boolean recordNote(PersonIdentity person, String note){
		Boolean check = false;
		
		if(person == null){
			System.out.println("Person object cannot be null!");
			return null;
		}
		if(note==null){
			System.out.println("Note cannot be null!");
			return null;
		}
		
		List<String> peopleNameList = new ArrayList<>();
		
		for (PersonIdentity personIdentity : peopleList) {
			peopleNameList.add(personIdentity.getName());
		}
		
		if(!peopleNameList.contains(person.getName())){
			System.out.println("This person is not added in the database.");
			return false;
		}
		//update FamilyTree set gender="Male",occupation="Software Engineer" where individualName = "Rushi";
		
		String sqlQuery = new String();
		
		//sqlQuery = String.format("update FamilyTree set notes= \"%s\" where individualName = \"%s\"", note,person.getName());
		sqlQuery = String.format("insert into NotesOfIndividual (individualName,notes) values (\"%s\", \"%s\")",person.getName(), note);
		
		try{
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if(checkStatus){
				System.out.println("References added for : " + person.getName());
				
				
				if(person.getNotes()==null){
					List<String> newNote = new ArrayList<String>();
					newNote.add(note);
					person.setNotes(newNote);
				}
				else{
				person.getNotes().add(note);
				}
				
				return true;
			}
			
		} catch(SQLException exception){
			exception.printStackTrace();
		}
		
		return check;
	}
	
	public Boolean recordChild(PersonIdentity parent,PersonIdentity child){
		Boolean check = false;
		
		if(parent == null){
			System.out.println("Parent object cannot be null!");
			return null;
		}
		if(child==null){
			System.out.println("Child object cannot be null!");
			return null;
		}
		
		
		if(parent.equals(child)){
			System.out.println("Both Parent and Child cannot be same!");
			return false;
		}
		
		List<String> peopleNameList = new ArrayList<>();
		
		for (PersonIdentity personIdentity : peopleList) {
			peopleNameList.add(personIdentity.getName());
		}
		
		if(!peopleNameList.contains(parent.getName())){
			System.out.println("Parent" +parent.getName() +" is not added in the database as a person");
			return false;
		}
		if(!peopleNameList.contains(child.getName())){
			System.out.println("Child" +child.getName() +" is not added in the database as a person");
			return false;
		}
		
		String sqlQuery = new String();
		sqlQuery = String.format("insert into Children (parent,child) values (\"%s\", \"%s\")",parent.getName(), child.getName());
		
		try{
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if(checkStatus){
				System.out.println("Recorded child for " + parent.getName());
				
				
				if(parent.getChildren()==null){
					List<PersonIdentity> newChild = new ArrayList<PersonIdentity>();
					newChild.add(child);
					parent.setChildren(newChild);
				}
				else{
					parent.getChildren().add(child);
				}
				
				
				return true;
			}
			
		} catch(SQLException exception){
			exception.printStackTrace();
		}
		
		return check;
	}
	
	public Boolean recordPartnering( PersonIdentity partner1, PersonIdentity partner2 ){
		Boolean check = false;
		
		if(partner1 == null){
			System.out.println("Partner1 object cannot be null!");
			return null;
		}
		if(partner2==null){
			System.out.println("Partner2 object cannot be null!");
			return null;
		}
		
		
		if(partner1.equals(partner2)){
			System.out.println("Both Partners cannot be same!");
			return false;
		}
		
		List<String> peopleNameList = new ArrayList<>();
		
		for (PersonIdentity personIdentity : peopleList) {
			peopleNameList.add(personIdentity.getName());
		}
		
		if(!peopleNameList.contains(partner1.getName()) || !peopleNameList.contains(partner2.getName())){
			System.out.println("Partner is not added in the database as a person");
			return false;
		}
		String sqlQuery = new String();
		String sqlQuery1 = new String();
		
		String updateDissolutionQuery = new String();
		String updateDissolutionQuery1 = new String();
		
		sqlQuery = String.format("update FamilyTree set partner = \"%s\" where individualName = \"%s\"", partner2.getName(),partner1.getName());
		
		sqlQuery1 = String.format("update FamilyTree set partner = \"%s\" where individualName = \"%s\"", partner1.getName(),partner2.getName());
		
		
		
		updateDissolutionQuery = String.format("update FamilyTree set dissolution = \"%s\" where individualName = \"%s\" and partner = \"%s\"",null,partner1.getName(),partner2.getName() );
		updateDissolutionQuery1 = String.format("update FamilyTree set dissolution = \"%s\" where individualName = \"%s\" and partner = \"%s\"",null,partner2.getName(),partner1.getName() );
		
		
		
		try{
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			
			boolean checkStatus1 = DBConnectionUtil.getInstance().updateData(sqlQuery1);
			
			boolean checkStatus2 = DBConnectionUtil.getInstance().updateData(updateDissolutionQuery);
			boolean checkStatus3 = DBConnectionUtil.getInstance().updateData(updateDissolutionQuery1);
			
			if(checkStatus && checkStatus1 && checkStatus2 && checkStatus3){
				System.out.println("Partnering recorded for "+partner1.getName()+" and "+partner2.getName());
				partner1.setPartner(partner2);
				partner2.setPartner(partner1);
				partner1.setDissolution(null);
				partner2.setDissolution(null);
				return true;
			}
			
		} catch(SQLException exception){
			exception.printStackTrace();
		}
		
		return check;
	}

	@Override
	public Boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2) {
		Boolean check = false;
		
		if(partner1 == null){
			System.out.println("Partner1 object cannot be null!");
			return null;
		}
		if(partner2==null){
			System.out.println("Partner2 object cannot be null!");
			return null;
		}
		
		if(partner1.equals(partner2)){
			System.out.println("Both Partners cannot be same!");
			return false;
		}
		
		List<String> peopleNameList = new ArrayList<>();
		
		for (PersonIdentity personIdentity : peopleList) {
			peopleNameList.add(personIdentity.getName());
		}
		
		if(!peopleNameList.contains(partner1.getName()) || !peopleNameList.contains(partner2.getName())){
			System.out.println("Partner is not added in the database as a person");
			return false;
		}
		
		if(partner1.getPartner().getName().equals(partner2.getName()) && partner2.getPartner().getName().equals(partner1.getName())){
			String sqlQuery = new String();
			String sqlQuery1 = new String();
			String sqlQueryUpdatePartner = new String();
			String sqlQueryUpdatePartner2 = new String();
			String yes = "yes";
			
			sqlQuery = String.format("update FamilyTree set dissolution = \"%s\" where individualName = \"%s\" and partner = \"%s\"",yes,partner1.getName(),partner2.getName() );
			sqlQuery1 = String.format("update FamilyTree set dissolution = \"%s\" where individualName = \"%s\" and partner = \"%s\"",yes,partner2.getName(),partner1.getName() );
			
			sqlQueryUpdatePartner = String.format("update FamilyTree set partner = \"%s\" where individualName = \"%s\"", null,partner1.getName());
			
			sqlQueryUpdatePartner2 = String.format("update FamilyTree set partner = \"%s\" where individualName = \"%s\"", null,partner2.getName());
			
			
			try{
				boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
				
				boolean checkStatus1 = DBConnectionUtil.getInstance().updateData(sqlQuery1);
				
				boolean checkStatus2 = DBConnectionUtil.getInstance().updateData(sqlQueryUpdatePartner);
				boolean checkStatus3 = DBConnectionUtil.getInstance().updateData(sqlQueryUpdatePartner2);
				
				if(checkStatus && checkStatus1 && checkStatus2 && checkStatus3){
					System.out.println("Dissolution recorded for "+partner1.getName()+" and "+partner2.getName());
					
					/*partner1.setPartner(null);
					partner2.setPartner(null);*/
					partner1.setDissolution("yes");
					partner2.setDissolution("yes");
					return true;
				}
				
			} catch(SQLException exception){
				exception.printStackTrace();
			}
			
		}
		
		return check;
	}
	
	//Method to remove the last character from a string
    //Referred stackoverflow for this method : https://stackoverflow.com/questions/7438612/how-to-remove-the-last-character-from-a-string
    public static String removeLastChar(String finalString){
    	if (finalString != null && finalString.length() > 0 && finalString.charAt(finalString.length() - 1) == ',') {
    		finalString = finalString.substring(0, finalString.length() - 1);
        }
        return finalString;
    }
	
}
