#include <iostream>
#include <fstream>
#include <queue>
#include "simProcess.h"

using namespace std;

//Original code was setup to allow FIFO, Round Robin, or Feedback
enum {
  S_FIFO,
  S_RR,
  S_FB
};

//select an algorithm for scheduling
const int ALGORITHM = S_RR;

int main() {
  //setup (double ended) queues for processes (based on state)
  int q;
  ifstream fin;
  double time[110];//store the time of all the time quantum
  for(q=1;q<=110;q++){
        deque<simProcess> newProcs;
        deque<simProcess> readyProcs;
        deque<simProcess> exitProcs;
        int counter=0;
        simProcess runningProc; //can only be one process running at a time
        bool isRunning = false; //initially nothing is running
  //load simulation data
  unsigned int arrival, duration;
  fin.open("input");
  while (fin >> arrival) {
    fin >> duration;
    simProcess sp = simProcess(arrival, duration);
    newProcs.push_back(sp);
  }


  fin.close();
  //////////////////////////////ready for simulation
  //continue until there are no new or ready processes (everything has finished running)
  unsigned int simTime = 0; //cap at 999999 to prevent accidental infinite loop
  while (!(newProcs.empty() && readyProcs.empty() && !isRunning) && (simTime < 999999)) {
    switch(ALGORITHM) {
    case S_FIFO:
      simTime++; //update simulation time
      //any arriving processes (per simulation time) can go into the ready queue
      while (simTime == newProcs.front().getArrival()) {
        readyProcs.push_back(newProcs.front());
        newProcs.pop_front();
        //cout << "Popped from newProcs " << readyProcs.back().getArrival() << endl;
      }
      if (!isRunning && !readyProcs.empty()) { //if no process WAS running, BUT we have one
        runningProc = readyProcs.front(); //pull first available process
        readyProcs.pop_front(); //no longer "ready" - it's "running"
        isRunning = true;
//cout << "Running process " << runningProc.getArrival() << endl;
      }
      if (isRunning) { //"process" one work unit
        if (runningProc.process()) { //if job has completed, move to finished queue
          runningProc.setFinished(simTime+1); //finishes *after* this time unit completes
          exitProcs.push_back(runningProc); //switch queues
          isRunning = false; //prepare for the next process
/*cout << "Finished process with normalized Turn-around time "
     << exitProcs.back().getNormalizedTurnaround() << endl
     << exitProcs.back().getArrival() << endl
     << exitProcs.back().getFinish() << endl
     << exitProcs.back().getDuration() << endl;
*/
        }
//cout << runningProc.getRemaining() << " work left on running process" << endl;
      }
    break;
    /////////////////TO DO ... round-robin scheduling
    case S_RR:
        simTime++;
        while (simTime == newProcs.front().getArrival()) {
        readyProcs.push_back(newProcs.front());
        newProcs.pop_front();
        }
      if(isRunning && counter==q){
               readyProcs.push_back(runningProc);
                isRunning=false;
                counter=0;
      }
      counter++;
        if (!isRunning && !readyProcs.empty()) { //if no process WAS running, BUT we have one
        runningProc = readyProcs.front(); //pull first available process
        readyProcs.pop_front(); //no longer "ready" - it's "running"
        isRunning = true;
//cout << "Running process " << runningProc.getArrival() << endl;
      }
       if (isRunning) { //"process" one work unit
        if (runningProc.process()) { //if job has completed, move to finished queue
          runningProc.setFinished(simTime+1); //finishes *after* this time unit completes
          exitProcs.push_back(runningProc); //switch queues
          isRunning = false; //prepare for the next process
          counter=0;
        }
        }

    break;
    /////////////////TO DO ... feedback scheduling
    case S_FB:
    break;
    ///////////////////////////////////////////////////////////////////
    ///////////Intentionally no default block - no scheduling alg == error
    default:
      cout << "ERROR - need a scheduling algorithm!" << endl << endl;
      return -1;
    }

  }

cout<<q  /*<< "     There are " << exitProcs.size() << " finished processes" */;
  unsigned int avgNTaT = 0;
  for (deque<simProcess>::iterator it = exitProcs.begin(); it != exitProcs.end(); it++) {
    avgNTaT += (*it).getNormalizedTurnaround();
  }
  time[q]=static_cast<double>(avgNTaT) / exitProcs.size();
  cout << "   average NT  "
       << time[q] << endl;
       exitProcs.clear();
  }

//find the time quantum that use least time
  double minTime=time[1];
  int i;
  for(i=1;i<=110;i++){
    if(minTime>time[i]){
        minTime=time[i];
        q=i;
    }
  }
  cout<<"the least time is"<<endl
      <<minTime<<endl
      <<"the time quantum is"<<endl
      <<q<<endl;
  return 0;
}
