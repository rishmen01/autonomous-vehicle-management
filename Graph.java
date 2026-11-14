public class Graph {
    private class Node {
        String location;
        int distance;
        Node next;

        Node(String location, int distance) {
            this.location = location;
            this.distance = distance;
            this.next = null;
        }
    }

    private class Vertex {
        String location;
        Node head;

        Vertex(String location) {
            this.location = location;
            this.head = null;
        }
    }

    private Vertex[] vertices;
    private int vertexCount;

    public Graph(int capacity) {
        vertices = new Vertex[capacity];
        vertexCount = 0;
    }

    public void addVertex(String location) {
        if (findVertex(location) == null && vertexCount < vertices.length) {
            vertices[vertexCount++] = new Vertex(location);
        }
    }

    public void addEdge(String src, String dest, int distance) {
        Vertex srcVertex = findVertex(src);
        Vertex destVertex = findVertex(dest);

        if (srcVertex != null && destVertex != null) {
            addEdgeHelper(srcVertex, dest, distance);
            addEdgeHelper(destVertex, src, distance);  
        }
    }

    private void addEdgeHelper(Vertex vertex, String dest, int distance) {
        Node newNode = new Node(dest, distance);
        if (vertex.head == null) {
            vertex.head = newNode;
        } else {
            Node temp = vertex.head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }
    
     public void deleteEdge(String src, String dest) {
        Vertex srcVertex = findVertex(src);
        Vertex destVertex = findVertex(dest);
        if (srcVertex != null && destVertex != null) {
            deleteEdgeHelper(srcVertex, dest);
            deleteEdgeHelper(destVertex, src);
        }
    }

    private void deleteEdgeHelper(Vertex vertex, String dest) {
        if (vertex.head != null) {
            Node prev = null;
            Node current = vertex.head;

            while (current != null && !current.location.equals(dest)) {
                prev = current;
                current = current.next;
            }

            if (current != null) {
                if (prev == null) {
                    vertex.head = current.next;
                } else {
                    prev.next = current.next;
                }
            }
        }
    }

    public void deleteVertex(String location) {
        int vertexIndex = findVertexIndex(location);
        if (vertexIndex != -1) {
            for (int i = vertexIndex; i < vertexCount - 1; i++) {
                vertices[i] = vertices[i + 1];
            }
            vertexCount--;
            vertices[vertexCount] = null; 

            //remove edges to this vertex from other vertices
            for (int i = 0; i < vertexCount; i++) {
                deleteEdgeHelper(vertices[i], location);
            }
        }
    }

    //finding shortest distance between two locations using BFS
    public int getDistance(String src, String dest) {
        Vertex srcVertex = findVertex(src);
        Vertex destVertex = findVertex(dest);

        if (srcVertex == null || destVertex == null) {
            return -1;  //return (-1) if either source or destination doesn't exist
        }
        return bfsShortestDistance(src, dest);
    }

    //bfs algorithm to find the shortest distance
    private int bfsShortestDistance(String src, String dest) {
        boolean[] visited = new boolean[vertexCount];
        int[] distances = new int[vertexCount];
        int infinity = 9999999;  //large value to represent infinity

        for (int i = 0; i < vertexCount; i++) {
            visited[i] = false;
            distances[i] = infinity;  //initialise distance to infinity
        }

        Queue queue = new Queue(vertexCount);
        Vertex srcVertex = findVertex(src);
        queue.enqueue(srcVertex);
        distances[findVertexIndex(src)] = 0;

        while (!queue.isEmpty()) {
            Vertex current = queue.dequeue();
            int currentIndex = findVertexIndex(current.location);
            visited[currentIndex] = true;

            Node neighbor = current.head;
            while (neighbor != null) {
                int neighborIndex = findVertexIndex(neighbor.location);
                if (!visited[neighborIndex]) {
                    int newDist = distances[currentIndex] + neighbor.distance;
                    if (newDist < distances[neighborIndex]) {
                        distances[neighborIndex] = newDist;
                    }
                    queue.enqueue(findVertex(neighbor.location));
                }
                neighbor = neighbor.next;
            }
        }

        int destinationIndex = findVertexIndex(dest);
        if (distances[destinationIndex] == infinity) {
            return -1;  //return (-1) if destination is unreachable
        }
        return distances[destinationIndex];  //return the shortest distance
    }

    //checking if a path exists between two locations 
    public boolean isPath(String src, String dest) {
        Vertex srcVertex = findVertex(src);
        Vertex destVertex = findVertex(dest);

        if (srcVertex == null || destVertex == null) {
            return false;  //return false if either source or destination doesn't exist
        }

        boolean[] visited = new boolean[vertexCount];
        Stack stack = new Stack(vertexCount);  
        stack.push(srcVertex);

        boolean foundPath = false;
        while (!stack.isEmpty()) {
            Vertex currentVertex = stack.pop();
            int vertexIndex = findVertexIndex(currentVertex.location);

            if (!visited[vertexIndex]) {
                visited[vertexIndex] = true;

                if (currentVertex.location.equals(dest)) {
                    foundPath = true;  
                } else {
                    Node neighbor = currentVertex.head;
                    while (neighbor != null) {
                        Vertex neighborVertex = findVertex(neighbor.location);
                        int neighborIndex = findVertexIndex(neighbor.location);
                        if (!visited[neighborIndex]) {
                            stack.push(neighborVertex);
                        }
                        neighbor = neighbor.next;
                    }
                }
            }
        }
        return foundPath;  
    }

    private Vertex findVertex(String location) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].location.equals(location)) {
                return vertices[i];
            }
        }
        return null;
    }

    private int findVertexIndex(String location) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].location.equals(location)) {
                return i;
            }
        }
        return -1;
    }

    public void displayGraph() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.print(vertices[i].location + " -> ");
            Node temp = vertices[i].head;
            while (temp != null) {
                System.out.print(temp.location + "(" + temp.distance + " km) ");
                temp = temp.next;
            }
            System.out.println();
        }
    }

    //Queue for BFS
    private class Queue {
        private int front, rear, size;
        private Vertex[] queueArray;

        public Queue(int capacity) {
            queueArray = new Vertex[capacity];
            front = 0;
            rear = -1;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void enqueue(Vertex vertex) {
            if (size < queueArray.length) {
                rear = (rear + 1) % queueArray.length;
                queueArray[rear] = vertex;
                size++;
            }
        }

        public Vertex dequeue() {
            Vertex temp = null;
            if (size > 0) {
                temp = queueArray[front];
                front = (front + 1) % queueArray.length;
                size--;
            }
            return temp;  
        }
    }

    //Stack for DFS 
    private class Stack {
        private Vertex[] stackArray;
        private int top;

        public Stack(int size) {
            stackArray = new Vertex[size];
            top = -1;
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public boolean isFull() {
            return top == stackArray.length - 1;
        }

        public void push(Vertex vertex) {
            if (!isFull()) {
                top++;
                stackArray[top] = vertex;
            }
        }

        public Vertex pop() {
            Vertex temp = null;
            if (!isEmpty()) {
                temp = stackArray[top];
                top--;
            }
            return temp; 
        }
    }
}

