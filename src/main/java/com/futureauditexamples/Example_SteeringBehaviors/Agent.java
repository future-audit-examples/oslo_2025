package com.futureauditexamples.Example_SteeringBehaviors;

public class Agent {
    public Vector2D position;
    public Vector2D velocity     = new Vector2D(0, 0);
    private Vector2D acceleration = new Vector2D(0, 0);

    public final double maxSpeed;   // pixels / frame
    public final double maxForce;   // pixels / frame²

    public Agent(Vector2D start, double maxSpeed, double maxForce) {
        this.position  = start;
        this.maxSpeed  = maxSpeed;
        this.maxForce  = maxForce;
    }

    public void applyForce(Vector2D f) {        // accumulate (steering) force
        acceleration = acceleration.add(f.limit(maxForce));
    }

    public void update() {                      // integrate once per frame
        velocity = velocity.add(acceleration).limit(maxSpeed);
        position = position.add(velocity);
        acceleration = new Vector2D(0, 0);      // clear for next frame
    }

    /* simple screen-wrap so the agents never disappear */
    public void wrap(double w, double h) {
        if (position.x < 0)  position.x += w;
        if (position.x > w)  position.x -= w;
        if (position.y < 0)  position.y += h;
        if (position.y > h)  position.y -= h;
    }
}