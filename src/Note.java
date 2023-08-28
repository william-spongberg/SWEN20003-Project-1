import bagel.*;
import bagel.util.Colour;

public class Note {
    // images
    private final Image IMAGE_NOTE_LEFT = new Image("res/noteLeft.png");
    private final Image IMAGE_NOTE_RIGHT = new Image("res/noteRight.png");
    private final Image IMAGE_NOTE_UP = new Image("res/noteUp.png");
    private final Image IMAGE_NOTE_DOWN = new Image("res/noteDown.png");

    private final Image IMAGE_NOTEHOLD_LEFT = new Image("res/holdNoteLeft.png");
    private final Image IMAGE_NOTEHOLD_RIGHT = new Image("res/holdNoteRight.png");
    private final Image IMAGE_NOTEHOLD_UP = new Image("res/holdNoteUp.png");
    private final Image IMAGE_NOTEHOLD_DOWN = new Image("res/holdNoteDown.png");

    // directions
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    // position
    private static final int START_Y = 100;

    // attributes with default values
    private Image image = IMAGE_NOTEHOLD_LEFT;
    private int delay = 0;
    private int dir = LEFT;
    private int y = 0;
    private boolean is_held = false;
    private boolean visual = false;
    private boolean active = true;

    // font [testing]
    private final static String FILE_FONT = "res/FSO8BITR.TTF";
    private final Font FONT_SMALL = new Font(FILE_FONT, 12);

    public Note(String dir, String type, int delay) {
        if (type == "Hold") {
            this.is_held = true;

            switch (dir) {
                case "Left":
                    this.image = IMAGE_NOTEHOLD_LEFT;
                    this.dir = LEFT;
                    break;
                case "Right":
                    this.image = IMAGE_NOTEHOLD_RIGHT;
                    this.dir = RIGHT;
                    break;
                case "Up":
                    this.image = IMAGE_NOTEHOLD_UP;
                    this.dir = UP;
                    break;
                case "Down":
                    this.image = IMAGE_NOTEHOLD_DOWN;
                    this.dir = DOWN;
                    break;
                default:
                    System.out.println("Error: invalid note");
            }
        } else {
            switch (dir) {
                case "Left":
                    this.image = IMAGE_NOTE_LEFT;
                    this.dir = LEFT;
                    break;
                case "Right":
                    this.image = IMAGE_NOTE_RIGHT;
                    this.dir = RIGHT;
                    break;
                case "Up":
                    this.image = IMAGE_NOTE_UP;
                    this.dir = UP;
                    break;
                case "Down":
                    this.image = IMAGE_NOTE_DOWN;
                    this.dir = DOWN;
                    break;
                default:
                    System.out.println("Error: invalid note");
            }
            this.delay = delay;
        }
    }

    public void update(int frame, int lane_x[]) {
        if (active) {
            // calculate y position
            this.y = (START_Y/4 + frame - this.delay)*4;
            // if note is on screen
            if (this.y > START_Y) {
                // then is visual
                if (!this.visual)
                    this.visual = true;
                // draw note
                this.image.draw(lane_x[this.dir], this.y);

                // draw note position [testing]
                /*DrawOptions opt = new DrawOptions();
                opt.setBlendColour(Colour.RED);
                FONT_SMALL.drawString("" + this.y, lane_x[this.dir] - FONT_SMALL.getWidth("" + this.y) / 2, this.y,
                        opt);
                FONT_SMALL.drawString("" + (657 - this.y), lane_x[this.dir] - FONT_SMALL.getWidth("" + this.y) / 2,
                        this.y + 10, opt);*/
            }
        }
    }

    public Image getImage() {
        return this.image;
    }

    public Integer getDir() {
        return this.dir;
    }

    public Integer getDelay() {
        return this.delay;
    }

    public Integer getY() {
        return this.y;
    }

    public Boolean isHeld() {
        return this.is_held;
    }

    public Boolean isVisual() {
        return this.visual;
    }

    public Boolean isActive() {
        return this.active;
    }

    public void endActive() {
        this.active = false;
    }
}
