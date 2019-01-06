
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Transport {
  private Number id;
  private String name;

  public void cg_init_Transport_1(final Number i, final String n) {

    id = i;
    name = n;
    return;
  }

  public Transport(final Number i, final String n) {

    cg_init_Transport_1(i, n);
  }

  public Number getID() {

    return id;
  }

  public String getName() {

    return name;
  }

  public Transport() {}

  public String toString() {

    return "Transport{" + "id := " + Utils.toString(id) + ", name := " + Utils.toString(name) + "}";
  }
}
