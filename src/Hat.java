import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Hat extends ActioningEntity {
    public static final String HAT_KEY = "hat";

    public static final int HAT_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int HAT_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int HAT_PARSE_PROPERTY_COUNT = 3;

    public Hat(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, animationPeriod, behaviorPeriod);
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Random random = new Random();

        int randomNumber = random.nextInt();

        if (randomNumber % 2 == 0) {
            Alligator alligator = new Alligator(Alligator.ALLIGATOR_KEY, getPosition(), imageLibrary.get(Alligator.ALLIGATOR_KEY), getAnimationPeriod(), getBehaviorPeriod());

            world.removeEntity(scheduler, this);
            world.addEntity(alligator);
            alligator.scheduleActions(scheduler, world, imageLibrary);
        } else {
            Raccoon raccoon = new Raccoon(Raccoon.RACCOON_KEY, getPosition(), imageLibrary.get(Raccoon.RACCOON_KEY), getAnimationPeriod(), getBehaviorPeriod());

            world.removeEntity(scheduler, this);
            world.addEntity(raccoon);
            raccoon.scheduleActions(scheduler, world, imageLibrary);
        }

    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduleAnimation(scheduler, world, imageLibrary);
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public void updateImage() {
        setImageIndex(getImageIndex() + 1);
    }
}
