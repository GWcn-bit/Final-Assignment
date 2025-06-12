/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carhw;
import processing.core.PApplet;
/**
 *
 * @author 16478
 */
/**
 * Obstacle class represents a moving obstacle in the game,
 * extending GameObject and providing velocity and bounce logic.
 */
public class Obstacle extends GameObject {
    private int dx, dy; // Movement speed in the x and y directions (sign indicates direction)

    /**
     * Constructor: creates an obstacle object and sets its random movement direction.
     * @param app   the Processing PApplet instance
     * @param x     the initial x-coordinate
     * @param y     the initial y-coordinate
     * @param speed the maximum movement speed (range from -speed to +speed)
     */
    public Obstacle(PApplet app, int x, int y, int speed) {
        super(app, x, y, "fire.png");

        dx = 0;
        dy = 0;
        // Ensure at least one direction has non-zero velocity (cannot remain stationary)
        while (dx == 0 && dy == 0) {
            dx = (int) app.random(-speed, speed + 1);
            dy = (int) app.random(-speed, speed + 1);
        }
    }

    /**
     * Renders the obstacle image to the window.
     */
    @Override
    public void draw() {
        getApp().image(getImg(), getX(), getY());
    }

    /**
     * Updates the obstacle's position and checks for boundary collisions.
     * If a boundary is hit, the direction is reversed (bounce effect).
     */
    @Override
    public void update() {
        // Move position
        setX(getX() + dx);
        setY(getY() + dy);

        // Reverse x-direction if hitting left or right boundary (from chatgpt)
        if (getX() <= 0 || getX() >= getApp().width - getSize()) {
            dx *= -1;
        }
        // Reverse y-direction if hitting top or bottom boundary
        if (getY() <= 0 || getY() >= getApp().height - getSize()) {
            dy *= -1;
        }
    }
}

