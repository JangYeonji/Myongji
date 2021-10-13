#include <stdio.h>
int main()
{
	int aa[] = { 10,20,30,40,50 };
	int count;
	int hap = 0;
	int i;

	count = sizeof(aa) / sizeof(int);

	for (i = 0; i < count; i++) {

		hap = hap + aa[i];

	}
	printf("aa[]의 전체 값의 합: %d \n", hap);

}
