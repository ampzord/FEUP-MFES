
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Route {
  private Number id;
  private Number duration;
  private Location destination;
  private Transport transport;
  private Number price;

  public void cg_init_Route_1(
      final Number i,
      final Number dur,
      final Location dest,
      final Transport transp,
      final Number pric) {

    id = i;
    duration = dur;
    destination = dest;
    transport = transp;
    price = pric;
    return;
  }

  public Route(
      final Number i,
      final Number dur,
      final Location dest,
      final Transport transp,
      final Number pric) {

    cg_init_Route_1(i, dur, dest, transp, pric);
  }

  public Number getID() {

    return id;
  }

  public Number getDuration() {

    return duration;
  }

  public Location getDestination() {

    return destination;
  }

  public Transport getTransport() {

    return transport;
  }

  public Number getPrice() {

    return price;
  }

  public void setDuration(final Number dur) {

    duration = dur;
  }

  public void setPrice(final Number p) {

    price = p;
  }

  public Route() {}

  public String toString() {

    return "Route{"
        + "id := "
        + Utils.toString(id)
        + ", duration := "
        + Utils.toString(duration)
        + ", destination := "
        + Utils.toString(destination)
        + ", transport := "
        + Utils.toString(transport)
        + ", price := "
        + Utils.toString(price)
        + "}";
  }
}
