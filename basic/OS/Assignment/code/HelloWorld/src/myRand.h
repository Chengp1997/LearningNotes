/*
 * myRand.h
 *
 *  Created on: Dec 22, 2016
 *      Author: mrghx4
 */

#ifndef MYRAND_H_
#define MYRAND_H_

using namespace std;

const float MY_PI = 3.1415926536;

template <typename T, typename U>
void repeater(const T first, const U second, const int numTimes = 1) {
	for (int i = 0; i < numTimes; i++) {
		cout << first << " " << second << endl;
	}
}

template <typename T>
void mySwap(T & a, T & b) {
	T temp = a;
	a = b;
	b = temp;
	return;
}

//pre:  none
//post: returns the volume of the cylinder
//desc: returns the volume of the cylinder or area of the circle
float myVol(float radius = 1, float height = 1);

//pre:  none
//post: new pseudo-random number is returned, based upon the previous number
//desc: pseudo-random number generator function based on prime numbers' properties
// initial seed is derived from current time with each new call fed from the previous seed
long myRand();

//pre:  none
//post: number of times function has been called is displayed to cout
void functionCounter();

#endif /* MYRAND_H_ */
