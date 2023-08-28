import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Level {
    // images
    private final Image IMAGE_LANE_LEFT = new Image("res/laneLeft.png");
    private final Image IMAGE_LANE_RIGHT = new Image("res/laneRight.png");
    private final Image IMAGE_LANE_UP = new Image("res/laneUp.png");
    private final Image IMAGE_LANE_DOWN = new Image("res/laneDown.png");

    // positions
    private static final int LANE_Y = 384;
    private static final int NOTE_STATIONARY_Y = 657;

    // scoring distances
    private static final int PERFECT = 15;
    private static final int GOOD = 50;
    private static final int BAD = 100;
    private static final int MISS = 200;

    // csv file indices
    private static final int INDEX_LANE = 0;
    private static final int INDEX_TYPE = 1;
    private static final int INDEX_DELAY = 2;

    // directions
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    // attributes with default values
    private List<Note> notes = new ArrayList<Note>();
    private int[] lane_x = new int[4];
    private boolean active = false;
    private static int num = -1;
    private int score = 0;
    private Note currentNote;

    public Level(List<List<String>> file) {
        // print out csv file [testing]
        for (List<String> record : file) {
            System.out.println(record);
        }

        // create objects from csv file
        int i = 0;
        for (List<String> record : file) {
            // get lane x coords and create list of notes
            if (record.get(INDEX_LANE).equals("Lane")) {
                this.lane_x[i] = Integer.parseInt(record.get(INDEX_DELAY));
                i++;
            } else {
                this.notes.add(new Note(record.get(INDEX_LANE), record.get(INDEX_TYPE),
                        Integer.parseInt(record.get(INDEX_DELAY))));
            }
        }

        // get first note
        currentNote = notes.get(0);

        // activate level
        this.active = true;
        num++;
    }

    public void update(int frame, Input input) {
        // draw lanes
        IMAGE_LANE_LEFT.draw(lane_x[0], LANE_Y);
        IMAGE_LANE_RIGHT.draw(lane_x[1], LANE_Y);
        IMAGE_LANE_UP.draw(lane_x[2], LANE_Y);
        IMAGE_LANE_DOWN.draw(lane_x[3], LANE_Y);

        // draw scoring positions [testing]
        /*Point a = new Point(0, NOTE_STATIONARY_Y - 100);
        Point b = new Point(2000, NOTE_STATIONARY_Y - 100);
        Drawing.drawLine(a, b, 200, Colour.WHITE);
        a = new Point(0, NOTE_STATIONARY_Y - 50);
        b = new Point(2000, NOTE_STATIONARY_Y - 50);
        Drawing.drawLine(a, b, 100, Colour.RED);
        a = new Point(0, NOTE_STATIONARY_Y - 25);
        b = new Point(2000, NOTE_STATIONARY_Y - 25);
        Drawing.drawLine(a, b, 50, Colour.BLUE);
        a = new Point(0, NOTE_STATIONARY_Y - 15 / 2);
        b = new Point(2000, NOTE_STATIONARY_Y - 15 / 2);
        Drawing.drawLine(a, b, 15, Colour.GREEN);*/

        // draw notes
        for (Note note : this.notes) {
            note.update(frame, lane_x);
        }

        // get input
        Integer dir = checkInput(input);

        // check score
        if (!checkScore(currentNote, dir)) {
            // if note is inactive, set next note to current note
            if (notes.indexOf(currentNote) < notes.size() - 1)
                currentNote = notes.get(notes.indexOf(currentNote) + 1);
        }
    }

    public Boolean checkScore(Note note, int dir) {
        // if input matches note and is in scoring range
        if (dir != -1 && dir == note.getDir() && (NOTE_STATIONARY_Y - note.getY() <= MISS)) {
            // if scored, delete
            if (NOTE_STATIONARY_Y - note.getY() <= PERFECT) {
                this.score += 10;
                note.endActive();
                System.out.println("PERFECT");
            } else if (NOTE_STATIONARY_Y - note.getY() <= GOOD) {
                this.score += 5;
                note.endActive();
                System.out.println("GOOD");
            } else if (NOTE_STATIONARY_Y - note.getY() <= BAD) {
                this.score -= 1;
                note.endActive();
                System.out.println("BAD");
                // if missed, don't delete
            } else if ((NOTE_STATIONARY_Y - note.getY() <= MISS)) {
                this.score -= 5;
                System.out.println("MISS");
            }
            // else if past scoring range, delete
            else if (NOTE_STATIONARY_Y - note.getY() < 0) {
                note.endActive();
                System.out.println("deleted node");
            }
        }
        return note.isActive();
    }

    public Integer checkInput(Input input) {
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
            return -1;
        }
    }

    public Boolean isActive() {
        return this.active;
    }

    public void endActive() {
        this.active = false;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getScore() {
        return this.score;
    }
}