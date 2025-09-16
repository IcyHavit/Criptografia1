package practica2;

import java.math.BigInteger;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Practica2 {

    public static void main(String[] args) {
        char[] asciiPrintable = new char[95];
        List<Integer> coprimos = new ArrayList<>();
        SimpleEntry<Integer, Integer> key = randomKey(15);
        System.out.println(key.getKey()+ " " + key.getValue());

        for (int i = 0; i < 95; i++) {
            asciiPrintable[i] = (char) (i + 32);
        }

        coprimos = createList(15);
        int test = test(15, 7);
        System.out.println(test);
    }

    // Z*_n: { x in 1..n-1 | gcd(x,n)=1 }
    public static List<Integer> createList(int n) {
        if (n < 2) throw new IllegalArgumentException("n debe ser >= 2");

        List<Integer> coprimos = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (BigInteger.valueOf(n).gcd(BigInteger.valueOf(i)).intValue() == 1) {
                coprimos.add(i);
            }
        }
        return coprimos;
    }

    // Devuelve b tal que a*b ≡ 1 (mod n), buscando en Z*_n
    public static int test(int n, int a){
        if (n < 2) throw new IllegalArgumentException("n debe ser >= 2");
        if (a <= 0 || a >= n) throw new IllegalArgumentException("a debe estar en [1, n-1]");
        if (BigInteger.valueOf(a).gcd(BigInteger.valueOf(n)).intValue() != 1)
            throw new IllegalArgumentException("a y n deben ser coprimos (a ∈ Z*_n)");

        List<Integer> coprimos = createList(n);
        for (int b : coprimos) {
            if ((a * b) % n == 1) return b;
        }
        return 0; // no encontrado (no debería ocurrir si a ∈ Z*_n)
    }

    // Llave afín válida: a ∈ Z*_n y b ∈ Z_n = {0..n-1}
    public static SimpleEntry<Integer, Integer> randomKey(int n){
        if (n < 2) throw new IllegalArgumentException("n debe ser >= 2");

        List<Integer> coprimos = createList(n);
        if (coprimos.isEmpty())
            throw new IllegalStateException("Z*_n está vacío para n=" + n);

        Random rand = new Random();
        int valor1 = coprimos.get(rand.nextInt(coprimos.size())); // a
        int valor2 = rand.nextInt(n);                             // b en [0, n-1]

        return new SimpleEntry<>(valor1, valor2);
    }
}
