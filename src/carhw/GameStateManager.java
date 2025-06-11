/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carhw;

/**
 *
 * @author 16478
 */

/**
 * The GameStateManager class manages the game's state information:
 * including score, health, win, and loss status.
 */
public class GameStateManager {
    private int score;       // The current score
    private int health;      // The current health value
    private boolean gameOver; // Whether the game is over
    private boolean gameWon;  // Whether the game has been won

    /**
     * Constructor: initializes score, health, and win/loss status.
     */
    public GameStateManager() {
        this.score = 0;
        this.health = 5;
        this.gameOver = false;
        this.gameWon = false;
    }

    /**
     * Increments the score by 1 each time it is called.
     * If score reaches 10 and health is above zero, sets win status to true.
     */
    public void incrementScore() {
        score++;
        if (score >= 10 && health > 0) {
            gameWon = true;
        }
    }

    /**
     * Decreases health by 1 each time it is called.
     * If health drops to zero or below, sets game over status to true.
     */
    public void decreaseHealth() {
        health--;
        if (health <= 0) {
            gameOver = true;
        }
    }

    /**
     * Returns whether the game is over.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns whether the game has been won.
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Returns the current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the current health value.
     */
    public int getHealth() {
        return health;
    }
}