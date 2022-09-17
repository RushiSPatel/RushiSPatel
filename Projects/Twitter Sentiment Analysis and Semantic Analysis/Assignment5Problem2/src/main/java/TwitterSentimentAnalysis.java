import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TwitterSentimentAnalysis {

    static int tweetNumber = 0;


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

    public void sentimentAnalysisOfTweets(MongoDatabase mongoDB) throws FileNotFoundException {

        String key = "Tweet Text";
        MongoCollection<Document> data = mongoDB.getCollection("Twitter Data");
        MongoCursor<Document> cursor = data.find().iterator();
        System.out.format("%-10s%-150s%-30s%-10s\n","Tweet #","message","match","polarity");
        System.out.println("");
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String tweetText = doc.getString(key);
            countFrequencyOfTweets(tweetText);
        }
    }

    public void countFrequencyOfTweets(String tweetText) throws FileNotFoundException {

        String[] tweetWordArray;
        List<String> positiveWords = new ArrayList<String>();
        List<String> negativeWords = new ArrayList<String>();

        String tweet = tweetText.replaceAll("[^\\w\\s]", "");

        Scanner positiveWordFile = new Scanner(new File("PositiveWords.txt"));
        Scanner negativeWordFile = new Scanner(new File("NegativeWords.txt"));

        while(positiveWordFile.hasNextLine()){
            positiveWords.add(positiveWordFile.nextLine());
        }

        while(negativeWordFile.hasNextLine()){
            negativeWords.add(negativeWordFile.nextLine());
        }

        tweetWordArray = tweet.split(" ");

        List<String> matchingWordsAndPolarity = matchBagOfWordsWithTweetText(tweetWordArray,positiveWords,negativeWords);
        String matchingWords = matchingWordsAndPolarity.get(0);
        String polarity = matchingWordsAndPolarity.get(1);


        System.out.format("%-10d%-150s%-30s%-10s\n",tweetNumber,tweetText.replaceAll("[\\t\\n\\r]+", " "),matchingWords,polarity);

    }

    public List<String> matchBagOfWordsWithTweetText(String[] tweetWordArray,List<String> positiveWords,List<String> negativeWords){
        Map<String,Integer> bow = new HashMap<>();
        int countOfPositiveWords = 0;
        int countOfNegativeWords = 0;
        List<String> matchingWordsAndPolarity = new ArrayList<>();
        for(int i=0;i<tweetWordArray.length;i++)
        {
            if(bow.containsKey(tweetWordArray[i]))
            {bow.put(tweetWordArray[i], bow.get(tweetWordArray[i])+1);}
            else
            {bow.put(tweetWordArray[i],1);}
        }

        Map<String,List<Integer>> matchingWordsAndCount = matchBagOfWords(bow,positiveWords,negativeWords,countOfPositiveWords,countOfNegativeWords);

        Map.Entry<String,List<Integer>> entry = matchingWordsAndCount.entrySet().iterator().next();
        String matchingWords = entry.getKey();
        List<Integer> counts = entry.getValue();
        countOfPositiveWords = counts.get(0);
        countOfNegativeWords = counts.get(1);

        String polarity = calculatePolarity(countOfPositiveWords,countOfNegativeWords);
        matchingWordsAndPolarity.add(matchingWords);
        matchingWordsAndPolarity.add(polarity);
        return matchingWordsAndPolarity;
    }

    public Map<String,List<Integer>> matchBagOfWords( Map<String,Integer> bow, List<String> positiveWords,List<String> negativeWords, int countOfPositiveWords, int countOfNegativeWords) {

        String matchingWords = "";
        Map<String,List<Integer>> matchingWordsCount = new HashMap<>();
        List<Integer> count = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : bow.entrySet()) {
            if (positiveWords.contains(entry.getKey())) {
                matchingWords += entry.getKey() + ",";
                countOfPositiveWords += entry.getValue();
            }
            if (negativeWords.contains(entry.getKey())) {
                matchingWords += entry.getKey() + ",";
                countOfNegativeWords += entry.getValue();
            }
        }
        count.add(countOfPositiveWords);
        count.add(countOfNegativeWords);
        matchingWordsCount.put(matchingWords,count);
        return matchingWordsCount;
    }

    public String calculatePolarity(int countOfPositiveWords, int countOfNegativeWords){

        String polarity = "";
        if(countOfPositiveWords-countOfNegativeWords<0) {
            polarity = "negative";
        }else if(countOfPositiveWords-countOfNegativeWords>0) {
            polarity = "positive";
        }else {
            polarity = "neutral";
        }

        tweetNumber++;
        return polarity;
    }

    public static void main(String[] args) {
        Twitter twitter = authentication();
        MongoDatabase mongoDB = connectToMongoDB();
        try {
            TwitterSentimentAnalysis sentimentAnalysis = new TwitterSentimentAnalysis();

            sentimentAnalysis.sentimentAnalysisOfTweets(mongoDB);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}