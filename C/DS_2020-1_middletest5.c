/* 
아래와 같이 은행의 창구에서 고객들이 번호표를 뽑고 해당 창구에 할당을 받아서 
은 행 업무를 보는 과정을 순서도로 작성하고 이를 프로그래밍으로 구현하시오. 
※ 은행에서 제공하는 업무 프로세스의 범위는 프로그램 개발자가 설정할 것.
*/

/*
순서도==>
https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7270d5e1-d86e-4c6e-beb7-55468a5c69a5/5.bmp?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20211004%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211004T054657Z&X-Amz-Expires=86400&X-Amz-Signature=14201670ff550dc2dee350d39ea3133108294991fc9feacfe05a7471bbea5565&X-Amz-SignedHeaders=host
*/

#include <stdio.h>

int main() {
	char stack[5];
	int top = 0;

	int stack2[5];
	int top2 = 0;

	int task = 0;
	char cus = 'A';
	int counter = 1;
	int select = 9;

five:
	while (select != 3) {
		printf("번호를 선택해주세요. [1] 업무 보기(번호표 뽑기) [2] 업무 마치기 [3] 은행 문 닫기 : ");
		scanf_s("%d", &select);

		switch (select) {
		case 1:
			if (top >= 5) {
				printf("모든 창구에서 업무를 보고 있습니다. 잠시만 기다려주세요.\n");
			}
			else {
				stack[top] = cus++;
				stack2[top2] = counter++;
				printf(" %c 손님이 %d번창구에 들어갑니다. \n", stack[top], stack2[top2]);
				top++;
				top2++;
				while (select != 4 || select != 5) {
					printf("무슨 업무를 보시겠습니까? [1]통장 만들기 [2]카드 만들기 [3]동전 바꾸기 [4]업무 마치기 [5]다음 손님 부르기 :");
					scanf_s("%d", &task);
					if (task == 1) printf("통장을 만들어드리겠습니다.\n");
					else if (task == 2) printf("카드를 만들어드리겠습니다.\n");
					else if (task == 3) printf("동전 바꿔드리겠습니다.\n");
					else if (task == 4) goto gogo;
					else if (task == 5) goto five;
				}


			}break;
		case 2:
			if (top <= 0) {
				printf("손님이 없습니다.\n");
			}
			else {
			gogo:
				top--;
				top2--;
				printf("%c 손님이 %d창구에서 빠짐\n", stack[top], stack2[top2]);
				stack[top] = cus--;
				stack2[top2] = counter--;
			}break;
		case 3:
			printf("현재 창구에 %d명의 손님이 있음.", top);
			printf("은행 업무시간이 끝났습니다..");
			break;
		default:
			printf("잘못 입력했습니다. 다시 입력하세요. \n");
			break;
		}
	}