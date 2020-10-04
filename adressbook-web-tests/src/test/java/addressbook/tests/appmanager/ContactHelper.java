package addressbook.tests.appmanager;

import addressbook.tests.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class ContactHelper extends BaseHelper {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToHomePage() {
    if (isElementPresent(By.id("maintable"))){
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

    if (creation){
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact() {
    click(By.name("selected[]"));
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

  public void goToContactEditPage() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void deleteContactFromEditPage() {
    click(By.xpath("(//input[@name='update'])[3]"));
  }

  public void openContactDetailsPage() {
    click(By.xpath("//img[@alt='Details']"));
  }

  public void clickContactModifyButton() {
    click(By.name("modifiy"));
  }

  public void submitContactUpdate() {
    click(By.xpath("(//input[@name='update'])"));
  }


 public void createContactWithGroup(ContactData contact){
   initContactCreation();
   fillContactForm(contact, true);
   submitContactForm();
   returnToHomePage();
 }


 public boolean isContactPresent(){
    return isElementPresent(By.name("selected[]"));
 }

 public void contactPreConditions (ContactData contact ){
   if (!isContactPresent()){
     createContactWithGroup(contact);
   }
 }









}
