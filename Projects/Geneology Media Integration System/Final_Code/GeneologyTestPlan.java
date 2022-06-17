import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * 
 * @author Rushi
 * @description Class to test Geneology functionalities.
 *
 */
public class GeneologyTestPlan {

	/**
	 * @author Rushi
	 * @description Method to test findPerson functionality.
	 */
	@Test
	public void findPersonTest() {
		
		PersonIdentityUtil p = new PersonIdentityUtil();
		PersonIdentity expected = p.addPerson("f");
		
		
		
		Geneology g = new Geneology();
		PersonIdentity actual = g.findPerson("f");
		
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
	}
	
	/**
	 * @author Rushi
	 * @description Method to test findMediaFile functionality.
	 */
	@Test
	public void findMediaFileTest() {
		
		FileIdentifierUtil p = new FileIdentifierUtil();
		FileIdentifier expected = p.addMediaFile("f");
		
		
		
		Geneology g = new Geneology();
		FileIdentifier actual = g.findMediaFile("f");
		
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getFileName(), actual.getFileName());
	}
	
	/**
	 * @author Rushi
	 * @description Method to test findName functionality.
	 */
	@Test
	public void findNameTest() {
		
		String expected = "b";
		
		PersonIdentity p = new PersonIdentity();
		p.setName("b");
		
		
		
		Geneology g = new Geneology();
		
		
		assertEquals(expected,g.findName(p));
		
	}
	
	/**
	 * @author Rushi
	 * @description Method to test findMediaFile functionality.
	 */
	@Test
	public void findMediaFileTest1() {
		
		String expected = "g";
		
		FileIdentifier actual = new FileIdentifier();
		actual.setFileName("g");
		
		
		Geneology g = new Geneology();
		
		assertEquals(expected,g.findMediaFile(actual));
		
	}
	
	/**
	 * @author Rushi
	 * @description Method to test findRelation functionality.
	 */
	@Test
	public void findRelationTest() {
		
		PersonIdentityUtil person = new PersonIdentityUtil();
		person.addPerson("A");
		person.addPerson("B");
		person.addPerson("C");
		person.addPerson("D");
		person.addPerson("E");
		person.addPerson("F");
		person.addPerson("G");
		person.addPerson("H");
		person.addPerson("I");
		person.addPerson("J");
		person.addPerson("K");
		person.addPerson("L");
		person.addPerson("M");
		
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(0));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(1));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(2));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(4));
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(5));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(6));
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(7));
		
		
		
		person.recordChild(PersonIdentityUtil.peopleList.get(11), PersonIdentityUtil.peopleList.get(10));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(3));
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(8));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(12), PersonIdentityUtil.peopleList.get(11));
		
		Geneology geneology = new Geneology();
		BiologicalRelation actual = geneology.findRelation(PersonIdentityUtil.peopleList.get(4),PersonIdentityUtil.peopleList.get(9));
		
		BiologicalRelation expected = new BiologicalRelation();
		expected.setCousinship(-1);
		
		assertEquals(expected.getCousinship(),actual.getCousinship());
		
	}
	
	/**
	 * @author Rushi
	 * @description Method to test descendants functionality.
	 */
	@Test
	public void descendantsTest() {
		
		PersonIdentityUtil person = new PersonIdentityUtil();
		person.addPerson("A");
		person.addPerson("B");
		person.addPerson("C");
		person.addPerson("D");
		person.addPerson("E");
		person.addPerson("F");
		person.addPerson("G");
		person.addPerson("H");
		person.addPerson("I");
		person.addPerson("J");
		person.addPerson("K");
		person.addPerson("L");
		person.addPerson("M");
		
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(0));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(1));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(2));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(4));
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(5));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(6));
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(7));
		
		
		
		person.recordChild(PersonIdentityUtil.peopleList.get(11), PersonIdentityUtil.peopleList.get(10));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(3));
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(8));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(12), PersonIdentityUtil.peopleList.get(11));
		
		Geneology geneology = new Geneology();
		
		int expected = 7;
		
		Set<PersonIdentity> descendants = geneology.descendents(PersonIdentityUtil.peopleList.get(9), 2);
		
		assertEquals(expected,descendants.size());
		//geneology.ancestors(PersonIdentityUtil.peopleList.get(0), 2);
		
	}
	
	/**
	 * @author Rushi
	 * @description Method to test ancestors functionality.
	 */
	@Test
	public void ancestorsTest() {
		
		PersonIdentityUtil person = new PersonIdentityUtil();
		person.addPerson("A");
		person.addPerson("B");
		person.addPerson("C");
		person.addPerson("D");
		person.addPerson("E");
		person.addPerson("F");
		person.addPerson("G");
		person.addPerson("H");
		person.addPerson("I");
		person.addPerson("J");
		person.addPerson("K");
		person.addPerson("L");
		person.addPerson("M");
		
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(0));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(1));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(2));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(4));
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(5));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(6));
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(7));
		
		
		
		person.recordChild(PersonIdentityUtil.peopleList.get(11), PersonIdentityUtil.peopleList.get(10));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(3));
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(8));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(12), PersonIdentityUtil.peopleList.get(11));
		
		Geneology geneology = new Geneology();
		
		int expected = 2;
		
		Set<PersonIdentity> ancestors = geneology.ancestors(PersonIdentityUtil.peopleList.get(0), 2);
		
		assertEquals(expected,ancestors.size());
		
		
	}
	
	/**
	 * @author Rushi
	 * @description Method to test notesAndReferences functionality.
	 */
	@Test
	public void notesAndReferencesTest() {
		
		PersonIdentity p = new PersonIdentity();
		p.setName("n");
		p.setId(1);
		PersonIdentityUtil util = new PersonIdentityUtil();
		util.addPerson("n");
		
		util.recordNote(p, "first");
		util.recordNote(p, "second");
		util.recordReference(p, "firstReference");
		
		
		
		Geneology geneology = new Geneology();
		
		assertEquals(true,geneology.notesAndReferences(p).contains("firstReference"));
		
	}
	
	/**
	 * @author Rushi
	 * @description Method to test findMediaByTag functionality.
	 */
	@Test
	public void findMediaByTagTest() {
		
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
		
		Geneology geneology = new Geneology();
		
		
		
		assertNotNull(geneology.findMediaByTag(tag, null, null));
		
	}
	
	/**
	 * @author Rushi
	 * @description Method to test findMediaByLocation functionality.
	 */
	@Test
	public void findMediaByLocationTest() {
		
		FileIdentifierUtil util = new FileIdentifierUtil();
		util.addMediaFile("r");
		
		FileIdentifier f = new FileIdentifier();
		f.setFileName("r");
		f.setId(1);
		f.setDate("1990-09-09");
		
		 Map<String,String> attributes = new HashMap<>();
			attributes.put("date", "1995-09-06");
			attributes.put("location", "Vadodara");
			util.recordMediaAttributes(f, attributes);
		
		String location = "Vadodara";
		
		Geneology geneology = new Geneology();
		
		assertNotNull(geneology.findMediaByLocation(location, null, null));
		
	}
	
	/**
	 * @author Rushi
	 * @description Method to test findIndividualsMedia functionality.
	 */
	@Test
	public void findIndividualsMediaTest() {
		
		PersonIdentityUtil person = new PersonIdentityUtil();
		person.addPerson("A");
		person.addPerson("B");
		person.addPerson("C");
		person.addPerson("D");
		person.addPerson("E");
		person.addPerson("F");
		person.addPerson("G");
		person.addPerson("H");
		person.addPerson("I");
		person.addPerson("J");
		person.addPerson("K");
		person.addPerson("L");
		person.addPerson("M");
		
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(0));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(1));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(2));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(4));
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(5));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(6));
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(7));
		
		
		
		person.recordChild(PersonIdentityUtil.peopleList.get(11), PersonIdentityUtil.peopleList.get(10));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(3));
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(8));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(12), PersonIdentityUtil.peopleList.get(11));
		
		
		
		List<PersonIdentity> people = new ArrayList();
		people.add(PersonIdentityUtil.peopleList.get(0));
		people.add(PersonIdentityUtil.peopleList.get(1));
		people.add(PersonIdentityUtil.peopleList.get(2));
		people.add(PersonIdentityUtil.peopleList.get(4));
		
		FileIdentifierUtil media = new FileIdentifierUtil();
		media.peopleInMedia(FileIdentifierUtil.mediaList.get(0),people);
		
		Set<PersonIdentity> peopleInMedia = new HashSet<>();
		peopleInMedia.add(PersonIdentityUtil.peopleList.get(0));
		peopleInMedia.add(PersonIdentityUtil.peopleList.get(2));
		
		
		Geneology geneology = new Geneology();
		
		assertNotNull(geneology.findIndividualsMedia(peopleInMedia,null,null));
		
	}

	/**
	 * @author Rushi
	 * @description Method to test findBiologicalFamilyMedia functionality.
	 */
	@Test
	public void findBiologicalFamilyMediaTest() {
		
		PersonIdentityUtil person = new PersonIdentityUtil();
		person.addPerson("A");
		person.addPerson("B");
		person.addPerson("C");
		person.addPerson("D");
		person.addPerson("E");
		person.addPerson("F");
		person.addPerson("G");
		person.addPerson("H");
		person.addPerson("I");
		person.addPerson("J");
		person.addPerson("K");
		person.addPerson("L");
		person.addPerson("M");
		
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(0));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(1));
		person.recordChild(PersonIdentityUtil.peopleList.get(3), PersonIdentityUtil.peopleList.get(2));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(4));
		person.recordChild(PersonIdentityUtil.peopleList.get(6), PersonIdentityUtil.peopleList.get(5));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(6));
		person.recordChild(PersonIdentityUtil.peopleList.get(8), PersonIdentityUtil.peopleList.get(7));
		
		
		
		person.recordChild(PersonIdentityUtil.peopleList.get(11), PersonIdentityUtil.peopleList.get(10));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(3));
		person.recordChild(PersonIdentityUtil.peopleList.get(9), PersonIdentityUtil.peopleList.get(8));
		
		person.recordChild(PersonIdentityUtil.peopleList.get(12), PersonIdentityUtil.peopleList.get(11));
		
		
		
		List<PersonIdentity> people = new ArrayList();
		people.add(PersonIdentityUtil.peopleList.get(0));
		people.add(PersonIdentityUtil.peopleList.get(1));
		people.add(PersonIdentityUtil.peopleList.get(2));
		people.add(PersonIdentityUtil.peopleList.get(4));
		
		FileIdentifierUtil media = new FileIdentifierUtil();
		media.peopleInMedia(FileIdentifierUtil.mediaList.get(0),people);
		
		Set<PersonIdentity> peopleInMedia = new HashSet<>();
		peopleInMedia.add(PersonIdentityUtil.peopleList.get(0));
		peopleInMedia.add(PersonIdentityUtil.peopleList.get(2));
		
		
		Geneology geneology = new Geneology();
		
		assertNotNull(geneology.findBiologicalFamilyMedia(PersonIdentityUtil.peopleList.get(6)));
		
	}
	
}
