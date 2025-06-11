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
 * The Fire class represents the flaming obstacles in the game。
 * It extends from the Obstacle class and represents an object that moves and collides.
 */
public class Fire extends Obstacle {
     /**
     * Constructor: creates a flame object with a default speed of 5
     * @param app Processing
     * @param x Initial x-coordinate of the flame
     * @param  y Initial y-coordinate of the flame
     */
    public Fire(PApplet app, int x, int y) {
        super(app, x, y, 5); // Call the parent Obstacle constructor and set the speed to 5
    }

    /**
     * Updates the position of the flame every frame, extends from Obstacle's update.
     */
    @Override
    public void update() {
        super.update(); // Update logic using parent class Obstacle (move and bounce)
    }

     /**
     * Drawing fire images
     */
    @Override
    public void draw() {
        super.draw(); // Calling parent class drawing methods
    }
}
