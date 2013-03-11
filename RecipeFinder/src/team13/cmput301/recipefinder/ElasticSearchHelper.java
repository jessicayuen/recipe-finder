/**
 * Deals with operations related to searching and retrieving of
 * recipes from http://cmput301.softwareprocess.es:8080/cmput301w13t12/
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ElasticSearchHelper {
	private static final String BASEURL = 
			"http://cmput301.softwareprocess.es:8080/cmput301w13t12/";

	// Singleton
	transient private static ElasticSearchHelper elasticSearchHelper = null;

	private HttpClient httpclient;
	private Gson gson;

	protected ElasticSearchHelper() {
		// Exists only to defeat instantiation
	}

	/**
	 * Retrieves the singleton ElasticSearchHelper. Initializes
	 * it if first call.
	 * @return elasticSearchHelper
	 */
	public static ElasticSearchHelper getElasticSearchHelper() {
		if (elasticSearchHelper == null) {
			elasticSearchHelper = new ElasticSearchHelper();
			elasticSearchHelper.httpclient = new DefaultHttpClient();
			elasticSearchHelper.gson = new Gson();
		}
		return elasticSearchHelper;
	}

	/**
	 * Inserts a recipe to elasticSearch service. Consumes the POST/Insert
	 * operation of the service.
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public void insertRecipe(Recipe recipe) throws IllegalStateException,
			IOException {
		final Recipe r = recipe;
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = new HttpPost(BASEURL + r.getId());
				StringEntity stringentity = null;
				try {
					stringentity = new StringEntity(gson.toJson(r));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				httpPost.setHeader("Accept", "application/json");

				httpPost.setEntity(stringentity);
				HttpResponse response = null;
				try {
					response = httpclient.execute(httpPost);
					String status = response.getStatusLine().toString();
					System.out.println(status);
					HttpEntity entity = response.getEntity();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent()));
					String output;
					System.err.println("Output from Server -> ");
					while ((output = br.readLine()) != null) {
						System.err.println(output);
					}
					entity.consumeContent();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	/**
	 * Retrieves a recipe object from its UUID from elasticSearch
	 */
	public void getRecipe(String uuid) {
		final String myUuid = uuid;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpGet getRequest = new HttpGet(BASEURL + myUuid
							+ "?pretty=1");// S4bRPFsuSwKUDSJImbCE2g?pretty=1

					getRequest.addHeader("Accept", "application/json");
					HttpResponse response = httpclient.execute(getRequest);
					String status = response.getStatusLine().toString();
					System.out.println(status);
					String json = getEntityContent(response);

					// We have to tell GSON what type we expect
					Type elasticSearchResponseType = 
							new TypeToken<ElasticSearchResponse<Recipe>>() {
					}.getType();
					// Now we expect to get a Recipe response
					ElasticSearchResponse<Recipe> esResponse = gson.fromJson(
							json, elasticSearchResponseType);
					// We get the recipe from it!
					Recipe recipe = esResponse.getSource();
					System.out.println(recipe.toString());

				} catch (ClientProtocolException e) {

					e.printStackTrace();

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * search by keywords
	 */
	public void searchRecipes(String query) {
		final String myQuery = query;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpGet searchRequest = new HttpGet(BASEURL
							+ "_search?pretty=1&q="
							+ java.net.URLEncoder.encode(myQuery, "UTF-8"));
					searchRequest.setHeader("Accept", "application/json");
					HttpResponse response = httpclient.execute(searchRequest);
					String status = response.getStatusLine().toString();
					System.out.println(status);

					String json = getEntityContent(response);

					Type elasticSearchSearchResponseType = 
							new TypeToken<ElasticSearchSearchResponse<Recipe>>() {
					}.getType();
					ElasticSearchSearchResponse<Recipe> esResponse = gson
							.fromJson(json, elasticSearchSearchResponseType);
					System.err.println(esResponse);
					for (ElasticSearchResponse<Recipe> r : esResponse.getHits()) {
						Recipe recipe = r.getSource();
						System.err.println(recipe);
						Log.wtf("recipe", recipe.toString());
					}
				} catch (IOException e) {
					
				}
			}
		}).start();
	}

	/**
	 * advanced search (logical operators)
	 */
	public void searchsearchRecipes(String str) throws ClientProtocolException,
			IOException {
		HttpPost searchRequest = new HttpPost(BASEURL + "_search?pretty=1");
		String query = "{\"query\" : {\"query_string\" : " +
				"{\"default_field\" : \"ingredients\",\"query\" : \""
				+ str + "\"}}}";
		StringEntity stringentity = new StringEntity(query);

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringentity);

		HttpResponse response = httpclient.execute(searchRequest);
		String status = response.getStatusLine().toString();
		System.out.println(status);

		String json = getEntityContent(response);

		Type elasticSearchSearchResponseType = 
				new TypeToken<ElasticSearchSearchResponse<Recipe>>() {
		}.getType();
		ElasticSearchSearchResponse<Recipe> esResponse = gson.fromJson(json,
				elasticSearchSearchResponseType);
		System.err.println(esResponse);
		for (ElasticSearchResponse<Recipe> r : esResponse.getHits()) {
			Recipe recipe = r.getSource();
			System.err.println(recipe);
		}
		// searchRequest.releaseConnection(); not available in android
		// httpclient
	}

	/**
	 * update a field in a recipe
	 */
	public void updateRecipes(String str) throws ClientProtocolException,
			IOException {
		HttpPost updateRequest = new HttpPost(BASEURL + "1/_update");
		String query = "{\"script\" : \"ctx._source." + str + "}";
		StringEntity stringentity = new StringEntity(query);

		updateRequest.setHeader("Accept", "application/json");
		updateRequest.setEntity(stringentity);

		HttpResponse response = httpclient.execute(updateRequest);
		String status = response.getStatusLine().toString();
		System.out.println(status);

		// String json = getEntityContent(response);
		// updateRequest.releaseConnection(); not available in android
		// httpclient
	}

	/**
	 * delete an entry specified by the id
	 */
	public void deleteRecipe() throws IOException {
		HttpDelete httpDelete = new HttpDelete(BASEURL + "1");
		httpDelete.addHeader("Accept", "application/json");

		HttpResponse response = httpclient.execute(httpDelete);

		String status = response.getStatusLine().toString();
		System.out.println(status);

		HttpEntity entity = response.getEntity();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				entity.getContent()));
		String output;
		System.err.println("Output from Server -> ");
		while ((output = br.readLine()) != null) {
			System.err.println(output);
		}
		// EntityUtils.consume(entity); not available in android httpclient
		entity.consumeContent();
		// httpDelete.releaseConnection(); not available in android httpclient
	}

	/**
	 * get the http response and return json string
	 */
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		System.err.println("Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:" + json);
		return json;
	}

}