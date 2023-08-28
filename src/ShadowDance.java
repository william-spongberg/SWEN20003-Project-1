import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2023
 * Please enter your name below
 * @author
 */
public class ShadowDance extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    private final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final Font FONT = new Font(FONT_FILE, 64);
    private final Font FONT_SMALL = new Font(FONT_FILE, 24);
    
    private static final String TITLE = "SHADOW DANCE";
    private static final String START_1 = "PRESS SPACE TO START";
    private static final String START_2 = "USE ARROW KEYS TO PLAY";

    private boolean paused = false;


    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV() {
        // read csv files from res


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
     * @param input The current mouse/keyboard state.
     * Refreshed at user monitor refresh rate (typically 60Hz, but 
     * will be marked on a 120hz monitor).
     */
    @Override
    protected void update(Input input) {
        // draw background first, even if paused
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        // allow user to exit
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // pause checking
        if (input.wasPressed(Keys.TAB)) {
            if (!paused)
                paused = true;
            else
                paused = false;
        }

        // game logic
        if (!paused) {
            //start screen - object/class?
            FONT.drawString(TITLE, WINDOW_WIDTH/2 - FONT.getWidth(TITLE)/2, WINDOW_HEIGHT/2 - 100);
            FONT_SMALL.drawString(START_1, WINDOW_WIDTH/2 - FONT_SMALL.getWidth(START_1)/2, WINDOW_HEIGHT/2);
            FONT_SMALL.drawString(START_2, WINDOW_WIDTH/2 - FONT_SMALL.getWidth(START_2)/2, WINDOW_HEIGHT/2 + 50);
        }
    }
}
