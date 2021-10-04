/*
2차원 배열을 이용하여 학생 5명의 5과목에 대한 성적을 입력받아서 합계와 평균을 구하고 
합계가 최고점수 취득한 학생의 학번을 출력하는 프로그래밍을 하시오.
*/

#include <stdio.h>

int main() {
	int data[5][8] = { 0 };
	int max = 0;

	for (int i = 0; i<5; i++) {
		printf("학번을 입력하세요 : ");
		scanf_s("%d", &data[i][0]);
		printf("5과목의 점수를 입력하세요\n");
		printf("C언어 : ");
		scanf_s("%d", &data[i][1]);
		printf("JAVA : ");
		scanf_s("%d", &data[i][2]);
		printf("정보통신개론 : ");
		scanf_s("%d", &data[i][3]);
		printf("네트워크 : ");
		scanf_s("%d", &data[i][4]);
		printf("웹프로그램 : ");
		scanf_s("%d", &data[i][5]);
		printf("\n");
	}
	for (int i = 0; i<5; i++) {
		for (int k = 1; k<6; k++) {
			data[i][6] += data[i][k];  //합계
		}
	}
	for (int i = 0; i<5; i++) {
		for (int k = 1; k<6; k++) {
			data[i][7] = data[i][6] / 5;  //평균
		}
	}

	for (int i = 0; i<5; i++) {
		if (data[i][6]>data[max][6])
			max = i;  //합계로 최고점 비교
	}

	printf("\n\t\t인터넷응용보안공학과 1반 성적\t\n");
	printf("*************************************************************\n");
	printf("학번\t C언어\t java\t INFO\t NET\t WEB\t 합계\t 평균\n");
	for (int i = 0;i<5;i++) {
		for (int k = 0;k<8;k++) {
			printf("%d\t ", data[i][k]);
		}
		printf("\n");
	}
	printf("\n최고 점수의 학생의 학번은 %d입니다.", data[max][0]);

}