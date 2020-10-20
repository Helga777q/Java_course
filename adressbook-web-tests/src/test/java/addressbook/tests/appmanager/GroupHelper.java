package addressbook.tests.appmanager;

import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    groupsCache=null;
    returnToGroupPage();
  }

  public void modify(GroupData group) {
    selectGroupById(group.getId());
    initGroupUpdate();
    fillGroupForm(group);
    submitGroupUpdate();
    groupsCache=null;
    returnToGroupPage();
  }


  public void delete(GroupData group) {
    selectGroupById(group.getId());
    deleteSelectedGroups();
    groupsCache=null;
    returnToGroupPage();
  }


  public void createIfNotPresent(GroupData group) {
    click(By.linkText("groups"));
    if (!isGroupPresent()) {
      create(group);
    }
    click(By.linkText("home"));
  }


private  Groups groupsCache = null;

  public Groups all() {
    if (groupsCache != null){
      return new Groups(groupsCache);
    }
    groupsCache  = new Groups();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
    for (WebElement element:  elements ){
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      groupsCache.add(new GroupData().withId(id).withName(name));
    }
    return new Groups(groupsCache); // return the copy of the groups lists

  }



}
