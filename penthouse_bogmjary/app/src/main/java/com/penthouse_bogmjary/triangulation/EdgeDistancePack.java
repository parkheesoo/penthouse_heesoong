package com.penthouse_bogmjary.triangulation;

public class EdgeDistancePack implements Comparable<EdgeDistancePack> {
    public double distance;
    public Edge2D edge;

    public EdgeDistancePack(Edge2D edge2D, double d) {
        this.edge = edge2D;
        this.distance = d;
    }

    public int compareTo(EdgeDistancePack edgeDistancePack) {
        return Double.compare(this.distance, edgeDistancePack.distance);
    }
}
