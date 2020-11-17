package rest;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase {


@Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New 12345678");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  @Test
  public void testIssues() throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json).getAsJsonObject();
    JsonElement issues = parsed.getAsJsonObject().get("issues");
  }


  private Set<Issue> getIssues() throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json).getAsJsonObject();
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }




  private int createIssue(Issue newIssue) throws IOException {
    String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
    .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
            new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json).getAsJsonObject();
    return parsed.getAsJsonObject().get("issue_id").getAsInt();

  }




  private void updateIssueStatus(int issueId) throws IOException {
    String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues/"+issueId+".json")
            .bodyForm(new BasicNameValuePair("method", "update"),
            new BasicNameValuePair("issue[state]", "3"))).returnContent().asString();
  }




  @Test
  public void checkIssueStateSkipTest() throws IOException {
    skipIfNotFixed(388);
    Issue issue = getIssue(387);
    System.out.println(issue.getStateName() + " "+ issue.getId()+ " " + issue.getDescription() + " " + issue.getSubject());
  }


}
