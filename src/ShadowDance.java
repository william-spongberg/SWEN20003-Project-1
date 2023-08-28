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
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image IMAGE_BACKGROUND = new Image("res/background.png");

    private final static String FILE_FONT = "res/FSO8BITR.TTF";
    private final Font FONT = new Font(FILE_FONT, 64);
    private final Font FONT_SMALL = new Font(FILE_FONT, 24);

    private static final String TITLE = "SHADOW DANCE";
    private static final String START_1 = "PRESS SPACE TO START";
    private static final String START_2 = "USE ARROW KEYS TO PLAY";

    private static final String FILE_LEVEL1 = "res/test1-60.csv";

    private boolean paused = false;
    private boolean started = true;
    private boolean ended = false;

    private int frame = 0;

    private Level level1;

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);

        // this.readCSV("res/test1.csv");
        level1 = this.readCSV(FILE_LEVEL1);
    }

    /**
     * Method used to read files and create level objects.
     */
    private Level readCSV(String fileName) {
        // read csv level files from res into arraylist of arrays of strings
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
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
        if (!paused) {
            // increment frame counter
            frame++;

            if (input.wasPressed(Keys.SPACE)) {
                started = false;
            }

            if (started) {
                // start screen - object/class/method?
                FONT.drawString(TITLE, WINDOW_WIDTH / 2 - FONT.getWidth(TITLE) / 2, WINDOW_HEIGHT / 2 - 100);
                FONT_SMALL.drawString(START_1, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(START_1) / 2, WINDOW_HEIGHT / 2);
                FONT_SMALL.drawString(START_2, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(START_2) / 2, WINDOW_HEIGHT / 2 + 50);
            }
            else if (ended) {
                // end screen - object/class/method?

            }
            else {
                // levels
                level1.update(frame);
            }
        }
    }
}
