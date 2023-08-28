import bagel.*;

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
    private final int LEFT = 0;
    private final int RIGHT = 1;
    private final int UP = 2;
    private final int DOWN = 3;

    // position
    private int y = 0;
    private final int start_y = 100;

    // attributes with default values
    private Image image = IMAGE_NOTEHOLD_LEFT;
    private int delay = 0;
    private int dir = LEFT;
    private boolean is_held = false;

    
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
        // draw note
        this.image.drawFromTopLeft(lane_x[this.dir], start_y + this.y);
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

    public Boolean getIsHeld() {
        return this.is_held;
    }
}
