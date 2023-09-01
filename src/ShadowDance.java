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
    // window dimensions, refresh rate, title and background
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String _60 = "-60";
    private final static String _120 = "-120";
    private final static String REFRESH_RATE = _60; /* NOTE TO MARKERS: change to _120 for 120hz */
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image IMAGE_BACKGROUND = new Image("res/background.png");

    // file level names
    private static final String FILE_LEVEL1 = "res/levels/test1" + REFRESH_RATE + ".csv";
    private static final String FILE_LEVEL2 = "res/levels/level1" + REFRESH_RATE + ".csv";

    // game logic constants
    private static final int GRADE_FRAMES = 30;
    private static final int WIN_SCORE = 150;

    // display text
    DisplayText disp = new DisplayText();

    // array list of levels, current level
    private List<Level> levels = new ArrayList<Level>();
    private Level currentLevel;

    // game logic booleans
    private boolean paused = false;
    private boolean started = true;
    private boolean ended = false;
    private Boolean level_ended = false;  
    private static boolean refresh_60 = false;

    // frame counter, grade frames counter, current grade, score, level number
    private int frame = 0;
    private int frames_grading = 0;
    private int current_grade = 0;
    private int score = 0;
    private int level_num = 0;
      

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);

        // add levels
        this.levels.add(readCSV(FILE_LEVEL1));
        this.levels.add(readCSV(FILE_LEVEL2));
        // etc
    }

    /**
     * Method used to read files and create level objects.
     */
    private Level readCSV(String fileName) {
        // if refresh rate is 60hz
        if (fileName.contains(_60)) {
            refresh_60 = true;
        }
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

                // if "r" pressed restart game
                restartGame(input);

            // if game in progress
            } else {
                // if "r" pressed restart game
                restartGame(input);

                // increment frame counter
                frame++;

                // draw frame counter [testing]
                /*
                 * disp.drawFrame(frame);
                 */

                // get current level, activate if not ended
                currentLevel = levels.get(level_num);
                if (!level_ended)
                    currentLevel.setActive(true);

                // if current level is running
                if (currentLevel.isActive()) {
                    // draw score
                    disp.drawScore(currentLevel.getScore());

                    // update level
                    currentLevel.update(frame, input);

                    // if there is a grade to display
                    if (currentLevel.getGrade() != 0) {
                        frames_grading = GRADE_FRAMES;
                        current_grade = currentLevel.getGrade();
                    }

                    // if there are frames left to display grade
                    if (frames_grading > 0) {
                        disp.drawGrade(current_grade);
                        frames_grading--;
                    } else {
                        // reset grade
                        current_grade = 0;
                    }

                    // if level no longer active
                    if (!currentLevel.isActive()) {
                        // end level, caclulate score
                        level_ended = true;
                        
                        if (currentLevel.getScore() >= WIN_SCORE) {
                            currentLevel.setWin(true);
                            score += currentLevel.getScore();
                        } else {
                            currentLevel.setWin(false);
                        }
                    }
                }
                // if level ended, display win/lose/end screen
                if (level_ended) {
                    if (currentLevel.hasWin()) {
                        disp.drawWinScreen(currentLevel.getScore(), score);
                        // if space pressed go to next level
                        if (input.wasPressed(Keys.SPACE)) {
                            level_ended = false;
                            // if there are more levels
                            if (levels.size() - level_num > 1) {
                                level_num++;
                                frame = 0;
                                frames_grading = 0;
                            // if no more levels, go to end screen
                            } else {
                                ended = true;
                            }
                        }
                    } else {
                        disp.drawLoseScreen(currentLevel.getScore());
                        // if space pressed try level again
                        if (input.wasPressed(Keys.SPACE)) {
                            level_ended = false;
                            currentLevel.setScore(0);
                            frame = 0;
                            frames_grading = 0;
                            currentLevel.reset(currentLevel);
                        }
                    }
                }
            }
        }
    }

    private void restartGame(Input input) {
        if (input.wasPressed(Keys.R)) {
            this.started = true;
            this.ended = false;
            this.score = 0;
            this.frame = 0;
            this.frames_grading = 0;
            for (Level level : this.levels) {
                level.reset(level);
            }
        }
    }

    public static final int getWidth() {
        return WINDOW_WIDTH;
    }

    public static final int getHeight() {
        return WINDOW_HEIGHT;
    }

    public static final Boolean hasRefresh60() {
        return refresh_60;
    }
}
