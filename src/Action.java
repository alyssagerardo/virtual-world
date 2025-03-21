/** A scheduled action to be carried out by a specific entity. */
abstract public class Action {

    /** Entity enacting the action. */
    private final Entity entity;

    /** World in which the action occurs. */
    private final World world;

    /** Image data that may be used by the action. */
    private final ImageLibrary imageLibrary;

    /** Constructor for Action class that assigns references to instance variables. */
    public Action(Entity entity, World world, ImageLibrary imageLibrary) {
        this.entity = entity;
        this.world = world;
        this.imageLibrary = imageLibrary;
    }

    /** Called when the action's scheduled time occurs. */
    abstract void execute(EventScheduler scheduler);

    /** Global getters for all subclass files */
    public Entity getEntity() {
        return entity;
    }

    public World getWorld() {
        return world;
    }

    public ImageLibrary getImageLibrary() {
        return imageLibrary;
    }
}
