abstract public class Car {
    public Car() {

    }

    @Override
    public int getSpeed() {
        return readSpeed();
    }

    @Override
    public void setSpeed(int s) {
        writeSpeed(s);
    }

    abstract protected void writeSpeed(int s);
    abstract protected int readSpeed();
}
