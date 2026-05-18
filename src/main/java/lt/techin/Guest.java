package lt.techin;

import java.io.Serializable;

public class Guest implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String name;
  private final String surname;

  public Guest(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  @Override
  public String toString() {
    return name + " " + surname;
  }

}
