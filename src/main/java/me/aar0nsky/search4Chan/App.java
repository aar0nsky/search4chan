package me.aar0nsky.search4Chan;

import java.util.Scanner;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	String myURL = "http://boards.4chan.org/";
    	String searchTerm = "delete";
    	String boardName = "b";
    	Scanner scanzor = new Scanner(System.in);

    	System.out.println("Searching: " + myURL);
    	System.out.println("Enter board: ");
    	boardName = scanzor.nextLine();
    	System.out.println("Enter search term: ");
    	searchTerm = scanzor.nextLine();
    	scanzor.close();
    	
    	myURL = myURL + boardName + "/";

    	for(int i = 0;i < 10;i++) {
    		
    		try {
    		searchPage(myURL + i, searchTerm);
    		}
    		catch (IOException e) {
    		System.out.println("IOException occured");
    		System.out.println("Search Term: " + searchTerm);
    		System.out.println("URL: " + myURL);
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
                // check if status is 200
                if (status.indexOf("200") > 0) {
               		HttpEntity entity1 = response1.getEntity();
               		// put page contents into a string
               		String pageBody = EntityUtils.toString(entity1);
               	// check if search term is on page
               	if(pageBody.indexOf(searchTerm) > 0) {
               		// open url in browser
               		openBrowser(myURL);
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

    // taken from mkyong.com
    // after being frustrated with linux not opening URLs like windows
    public static void openBrowser (String url) {
    		
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
		        String[] browsers = {"epiphany", "firefox", "mozilla", "google-chrome", "chromium", "konqueror",
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
