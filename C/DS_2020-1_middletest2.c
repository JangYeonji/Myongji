/*
1차원 배열을 이용하여 키보드를 통해서 임의의 숫자값을 10개 입력하고, 
이들 중에서 찾고자 하는 해당값(KEY)값을 찾고(Search) 
해당값이 저장된 배열의 인덱스(Index)값을 출력 하는 프로그래밍을 하시오.
*/

#include <stdio.h>

int main(void) {
	int data[10] = { 0 };
	int size, key;

	printf("크기가 10인 배열에 임의값을 입력하세요 : ");

	for (int i = 0;i<10;i++) {
		scanf_s("%d", &data[i]);
	}

	size = sizeof(data) / sizeof(data[0]);

	printf("arr = ");
	for (int i = 0;i<size;i++) {
		printf("%d ", data[i]);
	}
	printf("\n");

	printf("찾을 숫자값(kEY) :");
	scanf_s("%d", &key);

	for (int i = 0;i<size;i++) {
		if (data[i] == key)
			printf("찾는 원소의 인덱스 : %d \n", i);
	}
}