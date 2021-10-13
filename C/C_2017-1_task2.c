#include <stdio.h>

int main() {
	int aa[100], bb[100];
	int i;

	for (i = 0;i < 100;i++) {
		aa[i] = i * 3;
	}

	for (i = 0;i < 50;i++) {
		bb[i] = aa[i + 50];

		printf("bb[%d]=aa[%d]\t\t", i, aa[i + 50]);
	}

	for (i = 50;i < 100;i++) {
		bb[i] = aa[i - 50];

		printf("bb[%d]=aa[%d]\t\t", i, aa[i - 50]);
	}
}
