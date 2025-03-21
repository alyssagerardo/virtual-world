/** Subclass for Animation */
public class Animation extends Behavior {
    private int repeatCount;

    /** Constructor for Animation class */
    public Animation(Entity entity, int repeatCount) {
        super(entity, null, null);
        this.repeatCount = repeatCount;
    }

    /** Calls an execute function for an Animation event */
    public void execute(EventScheduler scheduler) {
        executeAnimation(scheduler);
    }

    /** When called updates image of Entity and may create new Animation event */
    public void executeAnimation(EventScheduler scheduler) {
        ((ActioningEntity) getEntity()).updateImage();
        if (repeatCount != 1) {
            scheduler.scheduleEvent(getEntity(), new Animation(this.getEntity(), Math.max(this.repeatCount - 1, 0)), ((ActioningEntity) getEntity()).getAnimationPeriod());
        }
    }
}
