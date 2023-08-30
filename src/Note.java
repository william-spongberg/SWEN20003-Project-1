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

    // starting y position
    private static final int START_Y = 100;
    private static final int START_Y_HOLD = 24;

    // refresh rate speed multipliers
    private static final int REFRESH_60_MULTIPLIER = 4;
    private static final int REFRESH_120_MULTIPLIER = 2;

    // hold image midpoint
    private static final int HOLD_MIDPOINT = 82;

    // attributes with default values
    private Image image = IMAGE_NOTEHOLD_LEFT;
    private int delay = 0;
    private int dir = LEFT;
    private int y = 0;
    private int start_y = START_Y;
    private boolean is_held = false;
    private boolean active = true;
    private boolean visual = false;

    // font [testing]
    private final static String FILE_FONT = "res/FSO8BITR.TTF";
    private final Font FONT_SMALL = new Font(FILE_FONT, 20);

    public Note(String dir, String type, int delay) {
        reset(dir, type, delay);
    }

    public void reset(String dir, String type, int delay) {
        // if held set to held
        if (type.equals("Hold")) {
            this.is_held = true;
            start_y = START_Y_HOLD;
        }

        switch (dir) {
            case "Left":
                if (this.is_held)
                    this.image = IMAGE_NOTEHOLD_LEFT;
                else 
                    this.image = IMAGE_NOTE_LEFT;
                this.dir = LEFT;
                break;
            case "Right":
                if (this.is_held)
                    this.image = IMAGE_NOTEHOLD_RIGHT;
                else 
                    this.image = IMAGE_NOTE_RIGHT;
                this.dir = RIGHT;
                break;
            case "Up":
                if (this.is_held)
                    this.image = IMAGE_NOTEHOLD_UP;
                else 
                    this.image = IMAGE_NOTE_UP;
                this.dir = UP;
                break;
            case "Down":
                if (this.is_held)
                    this.image = IMAGE_NOTEHOLD_DOWN;
                else 
                    this.image = IMAGE_NOTE_DOWN;
                this.dir = DOWN;
                break;
            default:
                System.out.println("Error: invalid note");
        }
        this.delay = delay;
    }

    public void reset(Note note) {
        this.image = note.getImage();
        this.delay = note.getDelay();
        this.dir = note.getDir();
        this.is_held = note.isHeld();
        this.y = 0;
        this.start_y = note.start_y;

        this.active = true;
        this.visual = false;
    }

    public void update(int frame, int lane_x[]) {
        // if active or visual
        if (this.active || this.visual) {
            // calculate y position
            // if 60 hz
            if (ShadowDance.hasRefresh60()) {
                this.y = start_y + (frame - this.delay) * REFRESH_60_MULTIPLIER;
            // else is 120 hz (according to marker)
            } else {
                this.y = start_y + (frame - this.delay) * REFRESH_120_MULTIPLIER;
            }

            // if note is on screen
            if ((this.y > start_y) && (this.y < (ShadowDance.getHeight() + this.image.getWidth() / 2))) {
                // now visual
                if (!this.visual)
                    this.visual = true;
                
                // draw note
                this.image.draw(lane_x[this.dir], this.y);

                // draw note position [testing]
                /*
                DrawOptions opt = new DrawOptions();
                opt.setBlendColour(Colour.RED);  
                FONT_SMALL.drawString("" + this.y, lane_x[this.dir] - FONT_SMALL.getWidth(""
                        + this.y) / 2, this.y,
                        opt);
                opt.setBlendColour(Colour.GREEN);
                FONT_SMALL.drawString("" + (657 - this.y), lane_x[this.dir] -
                        FONT_SMALL.getWidth("" + this.y) / 2,
                        this.y + 10, opt);
                /* */
            }
            // if not on screen
            else if ((this.y > (ShadowDance.getHeight() + this.image.getWidth() / 2))) {
                this.active = false;
                this.visual = false;
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

    public Boolean isActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setVisual(Boolean visual) {
        this.visual = visual;
    }

    public int getHeldMidpoint() {
        return HOLD_MIDPOINT;
    }
}
