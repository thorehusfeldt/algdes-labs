import javafx.util.Pair;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<DoublePoint> points = Parser.parse("../data/u1432-tsp.txt");
        ClosestPairAlgorithm naive = new NaiveClosestPairAlgorithm();
        ClosestPairAlgorithm divideAndConquer = new DivideAndConquerClosestPairAlgorithm();

        MeasurablePair naiveResult = naive.findClosestPair(points);
        System.out.println(naiveResult);
        System.out.println(points.size() + " " + naiveResult.distance());
        MeasurablePair divideAndConquerResult = divideAndConquer.findClosestPair(points);
        System.out.println(divideAndConquerResult);
        System.out.println(points.size() + " " + divideAndConquerResult.distance());

    }


}
