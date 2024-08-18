/*
 * converters.h
 *
 *  Created on: Dec 20, 2016
 *      Author: mrghx4
 */

#ifndef CONVERTERS_H_
#define CONVERTERS_H_

//pre: This function should take a value of C between valid temperature values
//post: Returns the corresponding temperature in degrees F
// desc: converts deg C to deg F
float convertCtoF(float degC);

//pre: This function should take a value of F between valid temperature values
//post: Returns the corresponding temperature in degrees C
// desc: converts deg F to deg C
float convertFtoC(float degF);

#endif /* CONVERTERS_H_ */
