package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public  void contactDeletionPreconditions(){
    //check that the contact exists, if not - create the new one
    if (app.contact().all().size()==0){
      app.contact().create(new ContactData()
      .withFirstName("Monica").withLastName("Bing").withAddress("testadd").withEmail("mon@friends.com").withHomePhone("+1111").withGroup("[none]")
      );
    }

  }


  @Test
  public void testContactDeletionHomePage() throws Exception {
   Contacts before = app.contact().all();
   ContactData deletedContact = before.iterator().next();
   app.contact().delete(deletedContact, "home");
   Contacts after = app.contact().all();
   assertThat(after.size(), equalTo(before.size()-1));
   assertThat(after, equalTo(before.without(deletedContact)));


  }
  @Test
  public void testContactDeletionEditPage() throws Exception {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact, "edit");
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()-1));
    assertThat(after, equalTo(before.without(deletedContact)));
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact, "details");
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()-1));
    assertThat(after, equalTo(before.without(deletedContact)));
  }


  @Test (enabled = false)
  public void testAllContactsDeletionHome() throws Exception {
    app.contact().deleteAll();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(0));
  }

}
