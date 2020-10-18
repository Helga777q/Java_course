package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public  void contactDeletionPreconditions(){
    //check that the contact exists, if not - create the new one
    if (app.contact().list().size()==0){
      app.contact().create(new ContactData()
      .withFirstName("Monica").withLastName("Bing").withAddress("testadd").withEmail("mon@friends.com").withHomePhone("+1111").withGroup("[none]")
      );
    }

  }


  @Test
  public void testContactDeletionHomePage() throws Exception {
   Set<ContactData> before = app.contact().all();
   ContactData deletedContact = before.iterator().next();
   app.contact().delete(deletedContact, "home");
   Set<ContactData> after = app.contact().all();
   assertEquals(after.size(), before.size()-1);
   before.remove(deletedContact);
   assertEquals(after, before);

  }
  @Test
  public void testContactDeletionEditPage() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact, "edit");
    Set<ContactData> after = app.contact().all();
    assertEquals(after.size(), before.size()-1);
    before.remove(deletedContact);
    assertEquals(after, before);
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact, "details");
    Set<ContactData> after = app.contact().all();
    assertEquals(after.size(), before.size()-1);
    before.remove(deletedContact);
    assertEquals(after, before);
  }


  @Test
  public void testAllContactsDeletionHome() throws Exception {
    app.contact().deleteAll();
    Set<ContactData> after = app.contact().all();
    assertEquals(after.size(), 0);
  }

}
