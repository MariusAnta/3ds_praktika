package lt.techin;

import java.util.Scanner;
import java.io.*;
import java.util.List;

public class HotelApp {
  private static int totalRooms = 6;
  private static double basePrice = 20.0;
  private static final String saveFile = "hotel.dat";

  public static void main(String[] args) {
    Hotel hotel = loadHotel();
    Scanner sc = new Scanner(System.in);

    while (true) {
      printMenu();
      String choice = sc.nextLine().trim();
      switch (choice) {
        case "1" -> registerGuest(sc, hotel);
        case "2" -> checkOutGuest(sc, hotel);
        case "3" -> showOccupied(hotel);
        case "4" -> showHistory(hotel);
        case "5" -> showReport(hotel);
        case "6" -> adjustPrice(sc);
        case "7" -> adjustRooms(sc);
        case "0" -> {
          saveHotel(hotel);
          System.out.println("Bye!");
          return;
        }
        default -> System.out.println("Unknown option.");
      }
    }
  }

  private static void saveHotel(Hotel hotel) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
      oos.writeObject(hotel.getRooms());
      System.out.println("Data saved.");
    } catch (IOException e) {
      System.out.println("Failed to save data: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private static Hotel loadHotel() {
    File file = new File(saveFile);
    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        List<Room> rooms = (List<Room>) ois.readObject();
        System.out.println("Data loaded.");
        return new Hotel(rooms);
      } catch (IOException | ClassNotFoundException e) {
        System.out.println("Failed to load data, starting fresh.");
      }
    }
    return new Hotel(totalRooms, basePrice);
  }

  private static void printMenu() {
    System.out.println("""
            ===== HOTEL MENU =====
            1. Register guest
            2. Check out guest
            3. Show occupied rooms
            4. Room history & status
            5. Occupancy report
            6. Adjust price
            7. Adjust rooms
            Choose:
            """);
  }

  private static void registerGuest(Scanner sc, Hotel hotel) {
    System.out.print("Name: ");
    String name = sc.nextLine().trim();
    System.out.print("Surname: ");
    String surname = sc.nextLine().trim();

    hotel.registerGuest(new Guest(name, surname))
            .ifPresentOrElse(
                    r -> System.out.printf("Registered in room %d (%s, %.2f eur)%n",
                            r.getNumber(), r.type(), r.getPrice()),
                    () -> System.out.println("Registration failed: no free rooms.")
            );
  }

  private static void checkOutGuest(Scanner sc, Hotel hotel) {
    System.out.print("Room number to check out: ");
    try {
      int n = Integer.parseInt(sc.nextLine().trim());
      System.out.println(hotel.checkOut(n)
              ? "Checked out successfully."
              : "Room not found or already free.");
    } catch (NumberFormatException e) {
      System.out.println("Invalid number.");
    }
  }

  private static void showOccupied(Hotel hotel) {
    var occ = hotel.getOccupiedRooms();
    if (occ.isEmpty()) {
      System.out.println("No occupied rooms.");
      return;
    }
    occ.forEach(r -> System.out.printf("Room %d (%s) - %s%n",
            r.getNumber(), r.type(), r.getCurrentGuest()));
  }

  private static void showHistory(Hotel hotel) {
    for (Room r : hotel.getRooms()) {
      System.out.printf("Room %d (%s) - %s%n",
              r.getNumber(), r.type(),
              r.isOccupied() ? "OCCUPIED" : "FREE");
      if (r.getHistory().isEmpty()) {
        System.out.println("  (no guests yet)");
      } else {
        r.getHistory().forEach(g -> System.out.println("  - " + g));
      }
    }
  }

  private static void showReport(Hotel hotel) {
    System.out.println("Room | Type     | Times | Profit (eur)");
    hotel.getOccupancyReport().forEach(r ->
            System.out.printf("%4d | %-8s | %5d | %.2f%n",
                    r.getNumber(), r.type(),
                    r.getTimesOccupied(), r.getTotalProfit()));
  }

  private static void adjustPrice(Scanner sc) {
    System.out.print("Base price: ");
    try {
      double p = Double.parseDouble(sc.nextLine().trim());
      basePrice = p;
      System.out.println("Base price adjusted.");
    } catch (NumberFormatException e) {
      System.out.println("Invalid number.");
    }
  }

  private static void adjustRooms(Scanner sc) {
    System.out.print("Total rooms: ");
    try {
      int r = Integer.parseInt(sc.nextLine().trim());
      totalRooms = r;
      System.out.println("Total rooms adjusted.");
    } catch (NumberFormatException e) {
      System.out.println("Invalid number.");
    }
  }
}