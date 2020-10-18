package addressbook.tests.appmanager;

import addressbook.tests.model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }


  public void selectGroupById(int id) {
    wd.findElement(By.cssSelector("input[value='"+id+"']")).click();
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

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  public void modify(GroupData group) {
    selectGroupById(group.getId());
    initGroupUpdate();
    fillGroupForm(group);
    submitGroupUpdate();
    returnToGroupPage();
  }


  public void delete(GroupData group) {
    selectGroupById(group.getId());
    deleteSelectedGroups();
    returnToGroupPage();
  }


  public void createIfNotPresent(GroupData group) {
    click(By.linkText("groups"));
    if (!isGroupPresent()) {
      create(group);
    }
    click(By.linkText("home"));
  }



  public Set<GroupData> all() {
    Set<GroupData> groups = new HashSet<GroupData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
    for (WebElement element:  elements ){
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      groups.add(new GroupData().withId(id).withName(name));
    }
    return groups;

  }



}
