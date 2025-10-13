// código para el punto 2 de la práctica 5
#include <stdio.h>
#include <limits.h>

void obtener_n_bit(void);
static void print_bits(unsigned int x);
void set_nth_bit(void);
void count_leading_zeros(void);
static void print_bits_full(unsigned int x);

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
    unsigned int x;
    unsigned int total_bits = (unsigned int)(sizeof(unsigned int) * CHAR_BIT);

    printf("Introduce un numero: ");
    if (scanf("%u", &x) != 1) {
        puts("Entrada invalida.");
        return;
    }

    unsigned int count = 0;
    if (x == 0u) {
        count = total_bits;
    } else {
        for (int i = (int)total_bits - 1; i >= 0; --i) {
            if ((x >> i) & 1u) break;
            ++count;
        }
    }

    printf("\nRepresentacion binaria (%u bits): ", total_bits);
    print_bits_full(x);  // ancho completo para visualizar los ceros iniciales
    printf("Leading zeros (ceros iniciales): %u\n", count);
    printf("------------------------------\n");
}

static void print_bits_full(unsigned int x) {
    unsigned int total_bits = (unsigned int)(sizeof(unsigned int) * CHAR_BIT);
    for (int i = (int)total_bits - 1; i >= 0; --i) {
        putchar((x & (1u << i)) ? '1' : '0');
        if (i % 8 == 0 && i != 0) putchar(' ');
    }
    putchar('\n');
}