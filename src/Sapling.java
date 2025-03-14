import processing.core.PImage;

import java.util.List;

public class Sapling extends Health implements Transformable {

    public static final String SAPLING_KEY = "sapling";

    public static final int SAPLING_PARSE_PROPERTY_COUNT = 0;

    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final double SAPLING_ANIMATION_PERIOD = 0.0125;

    public static final double SAPLING_BEHAVIOR_PERIOD = 2.0;


    public Sapling(String id, Point position, List<PImage> images) {
        super(id, position, images, SAPLING_ANIMATION_PERIOD, SAPLING_BEHAVIOR_PERIOD, 0);
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        setHealth(getHealth() + 1);
        if (!transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduleAnimation(scheduler, world, imageLibrary);
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getHealth() <= 0) {
            Stump stump = new Stump(Stump.STUMP_KEY + "_" + getId(), getPosition(), imageLibrary.get(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        } else if (getHealth() >= SAPLING_HEALTH_LIMIT) {
            Tree tree = new Tree(
                    Tree.TREE_KEY + "_" + getId(),
                    getPosition(),
                    imageLibrary.get(Tree.TREE_KEY),
                    NumberUtil.getRandomDouble(Tree.TREE_RANDOM_ANIMATION_PERIOD_MIN, Tree.TREE_RANDOM_ANIMATION_PERIOD_MAX), NumberUtil.getRandomDouble(Tree.TREE_RANDOM_BEHAVIOR_PERIOD_MIN, Tree.TREE_RANDOM_BEHAVIOR_PERIOD_MAX),
                    NumberUtil.getRandomInt(Tree.TREE_RANDOM_HEALTH_MIN, Tree.TREE_RANDOM_HEALTH_MAX)
            );

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageLibrary);

            return true;
        }

        return false;
    }

    public void updateImage() {
        if (getHealth() <= 0) {
            setImageIndex(0);
        } else if (getHealth() < SAPLING_HEALTH_LIMIT) {
            setImageIndex(getImages().size() * getHealth() / SAPLING_HEALTH_LIMIT);
        } else {
            setImageIndex(getImages().size() - 1);
        }
    }

}
