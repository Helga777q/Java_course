package mantis.tests.appmanager;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {

  private CloseableHttpClient httpClient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app) {
    this.app = app;
    httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
  }


  public boolean login(String username, String password) throws IOException {
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); //create POST request
    List<NameValuePair> params = new ArrayList<>(); // List of parameters of the request body
    params.add(new BasicNameValuePair("username", username));
    params.add(new BasicNameValuePair("password", password));
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));
    post.setEntity(new UrlEncodedFormEntity(params));
    CloseableHttpResponse response = httpClient.execute(post); //send POST request
    String body = getTextFrom(response); // get Text of the response
    return body.contains(String.format
            ("<span class=\"user-info\">%s</span>", username)); //check that the response has specific text

  }

  private String getTextFrom(CloseableHttpResponse response) throws IOException {
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }

  //checks what user is logged currently in the system
  public boolean isLoggedInAs(String username) throws IOException {
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php"); // create Get request (main page)
    CloseableHttpResponse response = httpClient.execute(get); // send GET request
    String body = getTextFrom(response); // get Text of the response
    return body.contains(String.format
            ("<span class=\"user-info\">%s</span>", username)); //check that the response has specific text on the page
  }


}
