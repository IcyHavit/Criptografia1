#include <iostream>
#include <tuple>
using namespace std;

// Función para calcular el Algoritmo Extendido de Euclides
tuple<int, int, int> extendedEuclideanAlgorithm(int r0, int r1) {
    int s0 = 1, t0 = 0;
    int s1 = 0, t1 = 1;
    int i = 1;

    while (r1 != 0) {
        i = i + 1;
        int r = r0 % r1;
        int q = (r0 - r) / r1;
        int s = s0 - q * s1;
        int t = t0 - q * t1;

        r0 = r1;
        r1 = r;
        s0 = s1;
        s1 = s;
        t0 = t1;
        t1 = t;
    }

    return make_tuple(r0, s0, t0);
}

int main() {
    char continuar;

    do {
        int r0, r1;

        cout << "Ingrese r0 y r1 (con r0 > r1): ";
        cin >> r0 >> r1;

        if (r0 <= 0 || r1 <= 0) {
            cerr << "Error: Ambos números deben ser positivos." << endl;
            continue;
        }

        if (r0 <= r1) {
            cerr << "Error: r0 debe ser mayor que r1." << endl;
            continue;
        }

        // Cálculo del Algoritmo Extendido de Euclides
        tuple<int, int, int> result = extendedEuclideanAlgorithm(r0, r1);

        int gcd = get<0>(result);
        int s = get<1>(result);
        int t = get<2>(result);

        // Mostrar resultados
        cout << "gcd = " << gcd << endl;
        cout << "s = " << s << endl;
        cout << "t = " << t << endl;

        cout << "¿Desea realizar otro calculo? (s/n): ";
        cin >> continuar;

    } while (continuar == 's' || continuar == 'S');

    return 0;
}
