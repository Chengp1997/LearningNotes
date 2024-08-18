/*
 * helpers.h
 *
 *  Created on: Dec 24, 2016
 *      Author: mrghx4
 */

#ifndef HELPERS_H_
#define HELPERS_H_

#include <iostream>
#include <string>

//using namespace std;
using std::string;

namespace temp
{
	enum menuChoice {MENU_C, MENU_F, MENU_QUIT};
}

temp::menuChoice getMenuChoice();

float getTemp(string inTemp);

void dispTemp(float degrees, string outTemp);

#endif /* HELPERS_H_ */
