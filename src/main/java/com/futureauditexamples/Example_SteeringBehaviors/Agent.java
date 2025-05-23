package com.futureauditexamples.Example_SteeringBehaviors;

public class Agent {

    public Vector2D velocity = new Vector2D(0, 0);
    private Vector2D acceleration = new Vector2D(0, 0);

    public Vector2D position;
    public final double maxSpeed;
    public final double maxForce;

    public Agent(Vector2D start, double maxSpeed, double maxForce) {
        this.position = start;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
    }

    public void applyForce(Vector2D f) {
        acceleration = acceleration.add(f.limit(maxForce));
    }

    public void update() {
        velocity = velocity.add(acceleration).limit(maxSpeed);
        position = position.add(velocity);
        acceleration = new Vector2D(0, 0);
    }

    public void wrap(double w, double h) {
        if (position.x < 0) position.x += w;
        if (position.x > w) position.x -= w;
        if (position.y < 0) position.y += h;
        if (position.y > h) position.y -= h;
    }
}