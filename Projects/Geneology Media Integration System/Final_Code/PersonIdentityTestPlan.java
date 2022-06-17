import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.*;

/**
 * 
 * @author Rushi
 * @description Test Plan for PersonIdentity
 *
 */
public class PersonIdentityTestPlan {

	/**
	 * Method to test BiologicalRelation class created
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testBiologicalRelationClass() throws ClassNotFoundException  {
		
		 Class<?> classExists = Class.forName("BiologicalRelation", false, getClass().getClassLoader());
	        assertNotNull(classExists);
		
	}
	
	/**
	 * Method to test FileIdentifier class created
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testFileIdentifierUtilClass() throws ClassNotFoundException  {
		
		 Class<?> classExists = Class.forName("FileIdentifierUtil", false, getClass().getClassLoader());
	        assertNotNull(classExists);
		
	}
	
	/**
	 * Method to test Geneology class created
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testGeneologyClass() throws ClassNotFoundException  {
		
		 Class<?> classExists = Class.forName("Geneology", false, getClass().getClassLoader());
	        assertNotNull(classExists);
		
	}
	
	/**
	 * Method to test PersonIdentity class created
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testPersonIdentityUtilClass() throws ClassNotFoundException  {
		
		 Class<?> classExists = Class.forName("PersonIdentityUtil", false, getClass().getClassLoader());
	        assertNotNull(classExists);
		
	}
	
	/**
	 * Method to test addPerson functionality
	 * @throws ClassNotFoundException
	 */
	@Test
	public void addPersonTest() {
		
		PersonIdentity expectedResult = new PersonIdentity();
		expectedResult.setName("Rushi");
		PersonIdentityUtil util = new PersonIdentityUtil();
		PersonIdentity actualResult = util.addPerson("Rushi");
		assertEquals(expectedResult.getName(), actualResult.getName());
		
	}
	
	/**
	 * Method to test recordAttribute functionality
	 */
	@Test
	public void recordAttributesTest(){
		
		Map<String,String> attributes = new HashMap<>();
		attributes.put("dateOfBirth", "1995-09-06");
		attributes.put("locationOfBirth", "Vadodara");
		
		PersonIdentityUtil p = new PersonIdentityUtil();
		p.addPerson("Rushi");
		 
		PersonIdentity person = new PersonIdentity();
		person.setName("Rushi");
		 
		assertEquals(true, p.recordAttributes(person, attributes));
		
	}
	
	/**
	 * Method to test recordReference functionality
	 */
	@Test
	public void recordReferenceTest(){
		
		String reference = "My reference";
		
		PersonIdentityUtil p = new PersonIdentityUtil();
		p.addPerson("Rushi");
		 
		PersonIdentity person = new PersonIdentity();
		person.setName("Rushi");
		person.setId(1);
		 
		assertEquals(true, p.recordReference(person, reference));
		
	}
	
	/**
	 * Method to test recordNote functionality
	 */
	@Test
	public void recordNoteTest(){
		
		String note = "My note";
		
		PersonIdentityUtil p = new PersonIdentityUtil();
		p.addPerson("Rushi");
		 
		PersonIdentity person = new PersonIdentity();
		person.setName("Rushi");
		person.setId(1);
		 
		assertEquals(true, p.recordNote(person, note));
		
	}
	
	/**
	 * Method to test recordChild functionality
	 */
	@Test
	public void recordChildTest(){
		
		String note = "My note";
		
		PersonIdentityUtil p = new PersonIdentityUtil();
		p.addPerson("Rushi");
		 
		PersonIdentity person = new PersonIdentity();
		person.setName("Rushi");
		person.setId(1);
		 
		assertEquals(true, p.recordNote(person, note));
		
	}
	
	/**
	 * Method to test recordPartnering functionality
	 */
	@Test
	public void recordPartneringTest(){
		
		PersonIdentityUtil p = new PersonIdentityUtil();
		p.addPerson("Rushi");
		p.addPerson("Mansi");
		 
		PersonIdentity partner1 = new PersonIdentity();
		partner1.setName("Rushi");
		partner1.setId(1);
		
		PersonIdentity partner2 = new PersonIdentity();
		partner2.setName("Mansi");
		partner2.setId(2);
		 
		assertEquals(true, p.recordPartnering(partner1, partner2));
		
	}
	
	/**
	 * Method to test recordDissolving functionality
	 */
	@Test
	public void recordDissolvingTest(){
		
		PersonIdentityUtil p = new PersonIdentityUtil();
		p.addPerson("Rushi");
		p.addPerson("Mansi");
		 
		PersonIdentity partner1 = new PersonIdentity();
		partner1.setName("Rushi");
		partner1.setId(1);
		
		PersonIdentity partner2 = new PersonIdentity();
		partner2.setName("Mansi");
		partner2.setId(2);
		p.recordPartnering(partner1, partner2);
		assertEquals(true, p.recordDissolution(partner1, partner2));
		
	}
	

	
}
