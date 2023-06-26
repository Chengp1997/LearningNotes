#ifndef SIMPROCESS_H
#define SIMPROCESS_H

class simProcess {
  private:
    unsigned int arr; //arrival
    unsigned int dur; //duration/service time
    unsigned int rem; //remaining time -- initially duration, finished when 0
    unsigned int fin; //finish time

  public:
  simProcess():arr(0), dur(0), rem(0), fin(0){ }

  //construcor - process is ready at arrival and requires duration units to complete
  simProcess(unsigned int arrival, unsigned int duration) {
    arr = arrival;
    dur = duration;
    rem = duration;
  }

  inline bool process() {rem--; return finished();}

  inline bool finished() {return (rem <= 0);} //finished if no remaining processing required
  //^^  safety check <= 0 ... technically should never continue processing if 0
  
  inline unsigned int getRemaining() const {return rem;}
  inline unsigned int getArrival() const {return arr;}
  inline unsigned int getDuration() const {return dur;}
  inline unsigned int getFinish() const {return fin;}
  inline unsigned int getTurnaround() const {return (fin - arr);}
  inline double getNormalizedTurnaround() const 
		{return (static_cast<double>(fin - arr + 1.0)/dur);}

  inline void setFinished(unsigned int finished) {fin = finished;}

  friend bool operator < (const simProcess & LHS, const simProcess & RHS);
};

#endif
