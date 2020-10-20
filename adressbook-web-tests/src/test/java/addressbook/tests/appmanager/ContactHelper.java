package addressbook.tests.appmanager;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class ContactHelper extends BaseHelper {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home page"));
  }

  public void submitForm() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }


  public void initCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }
  public void goToDetailsPage(int index) { wd.findElements(By.xpath("//img[@alt='Details']")).get(index).click(); }
  public void goToEditPage(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void deleteSelected() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptAlertContactsDeletion() {
    wd.switchTo().alert().accept();
  }

  public void selectAllContacts() {
    click(By.id("MassCB"));
  }


  public void deleteFromEditPage() {
    click(By.xpath("(//input[@name='update'])[3]"));
  }



  public void clickContactModifyButton() {
    click(By.name("modifiy"));
  }

  public void submitUpdate() {
    click(By.xpath("(//input[@name='update'])"));
  }


  public void create(ContactData contact) {
    initCreation();
    fillForm(contact, true);
    submitForm();
    contactsCache=null;
    returnToHomePage();
  }


  public boolean isContactPresent() {
    return isElementPresent(By.name("selected[]"));
  }

  public void contactPreConditions(ContactData contact) {
    if (!isContactPresent()) {
      create(contact);
    }
  }

  //counts quantity of Contacts
  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void waitForHomePageOpens(){
    waitForPresenceOfElement(15, (By.id("maintable")));
  }


  private Contacts contactsCache= null;

  public Contacts all() {
    if (contactsCache!=null){
      return new Contacts(contactsCache);
    }
   contactsCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//*[@name='entry']"));
    for (WebElement element : elements) {
      String firstName = element.findElement(By.cssSelector("td:nth-of-type(3)")).getText();
      String lastName = element.findElement(By.cssSelector("td:nth-of-type(2)")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactsCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
    }
    return new Contacts(contactsCache); //return the copy of the contacts list
  }



  public void modify(ContactData contact, String pageType) {
    switch (pageType) {
      case "edit": //edit Page
        goToEditPageById(contact.getId());
        break;
      case "details"://modify Page
        goToDetailsPageById(contact.getId());
        clickContactModifyButton();
        break;
    }
    fillForm(contact, false);
    submitUpdate();
    contactsCache=null;
    returnToHomePage();
  }



  public void deleteAll(){
    selectAllContacts();
    deleteSelected();
    acceptAlertContactsDeletion();
    contactsCache=null;
    waitForHomePageOpens();
  }


  public void delete(ContactData contact, String pageType){

    switch (pageType){
      case "home": // home page main
        selectContactById(contact.getId());
        deleteSelected();
        acceptAlertContactsDeletion();
        break;
      case "edit": //edit page of the contact
        goToEditPageById(contact.getId());
        deleteFromEditPage();
        break;
      case "details": //contact details page (modify)
        goToDetailsPageById(contact.getId());
        clickContactModifyButton();
        deleteFromEditPage();
        break;

    }
    contactsCache=null;
    waitForHomePageOpens();

  }

  private void goToDetailsPageById(int id) {
    wd.findElement(By.cssSelector("a[href='view.php?id="+id+"']")).click();

  }

  private void goToEditPageById(int id) {
    wd.findElement(By.cssSelector("a[href='edit.php?id="+id+"']")).click();

  }

  private void selectContactById(int id) {

    wd.findElement(By.cssSelector("input[value='"+id+"']")).click();
  }


}
