import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Fairy extends ActioningEntity implements Moveable {

    public static final String FAIRY_KEY = "fairy";

    public static final int FAIRY_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int FAIRY_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int FAIRY_PARSE_PROPERTY_COUNT = 2;

    public Fairy(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, animationPeriod, behaviorPeriod);
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveTo(world, fairyTarget.get(), scheduler)) {
                Sapling sapling = new Sapling(Sapling.SAPLING_KEY + "_" + fairyTarget.get().getId(), tgtPos, imageLibrary.get(Sapling.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageLibrary);
            }
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduleAnimation(scheduler, world, imageLibrary);
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public boolean moveTo(World world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacentTo(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());
            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(World world, Point destination) {
        // Differences between the destination and current position along each axis
        PathingStrategy pathingStrategy = new SingleStepPathingStrategy();

        Predicate<Point> canPassThrough = p -> !world.isOccupied(p) && world.inBounds(p);

        BiPredicate<Point, Point> withinReach = (p1, p2) -> p1.adjacentTo(p2);

        Function<Point, Stream<Point>> potentialNeighbors = pathingStrategy.CARDINAL_NEIGHBORS;

        List<Point> searchedPath = pathingStrategy.computePath(getPosition(), destination, canPassThrough, withinReach, potentialNeighbors);

        if (searchedPath.isEmpty()) {
            return getPosition();
        } else {
            return searchedPath.get(0);
        }
    }

    public void updateImage() {
        setImageIndex(getImageIndex() + 1);
    }

}
