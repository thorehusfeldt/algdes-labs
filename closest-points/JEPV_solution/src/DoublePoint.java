import java.awt.geom.Point2D;

public class DoublePoint extends Point2D.Double {

    private String id;

    public DoublePoint(String id, double x, double y) {
        super(x, y);
        this.id = id;
    }

    @Override
    public String toString() {
        return "[id: " + id + ", x: " + this.x + ", y: " + this.y + "]";
    }

    @Override
    public boolean equals(Object o) {
        if(super.equals(o)) {
            DoublePoint other = (DoublePoint) o;
            return this.id.equals(other.id);
        }

        return false;
    }

}
