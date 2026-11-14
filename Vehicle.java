public class Vehicle {
    private String id;
    private String location;
    private String destination;
    private int distanceToDestination;
    private int batteryLevel;

    public Vehicle(String id, String location, String destination, int distanceToDestination, int initialBatteryLevel) {
        this.id = id;
        this.location = location;
        this.destination = destination;
        this.distanceToDestination = distanceToDestination;
        this.batteryLevel = calculateBatteryLevel(initialBatteryLevel);  //calculate the battery level based on distance
    }

    //getters
    public String getID() { 
        return id; 
    }

    public String getLocation() { 
        return location; 
    }

    public String getDestination() { 
        return destination; 
    }

    public int getDistanceToDestination() { 
        return distanceToDestination; 
    }

    public int getBatteryLevel() { 
        return batteryLevel; 
    }

    //setters
    public void setLocation(String location) { 
        this.location = location; 
    }

    public void setDestination(String destination) { 
        this.destination = destination; 
    }

    public void setDistanceToDestination(int distanceToDestination) { 
        this.distanceToDestination = distanceToDestination; 
        this.batteryLevel = calculateBatteryLevel(batteryLevel);  //recalculate battery level if distance changes
    }

    public void setBatteryLevel(int batteryLevel) { 
        this.batteryLevel = batteryLevel; 
    }

    //method to calculate the battery level based on distance
    private int calculateBatteryLevel(int initialBatteryLevel) {
        int batteryReduction = (distanceToDestination / 100) * 5;  
        int newBatteryLevel = initialBatteryLevel - batteryReduction;
        if (newBatteryLevel < 0) {
            newBatteryLevel = 0;  //ensure tht the battery level doesn't go below 0
        }
        return newBatteryLevel;  
    }

    @Override
    public String toString() {
        return "Vehicle ID: " + id + ", Location: " + location + ", Destination: " + destination +
               ", Distance to Destination: " + distanceToDestination + " km, Battery Level: " + batteryLevel + "%";
    }
}

