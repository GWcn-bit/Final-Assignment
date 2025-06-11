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
 * GameObject is the abstract base class for all game objects, providing basic position,
 * image handling, sizing, and collision detection functionality.
 * All game characters or obstacles (such as Nuwa, Dragon, Fire) should inherit from this class.
 */
public abstract class GameObject {
    private int x, y;           // The object's coordinates on the screen
    private int size = 40;      // Default size (used for collision detection)
    private PApplet app;        // Processing application instance, used for rendering
    private PImage img;         // The object's image resource

    /**
     * Constructor: initializes position and loads the image.
     * @param app Processing application object
     * @param x   The x-coordinate
     * @param y   The y-coordinate
     * @param imagePath Path to the image file
     */
    public GameObject(PApplet app, int x, int y, String imagePath) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.img = app.loadImage(imagePath);
    }

    /**
     * Abstract draw method: subclasses must provide rendering logic.
     */
    public abstract void draw();

    /**
     * Abstract update method: subclasses must provide update logic.
     */
    public abstract void update();
    /**
     * Collision detection logic: uses a rectangular bounding box.
     * @param nuwa The Nuwa object to check against
     * @return true if this object collides with the Nuwa object
     */
    public boolean collidesWith(Nuwa nuwa) {
        return x < nuwa.getX() + size &&
               x + size > nuwa.getX() &&
               y < nuwa.getY() + size &&
               y + size > nuwa.getY();
    }

    // Getter and setter methods
    public int getX(){ 
        return x; 
    }
    public void setX(int x){ 
        this.x = x; 
    }

    public int getY(){ 
        return y; 
    }
    public void setY(int y){ 
        this.y = y; 
    }

    public int getSize(){ 
        return size; 
    }
    public void setSize(int size){ 
        this.size = size; 
    }

    public PApplet getApp(){ 
        return app; 
    }
    public void setApp(PApplet app){ 
        this.app = app; 
    }

    public PImage getImg(){ 
        return img; 
    }
    public void setImg(PImage img){ 
        this.img = img; 
    }
}
