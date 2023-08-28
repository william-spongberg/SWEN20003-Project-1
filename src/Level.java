import bagel.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private final Image IMAGE_LANE_LEFT = new Image("res/laneLeft.png");
    private final Image IMAGE_LANE_RIGHT = new Image("res/laneRight.png");
    private final Image IMAGE_LANE_UP = new Image("res/laneUp.png");
    private final Image IMAGE_LANE_DOWN = new Image("res/laneDown.png");

    private List<Note> notes = new ArrayList<Note>();

    private int[] lane_x = new int[4];
    private final int lane_y = 384;

    public Level(List<List<String>> file) {
        // print out csv file [testing]
        for (List<String> record : file) {
            System.out.println(record);
        }

        // create objects from csv file
        int i = 0;
        for (List<String> record : file) {
            // get lane x coords and create list of notes
            if (record.get(0) == "Lane") {
                this.lane_x[i] = Integer.parseInt(record.get(3));
                i++;
            }
            else {
                this.notes.add(new Note(record.get(0), record.get(1), Integer.parseInt(record.get(2))));
            }
        }
    }

    public void update(int frame) {
        // draw lanes
        IMAGE_LANE_LEFT.draw(lane_x[0], lane_y);
        IMAGE_LANE_RIGHT.draw(lane_x[1], lane_y);
        IMAGE_LANE_UP.draw(lane_x[2], lane_y);
        IMAGE_LANE_DOWN.draw(lane_x[3], lane_y);

        // draw notes
        for (Note note : this.notes) {
            note.update(frame, lane_x);
        }
    }
}
