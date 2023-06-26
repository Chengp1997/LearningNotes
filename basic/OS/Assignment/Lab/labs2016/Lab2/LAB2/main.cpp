#include <iostream>
#include <mutex>
#include <thread>
#include <condition_variable>

using std::cout;
using std::cin;
using std::endl;
using std::mutex;
using std::thread;
using std::unique_lock;
using std::condition_variable;

struct sharedData {
    mutex m;
    int sizeofbuffer=4;
    condition_variable produce,consume;
    int count;//the amount of the product
};

//producer
void producer(const int i, sharedData* dat)
{
    {
        unique_lock<mutex> lck(dat->m);
        cout<<"producer "<<i<< " come in!"<<endl;
    }
    /* how to protecting critical session*/
    //using the mutex. Every time the thread enter, it will get its unique lock, the thread will process during the
    //period of the unique lock. And the mutex will prevent the mutual exculsion, therefore prevent
    //the other process entering the critical session.
    std::this_thread::sleep_for (std::chrono::seconds(i));
    {
      unique_lock<mutex> lck(dat->m);
      while(dat->count==dat->sizeofbuffer){//if the buffer is full, stop putting the product in the buffer
        cout<<"For producer "<< i <<" garage full!! stop producing"<<endl;
        dat->produce.wait(lck);
      }
      /* how to ensure the upper bound */
      //for producer, if the amount of the products equals to the buffer size, which means
      //the producer can not produce new product into the buffer.
      //Here, I use the while statement to ensure the upper bound.While the situation occurs,
      //block the thread that is running, when the consumer consumes a product, release the
      //thread, allow the producer put the product into the buffer.

      /* interesting findings*/
      //if I use IF statement here, there is a little bit different in the results.using WHILE
      //statement here will make sure there will be no thread changing in the program.
      dat->count++;
      cout << "producer " << i << " produce a product, and the product amount is  " << dat->count << endl;

    }
    dat->consume.notify_one();
    /* how to prevent the deadlock */
    //for thread,every time it enters the process, it will get its lock. After all the operations are done,
    //the lock will be released afterwards.  which means, there will be no thread waiting to be released.
}
//consumer
void consumer(const int i, sharedData* dat)
{
     {
        unique_lock<mutex> lck(dat->m);
        cout<<"consumer"<<i<<" come in! the products are "<<dat->count<<endl;
    }
    std::this_thread::sleep_for (std::chrono::seconds(i));
    {
        unique_lock<mutex> lck(dat->m);
        while(dat->count==0){
                cout<<"For consumer "<< i <<",No products available right now!  WAITING!"<<endl;
                dat->consume.wait(lck);
        }
        /* how to ensure t he lower bound */
        //for consumer, if there is no products in the buffer size, which means
        //the consumer can not consume products from the buffer.
        //Here, I use the while statement to ensure the lower bound.While the situation occurs,
        //block the thread that is running, when the producer produces a product, release the
        //thread, allow the consumer consumes the product from the buffer.
       dat->count--;//³Ôµô
       cout << "consumer " << i << " consume a product, and the amount is  " << dat->count << endl;
    }
    //unlock
    dat->produce.notify_one();


}

int main()
{
    sharedData someData;
    someData.count = 0;
    thread spammers[12]; //create space to keep track of 4 threads

        for(int i = 0; i < 6; i++) //run the threads, keeping their ids
    {
        spammers[i] = thread(producer, i, &someData);
    }
     for(int i = 6; i < 12; i++) //run the threads, keeping their ids
    {
        spammers[i] = thread(consumer, i, &someData);
    }
    for(auto& s : spammers)
    {
        s.join();
    }

    cout << "Back in main the final count is " << someData.count << endl;
}
