package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void contactCreationPreconditions(){
    app.getGroupHelper().createGroupIfNotPresent(new GroupData(
            "Test",
            "test2-header",
            null));
  }


  @Test
  public void testContactCreationWithGroup() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().createContactWithGroup(new ContactData(
            "Monica1",
            "Geller",
            "New York, Central Perk 3",
            "+1555567888",
            "monica.geller@friends.com",
            "Test"));
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()+1);
  }

  @Test
  public void testContactCreationWithOutGroup(){
    List<ContactData> before = app.getContactHelper().getContactList();
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
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()+1);
  }



}