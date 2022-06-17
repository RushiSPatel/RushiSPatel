import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws SQLException {

		// This is the main class where I have only tested my manage class
		// methods.
		try {

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

			Geneology geneology = new Geneology();
			geneology.findRelation(PersonIdentityUtil.peopleList.get(4), PersonIdentityUtil.peopleList.get(9));

			geneology.findPerson("B");

			FileIdentifierUtil media = new FileIdentifierUtil();

			media.addMediaFile("D:\\New folder\\rspatel\\docs");

			media.addMediaFile("C:\\New folder\\rspatel\\docs2");
			media.addMediaFile("E:\\New folder\\rspatel\\docs3");

			Map<String, String> mediaAttributes = new HashMap<>();

			mediaAttributes.put("year", "1995");
			mediaAttributes.put("date", "1995-05-06");
			mediaAttributes.put("city", "Vadodara");
			media.recordMediaAttributes(FileIdentifierUtil.mediaList.get(0), mediaAttributes);

			Map<String, String> mediaAttributes1 = new HashMap<>();

			mediaAttributes1.put("year", "2000");
			mediaAttributes1.put("date", "2000-05-06");
			mediaAttributes1.put("date", "2000-05");
			mediaAttributes1.put("city", "Surat");
			media.recordMediaAttributes(FileIdentifierUtil.mediaList.get(1), mediaAttributes1);

			Map<String, String> mediaAttributes2 = new HashMap<>();

			mediaAttributes2.put("year", "2003");
			mediaAttributes2.put("date", "2003-05-06");
			mediaAttributes2.put("city", "Valsad");
			media.recordMediaAttributes(FileIdentifierUtil.mediaList.get(2), mediaAttributes2);

			media.peopleInMedia(FileIdentifierUtil.mediaList.get(0), people);

			media.peopleInMedia(FileIdentifierUtil.mediaList.get(2), people);

			media.peopleInMedia(FileIdentifierUtil.mediaList.get(2), people);

			media.tagMedia(FileIdentifierUtil.mediaList.get(0), "Sports");
			media.tagMedia(FileIdentifierUtil.mediaList.get(0), "Dance");

			media.tagMedia(FileIdentifierUtil.mediaList.get(1), "Sports");
			media.tagMedia(FileIdentifierUtil.mediaList.get(1), "Travel");

			media.tagMedia(FileIdentifierUtil.mediaList.get(1), "Date");
			media.tagMedia(FileIdentifierUtil.mediaList.get(2), "Sing");

			geneology.findMediaFile("D:\\New folder\\rspatel\\docs");

			geneology.findMediaFile(FileIdentifierUtil.mediaList.get(0));

			Map<String, String> attributes2 = new HashMap<>();
			attributes2.put("Date of Birth", "1995-06-09");
			attributes2.put("locationOfBirth", "Vadodara");
			attributes2.put("gender", "Male");
			attributes2.put("occupation", "Software Engineer");
			person.recordAttributes(PersonIdentityUtil.peopleList.get(0), attributes2);

			Map<String, String> attributes3 = new HashMap<>();
			attributes3.put("Date of Birth", "1995-06");
			attributes3.put("locationOfBirth", "Vadodara");
			attributes3.put("gender", "Male");
			attributes3.put("occupation", "Software Engineer");

			person.recordAttributes(PersonIdentityUtil.peopleList.get(1), attributes3);
			person.recordReference(PersonIdentityUtil.peopleList.get(0), "Family Dictionary");
			person.recordReference(PersonIdentityUtil.peopleList.get(1), "Family Dictionary1");
			person.recordReference(PersonIdentityUtil.peopleList.get(0), "Diary");
			person.recordNote(PersonIdentityUtil.peopleList.get(0), "Rushi's note");
			person.recordNote(PersonIdentityUtil.peopleList.get(1), "Rushi's note1");
			person.recordNote(PersonIdentityUtil.peopleList.get(0), "Rushi's note 2");

			geneology.notesAndReferences(PersonIdentityUtil.peopleList.get(0));
			geneology.notesAndReferences(PersonIdentityUtil.peopleList.get(1));

			geneology.findMediaByTag("Sports", null, null);

			geneology.findMediaByLocation("Vadoda", null, null);

			geneology.descendents(PersonIdentityUtil.peopleList.get(9), 2);
			geneology.ancestors(PersonIdentityUtil.peopleList.get(0), 2);

			geneology.findBiologicalFamilyMedia(PersonIdentityUtil.peopleList.get(6));

			Set<PersonIdentity> peopleInMedia = new HashSet<>();
			peopleInMedia.add(PersonIdentityUtil.peopleList.get(0));
			peopleInMedia.add(PersonIdentityUtil.peopleList.get(2));
			geneology.findIndividualsMedia(peopleInMedia, "1994-00-00", "2007-00-00");

			// person.recordChild(PersonIdentityUtil.peopleList.get(0),
			// PersonIdentityUtil.peopleList.get(1));

			person.recordPartnering(PersonIdentityUtil.peopleList.get(0), PersonIdentityUtil.peopleList.get(1));
			person.recordDissolution(PersonIdentityUtil.peopleList.get(0), PersonIdentityUtil.peopleList.get(1));
			person.recordPartnering(PersonIdentityUtil.peopleList.get(0), PersonIdentityUtil.peopleList.get(2));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
