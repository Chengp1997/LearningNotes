//============================================================================
// Name        : tempConvert.cpp
// Author      :
// Version     :
// Copyright   : Your copyright notice
// Description : Converting between C and F
//============================================================================

#include <iostream>
#include "converters.h"
#include "helpers.h"

using namespace std;

using temp::menuChoice;
using temp::MENU_C;
using temp::MENU_F;
using temp::MENU_QUIT;

int main() {
	float fahr = 0, celc = 0;
	float temperature = -40;
	string inTemp, outTemp;

	cout << "Greetings and welcome to the temperature convert-o-matic!" << endl;
	menuChoice response;

	do {
		response = getMenuChoice();

		switch (response) {
		case MENU_C:
			inTemp = "Celceius";
			outTemp = "Fahrenheit";
			temperature = getTemp(inTemp);
			dispTemp(convertCtoF(temperature), outTemp);
			break;
		case MENU_F:
			inTemp = "Fahrenheit";
			outTemp = "Celceius";
			temperature = getTemp(inTemp);
			dispTemp(convertFtoC(temperature), outTemp);
			break;
		case MENU_QUIT:
			break;
		default:
			cerr << "Should never reach this point! Error in menuChoice!" << endl;
		}
	} while (response != MENU_QUIT);

	return 0;
}
