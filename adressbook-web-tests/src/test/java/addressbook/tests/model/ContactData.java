package addressbook.tests.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.Objects;

@XStreamAlias("contact")

@Entity
@Table(name = "addressbook")
public class ContactData {
  @XStreamOmitField
  @Id
  @Column(name= "id")
  private  int id;

  @Expose
  @Column(name="firstname")
  private  String firstName;

  @Expose
  @Column(name="lastname")
  private  String lastName;

  @Expose
  @Column(name="address")
  @Type(type = "text")
  private  String address;

  @Column(name="home")
  @Type(type = "text")
  private  String home;

  @Expose
  @Column(name="mobile")
  @Type(type = "text")
  private String mobile;

  @Column(name="work")
  @Type(type = "text")
  private String work;

  @Expose

  @Column(name="email")
  @Type(type = "text")
  private  String email;

  @Column(name="email2")
  @Type(type = "text")
  private String email2;

  @Column(name="email3")
  @Type(type = "text")
  private String email3;

  @Transient
  private  String group;

  @Transient
  private String allPhones;

  @Transient
  private String allEmails;

  @Column(name="photo")
  @Type(type = "text")
  private String photo;

  @Expose
  @Column(name="bday", columnDefinition = "tinyint")
  private String birthDate;

  @Expose
  @Column(name="bmonth")
  private String birthMonth;

  @Expose
  @Column(name="byear")
  private String birthYear;




  public ContactData withBirthDate(String birthDate) {
    this.birthDate= birthDate;
    return this;
  }



  public ContactData withBirthMonth(String birthMonth) {
    this.birthMonth = birthMonth;
    return this;
  }



  public ContactData withBirthYear(String birthYear) {
    this.birthYear = birthYear;
    return this;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }





  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;

  }


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

  public ContactData withAllEmails(String email) {
    this.allEmails = email;
    return this;
  }

  public ContactData withEmailSecond(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmailThird(String email3) {
    this.email3 = email3;
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
  public String getEmail2() { return email2; }
  public String getEmail3() {return email3; }
  public String getGroup() { return group; }
  public String getAllPhones() { return allPhones; }

  public String getAllEmails() { return allEmails; }

  public File getPhoto() {
    if (photo != null) {
      return new File(photo);
    } else {
      return null;
    }
  }

  public String getBirthMonth() {
    return birthMonth;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public String getBirthYear() {
    return birthYear;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", address='" + address + '\'' +
            ", home='" + home + '\'' +
            ", mobile='" + mobile + '\'' +
            ", work='" + work + '\'' +
            ", email='" + email + '\'' +
            ", email2='" + email2 + '\'' +
            ", email3='" + email3 + '\'' +
            ", group='" + group + '\'' +
            ", photo='" + photo + '\'' +
            ", birthDate='" + birthDate + '\'' +
            ", birthMonth='" + birthMonth + '\'' +
            ", birthYear='" + birthYear + '\'' +
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
