# Autonomous Vehicle Management System (AVMS)

**Language:** Java

## Overview

The Autonomous Vehicle Management System (AVMS) is a command-line Java application that simulates the management of vehicles operating across a network of connected locations. It uses custom data structures and sorting algorithms to manage the road network, store vehicle records, check connectivity, and prioritise vehicles.

## Features

### Road network management
- Represent locations and roads using an adjacency-list graph.
- Add and delete locations.
- Add and delete bidirectional roads with distance values.
- Display the road network.
- Check whether a route exists between two locations using depth-first search (DFS).

### Vehicle management
- Store vehicle records in a custom hash table.
- Resolve hash collisions using linear probing.
- Search for and delete vehicles by ID.
- Display all stored vehicles.
- Calculate a battery estimate by reducing the initial battery level by 5 percentage points for every complete 100 km of the recorded journey distance.

### Sorting
- **Heapsort:** sort vehicles by distance to destination in ascending order.
- **Quicksort:** sort vehicles by battery level in descending order.

### Testing
- Includes a console-based test harness covering graph operations, vehicle insertion/search/deletion, and both sorting algorithms.
- The included test harness currently reports 12/12 defined tests passing.

## Data structures and algorithms

| Component | Implementation |
|---|---|
| Road network | Adjacency-list graph using arrays and linked nodes |
| Path existence | Depth-first search (DFS) using a custom stack |
| Distance traversal | Breadth-first search (BFS) using a custom queue |
| Vehicle storage | Array-based hash table with linear probing and tombstone states |
| Distance sorting | Heapsort |
| Battery sorting | Quicksort |

## Project structure

```text
autonomous-vehicle-management/
├── Graph.java
├── Menu.java
├── SortingAlgorithm.java
├── TestHarness.java
├── Vehicle.java
├── VehicleHashTable.java
├── README.md
└── .gitignore
```

## Requirements

- Java Development Kit (JDK)
- A terminal or command prompt

Check that Java and the Java compiler are available:

```bash
java -version
javac -version
```

## Compile and run

Open a terminal in the project directory and compile the source files:

```bash
javac *.java
```

Start the interactive application:

```bash
java Menu
```

## Run the test harness

Compile the project if it has not already been compiled, then run:

```bash
java TestHarness
```

The test harness prints the results of its graph, vehicle-management, and sorting tests, followed by a test summary.

## Important implementation note

The current `Graph.getDistance()` implementation uses BFS while the graph edges store different distance weights. BFS is suitable for finding paths with the fewest edges in an unweighted graph, but it does **not** guarantee the minimum total distance when edge weights differ. The current distance routine should therefore be improved with a weighted shortest-path algorithm such as Dijkstra's algorithm before the application is described or relied upon as returning the shortest weighted route.

The existing test harness checks one distance example, but that test does not distinguish between competing routes with different total weights. Additional tests should cover cases where the route with fewer edges is not the route with the lowest total distance.

## Possible future improvements

- Replace the weighted-distance BFS routine with Dijkstra's algorithm.
- Expand automated tests to cover disconnected graphs, duplicate roads, hash-table collisions, full-capacity behaviour, and alternative weighted routes.
- Validate road distances and battery-level input.
- Improve error messages when locations or vehicles cannot be added, found, or deleted.
- Separate source files into Java packages and introduce a repeatable build configuration.
