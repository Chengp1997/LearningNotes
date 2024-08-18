public class SpeedLimitedCar extends CarDecorator{
    public SpeedLimitedCar(Car car) {
        super(car);
    }

    @Override
    public void setSpeed(int s) {
        if (s <= 120) {
            super.setSpeed(s);
        }
        else {
            throw new RuntimeException("Wrong speed!");
        }
    }
}
