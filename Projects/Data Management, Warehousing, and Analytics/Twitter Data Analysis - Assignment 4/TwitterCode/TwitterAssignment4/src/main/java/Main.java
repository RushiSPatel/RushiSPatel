import java.io.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import org.bson.Document;
import twitter4j.json.DataObjectFactory;

class Tweets {

    static int count = 1;
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

    public static boolean extractionEngine(Twitter twitter, String query, MongoDatabase database1){
        boolean check = false;
        Query search = new Query(query);
        search.count(100);
        QueryResult tweetData;


        for (int counter = 0; counter < 32; counter++) {
            try {
                tweetData = twitter.search(search);
                System.out.println(tweetData.getCount());
                int tweetCounter = 1;
                String json = "{\"data\":[";
                for (Status tweet : tweetData.getTweets()) {
                    System.out.println(tweet.getId() + ":" + tweet.getText());
                    if(tweetCounter==100){
                        json += DataObjectFactory.getRawJSON(tweet);
                    }
                    else {
                        json += DataObjectFactory.getRawJSON(tweet) + ",";
                    }
                    System.out.println(json);
                    tweetCounter++;
                }
                json += "]}";
                check = true;

                //Reference: https://www.w3schools.com/java/java_files_create.asp
                File myObj = new File(Integer.toString(count)+".txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + Integer.toString(count)+".txt");
                } else {
                    System.out.println("File already exists.");
                }

                FileWriter myWriter = new FileWriter(Integer.toString(count)+".txt");
                myWriter.write(json);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
                check = true;

            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            count++;
        }

        return check;
    }

    public static boolean filtrationEngine(MongoDatabase db1){
        boolean check = false;

        try{
            JSONParser parser = new JSONParser();
            int filtrationCount=1;
            for (int counter = 0; counter < 32; counter++) {
                MongoCollection<Document> collection1 = db1.getCollection("Twitter Data");
                Object obj = parser.parse(new FileReader(Integer.toString(filtrationCount)+".txt"));
                JSONObject jsonObject = (JSONObject) obj;
                System.out.println(filtrationCount);
                JSONArray subjects = (JSONArray)jsonObject.get("data");
                for(int i=0;i<subjects.size();i++){
                    JSONObject jsonObject1 = (JSONObject) subjects.get(i);
                    Long id =(Long) jsonObject1.get("id");
                    String text = (String)jsonObject1.get("text");
                    Long retweetCount = (Long)jsonObject1.get("retweet_count");
                    String lang = (String)jsonObject1.get("lang");

                    String transformedID = Long.toString(id).replaceAll("[^\\w\\s]", "");
                    //https://stackoverflow.com/questions/42618872/regex-for-website-or-url-validation
                    String removeURL = text.replaceAll("((https?|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", "");
                    String transformedText = removeURL.replaceAll("[^\\w\\s]", "");

                    System.out.println("id: " + Long.toString(id));
                    System.out.println("text: " + text);

                    Document document1 = new Document("ID", transformedID).append("Tweet Text", transformedText).append("Retweet Count",retweetCount).append("Lang",lang);
                    collection1.insertOne(document1);
                    System.out.println("Inserted To myMongoTweet");
                }
                filtrationCount++;
            }


        } catch (Exception exception){
            exception.printStackTrace();
        }



        return check;
    }

    public static MongoDatabase connectToMongoDB() {
        //Replace your connection string url below
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://<ENTER_YOUR_USERNAME_HERE>:<ENTER_YOUR_PASSWORD_HERE>@cluster0.exxmu.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        //Reference : https://www.tabnine.com/code/java/methods/com.mongodb.MongoClientSettings/builder
        MongoClientSettings settings1 = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        MongoClient client1 = MongoClients.create(settings1);
        MongoDatabase mongoDB = client1.getDatabase("myMongoTweet");
        return mongoDB;
    }

    public static void main(String[] args) {
        Twitter twitter = authentication();

        String query = "lang:en (mask OR cold OR flu OR snow OR immune OR vaccine)";
        MongoDatabase mongoDB = connectToMongoDB();
        extractionEngine(twitter, query,mongoDB);
        filtrationEngine(mongoDB);
    }

}
