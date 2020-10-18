package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
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
    Contacts before = app.contact().all();
    ContactData updatedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(updatedContact.getId()).withFirstName("New first NAme333").withLastName("New LastNAme").withAddress("New address").withHomePhone("+136456634").withEmail("Test@email.com");
    app.contact().modify(contact, "edit");
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(updatedContact).withAdded(contact)));
  }

  @Test
  public void testContactUpdateDetailsPage() throws Exception {
    Contacts before = app.contact().all();
    ContactData updatedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(updatedContact.getId()).withFirstName("New first NAme111").withLastName("New LastNAme").withAddress("New address").withHomePhone("+136456634").withEmail("Test@email.com");
    app.contact().modify(contact, "details");
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(updatedContact).withAdded(contact)));

  }
}
