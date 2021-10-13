#include <stdio.h>

int main()
{
	int money, cho, add;

	printf("ㅣ-------------------------------------------------------ㅣ\n");
	printf("ㅣㅣ---------ㅣ ㅣ---------ㅣ ㅣ---------ㅣ ㅣ---------ㅣㅣ\n");
	printf("ㅣㅣ    1    ㅣ ㅣ    2    ㅣ ㅣ    3    ㅣ ㅣ    4    ㅣㅣ\n");
	printf("ㅣㅣ블랙 커피ㅣ ㅣ설탕 커피ㅣ ㅣ밀크 커피ㅣ ㅣ크림 커피ㅣㅣ\n");
	printf("ㅣㅣ   200   ㅣ ㅣ   200   ㅣ ㅣ   200   ㅣ ㅣ   200   ㅣㅣ\n");
	printf("ㅣㅣ---------ㅣ ㅣ---------ㅣ ㅣ---------ㅣ ㅣ---------ㅣㅣ\n");
	printf("ㅣ                                                       ㅣ\n");
	printf("ㅣ                  (ㅣ)      ㅣ---------ㅣ              ㅣ\n");
	printf("ㅣ                  (ㅣ)      ㅣ    5    ㅣ              ㅣ\n");
	printf("ㅣ                  (ㅣ)      ㅣ  반 환  ㅣ              ㅣ\n");
	printf("ㅣ                  (ㅣ)      ㅣ---------ㅣ              ㅣ\n");
	printf("ㅣ-------------------------------------------------------ㅣ\n");

	printf("돈을 넣어주세요 ==> ");
	scanf_s("%d", &money);

	while (1) {
		if (money < 200) {
			printf("돈이 부족합니다. 돈을 더 넣어주세요.==>");
			scanf_s("%d", &add);
			money = money + add;
		}
		if (money >= 200) {
			printf("%d 원이 있습니다. 무엇을 드시겠습니까? ==> ", money);
			scanf_s("%d", &cho);

			if (cho == 1) {
				money -= 200;
				printf("\n블랙 커피 나왔습니다. 거스름돈 %d \n", money);
				break;
			}else if (cho == 2) {
				money -= 200;
				printf("\n설탕 커피 나왔습니다. 거스름돈 %d \n", money);
				break;
			}else if (cho == 3) {
				money -= 200;
				printf("\n밀크 커피 나왔습니다. 거스름돈 %d \n", money);
				break;
			}else if (cho == 4) {
				money -= 200;
				printf("\n크림 커피 나왔습니다. 거스름돈 %d \n", money);
				break;
			}else if (cho == 5) {
				printf("\n 반환하겠습니다. 거스름돈 %d \n", money);
				break;
			}else {printf("\n 메뉴에 없습니다. 다시 선택해 주십시오.\n");}
		}
	}
}
