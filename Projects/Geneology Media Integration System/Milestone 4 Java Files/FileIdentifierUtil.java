import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileIdentifierUtil implements Media{

	static List<FileIdentifier> mediaList = new ArrayList<>();
	
	@Override
	public FileIdentifier addMediaFile(String fileLocation) {
		
		FileIdentifier mediaFile = new FileIdentifier();
		
		String updatedFileLocation = fileLocation.replace("\\", "\\\\");
		
		String sqlQuery = String.format("insert into MediaArchive (fileName) values(\"%s\")",updatedFileLocation);
		
		try {
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if(checkStatus){
				
				mediaFile.setFileName(fileLocation);
				mediaList.add(mediaFile);
				System.out.println("File added as media in the system!");
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return mediaFile;
	}

	@Override
	public Boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) {

		Boolean check =false;
		
		if(fileIdentifier==null){
			System.out.println("Media object cannot be null!");
			return null;
		}
		
		if(attributes==null){
			System.out.println("Attributes are null!");
			return false;
		}
		
		List<String> fileNameList = fetchCurrentMediaNameList(fileIdentifier);
		
		if(!checkMediaPresentInSystem(fileIdentifier, fileNameList)){
			System.out.println("This file is not added in the database.");
			return null;
		}
		
		String sqlQuery = new String();
		sqlQuery = "update MediaArchive set ";
		
		
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		
		    
		for (FileIdentifier fileIdentity : mediaList) {
			if(fileIdentity.getFileName().equals(fileIdentity.getFileName())){
				if(key.equals("year")){
					
					
					String[] finalDate = fileIdentity.getDate().split("-");
					finalDate[0] = value;
					
					
					String sqlValue = String.join("-", finalDate);
					
					
					if(sqlQuery.contains("dateOfMedia")){

						String[] replace = sqlQuery.split("= \"");
						replace[1] = replace[1].replace("\",", "");
						replace[1] = String.format("\"%s\",", sqlValue);
						sqlQuery = String.join("= ", replace);
						
						
					}
					else{
					sqlQuery = sqlQuery + String.format("dateOfMedia = \"%s\",",sqlValue);
					}
					fileIdentity.setDate(sqlValue);
					
				}
				if(key.equals("date")){
					
					String[] finalDate = fileIdentity.getDate().split("-");
					String[] monthDate = value.split("-");
					String sqlValue;
					
					if(monthDate[0].length()!=4){
						System.out.println("Cannot enter date with just the day or day and month!");
						return false;
					}
					
					if(monthDate.length==1){
						finalDate[0] = monthDate[0];
						
						sqlValue = String.join(",", finalDate);
					}
					else if(monthDate.length==3){
						finalDate = monthDate;
						sqlValue = String.join("-", finalDate);
					}
					else{
						finalDate[0] = monthDate[0];
						finalDate[1] = monthDate[1];
						sqlValue = String.join("-", finalDate);
					}

					if(sqlQuery.contains("dateOfMedia")){

						String[] replace = sqlQuery.split("= \"");
						replace[1] = replace[1].replace("\",", "");
						replace[1] = String.format("\"%s\",", sqlValue);
						sqlQuery = String.join("= ", replace);
						
						
					}
					else{
					sqlQuery = sqlQuery + String.format("dateOfMedia = \"%s\",",sqlValue);
					}
					fileIdentity.setDate(sqlValue);
				}
				if(key=="city"){
					sqlQuery = sqlQuery + String.format("location = \"%s\",",value);
					fileIdentity.setLocation(value);
				}
					
			}
		}
	    
	}
		sqlQuery = PersonIdentityUtil.removeLastChar(sqlQuery);
		
		String fileNameForQuery = fileIdentifier.getFileName().replace("\\", "\\\\");
		
		sqlQuery += String.format(" where fileName = \"%s\";", fileNameForQuery);
		
		
		
		try{
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if(checkStatus){
				System.out.println("Attributes added for : " + fileIdentifier.getFileName());
				return true;
			}
			
		} catch(SQLException exception){
			exception.printStackTrace();
		}
		
		
		return check;
	}

	@Override
	public Boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) {
		Boolean check = false;
		
		if(fileIdentifier==null){
			System.out.println("Media object cannot be null!");
			return null;
		}
		if(people==null){
			System.out.println("People list cannot be null!");
			return null;
		}
		
		List<String> fileNameList = fetchCurrentMediaNameList(fileIdentifier);
		
		if(!checkMediaPresentInSystem(fileIdentifier,fileNameList)){
			System.out.println("This file is not added in the database.");
			return null;
		}
		
		
		
		for (PersonIdentity person : people) {
			String sqlQuery = String.format("insert into MediaPeople (fileName, individualName) values(\"%s\", \"%s\")", fileIdentifier.getFileName(),person.getName());
			
			try {
				boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
				if(checkStatus){
					

					List<PersonIdentity> peopleInMedia = new ArrayList<>();
					peopleInMedia.add(person);
					
					fileIdentifier.getPeopleInMedia().addAll(peopleInMedia);
					
					System.out.println(person.getName() + " added as person in media!");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return check;
	}

	@Override
	public Boolean tagMedia(FileIdentifier fileIdentifier, String tag) {
		Boolean check = false;
		if(fileIdentifier == null){
			System.out.println("Media object cannot be null!");
			return null;
		}
		if(tag == null){
			System.out.println("Tag object cannot be null!");
			return null;
		}
		
		List<String> fileNameList = fetchCurrentMediaNameList(fileIdentifier);
		
		if(!checkMediaPresentInSystem(fileIdentifier,fileNameList)){
			System.out.println("This file is not added in the database.");
			return null;
		}
		
		String sqlQuery = String.format("insert into MediaTag (fileName, tag) values(\"%s\", \"%s\")",fileIdentifier.getFileName(),tag);
		
		try {
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if(checkStatus){
				
				List<String> tags = new ArrayList<>();
				tags.add(tag);
				
				fileIdentifier.getTags().addAll(tags);
				
				System.out.println(tag + " recorded for the media!");
				check = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return check;
	}

	public List<String> fetchCurrentMediaNameList(FileIdentifier fileIdentifier){
		
		List<String> fileNameList = new ArrayList<>();
		
		for (FileIdentifier fileIdentity : mediaList) {
			fileNameList.add(fileIdentity.getFileName());
		}
		
		return fileNameList;
	}
	
	public Boolean checkMediaPresentInSystem(FileIdentifier fileIdentifier, List<String> fileNameList){
		
		Boolean check = true;
		
		if(!fileNameList.contains(fileIdentifier.getFileName())){
			
			return false;
		}
		
		return check;
		
	}
	
}
