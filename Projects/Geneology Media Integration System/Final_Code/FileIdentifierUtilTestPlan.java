import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @author Rushi
 * @description Test Plan for FileIdentity
 *
 */
public class FileIdentifierUtilTestPlan {

	/**
	 * @author Rushi
	 * @description Method to test addMediaFile functionality
	 */
	@Test
	public void addMediaFileTest() {
		
		FileIdentifier expectedResult = new FileIdentifier();
		expectedResult.setFileName("xyz");
		FileIdentifierUtil util = new FileIdentifierUtil();
		FileIdentifier actualResult = util.addMediaFile("xyz");
		assertEquals(expectedResult.getFileName(), actualResult.getFileName());
		
	}
	
	/**
	 * @author Rushi
	 * Method to test recordAttribute functionality
	 */
	@Test
	public void recordAttributesTest(){
		
		Map<String,String> attributes = new HashMap<>();
		attributes.put("date", "1995-09-06");
		
		FileIdentifierUtil p = new FileIdentifierUtil();
		p.addMediaFile("y");
		 
		FileIdentifier file = new FileIdentifier();
		file.setFileName("y");
		file.setId(1);
		 
		assertEquals(true, p.recordMediaAttributes(file, attributes));
		
	}
	
	/**
	 * @author Rushi
	 * Method to test peopleInMedia functionality
	 */
	@Test
	public void peopleInMediaTest(){
		
		List<PersonIdentity> people = new ArrayList<>();
		PersonIdentityUtil util = new PersonIdentityUtil();
		util.addPerson("x");
		util.addPerson("y");
		
		PersonIdentity p = new PersonIdentity();
		p.setName("x");
		p.setId(1);
		PersonIdentity p1 = new PersonIdentity();
		p1.setName("y");
		p1.setId(2);
		
		people.add(p);
		people.add(p1);
		
		
		
		FileIdentifierUtil f = new FileIdentifierUtil();
		f.addMediaFile("x");
		
		FileIdentifier file = new FileIdentifier();
		file.setFileName("x");
		file.setId(1);
		 file.setDate("1999-09-09");
		 
		 Map<String,String> attributes = new HashMap<>();
			attributes.put("date", "1995-09-06");
		 
			f.recordMediaAttributes(file, attributes);
			
		assertEquals(true, f.peopleInMedia(file, people));
		
	}
	
	/**
	 * @author Rushi
	 * Method to test tagMedia functionality
	 */
	@Test
	public void tagMediaTest(){
		
		
		FileIdentifierUtil util = new FileIdentifierUtil();
		util.addMediaFile("r");
		
		FileIdentifier f = new FileIdentifier();
		f.setFileName("r");
		f.setId(1);
		f.setDate("1990-09-09");
		
		 Map<String,String> attributes = new HashMap<>();
			attributes.put("date", "1995-09-06");
		 
			util.recordMediaAttributes(f, attributes);
		
		String tag = "My tag";
		
		assertEquals(true, util.tagMedia(f, tag));
		
	
		
	}
	

}
