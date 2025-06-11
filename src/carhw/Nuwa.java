/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carhw;
import processing.core.PApplet;
import processing.core.PImage;
/**
 *
 * @author 16478
 */

/**
 * Nuwa class represents the player-controlled character "Nuwa" in the game,
 * providing functionality for movement, rendering, collision detection, and health management.
 */
public class Nuwa {
    private int x, y; // Nuwa's position on screen
    private int speed; // Nuwa's movement speed
    private int health; // Nuwa's current health value
    private PApplet app; // Reference to the Processing application instance
    private PImage img; // Nuwa's image resource
    public static int nuwaCount = 0;  // Static counter tracking how many Nuwa instances have been created

    /**
     * Constructor with default speed of 5.
     * @param app the Processing application instance
     * @param x   the starting x-coordinate
     * @param y   the starting y-coordinate
     */
    public Nuwa(PApplet app, int x, int y) {
        this(app, x, y, 5);
    }

    /**
     * Constructor: initializes position, speed, and loads the image.
     * @param app   the Processing application instance
     * @param x     the starting x-coordinate
     * @param y     the starting y-coordinate
     * @param speed the movement speed for Nuwa
     */
    public Nuwa(PApplet app, int x, int y, int speed) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = 3;
        this.img = app.loadImage("Nuwa.png");
        nuwaCount++;  // Increment the total count of Nuwa instances
    }

    /**
     * Returns Nuwa's current health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Moves Nuwa by dx, dy multiplied by speed and constrains position within the screen bounds.
     * @param dx horizontal movement direction (-1, 0, or 1)
     * @param dy vertical movement direction (-1, 0, or 1)
     */
    public void move(int dx, int dy) {
        x += dx * speed;
        y += dy * speed;
        x = app.constrain(x, 0, app.width - 50);
        y = app.constrain(y, 0, app.height - 50);
    }

    /**
     * Renders the Nuwa image at its current position.
     */
    public void draw() {
        app.image(img, x, y);
    }

    /**
     * Checks collision with any GameObject using rectangular overlap logic.
     * @param obj the GameObject to check collision against
     * @return true if this Nuwa collides with the given GameObject
     */
    public boolean collidesWith(GameObject obj) {
        boolean isLeftOfOtherRight = x < obj.getX() + 40;
        boolean isRightOfOtherLeft = x + 40 > obj.getX();
        boolean isAboveOtherBottom = y < obj.getY() + 40;
        boolean isBelowOtherTop = y + 40 > obj.getY();

        return isLeftOfOtherRight && isRightOfOtherLeft
            && isAboveOtherBottom && isBelowOtherTop;
    }

    /**
     * Reduces health by one (used when colliding with fire obstacles).
     */
    public void reduceHealth() {
        health--;
    }

    /**
     * Returns Nuwa's current x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns Nuwa's current y-coordinate.
     */
    public int getY() {
        return y;
    }
}