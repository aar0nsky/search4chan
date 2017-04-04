package me.aar0nsky.search4Chan;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	//String myURL = "http://httpbin.org/get";
    	String myURL = "http://httpbin.org";
    	String searchTerm = "delete";
    	try {
    		getHTTP(myURL, searchTerm);
    	}
    	catch (IOException e) {
    		System.out.println("IOException occured");
    		e.printStackTrace(System.out);
    	}
    }

    private static void getHTTP(String getURL, String searchTerm) throws IOException {

    	CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpGet httpGet = new HttpGet(getURL);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);

            try {
                String status = response1.getStatusLine().toString();
                //System.out.println(status);

                if (status.indexOf("200") > 0) {
                	//System.out.println("HTTP Success!");
               		HttpEntity entity1 = response1.getEntity();
               		String pageBody = EntityUtils.toString(entity1);
              // 	System.out.println(pageBody);

               	if(pageBody.indexOf(searchTerm) > 0) {
               		System.out.println("This page contains: " + searchTerm);

               	}
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity1);
                }

            } finally {
                response1.close();
            }
            }
            finally {
            httpclient.close();
        }	
    }

}
