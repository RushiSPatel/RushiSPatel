import java.util.List;
import java.util.Set;

public interface IGeneology {

	PersonIdentity findPerson( String name );
	
	FileIdentifier findMediaFile( String name );
	
	String findName( PersonIdentity id );
	
	String findMediaFile( FileIdentifier fileId );
	
	BiologicalRelation findRelation( PersonIdentity person1, PersonIdentity person2 );
	
	Set<PersonIdentity> descendents( PersonIdentity person, Integer generations );
	
	Set<PersonIdentity> ancestors( PersonIdentity person, Integer generations );
	
	List<String> notesAndReferences( PersonIdentity person );
	
	Set<FileIdentifier> findMediaByTag( String tag , String startDate, String endDate);
	
	Set<FileIdentifier> findMediaByLocation( String location, String startDate, String endDate);
	
	List<FileIdentifier> findIndividualsMedia( Set<PersonIdentity> people, String startDate, String endDate);
	
	List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person);
}
