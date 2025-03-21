import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MushroomWizard extends BehaviorEntity {

    public static final String MUSHROOM_WIZARD_KEY = "mushroom_wizard";

    public static final int MUSHROOM_WIZARD_PARSE_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int MUSHROOM_WIZARD_PARSE_PROPERTY_COUNT = 1;

    public MushroomWizard(String id, Point position, List<PImage> images, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        List<Point> adjacentPositions = new ArrayList<>(List.of(
                new Point(getPosition().x - 1, getPosition().y),
                new Point(getPosition().x + 1, getPosition().y),
                new Point(getPosition().x, getPosition().y - 1),
                new Point(getPosition().x, getPosition().y + 1)
        ));
        Collections.shuffle(adjacentPositions);




        List<Point> mushroomBackgroundPositions = new ArrayList<>();
        List<Point> mushroomEntityPositions = new ArrayList<>();
        for (Point adjacentPosition : adjacentPositions) {
            if (world.inBounds(adjacentPosition) && !world.isOccupied(adjacentPosition) && world.hasBackground(adjacentPosition)) {
                Background bg = world.getBackgroundCell(adjacentPosition);
                if (bg.getId().equals("grass")) {
                    mushroomBackgroundPositions.add(adjacentPosition);
                } else if (bg.getId().equals("grass_mushrooms_wizard")) {
                    mushroomEntityPositions.add(adjacentPosition);
                }
            }
        }

        if (!mushroomBackgroundPositions.isEmpty()) {
            Point backgroundPosition = mushroomBackgroundPositions.get(0);

            Background background = new Background("grass_mushrooms_wizard", imageLibrary.get("grass_mushrooms_wizard"), 0);
            world.setBackgroundCell(backgroundPosition, background);
        } else if (!mushroomEntityPositions.isEmpty()) {
            Random random = new Random();
            int randomNumber = random.nextInt(4);

            Point position = mushroomEntityPositions.get(0);

            if (randomNumber == 0) {
                MushroomWizard mushroomWizard = new MushroomWizard(MUSHROOM_WIZARD_KEY, position, imageLibrary.get(MUSHROOM_WIZARD_KEY), getBehaviorPeriod() );

                world.addEntity(mushroomWizard);
                mushroomWizard.scheduleActions(scheduler, world, imageLibrary);
            } else if (randomNumber == 1) {
                MushroomWizard mushroomWizard = new MushroomWizard(MUSHROOM_WIZARD_KEY, position, imageLibrary.get("green_" + MUSHROOM_WIZARD_KEY), getBehaviorPeriod() * 4.0);

                world.addEntity(mushroomWizard);
                mushroomWizard.scheduleActions(scheduler, world, imageLibrary);
            } else if (randomNumber == 2) {
                MushroomWizard mushroomWizard = new MushroomWizard(MUSHROOM_WIZARD_KEY, position, imageLibrary.get("purple_" + MUSHROOM_WIZARD_KEY), getBehaviorPeriod() * 2.0);

                world.addEntity(mushroomWizard);
                mushroomWizard.scheduleActions(scheduler, world, imageLibrary);
            } else {
                MushroomWizard mushroomWizard = new MushroomWizard(MUSHROOM_WIZARD_KEY, position, imageLibrary.get("red_" + MUSHROOM_WIZARD_KEY), getBehaviorPeriod() * 4.0);

                world.addEntity(mushroomWizard);
                mushroomWizard.scheduleActions(scheduler, world, imageLibrary);
            }

        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }
}
