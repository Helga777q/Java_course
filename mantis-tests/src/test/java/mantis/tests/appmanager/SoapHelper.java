package mantis.tests.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import mantis.tests.model.Issue;
import mantis.tests.model.IssueStatus;
import mantis.tests.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {


  private ApplicationManager app;

  public SoapHelper(ApplicationManager app){
    this.app = app;
}


public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
  MantisConnectPortType mc = getMantisConnect();
  ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
  return Arrays.asList(projects).stream()
          .map((p)-> new Project().withId(p.getId().intValue()).withName(p.getName()))
          .collect(Collectors.toSet());
}

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    URL url = new URL(app.getProperty("soap.url"));
    return new MantisConnectLocator().getMantisConnectPort
            (url);
  }


  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getSummary());
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
    IssueData newIssueData = mc.mc_issue_get("administrator", "root", issueId);
    return new Issue()
            .withId(newIssueData.getId().intValue()).withSummary(newIssueData.getSummary()).withDescription(newIssueData.getDescription())
            .withProject(new Project().withId(newIssueData.getProject().getId().intValue()).withName(newIssueData.getProject().getName()));


  }

  public String getIssueStatus(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData issueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
    ObjectRef status = issueData.getStatus();
    String statusName = status.getName();
    //getStatusList();
    return statusName;
  }

  //get list of status for the bugtracker
  public void getStatusList() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    List<IssueStatus> issueStatusList = Arrays.asList(mc.mc_enum_status("administrator", "root")).stream()
            .map((s) -> new IssueStatus().withId(s.getId().intValue()).withStatus(s.getName()))
            .collect(Collectors.toList());
    for (IssueStatus status: issueStatusList){
      System.out.println(status.getStatus());
    }
  }



}
