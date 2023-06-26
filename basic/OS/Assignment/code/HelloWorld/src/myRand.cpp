/*
 * myRand.cpp
 *
 *  Created on: Dec 22, 2016
 *      Author: mrghx4
 */

#include <ctime>                               // gives access to the time clock
#include <cmath>
#include <iostream>

#include "myRand.h"

float myVol(float radius, float height)
{
	return MY_PI * radius * radius * height;
}


long myRand()
{
    static long seed = time(NULL);             // assigns no of secs since jan 1, 1971
//    long seed = time(NULL);             // assigns no of secs since jan 1, 1971
    seed = (104729 + 7919 * seed) % 15485863;  // linear non-congruential rand no gen
    return seed;
}

void functionCounter()
{
    static short count = 1;
    std::cout << "This function has been called "
    		  << count << (count++ == 1 ? " time" : " times") << std::endl;
    //count++;
}
