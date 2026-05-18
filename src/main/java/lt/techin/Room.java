package lt.techin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
  private static final long serialVersionUID = 1L;
  private final int number;
  private  double price;
  private Guest currentGuest;
  private final List<Guest> guestHistory = new ArrayList<>();
  private int timesOccupied = 0;


  public Room(int number, double price) {
    this.number = number;
    this.price = price;
  }

  public int getNumber() {
    return number;
  }

  public double getPrice() {
    return price;
  }

  public Guest getCurrentGuest() {
    return currentGuest;
  }

  public void setCurrentGuest(Guest currentGuest) {
    this.currentGuest = currentGuest;
  }

  public List<Guest> getGuestHistory() {
    return guestHistory;
  }

  public boolean isOccupied() {
    return currentGuest != null;
  }

  public List<Guest> getHistory() {
    return guestHistory;
  }

  public int getTimesOccupied() {
    return timesOccupied;
  }

  public void checkIn(Guest guest) {
    this.currentGuest = guest;
    guestHistory.add(guest);
    timesOccupied++;
  }

  public void checkOut() {
    this.currentGuest = null;
  }

  public double getTotalProfit() {
    return timesOccupied * price;
  }

  public String type() {
    return "Standard";
  }
}