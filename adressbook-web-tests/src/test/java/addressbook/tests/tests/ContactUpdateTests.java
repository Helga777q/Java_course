package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class ContactUpdateTests extends TestBase {

  @BeforeMethod
  public void contactUpdatePreconditions() {
    if (app.contact().all().size()==0){
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Bing").withAddress("testadd").withEmail("mon@friends.com").withHomePhone("+1111").withGroup("[none]")
      );
    }
  }


  @Test
  public void testContactUpdateEditPage() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData updatedGroup = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(updatedGroup.getId()).withFirstName("New first NAme333").withLastName("New LastNAme").withAddress("New address").withHomePhone("+136456634").withEmail("Test@email.com");
    app.contact().modify(contact, "edit");
    Set<ContactData> after = app.contact().all();
    assertEquals(after.size(), before.size());
    before.remove(updatedGroup);
    before.add(contact);
    assertEquals(before, after);
  }

  @Test
  public void testContactUpdateDetailsPage() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData updatedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(updatedContact.getId()).withFirstName("New first NAme111").withLastName("New LastNAme").withAddress("New address").withHomePhone("+136456634").withEmail("Test@email.com");
    app.contact().modify(contact, "details");
    Set<ContactData> after = app.contact().all();
    assertEquals(after.size(), before.size());
    before.remove(updatedContact);
    before.add(contact);
    assertEquals(before, after);

  }
}
