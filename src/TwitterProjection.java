import java.io.IOException;
import okhttp3.*;
import twitter4j.*;
import twitter4j.conf.*;

public class TwitterProjection {
	
	public static String _userName;
	public static long _userID;
	public static int _followersCurrent;
	public static int _followersNextMonth;
	public static double _growthRate;
	private static Twitter _twitterObject;
	/**
	 * Twitter Counter API token(s)
	 */
	private static String _twitterCounterAPIKey = "f151f8a75d6eb79a4791a818c9cdad24";
	/**
	 * Twitter API token(s)
	 */
	private static String _twitterConsumerKey = "ZWGVbuNuxm3nMCcwhU8rgcNry";
	private static String _twitterConsumerSecret = "w5eKPw12J0SOgtQBfxTpAY9wVCdTASLXOP8KILwRJU4npadCzG";
	private static String _twitterAccessToken = "4715067237-oahqSnZY5gEhtRo2XTA854mqR1uzshkeCyMEXkP";
	private static String _twitterAccessTokenSecret = "hlYk57RZZ583tGcxBP9ls5otxPFuuSiftsHLbFX3kwogR";
	
	/**
	 * Constructor
	 */
	public TwitterProjection() {
		setUpTwitterFactory();
	}
	
	/**
	 * Handle authorization, create TwitterFactory object
	 */
	private void setUpTwitterFactory() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(_twitterConsumerKey);
		cb.setOAuthConsumerSecret(_twitterConsumerSecret);
		cb.setOAuthAccessToken(_twitterAccessToken);
		cb.setOAuthAccessTokenSecret(_twitterAccessTokenSecret);

		_twitterObject = new TwitterFactory(cb.build()).getInstance();
	}
	
	/**
	 * Set the twitter username for this class, to retrieve information about
	 * and trigger loading the ID number for that username
	 * @param userName
	 */
	public void setUserNameAndLoadID(String userName) {
		_userName = userName;
		loadTwitterUserID();
	}
	
	/**
	 * Simple getter for username value
	 * 
	 * @return username for the class
	 */
	public String getUserName() {
		return _userName;
	}
	
	/**
	 * Load user ID value
	 */
	private void loadTwitterUserID() {
		String user = getUserName();
		try {
			_userID = _twitterObject.showUser(user).getId();
		}
		catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Simple getter for user id
	 * @return
	 */
	public long getTwitterUserID() {
		return _userID;
	}
	
	/**
	 * Kick off connection to twitter counter API
	 * 
	 * @throws IOException
	 * @throws JSONException
	 */
	public void loadTwitterCounterResults() throws IOException, JSONException {
		connectToTwitterCounter();
	}
	
	/**
	 * Actually build the API call, kick off processing the results
	 * 
	 * @throws IOException
	 * @throws JSONException
	 */
	private static void connectToTwitterCounter() throws IOException, JSONException {
		long twitterAccountID = _userID;
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("http://api.twittercounter.com/?apikey=" + _twitterCounterAPIKey + "&twitter_id=" + twitterAccountID)
		  .get()
		  .addHeader("cache-control", "no-cache")
		  .build();

		Response response = client.newCall(request).execute();
		processProjectionResponse(response.body().string().toString());
		determineFollowerGrowthRate();
	}
	
	/**
	 * Get values from an API response string
	 * 
	 * @param response
	 * @throws JSONException
	 */
	private static void processProjectionResponse(String response) throws JSONException {
		JSONObject jsonObject = new JSONObject(response);
		int followersCurrent = jsonObject.getInt("followers_current");
		int nextMonthFollowers = jsonObject.getInt("next_month");
		_followersCurrent = followersCurrent;
		_followersNextMonth = nextMonthFollowers;
	}
	
	/**
	 * Simple method to determine the growth rate
	 */
	private static void determineFollowerGrowthRate() {
		double growthRate = (double) (_followersNextMonth - _followersCurrent) / _followersCurrent;
		_growthRate = growthRate;
	}
	
	/**
	 * Simple getter for growth rate value
	 * @return
	 */
	public double getGrowthRate() {
		return _growthRate;
	}
	
}
