/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package carhw;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author 16478
 */
public class GameMain extends PApplet {
    // Game character objects
    private Nuwa nuwa;
    private Dragon dragon;

    // Collection of flame obstacles
    private ArrayList<Obstacle> obstacles;

    // Game state manager (score, health, win/loss determination)
    private GameStateManager gameState;

    // Collision detection state
    private boolean hasCollidedWithDragon = false;

    // Background image resource
    private PImage backgroundImg;

    // Game stage: 0 = input name/speed, 1 = start game
    private int stage = 0;

    // Player name input
    private String playerName = "";
    private boolean nameEntered = false;

    // Input array: [0] = dragon speed, [1] = fire speed
    private String[] speedInputs = {"", ""};
    private int currentInput = 0;

    // Final speed parameters
    private int dragonSpeed = 2;
    private int fireSpeed = 2;

    // High-score table (2D array) & file I/O
    private String[][] highScores = new String[5][2];
    private static final String HS_FILE = "highscores.txt";
    private boolean highScoresUpdated = false;
    
    

    public static void main(String[] args) {
        PApplet.main("carhw.GameMain");
    }

    // Set window size
    public void settings() {
        size(626, 434);
    }

    // Initialize content
    public void setup() {
        obstacles = new ArrayList<>();
        gameState = new GameStateManager();
        backgroundImg = loadImage("Lv3.png");
        frameRate(60);
        textSize(20);
        loadHighScores(); // File input: read existing highscores
    }

    // Main game loop
    public void draw() {
        background(backgroundImg);

        // Name and speed input screen
        if (stage == 0) {
            fill(0);
            drawScores(); // Show high-score table only on main screen
            
            text("Rules: Touch the dragon 10 times to win, avoid the flames!", 50, 240);
            text("The game fails when the life value is 0.", 50, 270);

            // Name entry
            text("Enter Your Name:", 50, 100);
            text(playerName + " ", 200, 100);

            // Speed entry
            text("Enter Dragon Speed:", 50, 140);
            text(speedInputs[0] + " ", 300, 140);
            text("Enter Fire Speed:", 50, 180);
            text(speedInputs[1] + " ", 300, 180);

            // Prompt
            if (!nameEntered) {
                text("Press ENTER to confirm name", 50, 220);
            } else {
                text("Press ENTER to continue", 50, 220);
            }

            // Indicator arrow for current input after name entered
            if (nameEntered) {
                if (currentInput == 0) text("→", 30, 140);
                if (currentInput == 1) text("→", 30, 180);
            }
            return;
        }

        // Game over screen
        if (gameState.isGameOver()) {
            fill(255, 0, 0);
            textSize(32);
            text("Game Over", 240, 200);
            textSize(16);
            text("Press ENTER to restart", 240, 240);

            // Update highscores once on game over
            if (!highScoresUpdated) {
                updateHighScores(playerName, gameState.getScore());
                highScoresUpdated = true;
            }
            return;
        }

        // Victory screen
        if (gameState.isGameWon()) {
            fill(0, 255, 0);
            textSize(32);
            text("Mission Complete!", 240, 200);
            textSize(16);
            text("Press ENTER to restart", 240, 240);

            // Update highscores once on win
            if (!highScoresUpdated) {
                updateHighScores(playerName, gameState.getScore());
                highScoresUpdated = true;
            }
            return;
        }

        // Update and draw characters
        dragon.update();
        dragon.draw();
        nuwa.draw();

        // When player collides with dragon, increment score
        if (nuwa.collidesWith(dragon)) {
            if (!hasCollidedWithDragon) {
                gameState.incrementScore();
                hasCollidedWithDragon = true;
            }
        } else {
            hasCollidedWithDragon = false;
        }

        // Generate flames at intervals
        if (frameCount % 100 == 0) {
            int fx = (int) random(100, width - 100);
            int fy = (int) random(100, height - 100);
            obstacles.add(new Fire(this, fx, fy));
        }

        // Iterate over flame obstacles, update and detect collisions
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            Obstacle o = obstacles.get(i);
            o.update();
            o.draw();
            if (o.collidesWith(nuwa)) {
                gameState.decreaseHealth();
                obstacles.remove(i);
            }
        }

        // Display score and health status
        fill(0);
        text("Score: " + gameState.getScore(), 20, 30);
        text("Health: " + gameState.getHealth(), 20, 50);
    }

    // Keyboard input handler
    public void keyPressed() {
        // Name input stage
        if (stage == 0 && !nameEntered) {
            if (key == ENTER || key == RETURN) {
                if (!playerName.isEmpty()) {
                    nameEntered = true;
                    currentInput = 0;
                }
            } else if (key == BACKSPACE && !playerName.isEmpty()) {
                playerName = playerName.substring(0, playerName.length() - 1);
            } else if (isNumericKey(key) || Character.isLetter(key)) {
                if (playerName.length() < 10) {
                    playerName += key;
                }
            }
            return;
        }

        // Speed input stage
        if (stage == 0 && nameEntered) {
            handleSpeedInput();
            return;
        }

        //️ After game over or victory, press ENTER to restart
        if (gameState.isGameOver() || gameState.isGameWon()) {
            if (key == ENTER || key == RETURN) {
                resetGame();
                stage = 0; // Return to input screen
                nameEntered = false;
                playerName = "";
                highScoresUpdated = false;
            }
            return;
        }

        // In-game movement
        handleNuwaMovement();
    }

    private void resetGame() {
        nuwa = null;
        dragon = null;
        obstacles = new ArrayList<>();
        gameState = new GameStateManager();
        hasCollidedWithDragon = false;
        speedInputs[0] = "";
        speedInputs[1] = "";
        currentInput = 0;
        dragonSpeed = 2;
        fireSpeed = 2;
    }
    //

    // Handle player speed input
    private void handleSpeedInput() {
        if (key == ENTER || key == RETURN) {
             try { //error check
            int v = Integer.parseInt(speedInputs[currentInput]);
            if (v <= 0) {
                JOptionPane.showMessageDialog(null,
                    "Please enter a positive integer for speed.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "Invalid input! Only numbers are allowed.");
            return;
        }
            currentInput++;
            if (currentInput >= speedInputs.length) {
                dragonSpeed = Integer.parseInt(speedInputs[0]);
                fireSpeed = Integer.parseInt(speedInputs[1]);
                nuwa = new Nuwa(this, 100, height / 2);
                dragon = new Dragon(this, 300, height / 2, dragonSpeed, "dragon.png");
                stage = 1;
            }
            return;
        }

        // Numeric key input
        if (isNumericKey(key)) {
            if (speedInputs[currentInput].length() < 2) {
                speedInputs[currentInput] += key;
            }
            return;
        }

        // Delete input
        if (key == BACKSPACE) {
            if (!speedInputs[currentInput].equals("")) {
                speedInputs[currentInput] = speedInputs[currentInput].
                        substring(0, speedInputs[currentInput].length() - 1);
            }
        }
    }

    // Handle Nuwa movement
    private void handleNuwaMovement() {
        if (keyCode == LEFT) {
            nuwa.move(-5, 0);
        } else if (keyCode == RIGHT) {
            nuwa.move(5, 0);
        } else if (keyCode == UP){
            nuwa.move(0, -5);
        } else if (keyCode == DOWN) {
            nuwa.move(0, 5);
        }
    }

    // Check if key is numeric
    private boolean isNumericKey(char k) {
        return k >= '0' && k <= '9';
    } 
    // Draw high-score table (2D array)
    private void drawScores() {
        fill(0);
        textSize(16);
        text("High Scores:", width - 200, 50);
        for (int i = 0; i < highScores.length; i++) {
            text((i+1) + ". " + highScores[i][0] + " — " + highScores[i][1],
                 width - 200, 80 + i * 20);
        }
    }

    // File input: load highscores.txt into 2D array
    private void loadHighScores() {
        for (int i = 0; i < highScores.length; i++) {
            highScores[i][0] = "—";
            highScores[i][1] = "0";
        }
        try {
            Scanner in = new Scanner(new File(sketchPath(HS_FILE)));
            int row = 0;
            while (in.hasNextLine() && row < highScores.length) {
                String[] parts = in.nextLine().split(",", 2);
                if (parts.length == 2) {
                    highScores[row][0] = parts[0];
                    highScores[row][1] = parts[1];
                }
                row++;
            }
            in.close();
        } catch (IOException e) {
            // ignore if file not found
        }
    }

    // File output: save 2D array to highscores.txt
    private void saveHighScores() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(sketchPath(HS_FILE), false));
            for (int i = 0; i < highScores.length; i++) {
                out.printf("%s,%s%n", highScores[i][0], highScores[i][1]);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update highScores array and write to file
    private void updateHighScores(String playerName, int newScore) {
        for (int i = 0; i < highScores.length; i++) {
            int existing = Integer.parseInt(highScores[i][1]);
            if (newScore > existing) {
                for (int j = highScores.length - 1; j > i; j--) {
                    highScores[j][0] = highScores[j-1][0];
                    highScores[j][1] = highScores[j-1][1];
                }
                highScores[i][0] = playerName;
                highScores[i][1] = String.valueOf(newScore);
                saveHighScores();
                break;
            }
        }
    }
}
