package addressbook.tests.model;

import java.util.Objects;

public class ContactData {
  private  int id;
  private  String firstName;
  private  String lastName;
  private  String address;
  private  String home;
  private String mobile;
  private String work;
  private  String email;
  private  String group;



  public ContactData withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ContactData withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData withHome(String homePhone) {
    this.home = homePhone;
    return this;
  }

  public ContactData withMobile(String mobilePhone) {
    this.mobile = mobilePhone;
    return this;
  }

  public ContactData withWork(String workPhone) {
    this.work = workPhone;
    return this;
  }


  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }




  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }

  public String getHome() {
    return home;
  }

  public String getMobile() { return mobile; }

  public String getWork() { return work; }

  public String getEmail() {
    return email;
  }

  public String getGroup() { return group; }






  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName);
  }
}
