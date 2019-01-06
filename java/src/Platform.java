
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Platform {
  private Location insertedStart;
  private Location insertedDestination;
  private Graph graph;
  private VDMSet all_possible_routes;

  public void cg_init_Platform_1(final Graph g) {

    graph = g;
    all_possible_routes = SetUtil.set();
    return;
  }

  public Platform(final Graph g) {

    cg_init_Platform_1(g);
  }

  public VDMSet getAllPossibleRoutes() {

    return Utils.copy(all_possible_routes);
  }

  public Number getAllPossibleRoutesSize() {

    return all_possible_routes.size();
  }

  public Location getInsertedStart() {

    return insertedStart;
  }

  public Location getInsertedDestination() {

    return insertedDestination;
  }

  public Graph getGraph() {

    return graph;
  }

  public Number getGraphSize() {

    return graph.getSize();
  }

  public void setStart(final Number start_pos) {

    if (graph.locationExistsByID(start_pos)) {
      insertedStart = graph.searchLocationByID(start_pos);
    }
  }

  public void setDestination(final Number destination_pos) {

    if (graph.locationExistsByID(destination_pos)) {
      insertedDestination = graph.searchLocationByID(destination_pos);
    }
  }

  public void getAllRoutes() {

    VDMSeq routes = SeqUtil.seq();
    getAllRoutesUtils(insertedStart, Utils.copy(routes));
  }

  public void getAllRoutesUtils(final Location currLocation, final VDMSeq seqRoutes) {

    for (Iterator iterator_5 = currLocation.getRoutes().iterator(); iterator_5.hasNext(); ) {
      Route route = (Route) iterator_5.next();
      VDMSeq newRoute = Utils.copy(seqRoutes);
      Number routeDestinationID = route.getDestination().getID();
      if (!(SetUtil.inSet(routeDestinationID, getVisitedLocations(Utils.copy(seqRoutes))))) {
        Utils.mapSeqUpdate(newRoute, newRoute.size() + 1L, route);
      }

      if (Utils.equals(route.getDestination(), insertedDestination)) {
        all_possible_routes =
            SetUtil.union(Utils.copy(all_possible_routes), SetUtil.set(Utils.copy(newRoute)));

      } else {
        Number numberOfEdgesInDestination = route.getDestination().getRoutes().size();
        Boolean andResult_1 = false;

        if (!(SetUtil.inSet(routeDestinationID, getVisitedLocations(Utils.copy(seqRoutes))))) {
          if (numberOfEdgesInDestination.longValue() > 0L) {
            andResult_1 = true;
          }
        }

        if (andResult_1) {
          getAllRoutesUtils(route.getDestination(), Utils.copy(newRoute));
        }
      }
    }
  }

  public VDMSet getVisitedLocations(final VDMSeq route_sequence) {

    VDMSet locationsID = SetUtil.set(insertedStart.getID());
    for (Iterator iterator_6 = SeqUtil.elems(Utils.copy(route_sequence)).iterator();
        iterator_6.hasNext();
        ) {
      Route route = (Route) iterator_6.next();
      locationsID =
          SetUtil.union(SetUtil.set(route.getDestination().getID()), Utils.copy(locationsID));
    }
    return Utils.copy(locationsID);
  }

  public void printAllPossiblePaths() {

    IO.print("\n");
    IO.print("Start: ");
    IO.println(insertedStart.getName());
    IO.print("Destination: ");
    IO.println(insertedDestination.getName());
    IO.print("--------------------- \n\n");
    if (all_possible_routes.size() > 0L) {
      Number local_i = 1L;
      for (Iterator iterator_7 = all_possible_routes.iterator(); iterator_7.hasNext(); ) {
        VDMSeq routes = (VDMSeq) iterator_7.next();
        IO.print("Route: ");
        IO.println(local_i);
        printRouteInfo(Utils.copy(routes));
        local_i = local_i.longValue() + 1L;
        IO.print("\n");
      }

    } else {
      IO.print("Not Possible to reach the Destination from that Starting Point!");
      IO.println("\n");
    }
  }

  public void printRouteInfo(final VDMSeq seq_of_route) {

    Route prevRoute = null;
    Number totalTripDuration = 0L;
    Number totalTripCost = 0L;
    VDMSet totalTypeOfTransport = SetUtil.set();
    long toVar_1 = seq_of_route.size();

    for (Long i = 1L; i <= toVar_1; i++) {
      if (Utils.equals(i, 1L)) {
        IO.print("Travel from: ");
        IO.print(insertedStart.getName());
        IO.print(" to: ");
        IO.print(((Route) Utils.get(seq_of_route, i)).getDestination().getName());
        IO.print(" transport: ");
        IO.print(((Route) Utils.get(seq_of_route, i)).getTransport().getName());
        IO.print(" duration: ");
        IO.print(((Route) Utils.get(seq_of_route, i)).getDuration());
        IO.print(" with Price: ");
        IO.println(((Route) Utils.get(seq_of_route, i)).getPrice());
        totalTripDuration =
            totalTripDuration.longValue()
                + ((Route) Utils.get(seq_of_route, i)).getDuration().longValue();
        totalTripCost =
            totalTripCost.longValue() + ((Route) Utils.get(seq_of_route, i)).getPrice().longValue();
        totalTypeOfTransport =
            SetUtil.union(
                Utils.copy(totalTypeOfTransport),
                SetUtil.set(((Route) Utils.get(seq_of_route, i)).getTransport()));

      } else {
        IO.print("Travel from: ");
        IO.print(prevRoute.getDestination().getName());
        IO.print(" to: ");
        IO.print(((Route) Utils.get(seq_of_route, i)).getDestination().getName());
        IO.print(" transport: ");
        IO.print(((Route) Utils.get(seq_of_route, i)).getTransport().getName());
        IO.print(" duration: ");
        IO.print(((Route) Utils.get(seq_of_route, i)).getDuration());
        IO.print(" with Price: ");
        IO.println(((Route) Utils.get(seq_of_route, i)).getPrice());
        totalTripDuration =
            totalTripDuration.longValue()
                + ((Route) Utils.get(seq_of_route, i)).getDuration().longValue();
        totalTripCost =
            totalTripCost.longValue() + ((Route) Utils.get(seq_of_route, i)).getPrice().longValue();
        totalTypeOfTransport =
            SetUtil.union(
                Utils.copy(totalTypeOfTransport),
                SetUtil.set(((Route) Utils.get(seq_of_route, i)).getTransport()));
      }

      prevRoute = ((Route) Utils.get(seq_of_route, i));
    }
    IO.print("\n");
    IO.print("Total travel time: ");
    IO.print(totalTripDuration);
    IO.println(" hours.");
    IO.print("Total travel price: ");
    IO.print(totalTripCost);
    IO.println(" euros.");
    IO.print("Types of transport used: ");
    printTotalTypeOfTransportUsed(Utils.copy(totalTypeOfTransport));
    IO.print("\n");
  }

  public void printTotalTypeOfTransportUsed(final VDMSet typeOfTransport) {

    Number local_i = 0L;
    for (Iterator iterator_8 = typeOfTransport.iterator(); iterator_8.hasNext(); ) {
      Transport transport = (Transport) iterator_8.next();
      if (Utils.equals(local_i, 0L)) {
        IO.print(transport.getName());
      } else {
        IO.print(" -- ");
        IO.print(transport.getName());
      }

      local_i = local_i.longValue() + 1L;
    }
  }

  public Platform() {}

  public String toString() {

    return "Platform{"
        + "insertedStart := "
        + Utils.toString(insertedStart)
        + ", insertedDestination := "
        + Utils.toString(insertedDestination)
        + ", graph := "
        + Utils.toString(graph)
        + ", all_possible_routes := "
        + Utils.toString(all_possible_routes)
        + "}";
  }
}
