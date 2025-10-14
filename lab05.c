// código para el punto 2 de la práctica 5
#include <stdio.h>
#include <limits.h>
#include <string.h>

void obtener_n_bit(void);
static void print_bits(unsigned int x);
void set_nth_bit(void);
void count_leading_zeros(void);

int main(void) {
    int opcion;
    do {
    printf("LAB 05\n");
    printf("1. Obtener el bit n de un numero\n");
    printf("2. Prender el bit n de un numero\n");
    printf("3. Contar ceros a la izquierda\n");
    printf("4. Salir\n");
    printf("Elige una opcion: ");
    scanf("%d", &opcion);

    switch (opcion) {
        case 1:
            obtener_n_bit();
            break;
        case 2:
            set_nth_bit();
            break;
        case 3:
            count_leading_zeros();
            break;
        case 4:
            printf("Saliendo del programa.\n");
            break;
        default:
            printf("Opcion invalida.\n");
    }
    } while (opcion != 4);

    return 0;
}


void obtener_n_bit(void) {
    unsigned int x, n;
    unsigned int total_bits = sizeof(unsigned int) * CHAR_BIT;

    printf("Introduce un numero entero: ");
    if (scanf("%u", &x) != 1) {
        printf("Entrada invalida.\n");
        return;
    }

    printf("Introduce la posicion del bit: ");
    if (scanf("%u", &n) != 1) {
        printf("Entrada invalida.\n");
        return;
    }

    if (n >= total_bits) {
        printf("Error: el bit %u esta fuera del rango (0 a %u)\n", n, total_bits - 1);
        return;
    }

    unsigned int bit_shift = (x >> n) & 1;


    printf("\nNumero original: %u\n", x);
    print_bits(x);
    printf("Bit solicitado: %u\n", n);
    printf("Resultado: %u\n", bit_shift);
    printf("------------------------------\n");
}

static void print_bits(unsigned int x) {
    int started = 0;
    for (int i = sizeof(unsigned int) * CHAR_BIT - 1; i >= 0; --i) {
        int bit = (x >> i) & 1;
        if (bit) started = 1;
        if (started) putchar(bit ? '1' : '0');
    }
    if (!started) putchar('0'); 
    putchar('\n');
}

void set_nth_bit(void) {
    unsigned int x, n;
    unsigned int total_bits = (unsigned int)(sizeof(unsigned int) * CHAR_BIT);

    printf("Introduce un numero: ");
    if (scanf("%u", &x) != 1) { puts("Entrada invalida."); return; }

    printf("Introduce la posicion del bit n: ");
    if (scanf("%u", &n) != 1) { puts("Entrada invalida."); return; }

    if (n >= total_bits) {
        printf("Error: n fuera de rango (0..%u)\n", total_bits - 1);
        return;
    }

    unsigned int mascara = (1u << n);
    unsigned int y = x | mascara;


    printf("\nBits de x antes : ");
    print_bits(x);
    printf("x | mascara     : ");
    print_bits(y);

    printf("\nx = %u, n = %u\n", x, n);
    printf("Resultado (x | (1u<<n)) = %u\n", y);
}

void count_leading_zeros(void) {
    
    unsigned int num = 0;
    unsigned int count = 0;
    int bits = 0;
    int ch;

    printf("Introduce un numero binario (solo 0s y 1s): ");


    do {
        ch = getchar();
        if (ch == EOF) { puts("Entrada invalida."); return; }
    } while (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r');


    for ( ; ch == '0' || ch == '1'; ch = getchar()) {
        num <<= 1;             
        if (ch == '1') num |= 1u; 
        ++bits;
    }

    if (bits == 0) {  
        puts("No se ingreso ningun bit valido.");
       
        while (ch != '\n' && ch != EOF) ch = getchar();
        return;
    }

 
    if (ch != '\n' && ch != EOF) {
        puts("Error: el numero debe contener solo 0 y 1.");
        while (ch != '\n' && ch != EOF) ch = getchar();
        return;
    }


    for (int i = bits - 1; i >= 0; --i) {
        if ((num >> i) & 1u) break;  
        ++count;
    }

 
    printf("\nNumero binario interpretado: ");
    for (int i = bits - 1; i >= 0; --i)
        putchar(((num >> i) & 1u) ? '1' : '0');
    putchar('\n');

    printf("Longitud: %d bits\n", bits);
    printf("Leading zeros: %u\n", count);
    printf("------------------------------\n");
}