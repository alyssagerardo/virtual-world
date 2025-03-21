public class Animation extends Behavior {

    private int repeatCount;

    public Animation(Entity entity, int repeatCount) {
        super(entity, null, null);
        this.repeatCount = repeatCount;
    }

    public void execute(EventScheduler scheduler) {
        executeAnimation(scheduler);
    }

    public void executeAnimation(EventScheduler scheduler) {
        ((ActioningEntity) getEntity()).updateImage();
        if (repeatCount != 1) {
            scheduler.scheduleEvent(getEntity(), new Animation(this.getEntity(), Math.max(this.repeatCount - 1, 0)), ((ActioningEntity) getEntity()).getAnimationPeriod());
        }
    }

}
