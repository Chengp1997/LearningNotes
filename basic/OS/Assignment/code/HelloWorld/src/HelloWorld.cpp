//============================================================================
// Name        : HelloWorld.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include "myRand.h"

using namespace std;

int main() {
	cout << "!!!Hello C++ World!!!" << endl; // prints Hello World!

	cout << "Getting a few random numbers ... " << endl << myRand() << endl << myRand() << endl << myRand() << endl;
//
//	cout << "Calling the function counter a few times ... " << endl << endl;
//
//	for (int i = 0; i < 5; i++)
//	{
//		functionCounter();
//	}
//
//	cout << "Volume of sample cyl " << myVol(2, 2) << endl;
//	cout << "Area of sample circle " << myVol(2) << endl;
//	cout << "Area of sample circle " << myVol() << endl;

//	int a = 1, b = 2, c = 3;
//	float f = 4.3;
//
//	cout << a << "  " << b << endl;
//	mySwap(a,b);
//	cout << a << "  " << b << endl;
//
//	repeater(a, f, c);
//	cout << "NOW NOW NOW " << endl;
//	repeater(a, f);

	return 0;
}
