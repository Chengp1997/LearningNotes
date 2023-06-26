#include "function.h"
#include <iostream>
//constructor
process::process():arr(0), dur(0),rem(0),fin(0),processSize(0){}

process::process(int size,int arrival,int duration):arr(0), dur(0),rem(0),fin(0),processSize(0){
    processSize=size;
    arr=arrival;
    dur=duration;
    rem = duration;
    fragment=0;
}

bool process::allocate(int pagesize, int memory[],int &freespace){
    int i=0;
    int needPage=processSize/pagesize;
    int rest=processSize%pagesize;
    if(rest!=0)
        needPage+=1;
    if(freespace<0){
        return false;
    }
    freespace-=needPage;
    while (needPage!=0){
        if(memory[i]==0){
            memoryTable.push_back(i);
            memory[i]=pagesize;

            if(rest!=0){
                fragment=(pagesize-rest);
                if(needPage==1){
                memory[i]=rest;
                }
            }
            needPage--;
        }
        i++;
    }

    return true;
}

void process::leave(int pagesize,int memory[],int &freespace){
    //获取指定页
    int location;
    fragment=0;
    for(int i=0;i<memoryTable.size();i++){
        location=memoryTable[i];
        memory[location]=0;//reset to 0
    }

    freespace+=memoryTable.size();
    memoryTable.clear();

}

void process::printMemoryTable(){
    for(int i=0;i<memoryTable.size();i++){
       std::cout<<memoryTable[i]<< "  ";
    }
    std::cout<<endl;
}
void printMemory(int memory[]){
     for(int i=0;i<800;i++){
                cout<<memory[i]<<"  ";
                if(i%9==0)cout<<endl;
            }
}
