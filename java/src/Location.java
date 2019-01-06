
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Location {
  private Number id;
  private String name;
  private VDMSet routes = SetUtil.set();

  public void cg_init_Location_1(final Number i, final String n) {

    id = i;
    name = n;
    return;
  }

  public Location(final Number i, final String n) {

    cg_init_Location_1(i, n);
  }

  public Number getID() {

    return id;
  }

  public String getName() {

    return name;
  }

  public VDMSet getRoutes() {

    return Utils.copy(routes);
  }

  public Route getRouteByID(final Number newID) {

    Route tempRoute = null;
    for (Iterator iterator_3 = routes.iterator(); iterator_3.hasNext(); ) {
      Route route = (Route) iterator_3.next();
      if (Utils.equals(route.getID(), newID)) {
        tempRoute = route;
      }
    }
    return tempRoute;
  }

  public void addRoute(final Route newRoute) {

    routes = SetUtil.union(SetUtil.set(newRoute), Utils.copy(routes));
  }

  public void removeRoute(final Route deleteRoute) {

    routes = SetUtil.diff(Utils.copy(routes), SetUtil.set(deleteRoute));
  }

  public Boolean routeExistsByID(final Number routeID) {

    for (Iterator iterator_4 = routes.iterator(); iterator_4.hasNext(); ) {
      Route route = (Route) iterator_4.next();
      if (Utils.equals(route.getID(), routeID)) {
        return true;
      }
    }
    return false;
  }

  public Location() {}

  public String toString() {

    return "Location{"
        + "id := "
        + Utils.toString(id)
        + ", name := "
        + Utils.toString(name)
        + ", routes := "
        + Utils.toString(routes)
        + "}";
  }
}
