#include <stdio.h>

int main() {
	int aa[10];
	int hap = 0;
	int i;

	i = 0;
	while (i <= 9) {
		printf("%d번째 숫자를 입력하세요 : ", i + 1);
		scanf_s("%d", &aa[i]);

		hap = hap + aa[i];
		i++;
	}
	printf(" 합계 ==> %d \n", hap);
}
