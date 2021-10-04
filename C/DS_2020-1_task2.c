/*
순서도 => 
https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1fee0e71-420d-42d8-83f3-a6cd9eae3089%2F1.png?table=block&id=98246692-ea20-4b96-a49d-acb9efb7fe25&spaceId=efad4c11-8e5e-44ba-b491-514d165ffd42&width=2000&userId=&cache=v2
*/

#include <stdio.h>

int main() {
	int coin = 0; int add=0; int menu, menu_add;
	
	printf("자판기에 돈을 넣으세요. (|) ");
	scanf_s("%d", &coin);

	if (coin<200) {
		printf("돈이 부족합니다. 돈을 더 넣어주세요.");
		scanf_s("%d", &add);
		coin = coin + add;
	}

	gogo:
	printf("메뉴를 고르세요.\n\n 1. 아메리카노 200원 \n 2. 연한 아메리카노 200원 \n
		                      3. 카페라떼 200원 \n 4. 카푸치노 200원\n");
	scanf_s("%d", &menu);

	switch (menu) {
	case 1:
		printf("아메리카노 나왔습니다.");
		coin = coin - 200;
		if (coin>200) {
			printf("[1] 메뉴 더 선택 \t [2] 거스름돈 반환");
			scanf_s("%d", &menu_add);
			switch (menu_add) {
			case 1: goto gogo;
			case 2: printf("거스름돈 %d원 반환합니다.", coin);
				break;
			}
		}
		else if (coin<200) {
			printf("거스름 %d원 반환합니다.", coin);
		}
		break;
	case 2:
		printf("연한 아메리카노 나왔습니다.");
		coin = coin - 200;
		if (coin>200) {
			printf("[1] 메뉴 더 선택 \t [2] 거스름돈 반환");
			scanf_s("%d", &menu_add);
			switch (menu_add) {
			case 1: goto gogo;
			case 2: printf("거스름돈 %d원 반환합니다.", coin);
				break;
			}
		}
		else if (coin<200) {
			printf("거스름 %d원 반환합니다.", coin);
		}
		break;
	case 3:
		printf("카페라떼 나왔습니다.");
		coin = coin - 200;
		if (coin>200) {
			printf("[1] 메뉴 더 선택 \t [2] 거스름돈 반환");
			scanf_s("%d", &menu_add);
			switch (menu_add) {
			case 1: goto gogo;
			case 2: printf("거스름돈 %d원 반환합니다.", coin);
				break;
			}
		}
		else if (coin<200) {
			printf("거스름 %d원 반환합니다.", coin);
		}
		break;
	case 4:
		printf("카푸치노 나왔습니다.");
		coin = coin - 200;
		if (coin>200) {
			printf("[1] 메뉴 더 선택 \t [2] 거스름돈 반환");
			scanf_s("%d", &menu_add);
			switch (menu_add) {
			case 1: goto gogo;
			case 2: printf("거스름돈 %d원 반환합니다.", coin);
				break;
			}
		}
		else if (coin<200) {
			printf("거스름 %d원 반환합니다.", coin);
		}
		break;
	}
}