package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void checkIfGroupExists(){
    app.getGroupHelper().createGroupIfNotPresent(new GroupData(
            "Test",
            "test2-header",
            null));
  }


  @Test
  public void testContactCreationWithGroup() throws Exception {
    app.getContactHelper().createContactWithGroup(new ContactData(
            "Monica1",
            "Geller",
            "New York, Central Perk 3",
            "+1555567888",
            "monica.geller@friends.com",
            "Test"));
  }

  @Test
  public void testContactCreationWithOutGroup(){
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData(
            "Monica3",
            "Gekker",
            "Lon",
            "1",
            "hhhh",
            "[none]"), true);
    app.getContactHelper().submitContactForm();
    app.getNavigationHelper().goToHomePage();
  }

}