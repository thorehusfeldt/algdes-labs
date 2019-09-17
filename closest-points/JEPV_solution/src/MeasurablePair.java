import javafx.util.Pair;

public class MeasurablePair extends Pair<DoublePoint,DoublePoint> {

    public MeasurablePair(DoublePoint key, DoublePoint value) {
        super(key, value);
    }

    public MeasurablePair(Pair<DoublePoint,DoublePoint> pair) {
        super(pair.getKey(), pair.getValue());
    }

    public double distance() {
        return getKey().distance(getValue());
    }
}