/*
 * helpers.cpp
 *
 *  Created on: Dec 24, 2016
 *      Author: mrghx4
 */

#include "helpers.h"

using std::cin;
using std::cout;
using std::endl;

//using namespace temp;
using temp::menuChoice;
using temp::MENU_C;
using temp::MENU_F;
using temp::MENU_QUIT;

menuChoice getMenuChoice()
{
	char selection = '?';
	menuChoice toReturn;
	bool validSelection = false;

	do {
		std::cout << "1 (C): Convert from Celceius" << endl
			 << "2 (F): Convert from Fahrenheit" << endl
			 << "3 (Q): Quit" << endl << std::endl;
		std::cout << "Please make a selection: ";
		std::cin  >> selection;

		switch (selection) {
		case 'c':
		case 'C':
		case '1':
			toReturn = MENU_C;
			validSelection = true;
			break;
		case 'f':
		case 'F':
		case '2':
			toReturn = MENU_F;
			validSelection = true;
			break;
		case 'q':
		case 'Q':
		case '3':
			toReturn = MENU_QUIT;
			validSelection = true;
			break;
		default:
			cout << selection << " is not a valid selection! Please try again." << endl << endl;
		}
	} while (!validSelection);

	return toReturn;
}

float getTemp(string inTemp)
{
	float toReturn;

	do {
		cout << "Please enter the temperature in degrees " << inTemp << ": ";
		cin  >> toReturn;
	}while (toReturn < -200 || toReturn > 500);

	return toReturn;
}

void dispTemp(float degrees, string outTemp)
{
	cout << "The temperature is " << degrees << " " << outTemp << endl;
}
