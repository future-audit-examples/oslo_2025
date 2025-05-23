package com.futureauditexamples.Example_SteeringBehaviors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SteeringBehaviors extends JPanel implements ActionListener {

    private static final int FPS = 60;
    private static final double SEPARATION_STRENGTH = 0.6;

    // Chasers slow down inside this radius
    private static final double SEEK_SLOW_RADIUS = 150;

    // Runner flees inside this radius
    private static final double FLEE_PANIC_RADIUS = 200;

    // Too close
    private static final double SEPARATION_RADIUS = 80;

    private final Agent runner;
    private final Agent seekerA;
    private final Agent seekerB;

    public SteeringBehaviors(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        runner = new Agent(new Vector2D(width * 0.7, height * 0.6), 5, 0.30);
        seekerA = new Agent(new Vector2D(width * 0.2, height * 0.3), 6, 0.35);
        seekerB = new Agent(new Vector2D(width * 0.8, height * 0.2), 6, 0.35);
        Timer timer = new Timer(1000 / FPS, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Runner decides whether to flee
        fleeIfNear(seekerA);
        fleeIfNear(seekerB);

        // Chasers seek runner
        seekWithArrival(seekerA);
        seekWithArrival(seekerB);

        // Keep chasers from sitting on top of each other
        separate(seekerA, seekerB);

        runner.update();
        seekerA.update();
        seekerB.update();
        Dimension d = getSize();
        runner.wrap(d.width, d.height);
        seekerA.wrap(d.width, d.height);
        seekerB.wrap(d.width, d.height);

        repaint();
    }

    private void fleeIfNear(Agent threat) {
        Vector2D away = runner.position.subtract(threat.position);
        double distance = away.length();
        if (distance < FLEE_PANIC_RADIUS) {
            Vector2D desired = away.normalize().multiply(runner.maxSpeed);
            runner.applyForce(desired.subtract(runner.velocity));
        }
    }

    private void seekWithArrival(Agent hunter) {
        Vector2D toTarget = runner.position.subtract(hunter.position);
        double dist = toTarget.length();
        double desiredSpd = hunter.maxSpeed;

        if (dist < SEEK_SLOW_RADIUS) {
            desiredSpd = hunter.maxSpeed * (dist / SEEK_SLOW_RADIUS);
        }
        Vector2D desiredVel = toTarget.normalize().multiply(desiredSpd);
        hunter.applyForce(desiredVel.subtract(hunter.velocity));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillOval((int) runner.position.x - 10, (int) runner.position.y - 10, 20, 20);

        g.setColor(Color.GREEN);
        g.fillOval((int) seekerA.position.x - 10, (int) seekerA.position.y - 10, 20, 20);

        g.setColor(Color.GREEN);
        g.fillOval((int) seekerB.position.x - 10, (int) seekerB.position.y - 10, 20, 20);
    }

    private void separate(Agent a, Agent b) {
        Vector2D diff = a.position.subtract(b.position);
        double d = diff.length();

        // Too close
        if (d > 0 && d < SEPARATION_RADIUS) {
            double scale = (1 - d / SEPARATION_RADIUS) * SEPARATION_STRENGTH;
            Vector2D force = diff.normalize().multiply(scale * a.maxForce);

            // Push a away from b
            a.applyForce(force);

            // Push b away from a
            b.applyForce(force.multiply(-1));
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 1000;
        int height = 700;
        f.add(new SteeringBehaviors(width, height));
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
