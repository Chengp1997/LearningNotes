#include <iostream>
#include <fstream>
#include <queue>
#include "simProcess.h"

using namespace std;

enum {
  S_FIFO,
  S_RR,
  S_FB
};

//seleect an algorith for scheduling
const int ALGORITHM = S_FIFO;

int main() {
  //setup queues for processes (based on state)
  deque<simProcess> newProcs;
  deque<simProcess> readyProcs;
  deque<simProcess> exitProcs;

  simProcess runningProc; //can only be one process running at a time
  bool isRunning = false;

  //load simulation data
  unsigned int arrival, duration;

  ifstream fin;
  fin.open("input");

  while (fin >> arrival) {
    fin >> duration;
    simProcess sp = simProcess(arrival, duration);
    newProcs.push_back(sp);
  }

  fin.close();

  //////////////////////////////ready for simulation
  //continue until there are no new or ready processes (everything has finished running)
  unsigned int simTime = 0; //cap at 999999 to prevent infinite loop
  while (!(newProcs.empty() && readyProcs.empty()) && (simTime < 999999)) {
    switch(ALGORITHM) {
    case S_FIFO:
      simTime++; //update simulation time
//cout << "Sim time now " << simTime << endl;
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
          runningProc.setFinished(simTime); //note finished time for calculations
          exitProcs.push_back(runningProc); //switch queues
          isRunning = false; //prepare for the next process
/*cout << "Finished process normalized Turn-around time " 
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
    break;
    /////////////////TO DO ... feedback scheduling
    case S_FB:
    break;
    ///////////////////////////////////////////////////////////////////
    ///////////Intentionally no default block - no scheduling alg == error
    default:
      cout << "ERROR - need a scheduling algorithm!" << endl << endl;
    }

  }
   
  cout << "There are " << exitProcs.size() << " finished processes" << endl;
  unsigned int avgNTaT = 0;
  for (deque<simProcess>::iterator it = exitProcs.begin(); it != exitProcs.end(); it++) {
    avgNTaT += (*it).getNormalizedTurnaround();
  }
  cout << " with an average normalized turnaround time of " 
       << static_cast<double>(avgNTaT) / exitProcs.size() << endl;

  return 0;
}
