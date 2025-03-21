import processing.core.PImage;

import java.util.List;

/** Represents an entity that exists in the virtual world. */
public class Entity extends ScheduleActions {
    /**
     * Enumerated type defining different kinds of entities in the world.
     * Specific values are assigned to the entity's 'kind' instance variable at initialization.
     * Each kind represents a distinct kind of entity with unique properties and behaviors.
     */

    // Constant string identifiers for the corresponding type of entity.
    // Used to identify entities in the save file and retrieve image information.


    // Constant save file column positions for properties required by all entities.
    public static final int ENTITY_PROPERTY_KEY_INDEX = 0;
    public static final int ENTITY_PROPERTY_ID_INDEX = 1;
    public static final int ENTITY_PROPERTY_POSITION_X_INDEX = 2;
    public static final int ENTITY_PROPERTY_POSITION_Y_INDEX = 3;
    public static final int ENTITY_PROPERTY_COLUMN_COUNT = 4;

    // Constant save file column positions for properties corresponding to speicific entity types.
    // Do not use these values directly in any constructors or 'create' methods.

    // Constant limits and default values for specific entity types.


    /** Determines instance logic and categorization. */

    /** Entity's identifier that often includes the corresponding 'key' constant. */
    private String id;

    /** Entity's x/y position in the world. */
    private Point position;

    /** Entity's inanimate (singular) or animation (multiple) images. */
    private List<PImage> images;

    /** Index of the element from 'images' used to draw the entity. */
    private int imageIndex;

    /** Positive (non-zero) time delay between the entity's animations. */


    /** Positive (non-zero) time delay between the entity's behaviors. */

    /** Entity's current health level. */

    /** Number of resources collected by the entity. */

    /** Total number of resources the entity may hold. */

    /**
     * Constructs an Entity with specified characteristics.
     * In the base program, this is not called directly.
     * Instead, the encapsulated 'create' methods are used to create specific types of entities.
     *
     * @param id              The entity's identifier.
     * @param position        The entity's x/y position in the world.
     * @param images          The entity's inanimate (singular) or animation (multiple) images.
     */
    public Entity(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    /**
     * Returns a new 'Dude' type entity.
     * Constructor arguments provide hints to data necessary for a subclass.
     *
     * @param id              A unique string identifier for the entity.
     *                        Typically, the constant 'Dude' key, but can be anything.
     * @param position        The entity's x/y position in the world.
     * @param images          A list of images that represent an entity and its possible animation.
     * @param animationPeriod The time between when an entity's animation is scheduled and executed.
     * @param behaviorPeriod  The time between when an entity's activity is scheduled and executed.
     * @param resourceCount   The number of resources held by the entity.
     * @param resourceLimit   The total number of resources the entity can hold.
     *
     * @return A new Entity object configured as a 'Dude'.
     */


    /**
     * Creates an Entity representing a(n) 'Fairy'.
     * The parameters provide a hint to data relevant for a(n) 'Fairy' class.
     *
     * @param id              A unique string identifier for the entity.
     *                        Typically, the constant 'Fairy' key, but can be anything.
     * @param position        The entity's x/y position in the world.
     * @param images          A list of images that represent an entity and its possible animation.
     * @param animationPeriod The time between when an entity's animation is scheduled and executed.
     * @param behaviorPeriod  The time between when an entity's activity is scheduled and executed.
     *
     * @return A new Entity object configured as a 'Fairy'.
     */


    /**
     * Creates an Entity representing a(n) 'House'.
     * The parameters provide a hint to data relevant for a 'House' class.
     *
     * @param id A unique string identifier for the entity.
     *           Typically, the constant 'House' key, but can be anything.
     * @param position The entity's x/y position in the world.
     * @param images A list of images that represent an entity and its possible animation.
     *
     * @return A new Entity object configured as a 'House'.
     */


    /**
     * Creates an Entity representing a(n) 'Mushroom'.
     * The parameters provide a hint to data relevant for a 'Mushroom' class.
     *
     *
     * @param images A list of images that represent an entity and its possible animation.
     *
     * @return A new Entity object configured as a 'Mushroom'.
     */

    /**
     * Creates an Entity representing a(n) 'Water'.
     * The parameters provide a hint to data relevant for a 'Water' class.
     *
     * @param id A unique string identifier for the entity.
     *           Typically, the constant 'Water' key, but can be anything.
     * @param position The entity's x/y position in the world.
     * @param images A list of images that represent an entity and its possible animation.
     *
     * @return A new Entity object configured as a 'Water'.
     */

    /**
     * Creates an Entity representing a(n) 'Sapling'.
     * The parameters provide a hint to data relevant for a 'Sapling' class.
     *

     * @param images A list of images that represent an entity and its possible animation.
     *
     * @return A new Entity object configured as a 'Sapling'.
     */







    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary images) {}
    /**
     * Creates an Entity representing a(n) 'Tree'.
     * The parameters provide a hint to data relevant for a 'Tree' class.
     *
     * @param id              A unique string identifier for the entity.
     *                        Typically, the 'Tree' key, but can be anything.
     * @param position        The entity's x/y position in the world.
     * @param images          A list of images that represent an entity and its possible animation.
     * @param animationPeriod The time between when an entity's animation is scheduled and executed.
     * @param behaviorPeriod  The time between when an entity's activity is scheduled and executed.
     * @return A new Entity object configured as a 'Tree'.
     */


    /** Helper method for testing. Preserve this functionality for all kinds of entities. */
    public String log(){
        if (id.isEmpty()) {
            return null;
        } else {
            return String.format("%s %d %d %d", id, position.x, position.y, imageIndex);
        }
    }

    /** Called when an animation action occurs. */


    /** Called to begin animation and/or behavior for an entity. */


    /** Begins all animation updates for the entity. */


    /** Schedules a single behavior update for the entity. */


    /** Performs the entity's behavior logic. */


    /** Executes Dude specific Logic. */


    /** Returns the (optional) entity a Dude will path toward. */


    /** Attempts to move the Dude toward a target, returning True if already adjacent to it. */


    /** Determines a Dude's next position when moving. */


    /** Changes the Dude's graphics. */


    /** Executes Fairy specific Logic. */


    /** Attempts to move the Fairy toward a target, returning True if already adjacent to it. */


    /** Determines a Fairy's next position when moving. */


    /** Executes Mushroom specific Logic. */


    /** Executes Sapling specific Logic. */


    /** Checks the Sapling's health and transforms accordingly, returning true if successful. */


    /** Executes Tree specific Logic. */

    /** Checks the Tree's health and transforms accordingly, returning true if successful. */


    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public String getId() {
        return id;
    }

    public List<PImage> getImages() {
        return images;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
