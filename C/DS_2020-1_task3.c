/*
Q. 다음을 2차원 배열을 이용하여 프로그래밍하시오. 
단) 키보드로 학생의 학번과 c언어, 정보통신개론, 자바 3과목에 대한 점수의 합계와 평균을 구하시오.
*/

#include <stdio.h>

int main() {
	int aa[3][6] = { 0 };

	for (int i = 0; i<3; i++) {
		printf("학번을 입력하세요 : ");
		scanf_s("%d", &aa[i][0]);
		printf("\n3과목의 점수를 입력하세요. \n");
		printf("C언어 : ");
		scanf_s("%d", &aa[i][1]);
		printf("정보통신개론 : ");
		scanf_s("%d", &aa[i][2]);
		printf("자바 : ");
		scanf_s("%d", &aa[i][3]);
		printf("\n");
	}
	for (int i = 0; i<3; i++) {
		for (int k = 1; k<4; k++) {
			aa[i][4] += aa[i][k];
		}
	}
	for (int i = 0; i<3; i++) {
		aa[i][5] = aa[i][4] / (float)3;
	}
	printf("\n인터넷응용보안공학과 1반 성적\n\n\n");
	printf("학번\tInfo\tC_lang\tJava\t합계\t평균\n");

	for (int i = 0; i<3; i++) {
		for (int k = 0; k<6; k++) {
			printf("%d\t", aa[i][k]);
		}
		printf("\n");
	}

}