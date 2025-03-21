import processing.core.PImage;

import java.util.List;

/** Subclass for animation behavior (made to help Animation class) */
abstract class ActioningEntity extends BehaviorEntity {
    private final double animationPeriod;

    /** Constructor for ActioningEntity class */
    public ActioningEntity(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod);
        this.animationPeriod = animationPeriod;
    }

    /** When called creates a new Animation event and schedules the behavior */
    public void scheduleAnimation(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Animation(this, 0), animationPeriod);
    }

    /** More global getters and interface declaration */
    abstract public void updateImage();

    public double getAnimationPeriod() {
        return animationPeriod;
    }
}

