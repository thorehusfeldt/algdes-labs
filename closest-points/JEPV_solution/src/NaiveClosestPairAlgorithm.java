import javafx.util.Pair;

import java.util.List;

public class NaiveClosestPairAlgorithm implements ClosestPairAlgorithm {

    @Override
    public Pair<DoublePoint,DoublePoint> findClosestPair(List<DoublePoint> points) {

        double closestDistance = -1, currentDistance;
        Pair<DoublePoint,DoublePoint> closestPair = null;

        for(DoublePoint point1 : points) {
            for(DoublePoint point2 : points) {

                if(point1.equals(point2)) continue;

                currentDistance = point1.distance(point2);

                if(closestDistance == -1 && closestPair == null) {
                    closestDistance = currentDistance;
                    closestPair = new Pair<>(point1, point2);
                } else if(currentDistance < closestDistance) {
                    closestDistance = currentDistance;
                    closestPair = new Pair<>(point1, point2);
                }
            }
        }

        return closestPair;
    }
}
