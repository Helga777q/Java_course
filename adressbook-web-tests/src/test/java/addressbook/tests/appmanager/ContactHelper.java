package addressbook.tests.appmanager;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    type(By.name("home"), contactData.getHome());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWork());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    attach(By.name("photo"), contactData.getPhoto());
    selectFromDropDown(By.name("bday"), contactData.getBirthDate());
    selectFromDropDown(By.name("bmonth"), contactData.getBirthMonth());
    type(By.name("byear"), contactData.getBirthYear());

  if (creation) {
    if (contactData.getGroups().size()>0){
      Assert.assertTrue(contactData.getGroups().size()==1);
      //new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      selectFromDropDown(By.name("new_group"), contactData.getGroups().iterator().next().getName());
    }
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

  public boolean isPhotoPresent(){
    return isElementPresent(By.tagName("img"));
  }

  public void contactPreConditions(ContactData contact) {
    if (!isContactPresent()) {
      create(contact);
    }
  }

  //counts quantity of Contacts
  public int count() {
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
      String allPhones =  element.findElement(By.cssSelector("td:nth-of-type(6)")).getText();
      String address = element.findElement(By.cssSelector("td:nth-of-type(4)")).getText();
      //String[] phones = element.findElement(By.cssSelector("td:nth-of-type(6)")).getText().split("\n");
      //String[] emails = element.findElement(By.cssSelector("td:nth-of-type(5)")).getText().split("\n");
      String allEmails = element.findElement(By.cssSelector("td:nth-of-type(5)")).getText();
      contactsCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName)
              .withAllPhones(allPhones).withAddress(address)
              .withAllEmails(allEmails));
              //.withEmail(emails[0]).withEmailSecond(emails[1]).withEmailThird(emails[2])
              //.withHome(phones[0]).withMobile(phones[1]).withWork(phones[2]));
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

  public void goToDetailsPageById(int id) {
    wd.findElement(By.cssSelector("a[href='view.php?id="+id+"']")).click();

  }

  public void goToEditPageById(int id) {
   // wd.findElement(By.cssSelector("a[href='edit.php?id="+id+"']")).click();
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();

  }

  public void selectContactById(int id) {

    wd.findElement(By.cssSelector("input[value='"+id+"']")).click();
  }


  public ContactData infoFromEditForm(ContactData contact){
    goToEditPageById(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName).withAddress(address)
            .withHome(home).withMobile(mobile).withWork(work)
            .withEmail(email).withEmailSecond(email2).withEmailThird(email3);
  }

}
