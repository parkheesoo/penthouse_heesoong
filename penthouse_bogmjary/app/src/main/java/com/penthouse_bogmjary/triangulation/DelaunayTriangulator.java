package com.penthouse_bogmjary.triangulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DelaunayTriangulator {
    private List<Vector2D> pointSet;
    private TriangleSoup triangleSoup = new TriangleSoup();

    public DelaunayTriangulator(List<Vector2D> list) {
        this.pointSet = list;
    }

    public void triangulate() throws NotEnoughPointsException {
        Vector2D vector2D;
        this.triangleSoup = new TriangleSoup();
        List<Vector2D> list = this.pointSet;
        if (list == null || list.size() < 3) {
            throw new NotEnoughPointsException("Less than three points in point set.");
        }
        double d = 0.0d;
        for (Vector2D vector2D2 : getPointSet()) {
            d = Math.max(Math.max(vector2D2.x, vector2D2.y), d);
        }
        double d2 = d * 16.0d;
        double d3 = 3.0d * d2;
        double d4 = d2 * -3.0d;
        Triangle2D triangle2D = new Triangle2D(new Vector2D(0.0d, d3), new Vector2D(d3, 0.0d), new Vector2D(d4, d4));
        this.triangleSoup.add(triangle2D);
        for (int i = 0; i < this.pointSet.size(); i++) {
            Triangle2D findContainingTriangle = this.triangleSoup.findContainingTriangle(this.pointSet.get(i));
            if (findContainingTriangle == null) {
                Edge2D findNearestEdge = this.triangleSoup.findNearestEdge(this.pointSet.get(i));
                Triangle2D findOneTriangleSharing = this.triangleSoup.findOneTriangleSharing(findNearestEdge);
                Triangle2D findNeighbour = this.triangleSoup.findNeighbour(findOneTriangleSharing, findNearestEdge);
                Vector2D noneEdgeVertex = findOneTriangleSharing.getNoneEdgeVertex(findNearestEdge);
                Vector2D noneEdgeVertex2 = findNeighbour.getNoneEdgeVertex(findNearestEdge);
                this.triangleSoup.remove(findOneTriangleSharing);
                this.triangleSoup.remove(findNeighbour);
                Triangle2D triangle2D2 = new Triangle2D(findNearestEdge.a, noneEdgeVertex, this.pointSet.get(i));
                Triangle2D triangle2D3 = new Triangle2D(findNearestEdge.b, noneEdgeVertex, this.pointSet.get(i));
                Triangle2D triangle2D4 = new Triangle2D(findNearestEdge.a, noneEdgeVertex2, this.pointSet.get(i));
                Triangle2D triangle2D5 = new Triangle2D(findNearestEdge.b, noneEdgeVertex2, this.pointSet.get(i));
                this.triangleSoup.add(triangle2D2);
                this.triangleSoup.add(triangle2D3);
                this.triangleSoup.add(triangle2D4);
                this.triangleSoup.add(triangle2D5);
                legalizeEdge(triangle2D2, new Edge2D(findNearestEdge.a, noneEdgeVertex), this.pointSet.get(i));
                legalizeEdge(triangle2D3, new Edge2D(findNearestEdge.b, noneEdgeVertex), this.pointSet.get(i));
                legalizeEdge(triangle2D4, new Edge2D(findNearestEdge.a, noneEdgeVertex2), this.pointSet.get(i));
                legalizeEdge(triangle2D5, new Edge2D(findNearestEdge.b, noneEdgeVertex2), this.pointSet.get(i));
            } else {
                Vector2D vector2D3 = findContainingTriangle.a;
                Vector2D vector2D4 = findContainingTriangle.b;
                Vector2D vector2D5 = findContainingTriangle.c;
                this.triangleSoup.remove(findContainingTriangle);
                Triangle2D triangle2D6 = new Triangle2D(vector2D3, vector2D4, this.pointSet.get(i));
                Triangle2D triangle2D7 = new Triangle2D(vector2D4, vector2D5, this.pointSet.get(i));
                Triangle2D triangle2D8 = new Triangle2D(vector2D5, vector2D3, this.pointSet.get(i));
                this.triangleSoup.add(triangle2D6);
                this.triangleSoup.add(triangle2D7);
                this.triangleSoup.add(triangle2D8);
                legalizeEdge(triangle2D6, new Edge2D(vector2D3, vector2D4), this.pointSet.get(i));
                legalizeEdge(triangle2D7, new Edge2D(vector2D4, vector2D5), this.pointSet.get(i));
                legalizeEdge(triangle2D8, new Edge2D(vector2D5, vector2D3), this.pointSet.get(i));
            }
        }
        this.triangleSoup.removeTrianglesUsing(triangle2D.a);
        this.triangleSoup.removeTrianglesUsing(triangle2D.b);
        this.triangleSoup.removeTrianglesUsing(triangle2D.c);
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i2 < this.pointSet.size() - 1) {
                Vector2D vector2D6 = new Vector2D(this.pointSet.get(i2).x, this.pointSet.get(i2).y);
                int i3 = i2 + 1;
                Vector2D vector2D7 = new Vector2D(this.pointSet.get(i3).x, this.pointSet.get(i3).y);
                if (i2 == this.pointSet.size() - 2) {
                    vector2D = new Vector2D(this.pointSet.get(0).x, this.pointSet.get(0).y);
                } else {
                    int i4 = i2 + 2;
                    vector2D = new Vector2D(this.pointSet.get(i4).x, this.pointSet.get(i4).y);
                }
                Iterator<Triangle2D> it = this.triangleSoup.triangleSoup.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Triangle2D next = it.next();
                    if (next.hasEdge(new Edge2D(vector2D6, vector2D7)) && next.hasEdge(new Edge2D(vector2D7, vector2D))) {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    this.triangleSoup.add(new Triangle2D(vector2D6, vector2D7, vector2D));
                }
                i2 = i3;
            } else {
                return;
            }
        }
    }

    private void legalizeEdge(Triangle2D triangle2D, Edge2D edge2D, Vector2D vector2D) {
        Triangle2D findNeighbour = this.triangleSoup.findNeighbour(triangle2D, edge2D);
        if (findNeighbour != null) {
            Vector2D noneEdgeVertex = findNeighbour.getNoneEdgeVertex(edge2D);
            int i = 0;
            boolean z = false;
            for (int i2 = 0; i2 < this.pointSet.size() - 1; i2++) {
                if (this.pointSet.get(i2).x == noneEdgeVertex.x && this.pointSet.get(i2).y == noneEdgeVertex.y) {
                    int i3 = i2 + 1;
                    if (this.pointSet.get(i3).x == vector2D.x && this.pointSet.get(i3).y == vector2D.y) {
                        z = true;
                    }
                }
                if (this.pointSet.get(i2).x == vector2D.x && this.pointSet.get(i2).y == vector2D.y) {
                    int i4 = i2 + 1;
                    if (this.pointSet.get(i4).x == noneEdgeVertex.x && this.pointSet.get(i4).y == noneEdgeVertex.y) {
                        z = true;
                    }
                }
            }
            while (i < this.pointSet.size() - 1) {
                if (this.pointSet.get(i).x == edge2D.a.x && this.pointSet.get(i).y == edge2D.a.y) {
                    int i5 = i + 1;
                    if (this.pointSet.get(i5).x == edge2D.b.x && this.pointSet.get(i5).y == edge2D.b.y) {
                        return;
                    }
                }
                int i6 = i + 1;
                if (this.pointSet.get(i6).x != edge2D.a.x || this.pointSet.get(i6).y != edge2D.a.y || this.pointSet.get(i).x != edge2D.b.x || this.pointSet.get(i).y != edge2D.b.y) {
                    i = i6;
                } else {
                    return;
                }
            }
            if (z || findNeighbour.isPointInCircumcircle(vector2D)) {
                this.triangleSoup.remove(triangle2D);
                this.triangleSoup.remove(findNeighbour);
                Triangle2D triangle2D2 = new Triangle2D(noneEdgeVertex, edge2D.a, vector2D);
                Triangle2D triangle2D3 = new Triangle2D(noneEdgeVertex, edge2D.b, vector2D);
                this.triangleSoup.add(triangle2D2);
                this.triangleSoup.add(triangle2D3);
                legalizeEdge(triangle2D2, new Edge2D(noneEdgeVertex, edge2D.a), vector2D);
                legalizeEdge(triangle2D3, new Edge2D(noneEdgeVertex, edge2D.b), vector2D);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(this.pointSet);
    }

    public void shuffle(int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            arrayList.add(this.pointSet.get(i));
        }
        this.pointSet = arrayList;
    }

    public List<Vector2D> getPointSet() {
        return this.pointSet;
    }

    public List<Triangle2D> getTriangles() {
        return this.triangleSoup.getTriangles();
    }
}