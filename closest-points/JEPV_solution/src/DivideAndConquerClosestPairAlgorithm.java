import javafx.util.Pair;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class DivideAndConquerClosestPairAlgorithm implements ClosestPairAlgorithm {

    @Override
    public Pair<DoublePoint,DoublePoint> findClosestPair(List<DoublePoint> points) {

        List<DoublePoint> points_sorted_by_x = new ArrayList<>(points);
        points_sorted_by_x.sort(Comparator.comparingDouble(point -> point.x));

        List<DoublePoint> points_sorted_by_y = new ArrayList<>(points);
        points_sorted_by_y.sort(Comparator.comparingDouble(point -> point.y));

        return findClosestPairRecursive(points_sorted_by_x, points_sorted_by_y);
    }

    private MeasurablePair findClosestPairRecursive(List<DoublePoint> points_sorted_by_x, List<DoublePoint> points_sorted_by_y) {

        if(points_sorted_by_x.size() <= 3) {
            return new MeasurablePair(new NaiveClosestPairAlgorithm().findClosestPair(new ArrayList<>(points_sorted_by_x)));
        }

        int m = points_sorted_by_x.size() / 2;
        List<DoublePoint> Qx = points_sorted_by_x.subList(0, m);
        List<DoublePoint> Rx = points_sorted_by_x.subList(m+1, points_sorted_by_x.size());
        List<DoublePoint> Qy = points_sorted_by_y.subList(0, m);
        List<DoublePoint> Ry = points_sorted_by_y.subList(m+1, points_sorted_by_y.size());

        MeasurablePair q = findClosestPairRecursive(Qx, Qy);
        MeasurablePair r = findClosestPairRecursive(Rx, Ry);

        MeasurablePair currentClosestPair = q.distance() < r.distance() ? q : r;
        double xStar = points_sorted_by_x.get(points_sorted_by_x.size()-1).x;
        HashSet<DoublePoint> seperatingLine = new HashSet<>();
        for(DoublePoint point : points_sorted_by_x) {
            if(point.x == xStar) seperatingLine.add(point);
        }

        LinkedList<DoublePoint> S = new LinkedList<>();

        for(DoublePoint point : points_sorted_by_y) {
            for(DoublePoint linePoint : seperatingLine) {
                if(point.distance(linePoint) <= currentClosestPair.distance()) S.add(point);
            }
        }

        DoublePoint s, sInner;
        double minDistance = -1;
        MeasurablePair sPair = null;
        int sInnerIndex;

        for(int i = 0; i < S.size(); i++) {
            s = S.get(i);
            for(int j = 0; j < 15; j++) {
                sInnerIndex = i + j + 1;
                if(sInnerIndex < S.size()) {
                    sInner = S.get(sInnerIndex);
                } else {
                    break;
                }

                if(minDistance == -1 || s.distance(sInner) < minDistance) {
                    minDistance = s.distance(sInner);
                    sPair = new MeasurablePair(s, sInner);
                }
            }

        }

        if(sPair != null && sPair.distance() < currentClosestPair.distance()) {
            return sPair;
        } else return currentClosestPair;

    }

    private class MeasurablePair extends Pair<DoublePoint,DoublePoint> {

        private MeasurablePair(DoublePoint key, DoublePoint value) {
            super(key, value);
        }

        private MeasurablePair(Pair<DoublePoint,DoublePoint> pair) {
            super(pair.getKey(), pair.getValue());
        }

        private double distance() {
            return getKey().distance(getValue());
        }
    }

}
