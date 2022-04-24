#include <stdio.h> 
#include <stdlib.h>

int main ()
{
	int randChar, iterations;
	iterations = 0;

	do {
		int i = 0;
		while (i <4) {
			randChar = rand();
			randChar = (char)randChar + (char)(randChar / 0x1a) * -0x1a + 'a';
			printf("%c", randChar);
			i++;
		}
		printf("\n");
		iterations = iterations + 1;
	} while (iterations < 100);

	return 0;
}
