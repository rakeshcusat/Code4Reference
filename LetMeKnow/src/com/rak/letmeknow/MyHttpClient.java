package com.rak.letmeknow;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class MyHttpClient extends AsyncTask<String, Integer, String> {

	private HttpClient http_client;
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds

	private ProgressDialog dialog;
	private ActivityInterface ctx;

	public MyHttpClient(ActivityInterface ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		
		dialog = new ProgressDialog((Context)ctx);
		dialog.setMessage("Loading ....");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
	}
	@Override
	protected String doInBackground(String... params) {
		
		String result = executeHttpPost(params );
		return result;
	}

	@Override
	protected void onPostExecute(String e){
		dialog.dismiss();
		ctx.onData(e);
	}


	private HttpClient getHttpClient() {
		if (http_client == null) {
			http_client = new DefaultHttpClient();
			final HttpParams params = http_client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return  http_client;
	}

	/**
	 * Performs an HTTP Post request to the specified url with the
	 * specified parameters.
	 *
	 * @param url The web address to post the request to
	 * @param postParameters The parameters to send via the request
	 * @return The result of the request
	 * @throws Exception
	 */
	public  String executeHttpPost(String... params){


		String result = null;
		try {
			String url = params[0];
			JSONObject jobj = new JSONObject(params[1]);
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			HttpResponse response;
			
			request.setEntity(new ByteArrayEntity( (jobj.toString()).getBytes("UTF8")));
			response = client.execute(request);

			if(response!=null){
				result = EntityUtils.toString(response.getEntity()); 
			}
		} 
		catch(Exception e){
			e.printStackTrace();
		}
		return result;

	}
}