/***************************************************************************
*  FILE: TestHarness.java
*  AUTHOR: Rishon Mendis
*  UNIT: COMP1002 Data Structures and Algorithms
*  PURPOSE: Test Harness For Autonomous Vehicle Management System
*  LAST MOD: 27/10/2024
*  REQUIRES: Graph, VehicleHashTable, SortingAlgorithm, Vehicle classes
***************************************************************************/

public class TestHarness {

    public static void main(String[] args) {
        int iNumPassed = 0;
        int iNumTests = 0;

        Graph graph = new Graph(15); //graph with 15 vertices
        VehicleHashTable vehicleTable = new VehicleHashTable(15); //vhicle table with capacity 15
        SortingAlgorithm sortingAlgorithm = new SortingAlgorithm();

        System.out.println("\nTesting the Graph Operations");
        System.out.println("========================");

        //TEST1: Add locations
        try {
            iNumTests++;
            String[] locations = {"A", "B", "C", "D", "E", "F", "G", "H"};
            for (String location : locations) {
                graph.addVertex(location);
            }
            System.out.println("Test passed: Locations A-H added.");
            graph.displayGraph(); //display graph after adding vertices
            iNumPassed++;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //TEST2: Delete location
        try {
            iNumTests++;
            graph.deleteVertex("D");
            if (!graph.isPath("B", "D") && !graph.isPath("D", "F")) {
                System.out.println("\nTest passed: Location D deleted.");
                graph.displayGraph(); //display graph to confirm location deletion
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("\nFailed to delete Location D.");
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //TEST3: Add roads
        try {
            iNumTests++;
            graph.addEdge("A", "B", 100);
            graph.addEdge("B", "C", 150);
            graph.addEdge("A", "C", 250);
            graph.addEdge("C", "E", 300);
            graph.addEdge("E", "G", 350);
            graph.addEdge("A", "E", 200); 
            graph.addEdge("B", "G", 400);
            System.out.println("\nTest passed: Roads between locations added.");
            graph.displayGraph(); //display graph after adding edges
            iNumPassed++;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //TEST4: Check path exists
        try {
            iNumTests++;
            boolean pathExists = graph.isPath("A", "E");
            if (pathExists) {
                System.out.println("\nTest passed: Path exists between A and E.");
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("No path found between A and E.");
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //TEST5: Check wrong path
        try {
            iNumTests++;
            boolean noPath = graph.isPath("A", "H");
            if (!noPath) {
                System.out.println("\nTest passed: No path exists between A and H.");
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("\nUnexpected path found between A and H.");
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //TEST6: Distance calculation
        try {
            iNumTests++;
            int distance = graph.getDistance("A", "C");
            if (distance == 250) {
                System.out.println("\nTest passed: Distance between A and C is 250 km.");
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("Distance mismatch: Expected 250, got " + distance);
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        System.out.println("\nTesting the Vehicle Operations");
        System.out.println("===================================");

        //TEST7: Insert vehicles
        try {
            iNumTests++;
            Vehicle[] vehicles = {
                new Vehicle("V1", "A", "B", 100, 80),
                new Vehicle("V2", "B", "C", 150, 90),
                new Vehicle("V3", "A", "C", 250, 100),
                new Vehicle("V5", "E", "F", 350, 85),
                new Vehicle("V6", "F", "G", 300, 60),
                new Vehicle("V7", "G", "H", 450, 95),
                new Vehicle("V8", "C", "A", 180, 50)
            };
            for (Vehicle vehicle : vehicles) {
                vehicleTable.insert(vehicle);
            }
            System.out.println("Test passed: Vehicles V1-V8 inserted.");
            System.out.println("Displaying all vehicles after insertion:");
            vehicleTable.displayAll(); //display all vehicles after insertion
            iNumPassed++;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //TEST8: Search for vehicle
        try {
            iNumTests++;
            Vehicle searchV1 = vehicleTable.search("V1");
            if (searchV1 != null && searchV1.getID().equals("V1")) {
                System.out.println("\nTest passed: Vehicle V1 found.");
                System.out.println(searchV1); //display vehicle details
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("\nVehicle V1 not found.");
            }
        } catch (Exception e) {
            System.out.println("\nFAILED: " + e.getMessage());
        }

        //TEST9: Search for wrong vehicle
        try {
            iNumTests++;
            Vehicle searchV9 = vehicleTable.search("V9");
            if (searchV9 == null) {
                System.out.println("\nTest passed: Vehicle V9 not found.");
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("\nUnexpectedly found Vehicle V9.");
            }
        } catch (Exception e) {
            System.out.println("\nFAILED: " + e.getMessage());
        }

        //TEST10: Delete vehicle
        try {
            iNumTests++;
            vehicleTable.delete("V5");
            Vehicle searchDeletedV5 = vehicleTable.search("V5");
            if (searchDeletedV5 == null) {
                System.out.println("\nTest passed: Vehicle V5 deleted.");
                System.out.println("Displaying all vehicles after deletion:");
                vehicleTable.displayAll(); //display all vehicles after deletion
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("Failed to delete Vehicle V5.");
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //TEST11: Sort by distance - Heapsort
        System.out.println("\nTesting Sorting Operations (Heapsort)");
        System.out.println("=======================================");
        try {
            iNumTests++;
            Vehicle[] vehiclesForSort = extractVehiclesFromTable(vehicleTable);
            sortingAlgorithm.heapSort(vehiclesForSort);
            System.out.println("Vehicles sorted by distance:");
            displaySortedVehicles(vehiclesForSort); //display vehicles after sorting by distance
            if (vehiclesForSort[0].getDistanceToDestination() <= vehiclesForSort[1].getDistanceToDestination()) {
                System.out.println("Test passed: Vehicles sorted by distance.");
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("Heapsort failed to sort by distance.");
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //TEST12: Sort by battery - Quicksort
        System.out.println("\nTesting Sorting Operations (Quicksort)");
        System.out.println("=======================================");
        try {
            iNumTests++;
            Vehicle[] vehiclesForBatterySort = extractVehiclesFromTable(vehicleTable);
            sortingAlgorithm.quickSort(vehiclesForBatterySort, 0, vehiclesForBatterySort.length - 1);
            System.out.println("Vehicles sorted by battery level:");
            displaySortedVehicles(vehiclesForBatterySort); //display vehicles after sorting by battery
            if (vehiclesForBatterySort[0].getBatteryLevel() >= vehiclesForBatterySort[1].getBatteryLevel()) {
                System.out.println("Test passed: Vehicles sorted by battery level.");
                iNumPassed++;
            } else {
                throw new IllegalArgumentException("Quicksort failed to sort by battery.");
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        //print test summary
        System.out.println("\nTest Summary: " + iNumPassed + "/" + iNumTests + " tests passed (" + (int) ((double) iNumPassed / iNumTests * 100) + "%)");
    }

    private static Vehicle[] extractVehiclesFromTable(VehicleHashTable vehicleTable) {
        int vehicleCount = vehicleTable.getSize();
        Vehicle[] vehicles = new Vehicle[vehicleCount];
        int index = 0;

        for (int i = 0; i < vehicleTable.getCapacity(); i++) {
            if (vehicleTable.isOccupied(i)) {
                vehicles[index++] = vehicleTable.getVehicleAt(i);
            }
        }
        return vehicles;
    }

    private static void displaySortedVehicles(Vehicle[] vehicles) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle != null) {
                System.out.println(vehicle);
            }
        }
    }
}

