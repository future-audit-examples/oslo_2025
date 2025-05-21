package com.futureauditexamples.Example_SteeringBehaviors;

public class Vector2D {
    public double x, y;

    public Vector2D(double x, double y) { this.x = x; this.y = y; }

    public Vector2D add(Vector2D v)         { return new Vector2D(x + v.x, y + v.y); }
    public Vector2D subtract(Vector2D v)    { return new Vector2D(x - v.x, y - v.y); }
    public Vector2D multiply(double k)      { return new Vector2D(x * k, y * k); }

    public double length()                  { return Math.hypot(x, y); }

    public Vector2D normalize() {
        double len = length();
        return (len > 0) ? new Vector2D(x / len, y / len) : new Vector2D(0, 0);
    }

    public Vector2D limit(double max) {
        return (length() > max) ? normalize().multiply(max) : this;
    }
}
