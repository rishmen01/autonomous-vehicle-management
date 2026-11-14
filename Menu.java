import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Graph graph;
    private VehicleHashTable vehicleTable;
    private SortingAlgorithm sortingAlgorithm;

    public Menu(int graphCapacity, int vehicleTableCapacity) {
        graph = new Graph(graphCapacity);
        vehicleTable = new VehicleHashTable(vehicleTableCapacity);
        sortingAlgorithm = new SortingAlgorithm();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

         while (choice != 0) {
            System.out.println("\nAutonomous Vehicle Management System");
            System.out.println("1. Add a Location");
            System.out.println("2. Delete Location");
            System.out.println("3. Add Road between Locations");
            System.out.println("4. Delete Road between Locations");
            System.out.println("5. Display Road Network");
            System.out.println("6. Check if Path Exists");
            System.out.println("7. Add a Vehicle");
            System.out.println("8. Search for a Vehicle");
            System.out.println("9. Delete a Vehicle");
            System.out.println("10. Display All Vehicles");
            System.out.println("11. Sort Vehicles by Distance to Destination (Heapsort)");
            System.out.println("12. Sort Vehicles by Battery Level (Quicksort)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    addLocation(scanner);
                } else if (choice == 2) {
                    deleteLocation(scanner);
                } else if (choice == 3) {
                    addRoad(scanner);
                } else if (choice == 4) {
                    deleteRoad(scanner);
                } else if (choice == 5) {
                    System.out.println("Road Network:");
                    graph.displayGraph();
                } else if (choice == 6) {
                    checkPath(scanner);
                } else if (choice == 7) {
                    addVehicle(scanner);
                } else if (choice == 8) {
                    searchVehicle(scanner);
                } else if (choice == 9) {
                    deleteVehicle(scanner);
                } else if (choice == 10) {
                    displayAllVehicles();
                } else if (choice == 11) {
                    sortVehiclesByDistance();
                } else if (choice == 12) {
                    sortVehiclesByBattery();
                } else if (choice == 0) {
                    System.out.println("Exiting system.");
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void addLocation(Scanner scanner) {
        System.out.print("Enter Location Name: ");
        String location = scanner.nextLine();
        graph.addVertex(location);
        System.out.println("Location added.");
    }

    private void addRoad(Scanner scanner) {
        try {
            System.out.print("Enter Source Location: ");
            String src = scanner.nextLine();
            System.out.print("Enter Destination Location: ");
            String dest = scanner.nextLine();
            System.out.print("Enter Distance: ");
            int distance = scanner.nextInt();
            scanner.nextLine();
            graph.addEdge(src, dest, distance);
            System.out.println("Road added.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid distance! Please enter a valid number.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while adding the road: " + e.getMessage());
        }
    }
    
    private void deleteRoad(Scanner scanner) {
        System.out.print("Enter Source Location: ");
        String src = scanner.nextLine();
        System.out.print("Enter Destination Location: ");
        String dest = scanner.nextLine();
        graph.deleteEdge(src, dest);
        System.out.println("Road between " + src + " and " + dest + " deleted.");
    }

    private void deleteLocation(Scanner scanner) {
        System.out.print("Enter Location to Delete: ");
        String location = scanner.nextLine();
        graph.deleteVertex(location);
        System.out.println("Location " + location + " deleted.");
    }

    private void checkPath(Scanner scanner) {
        try {
            System.out.print("Enter Source Location: ");
            String source = scanner.nextLine();
            System.out.print("Enter Destination Location: ");
            String destination = scanner.nextLine();
            boolean pathExists = graph.isPath(source, destination);
            if (pathExists) {
                System.out.println("Path exists between " + source + " and " + destination + ".");
            } else {
                System.out.println("No path exists between " + source + " and " + destination + ".");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while checking the path: " + e.getMessage());
        }
    }

    private void addVehicle(Scanner scanner) {
        try {
            System.out.print("Enter Vehicle ID: ");
            String vehicleID = scanner.nextLine();

            Vehicle existingVehicle = vehicleTable.search(vehicleID);
            if (existingVehicle != null) {
                System.out.println("Error: A vehicle with ID " + vehicleID + " already exists.");
            } else {
                System.out.print("Enter Current Location: ");
                String currentLocation = scanner.nextLine();
                System.out.print("Enter Destination: ");
                String vehicleDest = scanner.nextLine();

                int distToDest = graph.getDistance(currentLocation, vehicleDest);
                if (distToDest == -1) {
                    System.out.println("Error: No valid path exists between " + currentLocation + " and " + vehicleDest);
                } else {
                    System.out.print("Enter Initial Battery Level (in %): ");
                    int initialBatteryLevel = scanner.nextInt();
                    scanner.nextLine();

                    Vehicle vehicle = new Vehicle(vehicleID, currentLocation, vehicleDest, distToDest, initialBatteryLevel);
                    vehicleTable.insert(vehicle);
                    System.out.println("Vehicle added with a calculated distance of " + distToDest + " km and a battery level of " + vehicle.getBatteryLevel() + "%.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input, Please enter a valid no. for the battery level.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while adding the vehicle: " + e.getMessage());
        }
    }

    private void searchVehicle(Scanner scanner) {
        try {
            System.out.print("Enter Vehicle ID to Search: ");
            String searchID = scanner.nextLine();
            Vehicle searchedVehicle = vehicleTable.search(searchID);
            if (searchedVehicle != null) {
                System.out.println("Vehicle Found: ");
                System.out.println(searchedVehicle);
            } else {
                System.out.println("Vehicle not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while searching for the vehicle: " + e.getMessage());
        }
    }

    private void deleteVehicle(Scanner scanner) {
        try {
            System.out.print("Enter Vehicle ID to Delete: ");
            String deleteID = scanner.nextLine();
            vehicleTable.delete(deleteID);
            System.out.println("Vehicle deleted.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the vehicle: " + e.getMessage());
        }
    }

    private void displayAllVehicles() {
        vehicleTable.displayAll();
    }
    
    //sort the vehicles by distance using heapsort
    private void sortVehiclesByDistance() {
        try {
            System.out.println("Sorting vehicles by distance to destination:");
            Vehicle[] vehicles = extractVehiclesFromTable(vehicleTable);
            sortingAlgorithm.heapSort(vehicles);
            displaySortedVehicles(vehicles);
        } catch (Exception e) {
            System.out.println("An error occurred while sorting vehicles: " + e.getMessage());
        }
    }


    //sort the vehicles by battery using quicksort
    private void sortVehiclesByBattery() {
        try {
            System.out.println("Sorting vehicles by battery level:");
            Vehicle[] vehiclesBattery = extractVehiclesFromTable(vehicleTable);
            sortingAlgorithm.quickSort(vehiclesBattery, 0, vehiclesBattery.length - 1);
            displaySortedVehicles(vehiclesBattery);
        } catch (Exception e) {
            System.out.println("An error occurred while sorting vehicles: " + e.getMessage());
        }
    }

    private Vehicle[] extractVehiclesFromTable(VehicleHashTable vehicleTable) {
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

    private void displaySortedVehicles(Vehicle[] vehicles) {
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i] != null) {
                System.out.println(vehicles[i]);
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(100, 100); //initialise capacities for graph and vehicle table
        menu.displayMenu();
    }
}

