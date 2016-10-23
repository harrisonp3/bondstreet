import java.io.IOException;
import java.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import twitter4j.*;

public class WatsonSentiment {
	public static double _coverageSentimentFactor;
	public static String _companyName;
	/**
	 * Watson API token(s)
	 */
	private static String _watsonApiKey = "9b816fa3fc16f39e664e088f459d40f20157c3cd";
	
	/**
	 * Constructor - takes company name as parameter
	 * @param companyName
	 * @throws JSONException
	 */
	public WatsonSentiment(String companyName) throws JSONException {
		_companyName = companyName;
		connectToWatson();
	}
	
	/**
	 * Handle connecting to the Watson API and kicking off processing the results
	 * @throws JSONException
	 */
	private static void connectToWatson() throws JSONException {
		OkHttpClient client = new OkHttpClient();

		String companyName = _companyName;
		// Searches through up to 100 articles in past 90 days with company name in the title
		// returns a sentiment score on each article, based on IBM machine learning
		Request request = new Request.Builder()
		  .url("https://gateway-a.watsonplatform.net/calls/data/GetNews?apikey=" + _watsonApiKey + "&outputMode=json&start=now-90d&end=now&count=100&q.enriched.url.title=" + companyName + "&return=enriched.url.url%2Cenriched.url.title%2Cenriched.url.docSentiment.score%2Cenriched.url.docSentiment.type")
		  .get()
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			processSentimentResponse(response.body().string().toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Determine if results in response string were successful, and if so, grab them
	 * 
	 * @param response
	 * @throws JSONException
	 */
	private static void processSentimentResponse(String response) throws JSONException {
		JSONObject jsonObject = new JSONObject(response);
		String errorMessage = "ERROR";
		if (jsonObject.get("status").equals(errorMessage)) {
			System.out.println("\nCouldn't connect to Watson API");
		} else {
			JSONArray docsList = jsonObject.getJSONObject("result").getJSONArray("docs");
			double aggregateSentiment = 0;
			for (int i = 0; i < docsList.length(); i++) {
				JSONObject docSource = docsList.getJSONObject(i).getJSONObject("source");
				double sentimentScore = ((Number) docSource.getJSONObject("enriched").getJSONObject("url").getJSONObject("docSentiment").get("score")).doubleValue();
				aggregateSentiment += sentimentScore;
			}
			_coverageSentimentFactor = aggregateSentiment / docsList.length();
		}
	}
	
	/**
	 * Simple getter for sentiment value
	 * @return
	 */
	public double getCoverageSentimentFactor() {
		return _coverageSentimentFactor;
	}

}
