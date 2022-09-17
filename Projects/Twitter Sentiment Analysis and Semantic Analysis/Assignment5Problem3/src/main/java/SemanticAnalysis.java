import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SemanticAnalysis {
    private static double peopleCounter = 0;
    private static double conditionCounter = 0;
    private static double weatherCounter = 0;
    private static double totalDocuments = 0;
    String key = "Tweet Text";

    //My code from Assignment 4 for Twitter authentication
    //Reference: https://www.tabnine.com/code/java/methods/twitter4j.conf.ConfigurationBuilder/setOAuthConsumerKey
    public static Twitter authentication() {
        ConfigurationBuilder confBuild = new ConfigurationBuilder();
        confBuild.setDebugEnabled(true);
        confBuild.setJSONStoreEnabled(true);
        confBuild.setOAuthConsumerKey("P9xd2SeGKmM75MPcu1e9h5Lhw");
        confBuild.setOAuthConsumerSecret("xEvpgofniw1O8wwtsmz0hxmhVCZz8xv8ifmPxQhfdE77lHSHBt");
        confBuild.setOAuthAccessToken("1499412296568877060-xrfuEHFBue9CDmRFz1zMeqrKvVPiWz");
        confBuild.setOAuthAccessTokenSecret("UPv1OsBFCIuaGOuL8Eo6qfaU3TuRNjIPToJ6kK3k8p2gu");
        return new TwitterFactory(confBuild.build()).getInstance();
    }

    //My code from Assignment 4 for connecting to MongoDB
    public static MongoDatabase connectToMongoDB() {
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://USERNAME:PASSWORD@cluster0.exxmu.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        //Reference : https://www.tabnine.com/code/java/methods/com.mongodb.MongoClientSettings/builder
        MongoClientSettings settings1 = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        MongoClient client1 = MongoClients.create(settings1);
        MongoDatabase mongoDB = client1.getDatabase("myMongoTweet");
        return mongoDB;
    }

    public static void semanticAnalysis(MongoDatabase mongoDB){
        String key = "Tweet Text";


        MongoCollection<Document> data = mongoDB.getCollection("Twitter Data");
        MongoCursor<Document> cursor = data.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();

            if(doc.getString(key).toLowerCase().contains("people")){
                peopleCounter++;
            }
            if(doc.getString(key).toLowerCase().contains("condition")){
                conditionCounter++;
            }
            if(doc.getString(key).toLowerCase().contains("weather")){
                weatherCounter++;
            }
            totalDocuments++;
        }

        System.out.println("Total Documents : " + totalDocuments);
        System.out.format("%-30s%-50s%-70s%-50s\n","Search Query", "Document Containing Term(df)", "Total Documents / no. of documents term appeared(df)","Log10(N/df)");
        String weatherDF = Double.toString(totalDocuments) + "/" + Double.toString(weatherCounter);
        Double weatherLog = Math.log10(totalDocuments/weatherCounter);
        String peopleDF = Double.toString(totalDocuments) + "/" + Double.toString(peopleCounter);
        Double peopleLog = Math.log10(totalDocuments/peopleCounter);
        String conditionDF = Double.toString(totalDocuments) + "/" + Double.toString(conditionCounter);
        Double conditionLog = Math.log10(totalDocuments/conditionCounter);
        System.out.format("%-30s%-50s%-70s%-50s\n","weather",weatherCounter,weatherDF,weatherLog);
        System.out.format("%-30s%-50s%-70s%-50s\n","people",peopleCounter,peopleDF,peopleLog);
        System.out.format("%-30s%-50s%-70s%-50s\n","condition",conditionCounter,conditionDF,conditionLog);
    }

    public List<Float> weatherFrequency(MongoDatabase mongoDB) {

        List<Float> relativeFrequencyList = new ArrayList<>();
        int tweetNumber = 1;
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.format("%-30s%-50s\n", "Term","weather");
        String a = "weather appeared in "+(int)weatherCounter+" documents";
        System.out.format("%-70s%-50s%-50s\n", a,"Total Words (m)", "Frequency");
        MongoCollection<Document> data = mongoDB.getCollection("Twitter Data");
        MongoCursor<Document> cursor = data.find().iterator();
        while (cursor.hasNext()) {
            int frequency = 0;
            Document doc = cursor.next();
            if(doc.getString(key).toLowerCase().contains("weather")){
                String[] tweetTotalWords = doc.getString(key).split(" ");
                int totalWords = tweetTotalWords.length;
                for (String word : tweetTotalWords) {
                    if (word.equalsIgnoreCase("weather")) {
                        frequency++;
                    }
                }
                String tweetNum = "tweet #"+tweetNumber;
                System.out.format("%-70s%-50d%-50d\n", tweetNum,totalWords, frequency);
                float addList = (float)frequency/(float)totalWords;
                relativeFrequencyList.add(addList);
                tweetNumber++;
            }

        }
        return relativeFrequencyList;
    }

    public void displayHighestRelativeFrequency(MongoDatabase mongoDB,List<Float> relativeFrequencyList) {
        float maxRelativeFrequency = Collections.max(relativeFrequencyList);
        int index = relativeFrequencyList.indexOf(maxRelativeFrequency);
        int tweetNumber = index + 1;
        int counter = 0;
        MongoCollection<Document> data = mongoDB.getCollection("Twitter Data");
        MongoCursor<Document> cursor = data.find().iterator();
        System.out.println();
        System.out.println("The highest frequency(f/m) among all the tweets is " + maxRelativeFrequency + " of tweet #" + tweetNumber);
        System.out.println();
        System.out.println("The tweet is displayed below which has the highest frequency(f/m):");
        System.out.println();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            if (doc.getString(key).toLowerCase().contains("weather")) {
                if (counter == tweetNumber) {
                    System.out.println(doc.getString(key));
                }
                counter++;
            }
        }
    }

    public static void main(String[] args) {
        Twitter twitter = authentication();
        SemanticAnalysis semanticAnalysis = new SemanticAnalysis();
        MongoDatabase mongoDB = connectToMongoDB();
        try {
            semanticAnalysis(mongoDB);
            List<Float> relativeFrequencyList = semanticAnalysis.weatherFrequency(mongoDB);
            semanticAnalysis.displayHighestRelativeFrequency(mongoDB,relativeFrequencyList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}