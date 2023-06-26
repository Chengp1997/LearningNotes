package Model;

public class VirtualCar {
    private String  carNumber;
    private String leaveTime;
    private String arriveTime;
    private String inParkingslotTime;

    public VirtualCar(){
        this.carNumber=getCarNumber();
        this.leaveTime=getLeaveTime();
        this.arriveTime=getArriveTime();
        this.inParkingslotTime=getInParkingslotTime();
    }
    public VirtualCar(String carNumber, String arriveTime){
        this.setCarNumber(carNumber);
        this.setArriveTime(arriveTime);
    }

    //判断两辆车是否相等
    public boolean equals(VirtualCar car){
        if (this.getCarNumber().equals(car.getCarNumber())){
            return true;
        }else {
            return false;
        }
    }


    public String getArriveTime() {
        return arriveTime;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getInParkingslotTime() {
        return inParkingslotTime;
    }

    public void setInParkingslotTime(String inParkingslotTime) {
        this.inParkingslotTime = inParkingslotTime;
    }


}
