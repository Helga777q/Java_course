package addressbook.tests.appmanager;

import addressbook.tests.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
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

  public void submitContactForm() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
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


  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptAlertContactsDeletion() {
    wd.switchTo().alert().accept();
  }

  public void selectAllContacts() {
    click(By.id("MassCB"));
  }

  public void goToContactEditPage(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void deleteContactFromEditPage() {
    click(By.xpath("(//input[@name='update'])[3]"));
  }

  public void openContactDetailsPage(int index) {

    wd.findElements(By.xpath("//img[@alt='Details']")).get(index).click();

  }

  public void clickContactModifyButton() {
    click(By.name("modifiy"));
  }

  public void submitContactUpdate() {
    click(By.xpath("(//input[@name='update'])"));
  }


  public void createContactWithGroup(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactForm();
    returnToHomePage();
  }


  public boolean isContactPresent() {
    return isElementPresent(By.name("selected[]"));
  }

  public void contactPreConditions(ContactData contact) {
    if (!isContactPresent()) {
      createContactWithGroup(contact);
    }
  }

  //counts quantity of Contacts
  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void waitForHomePageOpens(){
    waitForPresenceOfElement(15, (By.id("maintable")));
  }



  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//*[@name='entry']"));
    for (WebElement element : elements) {
      String firstName = element.findElement(By.cssSelector("td:nth-of-type(3)")).getText();
      String lastName = element.findElement(By.cssSelector("td:nth-of-type(2)")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
    }
    return contacts;
  }

  public void modifyContact(int index, ContactData contact, String pageType) {
    switch (pageType) {
      case "edit": //edit Page
        goToContactEditPage(index);
        break;
      case "details"://modify Page
        openContactDetailsPage(index);
        clickContactModifyButton();
        break;
    }
    fillContactForm(contact, false);
    submitContactUpdate();
    returnToHomePage();
  }


  public void deleteContacts(int index, String pageType){

    switch (pageType){
      case "home":
        selectContact(index);
        deleteSelectedContacts();
        acceptAlertContactsDeletion();
        break;
      case "edit":
        goToContactEditPage(index);
        deleteContactFromEditPage();
        break;
      case "details":
        openContactDetailsPage(index);
        clickContactModifyButton();
        deleteContactFromEditPage();
        break;
      case "homeAll":
        selectAllContacts();
        deleteSelectedContacts();
        acceptAlertContactsDeletion();
        break;
    }
    waitForHomePageOpens();

  }


}
