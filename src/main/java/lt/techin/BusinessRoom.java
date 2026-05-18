package lt.techin;

import java.io.Serializable;

public class BusinessRoom extends Room implements Serializable {
  private static final long serialVersionUID = 1L;

  public BusinessRoom(int number, double price) {
    super(number, price * 1.5);
  }

  @Override
  public String type() {
    return "Business";
  }


}
