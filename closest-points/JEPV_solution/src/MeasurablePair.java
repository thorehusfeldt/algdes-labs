public class MeasurablePair {

    DoublePoint point1;
    DoublePoint point2;

    public MeasurablePair(DoublePoint point1, DoublePoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }


    public double distance() {
        return point1.distance(point2);
    }
}