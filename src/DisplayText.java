import bagel.*;

public final class DisplayText {
    // dimensions
    private final static int WINDOW_WIDTH = ShadowDance.getWidth();
    private final static int WINDOW_HEIGHT = ShadowDance.getHeight();

    // font
    private final static String FILE_FONT = "res/FSO8BITR.TTF";
    private final Font FONT = new Font(FILE_FONT, 64);
    private final Font FONT_SMALL = new Font(FILE_FONT, 24);
    private final Font FONT_SCORE = new Font(FILE_FONT, 30);
    private final Font FONT_GRADE = new Font(FILE_FONT, 40);

    // grading
    private static final int[] GRADE = { 0, 10, 5, -1, -5 };
    private static final int GRADE_NONE = 0;
    private static final int GRADE_PERFECT = 1;
    private static final int GRADE_GOOD = 2;
    private static final int GRADE_BAD = 3;
    private static final int GRADE_MISS = 4;

    /* strings */
    // title
    private final static String GAME_TITLE = "SHADOW DANCE";
    // start screen
    private static final String START_1 = "PRESS SPACE TO START";
    private static final String START_2 = "USE ARROW KEYS TO PLAY";
    // game play
    private static final String SCORE = "SCORE ";
    private static final String PAUSED = "PAUSED";
    private static final String PERFECT = "PERFECT";
    private static final String GOOD = "GOOD";
    private static final String BAD = "BAD";
    private static final String MISS = "MISS";
    // game over
    private static final String GAME_OVER = "GAME OVER";
    private static final String GAME_RESTART = "PRESS SPACE TO RESTART";
    // testing
    /* */
    private static final String FRAME = "FRAME ";
    /* */

    public final void drawBackground(Image image) {
        image.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
    }

    public final void drawStartScreen() {
        FONT.drawString(GAME_TITLE, WINDOW_WIDTH / 2 - FONT.getWidth(GAME_TITLE) / 2, WINDOW_HEIGHT / 2 - 100);
        FONT_SMALL.drawString(START_1, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(START_1) / 2, WINDOW_HEIGHT / 2);
        FONT_SMALL.drawString(START_2, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(START_2) / 2,
                WINDOW_HEIGHT / 2 + 50);
    }

    public final void drawScore(int score) {
        FONT_SCORE.drawString(SCORE + score, 35, 35);
    }

    public final void drawPauseScreen() {
        FONT.drawString(PAUSED, WINDOW_WIDTH / 2 - FONT.getWidth(PAUSED) / 2,
                WINDOW_HEIGHT / 2);
    }

    public final void drawPerfect() {
        FONT_GRADE.drawString(PERFECT, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(PERFECT) / 2,
                WINDOW_HEIGHT / 2);
    }

    public final void drawGood() {
        FONT_GRADE.drawString(GOOD, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(GOOD) / 2,
                WINDOW_HEIGHT / 2);
    }

    public final void drawBad() {
        FONT_GRADE.drawString(BAD, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(BAD) / 2,
                WINDOW_HEIGHT / 2);
    }

    public final void drawMiss() {
        FONT_GRADE.drawString(MISS, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(MISS) / 2,
                WINDOW_HEIGHT / 2);
    }

    public final void drawGrade(int grade) {
        if (grade == GRADE[GRADE_PERFECT]) {
                drawPerfect();
        } else if (grade == GRADE[GRADE_GOOD]) {
                drawGood();
        } else if (grade == GRADE[GRADE_BAD]) {
                drawBad();
        } else if (grade == GRADE[GRADE_MISS]) {
                drawMiss();
        } else {
            System.out.println("Error: invalid grade");
        }
    }

    public final void drawEndScreen(int score) {
        FONT.drawString(GAME_OVER, WINDOW_WIDTH / 2 - FONT.getWidth(GAME_OVER) / 2,
                WINDOW_HEIGHT / 2 - 100);
        FONT_SMALL.drawString(SCORE + score, WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(SCORE + score) / 2,
                WINDOW_HEIGHT / 2);

        FONT_SMALL.drawString(GAME_RESTART,
                WINDOW_WIDTH / 2 - FONT_SMALL.getWidth(GAME_RESTART) / 2,
                WINDOW_HEIGHT / 2 + 50);
    }

    // testing
    /* */
    public final void drawFrame(int frame) {
        FONT_SMALL.drawString(FRAME + frame, WINDOW_WIDTH - FONT.getWidth(FRAME + frame) / 3 - 50, 50);
    }

    public final void drawDistance(int distance) {
        FONT_SMALL.drawString("" + distance, WINDOW_WIDTH - FONT.getWidth("" + distance) / 3 - 50, 100);
    }
    /* */
}
