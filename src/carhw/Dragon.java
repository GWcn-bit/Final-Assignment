/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carhw;
import processing.core.PApplet;

public class Dragon extends GameObject {
    private int speed; // Dragon's movement speed
    private float dx = 1; // Horizontal velocity (unit vector)
    private float dy = 1; // Velocity in vertical direction (unit vector)

    /**
     * Constructor: initialize the dragon's position, speed and image
     * @param app Processing
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     * @param speed Dragon's movement speed
     * @param imagePath Image File Path
     */
    public Dragon(PApplet app, int x, int y, int speed, String imagePath) {
        super(app, x, y, imagePath);
        this.speed = speed;
        randomizeDirection(); // Initial random direction
    }

    /**
     * Drawing images of dragons on the screen
     */
    @Override
    public void draw() {
        getApp().image(getImg(), getX(), getY());
    }

    /**
     * Updates the dragon's position every frame and handles boundary detection
     * Changes direction of motion every 60 frames
     */
    @Override
    public void update() {
        // Random change of direction every second (60 frames)
        if (getApp().frameCount % 60 == 0) {
            randomizeDirection();
        }

        // Dragons move
        setX((int) (getX() + dx * speed));
        setY((int) (getY() + dy * speed));

        // Boundary handling: stop at boundary and change direction if boundary is exceeded
        if (getX() < 0) {
            setX(0);
            randomizeDirection();
        } else if (getX() > getApp().width - getImg().width) {
            setX(getApp().width - getImg().width);
            randomizeDirection();
        }

        if (getY() < 0) {
            setY(0);
            randomizeDirection();
        } else if (getY() > getApp().height - getImg().height) {
            setY(getApp().height - getImg().height);
            randomizeDirection();
        }
    }

    /**
     * Randomly generate a new direction vector dx/dy to avoid approaching 0 and causing stationarity.
     */
    private void randomizeDirection() {
        dx = getApp().random(-3, 2);
        dy = getApp().random(-3, 2);

        // Avoid stopping with too little direction
        if (Math.abs(dx) < 0.1f) dx = 0.5f;
        if (Math.abs(dy) < 0.1f) dy = 0.5f;
    }
}
