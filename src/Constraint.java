public class Constraint<T extends Comparable>{
    private T lowerBoundry, upperBoundry;

    public Constraint(T lowerBoundry, T upperBoundry) {
        this.lowerBoundry = lowerBoundry;
        this.upperBoundry = upperBoundry;
    }

    public T getLowerBoundry() {
        return lowerBoundry;
    }

    public T getUpperBoundry() {
        return upperBoundry;
    }

    public void setLowerBoundry(T lowerBoundry) {
        this.lowerBoundry = lowerBoundry;
    }

    public void setUpperBoundry(T upperBoundry) {
        this.upperBoundry = upperBoundry;
    }

    public boolean respectsBoundaries(T compared) {
        if(compared == null) {
            return upperBoundry == null && lowerBoundry == null;
        }
        if(upperBoundry != null && upperBoundry.compareTo(compared) < 0)
            return false;
        if(lowerBoundry != null && lowerBoundry.compareTo(compared) > 0)
            return false;

        return true;
    }
}
