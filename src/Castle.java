import processing.core.PImage;

import java.util.List;

public class Castle extends Entity {

    public static final String CASTLE_KEY = "castle";

    public static final int CASTLE_PARSE_PROPERTY_COUNT = 0;

    public Castle(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }
}
