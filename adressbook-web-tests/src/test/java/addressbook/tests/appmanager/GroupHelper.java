package addressbook.tests.appmanager;

import addressbook.tests.model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import sun.plugin2.util.BrowserType;

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

  public void selectGroup() {
    click(By.name("selected[]"));
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


  public void createGroupIfNotPresent(GroupData group) {
    click(By.linkText("groups"));
    if (!isGroupPresent()) {
      createGroup(group);
    } else {
      click(By.linkText("home"));
    }
  }
}
