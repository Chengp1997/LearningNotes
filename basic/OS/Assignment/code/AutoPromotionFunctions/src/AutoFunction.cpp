//============================================================================
// Name        : AutoFunction.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Showing automatic promotion with function calls
//============================================================================

#include <iostream>

using namespace std;

double averageNums(double num1, double num2);
float averageNums(int num1, int num2);
float averageNums(int num1, int num2,  int num3);

//float averageNums(char a, char b);
//char averageNums(char a, char b);

int main() {
	float a = 16.6, b = 32.4;
	cout << "Hola World!" << endl;

	cout << "Averaging " << a << " and " << b << " equals " << averageNums(a,b, 8) << endl;

	int c = 16, d = 32;
	cout << "Averaging INTs " << c << " and " << d << " equals " << averageNums(c,d) << endl;

	double e = 16.6, f = 32.4;
	cout << "Averaging DOUBLEs " << e << " and " << f << " equals " << averageNums(e,f) << endl;

	char g = 16, h = 32;
	cout << "Averaging CHARs " << g << " and " << h << " equals " << averageNums(g,h) << endl;

	return 0;
}

double averageNums(double num1, double num2)
{
	return (num1 + num2) / 2.0;
}

float averageNums(int num1, int num2)
{
	return (num1 + num2) / 2.0;
}

//functions that only differ in their return type cannot be overloaded
//float averageNums(char num1, char num2)
//{
//	return (num1 + num2) / 2.0;
//}

float averageNums(int num1, int num2, int num3)
{
	return (num1 + num2 + num3) / 3.0;
}

