import bagel.*;

public class Grade {
    // position
    private static final int NOTE_STATIONARY_Y = 657;

    // grading
    private static final int[] GRADE = { 0, 10, 5, -1, -5 };
    private static final int GRADE_NONE = 0;
    private static final int GRADE_PERFECT = 1;
    private static final int GRADE_GOOD = 2;
    private static final int GRADE_BAD = 3;
    private static final int GRADE_MISS = 4;

    // grading distances
    private static final int NONE = 0;
    private static final int PERFECT = 15;
    private static final int GOOD = 50;
    private static final int BAD = 100;
    private static final int MISS = 200;

    // directions
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    // invalid input
    private static final int INVALID = -1;

    // attributes with default values
    private int grade = NONE;

    public Boolean[] checkScore(Note note, Input input, Boolean holding) {
        // input, note_y set to defualt
        int dir = INVALID, note_y = note.getY();

        // if note is not held *or* already holding and note is held
        if (!note.isHeld() || (note.isHeld() && holding)) {
            dir = checkInputReleased(input);
            // if holding get top of held note
            if (holding) {
                note_y = note.getY() - note.getHeldMidpoint();
                holding = false;
            }
        }
        // else if note is held and not already holding
        else if (note.isHeld() && !holding){
            dir = checkInputPressed(input);
            // get bottom of held note
            note_y = note.getY() + note.getHeldMidpoint();
            holding = true;
        }

        // if within range of grading
        if (gradeInput(dir, note.getDir(), note_y, holding)) {
            // if not holding, start holding
            if (!holding)
                note.endActive();
        }
        Boolean[] array = { note.isActive(), holding };

        return array;
    }

    public Boolean gradeInput(int dir, int note_dir, int note_y, boolean holding) {
        boolean inactive = false;
        // if unable to hit note
        if ((note_y > NOTE_STATIONARY_Y) && !holding) {
            this.grade = GRADE[GRADE_MISS];
            inactive = true;

            System.out.println("MISS - off screen");
        }
        // if on screen, valid input and note can be hit
        else if (dir != INVALID && note_y <= NOTE_STATIONARY_Y) {
            // if wrong direction
            if (dir != note_dir) {
                this.grade = GRADE[GRADE_MISS];

                System.out.println("MISS - wrong direction");
            }
            // if within PERFECT range
            else if (NOTE_STATIONARY_Y - note_y <= PERFECT) {
                this.grade = GRADE[GRADE_PERFECT];
                inactive = true;

                System.out.println("PERFECT");
                // if within GOOD range
            } else if (NOTE_STATIONARY_Y - note_y <= GOOD) {
                this.grade = GRADE[GRADE_GOOD];
                inactive = true;

                System.out.println("GOOD");
                // if within BAD range
            } else if (NOTE_STATIONARY_Y - note_y <= BAD) {
                this.grade = GRADE[GRADE_BAD];
                inactive = true;

                System.out.println("BAD");
                // if within MISS range
            } else if (NOTE_STATIONARY_Y - note_y <= MISS) {
                this.grade = GRADE[GRADE_MISS];
                inactive = true;

                System.out.println("MISS");
            }
        }
        return inactive;
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

    public static final int noGrade() {
        return GRADE_NONE;
    }

    public static final int perfectGrade() {
        return GRADE_PERFECT;
    }

    public static final int goodGrade() {
        return GRADE_GOOD;
    }

    public static final int badGrade() {
        return GRADE_BAD;
    }

    public static final int missGrade() {
        return GRADE_MISS;
    }
}
