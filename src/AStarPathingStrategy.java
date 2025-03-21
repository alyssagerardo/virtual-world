import org.w3c.dom.Node;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy {

    /**
     * Return a list containing a single point representing the next step toward a goal
     * If the start is within reach of the goal, the returned list is empty.
     *
     * @param start the point to begin the search from
     * @param end the point to search for a point within reach of
     * @param canPassThrough a function that returns true if the given point is traversable
     * @param withinReach a function that returns true if both points are within reach of each other
     * @param potentialNeighbors a function that returns the neighbors of a given point, as a stream
     */
    public List<Point> computePath(
            Point start,
            Point end,
            Predicate<Point> canPassThrough,
            BiPredicate<Point, Point> withinReach,
            Function<Point, Stream<Point>> potentialNeighbors
    ) {
        if (withinReach.test(start, end)) {
            return new ArrayList<>();
        }

        Stream<Point> nextPosition = potentialNeighbors.apply(start)
                .filter(p -> Math.abs(end.x - p.x) < Math.abs(end.x - start.x))
                .filter(canPassThrough);

        List<Point> pointList = nextPosition.toList();
        double currentf = 0.0;
        Point currentPoint = null;
        if (!pointList.isEmpty()) {
            if (pointList.size() == 1) {
                Point nextPoint = new Point(pointList.get(0).x, pointList.get(0).y);

                List<Point> path = new ArrayList<>();
                path.add(nextPoint);

                path.addAll(computePath(nextPoint, end, canPassThrough, withinReach, potentialNeighbors));

                return path;
            } else {
                for (Point point : pointList) {
                    double g = Math.abs(start.x - point.x) + Math.abs(start.y - point.y);
                    double h = Math.abs(end.x - point.x) + Math.abs(end.y - point.y);
                    double f = g + h;

                    if (f < currentf) {
                        currentPoint = point;
                    }
                }
                List<Point> path = new ArrayList<>();
                path.add(currentPoint);

                path.addAll(computePath(currentPoint, end, canPassThrough, withinReach, potentialNeighbors));

                return path;
            }
        }


        return new ArrayList<>();
    }
}
