/*
1차원 배열을 이용하여 키보드를 통해서 임의의 숫자값을 10개 입력하고, 
이들 중에서 최대값 찾고자 하는 해당값(KEY)값을 찾고(Search) 
해당값이 저장된 배열의 인덱스 (Index)값을 출력하는 프로그래밍을 하시오.
*/

#include <stdio.h>

int main() {
	int data[10] = { 0 };
	int size, max, min;

	printf("크기가 10인 배열에 임의값을 입력하세요 : ");

	for (int i = 0;i<10;i++) {
		scanf_s("%d", &data[i]);
	}

	size = sizeof(data) / sizeof(data[0]);

	printf("배열값 = ");
	for (int i = 0;i<size;i++) {
		printf("%d ", data[i]);
	}
	printf("\n");

	max = 0;
	for (int i = 0;i<size;i++) {
		if (data[i] > data[max])
			max = i;
	}

	min = 0;
	for (int k = 0;k<size;k++) {
		if (data[k] < data[min])
			min = k;
	}

	printf("최대값: %d\n", data[max]);
	printf("최소값: %d\n", data[min]);
}