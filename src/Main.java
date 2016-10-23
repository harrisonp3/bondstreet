import java.io.*;
import java.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.*;

public class Main {
	public static void main(String args[]) throws Exception{
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter business name : ");
		String businessName = input.next();

		System.out.println("Please enter owner's name : ");
		String ownerName = input.next();

		System.out.println("Please enter twitter username : ");
		String twitterUserName = input.next();
		
		TwitterProjection twitter = new TwitterProjection();
		twitter.setUserNameAndLoadID(twitterUserName);
		twitter.loadTwitterCounterResults();
		double growthRate = twitter.getGrowthRate();
		System.out.println("\nGrowth rate for twitter user is = " + growthRate);
		
		WatsonSentiment watson = new WatsonSentiment(businessName);
		double sentimentFactor = watson.getCoverageSentimentFactor();
		System.out.println("\nWatson coverage sentiment factor is = " + sentimentFactor);
		
		// For now, P-score is simply a sum of the twitter account's 1 month follower growth rate
		// and the average sentiment value on news coverage featuring the business name in article titles
		System.out.println("\nComposite P-score for " + businessName + " = " + (growthRate + sentimentFactor)); 
  }
}