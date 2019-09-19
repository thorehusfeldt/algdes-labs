import javafx.util.Pair;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class DivideAndConquerClosestPairAlgorithm implements ClosestPairAlgorithm {

    @Override
    public MeasurablePair findClosestPair(List<DoublePoint> points) {

        List<DoublePoint> points_sorted_by_x = new ArrayList<>(points);
        points_sorted_by_x.sort(Comparator.comparingDouble(point -> point.x));

        List<DoublePoint> points_sorted_by_y = new ArrayList<>(points);
        points_sorted_by_y.sort(Comparator.comparingDouble(point -> point.y));

        for(int i = 0; i < points.size(); i++) {
            DoublePoint xPoint = points_sorted_by_x.get(i);
            xPoint.setIndexInX(i);
        }

        return findClosestPairRecursive(points_sorted_by_x, points_sorted_by_y);
    }

    private MeasurablePair findClosestPairRecursive(List<DoublePoint> points_sorted_by_x, List<DoublePoint> points_sorted_by_y) {
        if(points_sorted_by_x.size() <= 3) {
            return new NaiveClosestPairAlgorithm().findClosestPair(new ArrayList<>(points_sorted_by_x));
        }

        int m = points_sorted_by_x.size() / 2;

        List<DoublePoint> Qx = new ArrayList<>(m);
        List<DoublePoint> Rx = new ArrayList<>(m);
        List<DoublePoint> Qy = new ArrayList<>(m);
        List<DoublePoint> Ry = new ArrayList<>(m);


        for(DoublePoint point : points_sorted_by_y) {
            if(point.getIndexInX() < m) {
                Qy.add(point);
            } else {
                Ry.add(point);
            }

        }

        List<DoublePoint> currentList;
        for(int i = 0; i < points_sorted_by_x.size(); i++) {
            DoublePoint point = points_sorted_by_x.get(i);
            if(i < m) {
                currentList = Qx;
            } else {
                currentList = Rx;
            }

            currentList.add(point);
            point.setIndexInX(currentList.size()-1);
        }



        //Qy.sort(Comparator.comparingDouble(point -> point.y));
        //Ry.sort(Comparator.comparingDouble(point -> point.y));


        /*List<DoublePoint> Qx = new ArrayList<>(points_sorted_by_x.subList(0, m));
        List<DoublePoint> Rx = new ArrayList<>(points_sorted_by_x.subList(m, points_sorted_by_x.size()));
        List<DoublePoint> Qy = new ArrayList<>(points_sorted_by_y.subList(0, m));
        List<DoublePoint> Ry = new ArrayList<>(points_sorted_by_y.subList(m, points_sorted_by_y.size()));*/

        MeasurablePair q = findClosestPairRecursive(Qx, Qy);
        MeasurablePair r = findClosestPairRecursive(Rx, Ry);

        MeasurablePair currentClosestPair = q.distance() < r.distance() ? q : r;

        DoublePoint linePoint1 = Qx.get(Qx.size() - 1);
        DoublePoint linePoint2 = new DoublePoint(null, linePoint1.x, linePoint1.y + 1);

        Line2D.Double L = new Line2D.Double(linePoint1, linePoint2);

        LinkedList<DoublePoint> S = new LinkedList<>();

        for(DoublePoint point : points_sorted_by_y) {
            if(L.ptLineDist(point) <= currentClosestPair.distance()) {
                S.add(point);
            }
        }


        /*double xStar = Qx.get(Qx.size()-1).x;
        LinkedList<DoublePoint> separatingLine = new LinkedList<>();

        for(DoublePoint point : points_sorted_by_x) {
            if(point.x == xStar) separatingLine.add(point);
        }

        LinkedList<DoublePoint> S = new LinkedList<>();

        for(DoublePoint point : points_sorted_by_y) {
            for(DoublePoint linePoint : separatingLine) {
                if(!point.equals(linePoint) && point.distance(linePoint) <= currentClosestPair.distance()) {
                    S.add(point);
                    break;
                }
            }
        }*/

        DoublePoint s, sInner;
        double minDistance = Double.MAX_VALUE;
        MeasurablePair sPair = null;
        int sInnerIndex;

        for(int i = 0; i < S.size(); i++) {
            s = S.get(i);
            for(int j = 0; j < 15; j++) {
                sInnerIndex = i + j + 1;
                if(sInnerIndex < S.size()) {
                    sInner = S.get(sInnerIndex);

                    if(s.equals(sInner)) continue;;

                    if(s.distance(sInner) < minDistance) {
                        minDistance = s.distance(sInner);
                        sPair = new MeasurablePair(s, sInner);
                    }

                } else {
                    break;
                }


            }

        }

        if(sPair != null && sPair.distance() < currentClosestPair.distance()) {
            return sPair;
        } else return currentClosestPair;

    }

}
