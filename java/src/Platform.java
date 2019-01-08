
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
    else
    	System.out.println("Start doesnt exist");
  }

  public void setDestination(final Number destination_pos) {

    if (graph.locationExistsByID(destination_pos)) {
      insertedDestination = graph.searchLocationByID(destination_pos);
    }
    else
    	System.out.println("Destination doesnt exist");
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
  
  public static int getLocation(Graph graph2, String name){
	  for(int i=0; i<graph2.getLocations().size();i++){
		  if(name.equals(((Location) graph2.getLocations().toArray()[i]).getName())){
			  Number aa=  ((Location) graph2.getLocations().toArray()[i]).getID();
			  System.out.println( "id = " +  aa );
			  return (int) aa;
		  }
	  }
	  return -1;
  }
  
  public static void main(String [ ] args)
  {
	  
	  Graph graph = new Graph();

	  Platform platform = new Platform(graph);
	  
	  
	  Location location0 = new Location(0,"Oporto");
	  Location location1 = new Location(1,"Lisbon");
	  Location location2 = new Location(2,"Faro");
	  Location location3 = new Location(3,"Madrid");
	  Location location4 = new Location(4,"Paris");
	  Location location5 = new Location(5,"London");
	  
	  /**
		location6 : Location := new Location(6,"Rome");
		location7 : Location := new Location(7,"Amsterdam");
		location8 : Location := new Location(8,"Berlin");
		location9 : Location := new Location(9,"Vienna");
		location10 : Location := new Location(10,"Barcelona");
		location100 : Location := new Location(100,"Tokyo");
*/
	  Transport	transport0 = new Transport(0,"Car");
	  Transport transport1 = new Transport(1,"Train");
	  Transport transport2 = new Transport(2,"Plane");
	  Transport transport3 = new Transport(3,"Ferry");
	  Transport transport4 = new Transport(4,"Bus");
	  
	  	Route route0 = new Route(0,3,location1,transport0,20);
	  	Route route1 = new Route(1,5,location3,transport1,30);
	  	Route route2 = new Route(2,7,location3,transport2,40);
	  	Route route3 = new Route(3,7,location3,transport3,50);
	  	Route route4 = new Route(4,6,location4,transport4,60);
	  	Route route5 = new Route(5,8,location5,transport2,111);
	  	
	  	platform.getGraph().addLocation(location0);
		platform.getGraph().addLocation(location1);
		platform.getGraph().addLocation(location3);
		platform.getGraph().addLocation(location5);

		
		platform.getGraph().searchLocationByID(location0.getID()).addRoute(route0);
		platform.getGraph().searchLocationByID(location0.getID()).addRoute(route1);
		platform.getGraph().searchLocationByID(location1.getID()).addRoute(route2);

		
	  
	  
      System.out.println("Welcome to Rome2Rio");
      System.out.println("-------------------");

      
      Scanner scanner = new Scanner( System.in );
/**
      System.out.print( "Insert start:" );
      // 3. Use the Scanner to read a line of text from the user.
      String start = scanner.nextLine();
      System.out.print( "Insert Destination:" );
      // 3. Use the Scanner to read a line of text from the user.
      String dest = scanner.nextLine();

     
      
      int st = platform.getLocation(platform.getGraph(),start);
      int destination = platform.getLocation(platform.getGraph(),dest);
     
      
      platform.setStart(0);
      platform.setDestination(1);
      System.out.println( "startID = " + platform.getInsertedStart().getName() );
      System.out.println( "destinationID = " + platform.getInsertedDestination().getName() );
      
      platform.getAllRoutes();
      platform.printAllPossiblePaths();
    	  */
     
      System.out.println( "------Admin-----" );
     
     
      int  inserted = scanner.nextInt();

      if(inserted == 1 ){
    	  System.out.println( "Insert a new Route Oporto-Londres" );
          System.out.println("Routes:"+ platform.getGraph().searchLocationByID(location0.getID()).getRoutes());
          System.out.println("addind:"+ route5 );
          platform.getGraph().searchLocationByID(location0.getID()).addRoute(route5);
          System.out.println("Routes:"+ platform.getGraph().searchLocationByID(location0.getID()).getRoutes());
      }
      if(inserted ==2){
          System.out.println( "Edit the Route Oporto-Lisbon" );
          System.out.println(platform.getGraph().searchLocationByID(location0.getID()).getRouteByID(0));
          System.out.println( "Changing duration = 5 and price=55" );
          platform.getGraph().searchLocationByID(location0.getID()).getRouteByID(0).setDuration(5);
          platform.getGraph().searchLocationByID(location0.getID()).getRouteByID(0).setPrice(55);
          System.out.println(platform.getGraph().searchLocationByID(location0.getID()).getRouteByID(0));
      }
      if(inserted == 3){
          System.out.println( "Removing the Route Oporto-Lisbon" );
          System.out.println("Routes:"+ platform.getGraph().searchLocationByID(location0.getID()).getRoutes());
          System.out.println("number of routes:"+ platform.getGraph().searchLocationByID(location0.getID()).getRoutes().size());
          System.out.println( "After removing:" );
          platform.getGraph().searchLocationByID(location0.getID()).removeRoute(route0);
          System.out.println("Routes:"+ platform.getGraph().searchLocationByID(location0.getID()).getRoutes());
          System.out.println("number of routes:"+ platform.getGraph().searchLocationByID(location0.getID()).getRoutes().size());

      }
      
      


     
      
      

      
      
      


  
  }
  
  
  
  
}
