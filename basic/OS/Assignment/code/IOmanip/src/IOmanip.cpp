//============================================================================
// Name        : IOmanip.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <iomanip>

using namespace std;

int main() {
	float grade = 86.1263f;
	cout.precision(14);
	cout << grade << endl;
	cout.precision(4);
	cout << grade << endl;

	float grade1 = 86.1243f;
	float grade2 = 93.1311f;
	cout.width(10);
	cout << grade1 << endl;
	cout << grade2 << endl;


	double mole = 602200000000000000000000.0;
	float grade3 = 97.153f;
	cout.setf(ios::scientific);
	cout << mole << endl;
	cout.unsetf(ios::scientific);
	cout << grade3 << endl;


	float money = 1441.3531f;
	cout.setf(ios::fixed);
	cout.setf(ios::showpoint);
	cout.precision(2);
	cout << money << endl;

return 0;
}
