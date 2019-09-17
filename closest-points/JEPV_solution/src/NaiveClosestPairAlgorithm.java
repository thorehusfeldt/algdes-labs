import javafx.util.Pair;

import java.util.List;

public class NaiveClosestPairAlgorithm implements ClosestPairAlgorithm {

    @Override
    public MeasurablePair findClosestPair(List<DoublePoint> points) {

        double closestDistance = Double.MAX_VALUE, currentDistance;
        MeasurablePair closestPair = null;

        for(DoublePoint point1 : points) {
            for(DoublePoint point2 : points) {

                if(point1.equals(point2)) continue;

                currentDistance = point1.distance(point2);

                if(currentDistance < closestDistance) {
                    closestDistance = currentDistance;
                    closestPair = new MeasurablePair(point1, point2);
                }
            }
        }

        return closestPair;
    }
}
