package rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;
import java.util.List;

public class TestBase {


  boolean isIssueOpen(int issueId) throws IOException {
    if (getIssuesStateId(issueId) == 3) {
      return true;
    } else{
      return false;
    }
  }


  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public  int getIssuesStateId(int issueId) throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues/"+issueId+".json"))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json).getAsJsonObject();
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    List<Issue> issue = new Gson().fromJson(issues, new TypeToken<List<Issue>>(){}.getType());
    int stateId= Integer.parseInt(issue.get(0).getState());
    return stateId;
  }

  public  Issue getIssue (int issueId) throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues/"+issueId+".json"))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json).getAsJsonObject();
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    List<Issue> issue = new Gson().fromJson(issues, new TypeToken<List<Issue>>(){}.getType());
   Issue newIssue = issue.stream().iterator().next();
    return newIssue;
  }

  public Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

}
