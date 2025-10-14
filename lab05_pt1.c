#include <stdio.h>

void point1() {
    printf("-----POINT 1-----\n");
    unsigned char data = 'B';
    printf("Character: %c\n", data);
    printf("Hexadecimal: %X\n", data);
    printf("Integer: %d\n", data);
}

void point2() {
    printf("-----POINT 2-----\n");
    unsigned char data = 'C';
    for (int i = 0; i < 8; i++) {
        printf("Shift %d:\n", i);
        printf("Character: %c\n", data);
        printf("Hexadecimal: %X\n", data);
        printf("Integer: %d\n", data);
        data <<= 1;
    }
}

void point3() {
    printf("-----POINT 3-----\n");
    unsigned char data = 'A';
    for (int i = 0; i < 8; i++) {
        printf("Shift %d:\n", i);
        printf("Character: %c\n", data);
        printf("Hexadecimal: %X\n", data);
        printf("Integer: %d\n", data);
        data >>= 1;
    }
}

void point4() {
    printf("------POINT 4--------\n");
    unsigned char v1 = 0b11001010;
    unsigned char v2 = 0b10101010;
    printf("hexadecimal v1 AND v2 : %X\n", v1 & v2);
    printf("decimal v1 AND v2 : %d\n\n", v1 & v2);
    printf("hexadecimal v1 OR v2: %X\n", v1 | v2);
    printf("decimal v1 OR v2: %d\n\n", v1 | v2);
    printf("hexadecimal v1 XOR v2: %X\n", v1 ^ v2);
    printf("decimal v1 XOR v2: %d\n", v1 ^ v2);
}


void print_binary(unsigned char n) {
    for (int i = 7; i >= 0; i--) {
        printf("%d", (n >> i) & 1);
    }
    printf("\n");
}

void point5() {
    printf("------POINT 5--------\n");
    unsigned char data2 = 0b10111010;
    unsigned char result = data2 & 0b10000000;
    printf("MSB:    %d\n", (result >> 7));

}

void point6() {
    printf("------POINT 6--------\n");
    unsigned char data2 = 0b10111010;
    unsigned char result = data2 & 0b00001111;

    printf("data2:   ");
    print_binary(data2);
    printf("mask:    ");
    print_binary(0b00001111);
    printf("result:  ");
    print_binary(result);

    printf("Result (hex): 0x%X | (dec): %d\n\n", result, result);
}

int main() {
    printf("\n\n");
    point1();
    point2();
    point3();
    point4();
    point5();
    point6();
    return 0;
}