public interface Moveable {

    Point nextPosition(World world, Point point);

    boolean moveTo(World world, Entity target, EventScheduler scheduler);
}
