import processing.core.PImage;

import java.util.List;

abstract class ActioningEntity extends BehaviorEntity {
    private final double animationPeriod;

    public ActioningEntity(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod);
        this.animationPeriod = animationPeriod;
    }


    public void scheduleAnimation(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Animation(this, 0), animationPeriod);
    }

    abstract public void updateImage();

    public double getAnimationPeriod() {
        return animationPeriod;
    }


}

