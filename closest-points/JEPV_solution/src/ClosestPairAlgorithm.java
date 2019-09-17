import javafx.util.Pair;
import java.util.List;

public interface ClosestPairAlgorithm {

    Pair<DoublePoint,DoublePoint> findClosestPair(List<DoublePoint> points);

}
