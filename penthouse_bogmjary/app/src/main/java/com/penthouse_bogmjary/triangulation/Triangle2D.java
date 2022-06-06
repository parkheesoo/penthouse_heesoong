package com.penthouse_bogmjary.triangulation;

import java.util.Arrays;

public class Triangle2D {
    public Vector2D a;
    public Vector2D b;
    public Vector2D c;

    public Triangle2D(Vector2D vector2D, Vector2D vector2D2, Vector2D vector2D3) {
        this.a = vector2D;
        this.b = vector2D2;
        this.c = vector2D3;
    }

    public boolean contains(Vector2D vector2D) {
        double cross = vector2D.sub(this.a).cross(this.b.sub(this.a));
        if (hasSameSign(cross, vector2D.sub(this.b).cross(this.c.sub(this.b))) && hasSameSign(cross, vector2D.sub(this.c).cross(this.a.sub(this.c)))) {
            return true;
        }
        return false;
    }

    public boolean isPointInCircumcircle(Vector2D vector2D) {
        double d = this.a.x - vector2D.x;
        double d2 = this.b.x - vector2D.x;
        double d3 = this.c.x - vector2D.x;
        double d4 = this.a.y - vector2D.y;
        double d5 = this.b.y - vector2D.y;
        double d6 = this.c.y - vector2D.y;
        double d7 = ((this.a.x - vector2D.x) * (this.a.x - vector2D.x)) + ((this.a.y - vector2D.y) * (this.a.y - vector2D.y));
        double d8 = ((this.b.x - vector2D.x) * (this.b.x - vector2D.x)) + ((this.b.y - vector2D.y) * (this.b.y - vector2D.y));
        double d9 = ((this.c.x - vector2D.x) * (this.c.x - vector2D.x)) + ((this.c.y - vector2D.y) * (this.c.y - vector2D.y));
        double d10 = ((((((d * d5) * d9) + ((d4 * d8) * d3)) + ((d7 * d2) * d6)) - ((d7 * d5) * d3)) - ((d4 * d2) * d9)) - ((d * d8) * d6);
        return isOrientedCCW() ? d10 > 0.0d : d10 < 0.0d;
    }

    public boolean isOrientedCCW() {
        return ((this.a.x - this.c.x) * (this.b.y - this.c.y)) - ((this.a.y - this.c.y) * (this.b.x - this.c.x)) > 0.0d;
    }

    public boolean isNeighbour(Edge2D edge2D) {
        return (this.a == edge2D.a || this.b == edge2D.a || this.c == edge2D.a) && (this.a == edge2D.b || this.b == edge2D.b || this.c == edge2D.b);
    }

    public Vector2D getNoneEdgeVertex(Edge2D edge2D) {
        if (this.a != edge2D.a && this.a != edge2D.b) {
            return this.a;
        }
        if (this.b != edge2D.a && this.b != edge2D.b) {
            return this.b;
        }
        if (this.c == edge2D.a || this.c == edge2D.b) {
            return null;
        }
        return this.c;
    }

    public boolean hasVertex(Vector2D vector2D) {
        return this.a == vector2D || this.b == vector2D || this.c == vector2D;
    }

    public boolean hasEdge(Edge2D edge2D) {
        if (edge2D.a.x == this.a.x && edge2D.a.y == this.a.y && edge2D.b.x == this.b.x && edge2D.b.y == this.b.y) {
            return true;
        }
        if (edge2D.a.x == this.b.x && edge2D.a.y == this.b.y && edge2D.b.x == this.a.x && edge2D.b.y == this.a.y) {
            return true;
        }
        if (edge2D.a.x == this.a.x && edge2D.a.y == this.a.y && edge2D.b.x == this.c.x && edge2D.b.y == this.c.y) {
            return true;
        }
        if (edge2D.a.x == this.c.x && edge2D.a.y == this.c.y && edge2D.b.x == this.a.x && edge2D.b.y == this.a.y) {
            return true;
        }
        if (edge2D.a.x == this.b.x && edge2D.a.y == this.b.y && edge2D.b.x == this.c.x && edge2D.b.y == this.c.y) {
            return true;
        }
        return edge2D.a.x == this.c.x && edge2D.a.y == this.c.y && edge2D.b.x == this.b.x && edge2D.b.y == this.b.y;
    }

    public EdgeDistancePack findNearestEdge(Vector2D vector2D) {
        EdgeDistancePack[] edgeDistancePackArr = {new EdgeDistancePack(new Edge2D(this.a, this.b), computeClosestPoint(new Edge2D(this.a, this.b), vector2D).sub(vector2D).mag()), new EdgeDistancePack(new Edge2D(this.b, this.c), computeClosestPoint(new Edge2D(this.b, this.c), vector2D).sub(vector2D).mag()), new EdgeDistancePack(new Edge2D(this.c, this.a), computeClosestPoint(new Edge2D(this.c, this.a), vector2D).sub(vector2D).mag())};
        Arrays.sort(edgeDistancePackArr);
        return edgeDistancePackArr[0];
    }

    private Vector2D computeClosestPoint(Edge2D edge2D, Vector2D vector2D) {
        Vector2D sub = edge2D.b.sub(edge2D.a);
        double dot = vector2D.sub(edge2D.a).dot(sub) / sub.dot(sub);
        if (dot < 0.0d) {
            dot = 0.0d;
        } else if (dot > 1.0d) {
            dot = 1.0d;
        }
        return edge2D.a.add(sub.mult(dot));
    }

    private boolean hasSameSign(double d, double d2) {
        return Math.signum(d) == Math.signum(d2);
    }

    public String toString() {
        return "Triangle2D[" + this.a + ", " + this.b + ", " + this.c + "]";
    }
}