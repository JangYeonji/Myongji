/*
Q. 다음을 2차원배열과 최고값을 선택하는 프로그래밍을 하시오. 
키보드로 학생 5명의 학번과 4과목의 점수를 입력 받아서 아래와 같이 출력되도록 프로그래밍 하시오.
*/

#include <stdio.h>

int main() {
	int aa[6][7] = { 0 };

	for (int i = 0; i<5; i++) {
		printf("학번을 입력하세요 : ");
		scanf_s("%d", &aa[i][0]);
		printf("c언어 : ");
		scanf_s("%d", &aa[i][1]);
		printf("java : ");
		scanf_s("%d", &aa[i][2]);
		printf("정보통신개론 : ");
		scanf_s("%d", &aa[i][3]);
		printf("네트워크 : ");
		scanf_s("%d", &aa[i][4]);
		printf("웹프로그램 : ");
		scanf_s("%d", &aa[i][5]);
		printf("\n");
	}
	for (int i = 0; i<5; i++) {
		for (int k = 1; k<6; k++) {
			aa[i][6] += aa[i][k];
		}
	}
	int max = aa[0][6];

	for (int i = 0; i<5; i++) {
		if (aa[i][6]>aa[max][6])
			max = i;
	}

	printf("\n최고 점수의 학생의 학번은 %d입니다.", aa[max][0]);

}