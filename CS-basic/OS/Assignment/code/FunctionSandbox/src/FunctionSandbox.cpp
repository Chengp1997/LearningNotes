//============================================================================
// Name        : FunctionSandbox.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Testing functions & characteristics
//============================================================================

#include <iostream>
using namespace std;

void getPoint(int & x, int & y, int & z);
void printPoint(const int x, const int y, const int z);

int main() {
	int a, b, c;

	cout << "Getting the point!" << endl;

	getPoint(a, b, c);

	cout << "Got point ";
	printPoint(a,b,c);
	cout << endl;

	return 0;
}


void getPoint(int & x, int & y, int & z)
{
	cout << "input a value for x: ";
	cin  >> x;

	cout << endl << "input a value for y: ";
	cin  >> y;

	cout << endl << "input a value for z: ";
	cin  >> z;

	return;
}

void printPoint(const int x, const int y, const int z)
{
	cout << "(" << x << "," << y << "," << z << ")";

	return;
}

