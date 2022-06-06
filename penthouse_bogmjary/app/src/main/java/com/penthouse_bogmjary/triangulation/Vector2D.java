package com.penthouse_bogmjary.triangulation;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public Vector2D sub(Vector2D vector2D) {
        return new Vector2D(this.x - vector2D.x, this.y - vector2D.y);
    }

    public Vector2D add(Vector2D vector2D) {
        return new Vector2D(this.x + vector2D.x, this.y + vector2D.y);
    }

    public Vector2D mult(double d) {
        return new Vector2D(this.x * d, this.y * d);
    }

    public double mag() {
        double d = this.x;
        double d2 = this.y;
        return Math.sqrt((d * d) + (d2 * d2));
    }

    public double dot(Vector2D vector2D) {
        return (this.x * vector2D.x) + (this.y * vector2D.y);
    }

    public double cross(Vector2D vector2D) {
        return (this.y * vector2D.x) - (this.x * vector2D.y);
    }

    public String toString() {
        return "Vector2D[" + this.x + ", " + this.y + "]";
    }
}