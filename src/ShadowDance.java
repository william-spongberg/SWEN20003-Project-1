import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SWEN20003 Project 1, Semester 2, 2023
 * 
 * @author William Spongberg
 */
public class ShadowDance extends AbstractGame {
    // window dimensions, refresh rate, background and title
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String REFRESH_RATE = "-60"; // set to empty for 120hz
    private final Image IMAGE_BACKGROUND = new Image("res/background.png");
    private final static String GAME_TITLE = "SHADOW DANCE";

    // game logic booleans
    private boolean paused = false;
    private boolean started = true;
    private boolean ended = false;

    // frame counter, grading frame counter and score
    private int frame = 0;
    private static final int GRADE_FRAMES = 30;
    private int frames_grading = 0;
    private int current_grade = 0;
    private int score = 0;

    // display text
    DisplayText disp = new DisplayText();

    // array list of levels
    private List<Level> levels = new ArrayList<Level>();

    // file level names
    private static final String FILE_LEVEL1 = "res/levels/level1"+REFRESH_RATE+".csv";

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);

        // add levels
        this.levels.add(readCSV(FILE_LEVEL1));
        // etc
    }

    /**
     * Method used to read files and create level objects.
     */
    private Level readCSV(String fileName) {
        // read csv level files from res into arraylist of arrays of strings
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // read until end of file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
            // catch and print errors
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Level(records);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * Allows the game to pause when the tab key is pressed.
     * 
     * Contains game state logic.
     * 
     * Refreshed at user monitor refresh rate (typically 60Hz, but
     * will be marked on a 120hz monitor).
     */
    @Override
    protected void update(Input input) {
        // draw background
        disp.drawBackground(IMAGE_BACKGROUND);

        // allow user to exit
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // pause logic
        if (input.wasPressed(Keys.TAB)) {
            if (!paused)
                paused = true;
            else
                paused = false;
        }

        // game state logic
        if (paused) {
            disp.drawPauseScreen();
        } else {
            // if space pressed start game
            if (input.wasPressed(Keys.SPACE)) {
                started = false;
            }

            // if game started
            if (started) {
                disp.drawStartScreen();
            } else if (ended) { // if game ended
                disp.drawEndScreen(score);
                // if space pressed start game again
                if (input.wasPressed(Keys.SPACE)) {
                    // game restart [TO DO]
                }
            // if game in progress
            } else {
                // increment frame counter
                frame++;

                // draw frame counter [testing]
                /* */
                disp.drawFrame(frame);
                /* */

                // update levels
                for (Level level : this.levels) {
                    if (level.isActive()) {
                        // draw score
                        disp.drawScore(level.getScore());

                        // update level
                        level.update(frame, input);
                        

                        // if grade to display
                        if (level.getGrade() != 0) {
                            frames_grading = GRADE_FRAMES;
                            current_grade = level.getGrade();
                        }

                        // if frames left to display grade
                        if (frames_grading > 0) {
                            disp.drawGrade(current_grade);
                            frames_grading--;
                        }
                        else {
                            // reset grade
                            current_grade = 0;
                        }
                        // add score to total score
                        this.score += level.getScore();

                        // if level ended, go to end screen
                        if (!level.isActive()) {
                            ended = true;
                            score = level.getScore();
                        }
                    }
                }
            }
        }
    }
    public static final int getWidth() {
        return WINDOW_WIDTH;
    }

    public static final int getHeight() {
        return WINDOW_HEIGHT;
    }
}
