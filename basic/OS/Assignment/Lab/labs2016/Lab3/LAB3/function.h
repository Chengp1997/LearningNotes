#ifndef FUNCTION_H_INCLUDED
#define FUNCTION_H_INCLUDED
#include <vector>
using namespace std;
class process{

    public:
        process();//constructor
        process(int size,int arrival,int duration);
        bool allocate(int pagesize,int memory[],int &freespace);//allocate the page into the memory
        void leave(int pagesize,int memory[],int &freespace);
        void printMemoryTable();
        inline bool exit() {rem--; return finished();}
        inline bool finished() {return (rem == 0);}
        inline void setFinished(unsigned int finished) {fin = finished;}
        inline unsigned int getProcessSize()const {return processSize;}
        inline unsigned int getArrival() const {return arr;}//arrival time
        inline unsigned int getDuration() const {return dur;}//service time
        inline unsigned int getRemaining() const {return rem;}//remaining time
        inline unsigned int getFinish() const {return fin;}
        inline getFragment(){return fragment;}
    private:
        unsigned int arr; //arrival
        unsigned int dur; //duration/service time
        unsigned int rem; //remaining time -- initially duration, finished when 0
        unsigned int fin;
        unsigned int processSize;
        vector <int> memoryTable;
        int fragment;
};
void printMemory(int memory[]);

#endif // FUNCTION_H_INCLUDED
