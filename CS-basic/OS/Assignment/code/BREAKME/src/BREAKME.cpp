#include <iostream>
using namespace std;

void outputChars(const char theArray[], const int numChars) {
	for (int i = 0; i < numChars; i++) {
		cout << (theArray[i] == '\0' ? '-' : theArray[i]);
	}
}

int main() {
	int a = 7, b = 2;
	char string1[20] = "abc";
	char string2[20] = "ab";

	//NO! string1 = "jim";
//	cout << "What is YOUR name? ";
//	cin  >> string1;
//
//	cout << "Where are you from ";
//	cin  >> string2;
//
//	cout << "Welcome " << string1 << " I see you are from " << string2 << endl;
//
//	outputChars(string1, 20);
//	cout << endl;
//	outputChars(string2, 20);
//
//	cout << endl << strlen(string1) << endl;
//	cout << endl << strlen(string2) << endl;


	cout << strcmp(string1, string2) << endl;

	return 0;
}
