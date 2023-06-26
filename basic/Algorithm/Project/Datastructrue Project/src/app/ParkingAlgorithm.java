package app;

import Model.VirtualCar;
import STL.Queue;
import STL.Stack;

public class ParkingAlgorithm {
    private int n=5;//假设停车场的大小可以停放100辆汽车
    private Stack<VirtualCar> parkingSlot;//停车场
    private Stack<VirtualCar> outCars;//从停车场暂时退出的车辆
    private Queue<VirtualCar> waitingLine;


    public ParkingAlgorithm() {
        VirtualCar car1=new VirtualCar("辽A00000","10：05");
        VirtualCar car2=new VirtualCar("辽A00001","10：06");
        VirtualCar car3=new VirtualCar("辽A00002","10：07");
        VirtualCar car4=new VirtualCar("辽A00003","10：08");
        this.parkingSlot=new Stack<>(5);
        parkingSlot.push(car1);
        parkingSlot.push(car2);
        parkingSlot.push(car3);
        parkingSlot.push(car4);
        this.outCars=new Stack<>(5);
        this.waitingLine=new Queue<>();
    }
    /**
     * 离开停车场
     * @param number  要离开的车的车牌号
     * @param leaveTime  离开时间
     * @throws Exception
     */
    public void leave(String number,String leaveTime ) throws Exception {
        System.out.println("\t\t\t\t****离开****");
        VirtualCar car=new VirtualCar(number,leaveTime);
//        for (int i=0;i<parkingSlot.depth();i++){
//            if (!parkingSlot.element(i).getCarNumber().equals(car.getCarNumber())){
//                System.out.println("输入错误，停车场无此车，请从新输入");return;
//            }
//        }
        VirtualCar outCar = null;
        while (!parkingSlot.isEmpty()){
            outCar=parkingSlot.pop();//将顶部的元素推出

            if (outCar.getCarNumber().equals(number)){//若是要离开的车，则停止算法，将原来的车推回停车场
                outCar.setLeaveTime(leaveTime);//设置离开时间
                System.out.println("\t\t\t\t车牌号为："+outCar.getCarNumber());
                System.out.println("\t\t\t\t离开时间： "+outCar.getLeaveTime());
                while (!outCars.isEmpty()){
                    VirtualCar backCar=outCars.pop();
                    parkingSlot.push(backCar);
                }
                break;
            }else {
                outCars.push(outCar);//将顶部的车推入另一个Stack
            }
            printStatus();
        }
        //当有车离开时，将等待中的车推入停车场
        if (!waitingLine.empty()){//若等待的队列不为空
            VirtualCar inCar=waitingLine.remove();
            inCar.setInParkingslotTime(leaveTime);
            parkingSlot.push(inCar);
            System.out.println("\t\t\t\t****"+outCar.getCarNumber()+"离开停车场****");
            System.out.println("\t\t\t\t****"+inCar.getCarNumber()+"能够进入停车场****");
            System.out.println("\t\t\t\t进入时间为： "+inCar.getInParkingslotTime());
            printStatus();
        }
    }

    /**
     * 停车
     * @param number
     * @param arriveTime
     * @throws Exception
     */
    public void parkCar(String number,String arriveTime) throws Exception {
        System.out.println("\t\t\t\t****停车****");
        VirtualCar car=new VirtualCar(number,arriveTime);
        for (int i=0;i<parkingSlot.depth();i++){
            if (parkingSlot.element(i).getCarNumber().equals(car.getCarNumber())){
                System.out.println("输入错误，停车场已有此车，请从新输入");return;
            }
        }
            //如果停车场满了
            if (parkingSlot.isFull()){
                waitingLine.add(car);
                System.out.println("\t\t\t\t车牌号为： "+car.getCarNumber());
                System.out.println("\t\t\t\t停车场已满！请到过道排队");
            }else {
                parkingSlot.push(car);
                car.setInParkingslotTime(arriveTime);
                System.out.println("\t\t\t\t车牌号为： "+car.getCarNumber());
                System.out.println("\t\t\t\t到达时间为: "+car.getInParkingslotTime());
            }
            printStatus();


//        if (parkingSlot.contains(car)){
//            System.out.println("输入错误，停车场已有此车");
//        }else{

//        }

    }

    /**
     * 显示停车场状态
     */
    public void printParkingSlot(){
        System.out.println("\t\t\t\t*******停车场中车辆******");
        System.out.println("\t\t\t\t****已有车辆："+parkingSlot.depth()+"****");
        for (int i=0;i<parkingSlot.depth();i++){
            System.out.print(parkingSlot.element(i).getCarNumber()+"  ");
        }
        System.out.println();
    }

    /**
     * 用于显示当前各地状态
     */
    public void printTempParkingSlot(){
        System.out.println("\t\t\t\t*******暂停车场中车辆******");
        System.out.println("\t\t\t\t****已有车辆："+outCars.depth()+"****");
        for (int i=0;i<outCars.depth();i++){
            System.out.print(outCars.element(i).getCarNumber()+"  ");
        }
        System.out.println();
    }

    public void printWaitingLine(){
        System.out.println("\t\t\t\t*******过道中车辆******");
        System.out.println("\t\t\t\t****已有车辆："+waitingLine.size()+"在等待****");
    }

    public void printStatus(){
        printParkingSlot();
        printTempParkingSlot();
        printWaitingLine();
        System.out.println();
    }
}
