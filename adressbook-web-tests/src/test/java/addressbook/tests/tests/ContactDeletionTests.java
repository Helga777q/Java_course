package addressbook.tests.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {


  @Test
  public void testContactDeletion (){
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
  }

  @Test
  public void testAllContactsDeletion (){
    app.getContactHelper().selectAllContacts();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptAlertContactsDeletion();
  }


  



}
