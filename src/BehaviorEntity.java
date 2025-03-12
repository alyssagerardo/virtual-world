import processing.core.PImage;

import java.util.List;

abstract class BehaviorEntity extends Entity {

    private final double behaviorPeriod;

    public BehaviorEntity(String id, Point position, List<PImage> images, double behaviorPeriod) {
        super(id, position, images);
        this.behaviorPeriod = behaviorPeriod;
    }

    abstract void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler);

    public void scheduleBehavior(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Behavior(this, world, imageLibrary), behaviorPeriod);
    }

    public double getBehaviorPeriod() {
        return behaviorPeriod;
    }
}
