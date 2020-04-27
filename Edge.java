
public class Edge  {
    private final Integer id; //actuallly this is index 
    private final Vertex source;
    private final Vertex destination;
    private final Float weight;

    public Edge(Integer id, Vertex source, Vertex destination, Float weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }
    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }
    public Float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }


}