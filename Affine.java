import java.math.BigInteger;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Affine {

    public static void main(String[] args) {
        // pruebas de cifrado afín
        String textoClaro = "Hola, mundo!";
        SimpleEntry<Integer, Integer> key = randomKey(95); // n = 95 para ASCII imprimible
        System.out.println("Llave generada: a=" + key.getKey() + ", b=" + key.getValue());
        String cifrado = affineEncipher(textoClaro, key);
        System.out.println("Texto cifrado: " + cifrado);

        // pruebas de descifrado
        String descifrado = affineDecipher(cifrado, key);
        System.out.println("Texto descifrado: " + descifrado);
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

    // Función para cifrar el texto claro usando affine cipher
    
    public static String affineEncipher(String plaintext, SimpleEntry<Integer, Integer> key) {
        int n = 95;
        int a = key.getKey();
        int b = key.getValue();
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (c < 32 || c > 126) throw new IllegalArgumentException("Carácter fuera del alfabeto imprimible ASCII: " + c);
            int mi = c - 32;
            int y = (a * mi + b) % n;
            ciphertext.append((char)(y + 32));
        }
        return ciphertext.toString();
    }

    // Función para descifrar el texto cifrado usando affine cipher

    public static String affineDecipher(String ciphertext, SimpleEntry<Integer, Integer> key) {
        int n = 95;
        int a = key.getKey();
        int b = key.getValue();
        StringBuilder plaintext = new StringBuilder();
        int a_inv = test(n, a);
        for (char c : ciphertext.toCharArray()) {
            if (c < 32 || c > 126) throw new IllegalArgumentException("Carácter fuera del alfabeto imprimible ASCII: " + c);
            int mi = c - 32;
            int y = (a_inv * (mi - b + n)) % n;
            plaintext.append((char)(y + 32));
        }
        return plaintext.toString();
    }
}
