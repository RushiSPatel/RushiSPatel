import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		// This is the main class where I have only tested my manage class methods.
		try{
		
		FileIdentifierUtil media = new FileIdentifierUtil();
		
		media.addMediaFile("D:\\New folder\\rspatel\\docs");
			
		Map<String,String> mediaAttributes = new HashMap<>();
		
		mediaAttributes.put("year", "1995");
		mediaAttributes.put("date", "2000-06");
		mediaAttributes.put("city", "Vadodara");
		media.recordMediaAttributes(FileIdentifierUtil.mediaList.get(0),mediaAttributes);
			
		PersonIdentityUtil person = new PersonIdentityUtil();
		person.addPerson("Rushi");
		person.addPerson("Deepika");
		person.addPerson("Mansi");
		
		List<PersonIdentity> people = new ArrayList();
		people.add(PersonIdentityUtil.peopleList.get(0));
		people.add(PersonIdentityUtil.peopleList.get(1));
		people.add(PersonIdentityUtil.peopleList.get(2));
		
		media.peopleInMedia(FileIdentifierUtil.mediaList.get(0),people);
		media.tagMedia(FileIdentifierUtil.mediaList.get(0), "Sports");
		media.tagMedia(FileIdentifierUtil.mediaList.get(0), "Dance");
		
		Map<String,String> attributes = new HashMap<>();
		//attributes.put("Date of Birth", "09/06/1995");
		attributes.put("locationOfBirth", "Vadodara");
		attributes.put("gender", "Male");
		attributes.put("occupation", "Software Engineer");
		person.recordAttributes(PersonIdentityUtil.peopleList.get(0),attributes);
		person.recordReference(PersonIdentityUtil.peopleList.get(0), "Family Dictionary");
		person.recordReference(PersonIdentityUtil.peopleList.get(0), "Diary");
		person.recordNote(PersonIdentityUtil.peopleList.get(0), "Rushi's note");
		person.recordChild(PersonIdentityUtil.peopleList.get(0), PersonIdentityUtil.peopleList.get(0));
		
		person.recordPartnering(PersonIdentityUtil.peopleList.get(0), PersonIdentityUtil.peopleList.get(1));
		person.recordDissolution(PersonIdentityUtil.peopleList.get(0), PersonIdentityUtil.peopleList.get(1));
		person.recordPartnering(PersonIdentityUtil.peopleList.get(0), PersonIdentityUtil.peopleList.get(2));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
