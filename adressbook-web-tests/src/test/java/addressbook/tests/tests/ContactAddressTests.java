package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

  // add preconditions - contact is created
  @BeforeMethod
  public  void contactCheckAddressPreconditions(){
    //check that the contact exists, if not - create the new one
    if (app.contact().all().size()==0){
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Bing").withGroup("[none]")
              .withAddress("Test Address"));
    }

  }


  @Test
  public void testContactAddress(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next(); //choose any contact from the table
    ContactData contactInfoFromEditPage = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditPage.getAddress()));

  }


}
