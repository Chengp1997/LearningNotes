# Observer Pattern

### Design Principle：Strive for loosely coupled design

- Pattern name: Observable pattern
- problem：

  - 意义：定义了一种对象间一对多的依赖，从而，如果一个对象改变了，其他它依赖的对象都能够被通知
  - motivation：当对象间为了维持一致性而交换数据时，**高内聚，低耦合**的原则让这些对象间能够低耦合地进行交互
- solution：

  - ![1545442848124](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1545442848124.png)
  - Subject：主题，知晓其所拥有的observers，提供接口来增加/减少观察者
  - Observer：观察者，定义了一个接口用来通知更新的数据
  - ConcreteSubject：储存实观察者，当有新更新，告知观察者
  - ConcreteObserver：实现观察者接口
  - ![1544974089162](C:\Users\Emily_Chen\AppData\Roaming\Typora\typora-user-images\1544974089162.png)
- consequences：
  - advantages
    - Abstract coupling between Subject and Observer：使subject和observer之间松耦合（仍然能够交互，只是不知道细节）
    - 支持组播
    - Subject and Observer belong to different layers of abstraction in a system. (DIP)
  - drawbacks
    - Unexpected updates
    - cost of updates may be really high
- Applicability
  - When an abstraction has two aspects, one dependent on the other. Encapsulating these aspects in separate objects lets you vary and reuse them independently. 
  - When a change to one object requires changing others, and you don't know how many objects need to be changed. 
  - When an object should be able to notify other objects without making assumptions about who these objects are. In other words, you don't want these objects tightly coupled. 

Observer VS Observable

Observable是一个类

**Design Principle:Strive for loosely coupled designs**

```java
package com.company.ObservablePattern.example;

public interface Subject {
        public void attach(Observer observer);
        public void detach(Observer observer);
        public void notifyObservers();
}

```



```java
package com.company.ObservablePattern.example;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    public WeatherData(){
        observers=new ArrayList<Observer>();
    }
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o:observers){
            o.update(temperature,humidity,pressure);
        }
    }

    public void measurementsChanged(){
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature=temperature;
        this.humidity=humidity;
        this.pressure=pressure;
        measurementsChanged();
    }
}

```

```java
package com.company.ObservablePattern.example;

public interface Observer {
    public void update(float temperature, float humidity, float pressure);
}

```

```java
package com.company.ObservablePattern.example;

public class CurrentCondition implements Observer {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.println("temperature: "+temperature+"  humidity: "+humidity+"  pressure:  "+pressure);
    }
}

```

```java
package com.company.ObservablePattern.example;

public class App {
    public static void  main(String arg[]){
        Subject weatherData=new WeatherData();
        Observer observer1=new CurrentCondition();
        Observer observer2=new CurrentCondition();
        weatherData.attach(observer1);
        weatherData.attach(observer2);
        ((WeatherData) weatherData).setMeasurements(111,111,111);
        ((WeatherData) weatherData).setMeasurements(2222,222,222);
       }
}

```

参考：

https://www.cnblogs.com/zhenyulu/articles/73723.html