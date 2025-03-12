import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Wizard extends ActioningEntity {
    public static final String WIZARD_KEY = "wizard";

    public static final int WIZARD_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int WIZARD_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int WIZARD_PARSE_PROPERTY_COUNT = 3;

    private int spell_count;

    public Wizard(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int spell_count) {
        super(id, position, images, animationPeriod, behaviorPeriod);
        this.spell_count = spell_count;
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> wizardTarget = findWizardTarget(world);
        if (wizardTarget.isPresent()) {
            Point tgtPos = wizardTarget.get().getPosition();

            if (moveTo(world, wizardTarget.get(), scheduler, imageLibrary)) {
                if (spell_count > 3) {
                    world.removeEntity(scheduler, this);
                    System.out.println("Dang it. I guess that potion I drank earlier had some limits.");
                }
                spell_count += 1;
                Hat hat = new Hat(Hat.HAT_KEY, tgtPos, imageLibrary.get(Hat.HAT_KEY), getAnimationPeriod(), getBehaviorPeriod());

                world.addEntity(hat);
                hat.scheduleActions(scheduler, world, imageLibrary);
            }
        }
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduleAnimation(scheduler, world, imageLibrary);
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public Optional<Entity> findWizardTarget(World world) {
        List<Class<?>> potentialTargets;

        potentialTargets = List.of(Fairy.class, Dude.class);

        return world.findNearest(getPosition(), potentialTargets);
    }

    public boolean moveTo(World world, Entity target, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getPosition().adjacentTo(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());
            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
                Background bg = new Background("yellowroad", imageLibrary.get("yellowroad"), 0);
                world.setBackgroundCell(nextPos, bg);
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
