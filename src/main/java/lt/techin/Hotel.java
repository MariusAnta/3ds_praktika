package lt.techin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Hotel {
  private final List<Room> rooms = new ArrayList<>();

  public Hotel(int totalRooms, double basePrice) {
    if (totalRooms % 2 != 0)
      throw new IllegalArgumentException("Total rooms must be even");

    int half = totalRooms / 2;
    for (int i = 1; i <= half; i++) {
      rooms.add(new Room(i, basePrice));
    }
    for (int i = half + 1; i <= totalRooms; i++) {
      rooms.add(new BusinessRoom(i, basePrice));
    }
  }

  public Hotel(List<Room> rooms) {
    if (rooms == null) {
      throw new IllegalArgumentException("Rooms list cannot be null");
    }
    this.rooms.addAll(rooms);
  }

  public List<Room> getRooms() {
    return rooms;
  }

  /**
   * Registers a guest in the first free room. Returns the room or empty.
   */
  public Optional<Room> registerGuest(Guest guest) {
    return rooms.stream()
            .filter(r -> !r.isOccupied())
            .findFirst()
            .map(r -> {
              r.checkIn(guest);
              return r;
            });
  }

  /**
   * Checks out the guest in given room number. Returns true on success.
   */
  public boolean checkOut(int roomNumber) {
    return rooms.stream()
            .filter(r -> r.getNumber() == roomNumber && r.isOccupied())
            .findFirst()
            .map(r -> {
              r.checkOut();
              return true;
            })
            .orElse(false);
  }

  public List<Room> getOccupiedRooms() {
    return rooms.stream().filter(Room::isOccupied).toList();
  }

  /**
   * Rooms sorted by times occupied DESC.
   */
  public List<Room> getOccupancyReport() {
    List<Room> sorted = new ArrayList<>(rooms);
    sorted.sort(Comparator.comparingInt(Room::getTimesOccupied).reversed());
    return sorted;
  }

}
