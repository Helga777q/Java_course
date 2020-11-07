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
    //check that the contact exists in DB, if not - create the new one
    if (app.db().contacts().size()==0){
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Bing")
              .withAddress("Test Address vvvvvv  vcxvcxv vxc vc xvccvvvv                  vvvvvv"));
    }

  }


  @Test
  public void testContactAddress(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next(); //choose any contact from the table
    ContactData contactInfoFromEditPage = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAddress(), equalTo(cleaned(contactInfoFromEditPage.getAddress())));
  }

//trim address before and after, replaces >2 spaces with single space
  public String cleaned(String address){
    return address.trim().replaceAll(" +", " ");
  }


}
