public class BasicCar extends Car {
    private int speed;

    protected void writeSpeed(int s) {
        this.speed = s;
    }

    protected int readSpeed() {
        return speed;
    }

}
