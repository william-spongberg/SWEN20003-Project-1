import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;

public class Grade {
    // grading
    private static final int PERFECT = 0;
    private static final int GOOD = 1;
    private static final int BAD = 2;
    private static final int MISS = 3;

    // grading scores
    private static final int[] GRADE = { 10, 5, -1, -5 };

    // grading distances
    private static final int[] DISTANCE = { 15, 50, 100, 200 };

    // grade y position
    private static final int NOTE_STATIONARY_Y = 657;

    // directions
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    // invalid input
    private static final int INVALID = -1;

    // attributes with default values
    private int grade = 0;

    public Boolean[] checkScore(Note note, Input input, Boolean holding) {
        // input, note_y set to defualt
        int dir = INVALID, note_y = note.getY();

        // if note is not held
        if (!note.isHeld()) {
            dir = checkInputPressed(input);
        }
        // else if note is held and not already holding
        else if (note.isHeld() && !holding) {
            // get input
            dir = checkInputPressed(input);
            // if valid input
            if (dir != INVALID) {
                // get bottom of held note
                note_y = note.getY() + note.getHeldMidpoint();
                holding = true;
            }
        }
        // else if note is held and already holding
        else if (note.isHeld() && holding) {
            // get input
            dir = checkInputReleased(input);
            // if valid input
            if (dir != INVALID) {
                // get top of held note
                note_y = note.getY() - note.getHeldMidpoint();
                holding = false;
            }
        }

        // check distance [testing]
        /*
        Point a = new Point(50, note_y);
        Point b = new Point(50, NOTE_STATIONARY_Y);
        Drawing.drawLine(a, b, 5, Colour.BLUE);
        /* */

        // if close enough for grading
        if (NOTE_STATIONARY_Y - note_y <= DISTANCE[MISS]) {
            // grade the input
            if (gradeInput(dir, note, note_y)) {
                // if note graded and not holding, set note to inactive
                if (!holding)
                    note.setActive(false);
            }
        }

        return new Boolean[] { note.isActive(), holding };
    }

    public Boolean gradeInput(int dir, Note note, int note_y) {
        boolean graded = false;
        // TO DO: hold notes deleting too early/not grading properly

        // if unable to hit note
        if ((note_y > NOTE_STATIONARY_Y) && !note.isHeld()) {
            this.grade = GRADE[MISS];
            graded = true;

            System.out.println("MISS - off screen");
        }
        // if on screen, valid input and note can be hit
        else if (dir != INVALID && note_y <= NOTE_STATIONARY_Y) {
            // if wrong direction
            if (dir != note.getDir()) {
                this.grade = GRADE[MISS];

                System.out.println("MISS - wrong direction");
                System.out.println("" + dir + " != " + note.getDir());
            }
            // if within PERFECT range
            else if (NOTE_STATIONARY_Y - note_y <= DISTANCE[PERFECT]) {
                this.grade = GRADE[PERFECT];
                graded = true;
                note.setVisual(false);

                System.out.println("PERFECT");
                // if within GOOD range
            } else if (NOTE_STATIONARY_Y - note_y <= DISTANCE[GOOD]) {
                this.grade = GRADE[GOOD];
                graded = true;
                note.setVisual(false);

                System.out.println("GOOD");
                // if within BAD range
            } else if (NOTE_STATIONARY_Y - note_y <= DISTANCE[BAD]) {
                this.grade = GRADE[BAD];
                graded = true;
                note.setVisual(false);

                System.out.println("BAD");
                // if within MISS range
            } else if (NOTE_STATIONARY_Y - note_y <= DISTANCE[MISS]) {
                this.grade = GRADE[MISS];
                graded = true;
                note.setVisual(false);

                System.out.println("MISS");
            }
        }
        return graded;
    }

    public Integer checkInputReleased(Input input) {
        // check for input
        if (input.wasReleased(Keys.LEFT)) {
            return LEFT;
        } else if (input.wasReleased(Keys.RIGHT)) {
            return RIGHT;
        } else if (input.wasReleased(Keys.UP)) {
            return UP;
        } else if (input.wasReleased(Keys.DOWN)) {
            return DOWN;
        } else {
            return INVALID;
        }
    }

    public Integer checkInputPressed(Input input) {
        // check for input
        if (input.wasPressed(Keys.LEFT)) {
            return LEFT;
        } else if (input.wasPressed(Keys.RIGHT)) {
            return RIGHT;
        } else if (input.wasPressed(Keys.UP)) {
            return UP;
        } else if (input.wasPressed(Keys.DOWN)) {
            return DOWN;
        } else {
            return INVALID;
        }
    }

    public int getGrade() {
        return this.grade;
    }

    public static int getPerfectGrade() {
        return GRADE[PERFECT];
    }

    public static int getGoodGrade() {
        return GRADE[GOOD];
    }

    public static int getBadGrade() {
        return GRADE[BAD];
    }

    public static int getMissGrade() {
        return GRADE[MISS];
    }
}
