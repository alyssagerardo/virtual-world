import org.junit.rules.Timeout;
import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Raccoon extends ActioningEntity {
    public static final String RACCOON_KEY = "raccoon";

    public static final int RACCOON_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int RACCOON_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int RACCOON_PARSE_PROPERTY_COUNT = 2;

    public Raccoon(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, animationPeriod, behaviorPeriod);
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> raccoonTarget = findRaccoonTarget(world);
        if (raccoonTarget.isPresent()) {
            Point tgtPos = raccoonTarget.get().getPosition();

            if (moveTo(world, raccoonTarget.get(), scheduler)) {
                Random random = new Random();
                int randomNumber = random.nextInt(4);

                if (randomNumber == 0) {
                    MushroomWizard mushroomWizard = new MushroomWizard(MushroomWizard.MUSHROOM_WIZARD_KEY, tgtPos, imageLibrary.get(MushroomWizard.MUSHROOM_WIZARD_KEY), getBehaviorPeriod() * 4.0);

                    world.addEntity(mushroomWizard);
                    mushroomWizard.scheduleActions(scheduler, world, imageLibrary);
                } else if (randomNumber == 1) {
                    MushroomWizard mushroomWizard = new MushroomWizard(MushroomWizard.MUSHROOM_WIZARD_KEY, tgtPos, imageLibrary.get("green_" + MushroomWizard.MUSHROOM_WIZARD_KEY), getBehaviorPeriod() * 4.0);

                    world.addEntity(mushroomWizard);
                    mushroomWizard.scheduleActions(scheduler, world, imageLibrary);
                } else if (randomNumber == 2) {
                    MushroomWizard mushroomWizard = new MushroomWizard(MushroomWizard.MUSHROOM_WIZARD_KEY, tgtPos, imageLibrary.get("purple_" + MushroomWizard.MUSHROOM_WIZARD_KEY), getBehaviorPeriod() * 4.0);

                    world.addEntity(mushroomWizard);
                    mushroomWizard.scheduleActions(scheduler, world, imageLibrary);
                } else {
                    MushroomWizard mushroomWizard = new MushroomWizard(MushroomWizard.MUSHROOM_WIZARD_KEY, tgtPos, imageLibrary.get("red_" + MushroomWizard.MUSHROOM_WIZARD_KEY), getBehaviorPeriod() * 4.0);

                    world.addEntity(mushroomWizard);
                    mushroomWizard.scheduleActions(scheduler, world, imageLibrary);
                }
            }
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduleAnimation(scheduler, world, imageLibrary);
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public Optional<Entity> findRaccoonTarget(World world) {
        List<Class<?>> potentialTargets;

        potentialTargets = List.of(Mushroom.class);

        return world.findNearest(getPosition(), potentialTargets);
    }

    public boolean moveTo(World world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacentTo(target.getPosition())) {
            if (target instanceof Mushroom) {
                world.removeEntity(scheduler, target);
            }
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

        Predicate<Point> canPassThrough = p -> world.inBounds(p) && (!world.isOccupied(p) || (world.getOccupant(p).isPresent() && world.getOccupant(p).get() instanceof Tree));

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
