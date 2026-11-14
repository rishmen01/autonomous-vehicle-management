public class VehicleHashTable {
    private Vehicle[] table;
    private int[] states;  // 0=never used, 1=occupied, (-1)=previously used
    private int capacity;
    private int size;

    public VehicleHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Vehicle[capacity];
        this.states = new int[capacity];  //initialise states to 0 
        this.size = 0;
    }

    //hash function
    private int hashFunction(String id) {
        int hash = 0;
        for (int i = 0; i < id.length(); i++) {
            hash = (31 * hash + id.charAt(i)) % capacity;
        }
        return hash;
    }

    //inserting vehicle using linear probing
    public void insert(Vehicle vehicle) {
        if (size < capacity) {
            int index = hashFunction(vehicle.getID());
            boolean inserted = false;

            while (!inserted) {
                if (states[index] != 1) {  //if slot is not occupied
                    table[index] = vehicle;
                    states[index] = 1;  //mark as occupied
                    size++;
                    inserted = true;
                } else {
                    index = (index + 1) % capacity;  
                }
            }
        }
    }

    //searching for a vehicle by ID
    public Vehicle search(String vehicleID) {
        int index = hashFunction(vehicleID);
        int startIndex = index;
        Vehicle foundVehicle = null;
        boolean searchComplete = false;

        while (!searchComplete) {
            if (states[index] == 1 && table[index].getID().equals(vehicleID)) {
                foundVehicle = table[index];
                searchComplete = true;
            } else if (states[index] == 0) {  
                searchComplete = true;
            } else {  
                index = (index + 1) % capacity;
                if (index == startIndex) {
                    searchComplete = true;  
                }
            }
        }
        return foundVehicle;
    }

    //deleting a vehicle by ID
    public void delete(String vehicleID) {
        int index = hashFunction(vehicleID);
        int startIndex = index;
        boolean deleted = false;

        while (!deleted) {
            if (states[index] == 1 && table[index].getID().equals(vehicleID)) {
                table[index] = null;
                states[index] = -1;  //mark as previously used
                size--;
                deleted = true;
            } else if (states[index] == 0) {  
                deleted = true;  //can't find the vehicle
            } else {  
                index = (index + 1) % capacity;
                if (index == startIndex) {
                    deleted = true;  
                }
            }
        }
    }

    //display all vehicles
    public void displayAll() {
        for (int i = 0; i < capacity; i++) {
            if (states[i] == 1) {  //only display occupied slots
                System.out.println(table[i]);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isOccupied(int index) {
        return states[index] == 1;
    }

    public Vehicle getVehicleAt(int index) {
        return table[index];
    }
}

