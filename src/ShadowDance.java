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
    // window dimensions, title and background
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image IMAGE_BACKGROUND = new Image("res/background.png");

    // font
    private final static String FILE_FONT = "res/FSO8BITR.TTF";
    private final Font FONT = new Font(FILE_FONT, 64);
    private final Font FONT_SMALL = new Font(FILE_FONT, 24);

    // title screen strings
    private static final String TITLE = "SHADOW DANCE";
    private static final String START_1 = "PRESS SPACE TO START";
    private static final String START_2 = "USE ARROW KEYS TO PLAY";
    private static final String PAUSED = "PAUSED";

    // file level names
    private static final String FILE_LEVEL1 = "res/test1-60.csv";

    // game logic booleans
    private boolean paused = false;
    private boolean started = true;
    private boolean ended = false;

    // frame counter
    private int frame = 0;

    // any number of levels
    private List<Level> levels = new ArrayList<Level>();

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);

        this.levels.add(readCSV(FILE_LEVEL1));
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
     * Refreshed at user monitor refresh rate (typically 60Hz, but
     * will be marked on a 120hz monitor).
     * 
     * @param input The current mouse/keyboard state.
     */
    @Override
    protected void update(Input input) {
        // even if paused:
        // draw background
        IMAGE_BACKGROUND.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
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

        // game logic
        if (paused) {
            // pause screen - object/class/method?
            FONT.drawString(PAUSED, WINDOW_WIDTH / 2 - FONT.getWidth(PAUSED) / 2, WINDOW_HEIGHT / 2);
        } else { // if not paused
            // increment frame counter
            frame++;

            // drawing frame counter [testing]
            FONT_SMALL.drawString("Frame " + frame, WINDOW_WIDTH - FONT.getWidth("Frame " + frame)/3 - 50, 50);

            // if space pressed start game
            if (input.wasPressed(Keys.SPACE)) {
                started = false;
            }

            // if game started
            if (started) {
                // start screen - object/class/method?
                FONT.drawString(TITLE, WINDOW_WIDTH / 2 - FONT.getWidth(TITLE) / 2, WINDOW_HEIGHT / 2 - 100);
                FONT_SMALL.drawString(START_1, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(START_1) / 2, WINDOW_HEIGHT / 2);
                FONT_SMALL.drawString(START_2, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(START_2) / 2,
                        WINDOW_HEIGHT / 2 + 50);
            } else if (ended) { // if game ended
                // end screen - object/class/method?

            } else { // if game in progress
                // update levels
                for (Level level : this.levels) {
                    if (level.isActive())
                        // draw frame counter [testing]
                        FONT_SMALL.drawString("" + level.checkInput(input), 50, 100);
                        level.update(frame, input);
                        // draw score
                        FONT_SMALL.drawString("Score " + level.getScore(), 50, 50);
                }
            }
        }
    }
}
