package rest;

import java.util.Objects;

public class Issue {

  private int id;
  private String subject;
  private String description;
  private String state;
  private String state_name;


  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public Issue withSubject(String subject) {
    this.subject = subject;
    return this;

  }

  public Issue withStateName(String state_name) {
    this.state_name = state_name;
    return this;

  }


  public Issue withState(String state) {
    this.state = state;
    return this;

  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;

  }

  public int getId() {
    return id;
  }

  public String getSubject() {
    return subject;
  }

  public String getState() {
    return state;
  }

  public String getDescription() {
    return description;
  }

  public String getStateName() {
    return state_name;
  }

  @Override
  public String toString() {
    return "Issue{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", description='" + description + '\'' +
            ", state='" + state + '\'' +
            ", stateName='" + state_name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Issue issue = (Issue) o;
    return id == issue.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
