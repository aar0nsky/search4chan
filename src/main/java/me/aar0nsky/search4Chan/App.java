package me.aar0nsky.search4Chan;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;

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
    	//String MYURL = "http://httpbin.org/get";
    	//public static final String MYURL = "http://httpbin.org";
    	//public static final String SEARCHTERM = "delete";

    	String MYURL = "http://boards.4chan.org/";
    	String SEARCHTERM = "delete";
    	String BOARD = "b";
    	Scanner scanzor = new Scanner(System.in);

    	System.out.println("Searching: " + MYURL);
    	System.out.println("Enter board: ");
    	BOARD = scanzor.nextLine();
    	System.out.println("Enter search term: ");
    	SEARCHTERM = scanzor.nextLine();

    	MYURL = MYURL + BOARD + "/";

    	for(int i = 0;i < 10;i++) {
    		
    		try {
    		searchPage(MYURL + i, SEARCHTERM);
    		}
    		catch (IOException e) {
    		System.out.println("IOException occured");
    		System.out.println("Search Term: " + SEARCHTERM);
    		System.out.println("URL: " + MYURL);
    		e.printStackTrace(System.out);
    		}

    	}
    	
    }

    private static void searchPage(String myURL, String searchTerm) throws IOException {

    	CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpGet httpGet = new HttpGet(myURL);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);

            try {
                String status = response1.getStatusLine().toString();
                //System.out.println(status);

                if (status.indexOf("200") > 0) {
                	//System.out.println("HTTP Success!");
               		HttpEntity entity1 = response1.getEntity();
               		// put page contents into a string
               		String pageBody = EntityUtils.toString(entity1);
              // 	System.out.println(pageBody);
               	// check if search term is on page
               	if(pageBody.indexOf(searchTerm) > 0) {
               		//System.out.println("This page contains: " + searchTerm);
               		// open url 
               		openBrowsermkyong(myURL);

               		// only works on windows?
               		// Desktop desktop = java.awt.Desktop.getDesktop();
               		// try {
               		// desktop.browse(convertURL(myURL));
               		// }
               		// catch (URISyntaxException e) {

               		// }
               		// ?? this method didnt work either
               		//Runtime.getRuntime().exec(myURL);
               	}
                // fully consume
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

    // public static URI convertURL(String URL) throws URISyntaxException {
    //            		URI myURI = new URI(URL);
    //            		return myURI;
    // }

    // taken from mkyong.com
    // after being frustrated with linux not opening URLs like windows
    public static void openBrowsermkyong (String url) {
    		
    		String os = System.getProperty("os.name").toLowerCase();
			Runtime rt = Runtime.getRuntime();

    		try{

	    if (os.indexOf( "win" ) >= 0) {

	        // this doesn't support showing urls in the form of "page.html#nameLink"
	        rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);

	    } else if (os.indexOf( "mac" ) >= 0) {

	        rt.exec( "open " + url);

            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {

	        // Do a best guess on unix until we get a platform independent way
	        // Build a list of browsers to try, in this order.
	        String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
	       			             "netscape","opera","links","lynx"};

	        // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
	        StringBuffer cmd = new StringBuffer();
	        for (int i=0; i<browsers.length; i++)
	            cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

	        rt.exec(new String[] { "sh", "-c", cmd.toString() });

           } else {
                return;
           }
       }catch (Exception e){
	    return;
       }
    }
}
