package com.penthouse_bogmjary.triangulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriangleSoup {
    public List<Triangle2D> triangleSoup = new ArrayList();

    public void add(Triangle2D triangle2D) {
        this.triangleSoup.add(triangle2D);
    }

    public void remove(Triangle2D triangle2D) {
        this.triangleSoup.remove(triangle2D);
    }

    public List<Triangle2D> getTriangles() {
        return this.triangleSoup;
    }

    public Triangle2D findContainingTriangle(Vector2D vector2D) {
        for (Triangle2D triangle2D : this.triangleSoup) {
            if (triangle2D.contains(vector2D)) {
                return triangle2D;
            }
        }
        return null;
    }

    public Triangle2D findNeighbour(Triangle2D triangle2D, Edge2D edge2D) {
        for (Triangle2D triangle2D2 : this.triangleSoup) {
            if (triangle2D2.isNeighbour(edge2D) && triangle2D2 != triangle2D) {
                return triangle2D2;
            }
        }
        return null;
    }

    public Triangle2D findOneTriangleSharing(Edge2D edge2D) {
        for (Triangle2D triangle2D : this.triangleSoup) {
            if (triangle2D.isNeighbour(edge2D)) {
                return triangle2D;
            }
        }
        return null;
    }

    public Edge2D findNearestEdge(Vector2D vector2D) {
        ArrayList arrayList = new ArrayList();
        for (Triangle2D triangle2D : this.triangleSoup) {
            arrayList.add(triangle2D.findNearestEdge(vector2D));
        }
        EdgeDistancePack[] edgeDistancePackArr = new EdgeDistancePack[arrayList.size()];
        arrayList.toArray(edgeDistancePackArr);
        Arrays.sort(edgeDistancePackArr);
        return edgeDistancePackArr[0].edge;
    }

    public void removeTrianglesUsing(Vector2D vector2D) {
        ArrayList arrayList = new ArrayList();
        for (Triangle2D triangle2D : this.triangleSoup) {
            if (triangle2D.hasVertex(vector2D)) {
                arrayList.add(triangle2D);
            }
        }
        this.triangleSoup.removeAll(arrayList);
    }
}