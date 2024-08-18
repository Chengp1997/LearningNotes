#include <iostream>
#include <fstream>
#include <queue>
#include "function.h"

const int total=102400;//total bytes
int main()
{
    int processSize;

    ifstream fin;
    int pageSize=1024;
    int page=total/pageSize;//page
    int memory[page]={0};
    cout<<sizeof(memory)/sizeof(memory[0])<<endl;
    //store process
    deque<process> newProcs;
    vector<process> runningProcess;
    deque<process> exitProcs;

    //calculate the free space
    int freespace=page;
    unsigned int arrival, duration, size;
    fin.open("input.txt");
   // fin.open("sameData.txt");//for testing the same data
    int countprocess=1;
    while(fin>>arrival){
        fin>>duration;
        //fin>>size;//to test the same data, use the same size for each test
       process p=process((rand() % (1024-2+1))+ 2,arrival,duration);
       //process p=process(size,arrival,duration);//to test the same data
        cout<<p.getArrival()<<"  "<<p.getDuration()<<"  "<<p.getProcessSize()<<" "<<p.getRemaining()<<endl;
        newProcs.push_back(p);
        ;
    }
    fin.close();
    int totalprocess=newProcs.size();
    unsigned int simTime=0;
    while(totalprocess!=exitProcs.size()){

     while (simTime == newProcs.front().getArrival()) {

            if(newProcs.front().allocate(pageSize,memory,freespace)){
             cout<< "for process "<<countprocess<< "the memoryTable is "<<endl;
            newProcs.front().printMemoryTable();
            runningProcess.push_back(newProcs.front());//this is the process begin to run
            newProcs.pop_front();
            countprocess++;
            }else{
                cout<< "no memory right now!"<<endl;
            }
     }
     simTime++;
    //cout<<"allocate now!!  "<<endl;
   //printMemory(memory);

    for(int i=0;i<runningProcess.size();i++){
        if (runningProcess[i].exit()) {
          runningProcess[i].setFinished(simTime+1);
          exitProcs.push_back(runningProcess[i]);
          runningProcess[i].leave(pageSize,memory,freespace);
          //cout<< "after reallocate ,the free space now is "<<freespace<<endl;
          cout<<"leave now!!  "<<endl;
            //printMemory(memory);
            }
    }
    int fragment=0;
    for(int i=0;i<runningProcess.size();i++){
        fragment+=runningProcess[i].getFragment();
    }
      cout<< "Simulation Time : "<<simTime<< "     free space now: "<<freespace<<"    fragment now :  "<<fragment<<endl;

    }

return 0;
}


