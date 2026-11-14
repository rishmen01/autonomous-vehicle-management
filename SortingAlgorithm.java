public class SortingAlgorithm {

    //Heap Sort: Sort vehicles by DistanceToDestination in ascending order
    public void heapSort(Vehicle[] vehicles) {
        int n = vehicles.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            trickleDown(vehicles, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(vehicles, 0, i); 
            trickleDown(vehicles, i, 0); 
        }
    }

    //trickle-Down
    private void trickleDown(Vehicle[] vehicles, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        //find the largest among root, left child and right child
        if (left < n && vehicles[left].getDistanceToDestination() > vehicles[largest].getDistanceToDestination()) {
            largest = left;
        }
        if (right < n && vehicles[right].getDistanceToDestination() > vehicles[largest].getDistanceToDestination()) {
            largest = right;
        }

        //if the largest is not the root, swap and continue trickling down
        if (largest != i) {
            swap(vehicles, i, largest);
            trickleDown(vehicles, n, largest); 
        }
    }

    //trickle-Up 
    private void trickleUp(Vehicle[] vehicles, int i) {
        int parent = (i - 1) / 2;

        //move up the element as long as it's greater than its parent
        while (i > 0 && vehicles[i].getDistanceToDestination() > vehicles[parent].getDistanceToDestination()) {
            swap(vehicles, i, parent);
            i = parent;
            parent = (i - 1) / 2;
        }
    }

    //Quick Sort: Sort vehicles by BatteryLevel in descending order
    public void quickSort(Vehicle[] vehicles, int low, int high) {
        if (low < high) {
            int pi = partition(vehicles, low, high);
            quickSort(vehicles, low, pi - 1);  
            quickSort(vehicles, pi + 1, high); 
        }
    }

    private int partition(Vehicle[] vehicles, int low, int high) {
        Vehicle pivot = vehicles[high];
        int i = low - 1; 

        for (int j = low; j < high; j++) {
            if (vehicles[j].getBatteryLevel() > pivot.getBatteryLevel()) {
                i++;
                swap(vehicles, i, j);
            }
        }
        swap(vehicles, i + 1, high);
        return i + 1;
    }

    private void swap(Vehicle[] vehicles, int i, int j) {
        Vehicle temp = vehicles[i];
        vehicles[i] = vehicles[j];
        vehicles[j] = temp;
    }
}

