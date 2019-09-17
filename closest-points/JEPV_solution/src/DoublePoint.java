import java.awt.geom.Point2D;

public class DoublePoint extends Point2D.Double {

    private String id;

    public DoublePoint(String id, double x, double y) {
        super(x, y);
        this.id = id;
    }

    public String getID() {
        return this.id;
    }

}
