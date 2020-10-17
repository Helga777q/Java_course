package addressbook.tests.appmanager;

import addressbook.tests.model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import sun.plugin2.util.BrowserType;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends BaseHelper {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }


  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.xpath("(//input[@name='delete'])[2]"));
  }

  public void selectGroup(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void initGroupUpdate() {
    click((By.name("edit")));
  }


  public void submitGroupUpdate() {
    click(By.name("update"));
  }

  public boolean isGroupPresent() {
    return isElementPresent(By.name("selected[]"));
  }

  public void createGroup(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  public void modifyGroup(int index, GroupData group) {
    selectGroup(index);
    initGroupUpdate();
    fillGroupForm(group);
    submitGroupUpdate();
    returnToGroupPage();
  }


  public void deleteGroup(int index) {
    selectGroup(index);
    deleteSelectedGroups();
    returnToGroupPage();
  }


  public void createGroupIfNotPresent(GroupData group) {
    click(By.linkText("groups"));
    if (!isGroupPresent()) {
      createGroup(group);
    } else {
      click(By.linkText("home"));
    }
  }

  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<GroupData> getGroupList() {
    List<GroupData> groups = new ArrayList<>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));

    for (WebElement element:  elements ){
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      GroupData group = new GroupData (id, name, null, null);
      groups.add(group);
    }
    return groups;

  }
}
