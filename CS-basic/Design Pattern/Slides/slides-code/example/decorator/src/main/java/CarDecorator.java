abstract public class CarDecorator extends Car{
    private Car baseObject;

    protected CarDecorator(Car car) {
        this.baseObject = car;
    }

    @Override
    public int getSpeed() {
        return baseObject.getSpeed();
    }

    @Override
    public void setSpeed(int s) {
        baseObject.setSpeed(s);
    }
}
