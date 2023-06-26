/*
 * converters.cpp
 *
 *  Created on: Dec 20, 2016
 *      Author: mrghx4
 */

float convertCtoF(float degC) {
    return degC * 9.0 / 5.0 + 32.0;
}

float convertFtoC(float degF) {
	return (degF - 32.0) * 5.0 / 9.0;
}

