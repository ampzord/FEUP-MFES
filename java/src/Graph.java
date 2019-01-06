
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Graph {
  private VDMSet locations;

  public void cg_init_Graph_1() {

    locations = SetUtil.set();
    return;
  }

  public Graph() {

    cg_init_Graph_1();
  }

  public VDMSet getLocations() {

    return Utils.copy(locations);
  }

  public Number getSize() {

    return locations.size();
  }

  public void addLocation(final Location newLocation) {

    locations = SetUtil.union(SetUtil.set(newLocation), Utils.copy(locations));
  }

  public void removeLocation(final Location deleteLocation) {

    locations = SetUtil.diff(Utils.copy(locations), SetUtil.set(deleteLocation));
  }

  public Location searchLocationByID(final Number locationID) {

    Location tempCity = null;
    for (Iterator iterator_1 = locations.iterator(); iterator_1.hasNext(); ) {
      Location city = (Location) iterator_1.next();
      if (Utils.equals(city.getID(), locationID)) {
        tempCity = city;
      }
    }
    return tempCity;
  }

  public Boolean locationExistsByID(final Number locationID) {

    for (Iterator iterator_2 = locations.iterator(); iterator_2.hasNext(); ) {
      Location city = (Location) iterator_2.next();
      if (Utils.equals(city.getID(), locationID)) {
        return true;
      }
    }
    return false;
  }

  public String toString() {

    return "Graph{" + "locations := " + Utils.toString(locations) + "}";
  }
}
