/*
학과가 다른 두 학생의 이름, 학과, 학번을 입력받아서
3차원 문자 배열을 이용하여 저 장하고 이를 아래와 같이 출력하는 프로그래밍을 하시오. 
*/

#include<stdio.h>

int main() {
	char data[2][3][30] = { 0 };

	for (int i = 0; i<2; i++) {
		printf("\n학생 %d의 이름 :", i + 1);
		gets(data[i][0]);
		printf("\n학생 %d의 학과 :", i + 1);
		gets(data[i][1]);
		printf("\n학생 %d의 학번 :", i + 1);
		gets(data[i][2]);
	}
	for (int i = 0; i<2; i++) {
		printf("\n\n학생 %d", i + 1);
		for (int k = 0; k<3; k++) {
			printf("\n\t");
			for (int j = 0; j<30; j++) {
				putchar(data[i][k][j]);
			}
		}
	}
}