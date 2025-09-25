package practica2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Practica2 {

    /**
     * Método principal
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU---");
            System.out.println("1. Listar coprimos con 'n'");
            System.out.println("2. Encontrar inversa modular");
            System.out.println("3. Generar una llave aleatoria");
            System.out.println("4. Listar TODAS las llaves validas para un 'n' en un archivo");
            System.out.println("5. Cifrar mensaje con LLAVE MANUAL");
            System.out.println("6. Descifrar mensaje");
            System.out.println("7. Salir");
            System.out.print("Elige una opcion: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    // --- Opción 1: Probar createList ---
                    case 1: {
                        System.out.print("Introduce el tamaño del alfabeto (n): ");
                        try {
                            int n = scanner.nextInt(); scanner.nextLine();
                            List<Integer> coprimos = createList(n);
                            System.out.println("Los numeros coprimos con " + n + " (elementos de Z*_" + n + ") son:");
                            System.out.println(coprimos);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }

                    // --- Opción 2: Probar test (inversa) ---
                    case 2: {
                        try {
                            System.out.print("Introduce el modulo (n): ");
                            int n = scanner.nextInt();
                            System.out.print("Introduce el numero para hallar su inversa (a): ");
                            int a = scanner.nextInt(); scanner.nextLine();
                            int[] res = eea(n, a);
                            int inversa = res[2];                            
                            System.out.println("La inversa de " + a + " modulo " + n + " es: " + inversa);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }

                    // --- Opción 3: Probar randomKey ---
                    case 3: {
                        System.out.print("Introduce el tamano del alfabeto (n) para generar la llave: ");
                        try {
                            int n = scanner.nextInt(); scanner.nextLine();
                            SimpleEntry<Integer, Integer> key = randomKey(n);
                            System.out.println("Llave aleatoria generada (a, b): (" + key.getKey() + ", " + key.getValue() + ")");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }

                    // --- Opción 4: Listar todas las llaves en archivo ---
                    case 4: {
                        System.out.print("Introduce el tamano del alfabeto (n) para listar todas sus llaves: ");
                        try {
                            int n = scanner.nextInt(); scanner.nextLine();
                            String nombreArchivo = "llaves_afin_n" + n + ".txt";
                            listAllValidKeysToFile(n, nombreArchivo);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }

                    // --- Opción 6: Cifrar con llave manual ---
                    case 5: {
                        try {
                            System.out.print("Introduce el valor de 'a' de la llave: ");
                            int a = scanner.nextInt();
                            if (BigInteger.valueOf(a).gcd(BigInteger.valueOf(95)).intValue() != 1) {
                                System.out.println("Error: La llave es inválida. 'a' (" + a + ") debe ser coprimo con 95.");
                                scanner.nextLine();
                                break;
                            }
                            System.out.print("Introduce el valor de 'b' de la llave: ");
                            int b = scanner.nextInt(); scanner.nextLine();
                            
                            SimpleEntry<Integer, Integer> key = new SimpleEntry<>(a, b);
                            System.out.print("Introduce el mensaje a cifrar: ");
                            String textoClaro = scanner.nextLine();
                            
                            String cifrado = affineEncipher(textoClaro, key);
                            System.out.println("\n--- RESULTADO ---");
                            System.out.println("Texto cifrado (C): " + cifrado);
                        } catch (Exception e) {
                            System.out.println("Error durante el cifrado: " + e.getMessage());
                        }
                        break;
                    }

                    case 6: {
                        try {
                            System.out.print("Introduce el valor de 'a' de la llave K: ");
                            int a = scanner.nextInt();
                            System.out.print("Introduce el valor de 'b' de la llave K: ");
                            int b = scanner.nextInt(); scanner.nextLine();
                            SimpleEntry<Integer, Integer> key = new SimpleEntry<>(a, b);
                            System.out.print("Introduce el texto cifrado (C): ");
                            String cifrado = scanner.nextLine();
                            String descifrado = affineDecipher(cifrado, key);
                            System.out.println("\n--- RESULTADO ---");
                            System.out.println("Texto original recuperado (M): " + descifrado);
                        } catch (Exception e) {
                            System.out.println("Error durante el descifrado: " + e.getMessage());
                        }
                        break;
                    }

                    case 7:
                        salir = true;
                        System.out.println("¡Hasta luego!");
                        break;

                    default:
                        System.out.println("Opcion no valida. Por favor, elige un numero del 1 al 8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un numero. Intentalo de nuevo.");
                scanner.nextLine(); // Limpiar el buffer en caso de error
            }
        }
        scanner.close();
    }

    // --- FUNCIONES LÓGICAS ---

    public static void listAllValidKeysToFile(int n, String filename) throws IOException {
        System.out.println("Generando llaves para n = " + n + " y guardando en '" + filename + "'...");
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Llaves validas para el Cifrado Afin con n = " + n);
            writer.println("Formato: Llave K=(a, b), Inversa de a (a⁻¹ mod n)");
            writer.println("-----------------------------------------------------");
            List<Integer> valid_a = createList(n);
            if (valid_a.isEmpty()) {
                writer.println("No existen valores validos para 'a' ya que no hay coprimos con n=" + n);
                System.out.println("No se encontraron llaves válidas.");
                return;
            }
            for (int a : valid_a) {
                int[] res = eea(n, a);
                int a_inverse = res[2];
                for (int b = 0; b < n; b++) {
                    writer.printf("K=(%d, %d), a⁻¹=%d\n", a, b, a_inverse);
                }
            }
            System.out.println("¡Listo! El archivo '" + filename + "' ha sido creado con exito.");
        }
    }
    
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
    
    public static int[] eea(int n, int a) {
        int r0 = n, r1 = a;
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
            s0 = s1; s1 = s;
            t0 = t1; t1 = t;
        }
        
        if(t0<0) t0 = n - ((-t0)%n); 
        return new int[]{r0, s0, t0};
    }
    
    public static SimpleEntry<Integer, Integer> randomKey(int n) {
        if (n < 2) throw new IllegalArgumentException("n debe ser >= 2");
        List<Integer> coprimos = createList(n);
        if (coprimos.isEmpty())
            throw new IllegalStateException("Z*_n está vacío para n=" + n);
        Random rand = new Random();
        int valor1 = coprimos.get(rand.nextInt(coprimos.size()));
        int valor2 = rand.nextInt(n);
        return new SimpleEntry<>(valor1, valor2);
    }
    
    public static String affineEncipher(String plaintext, SimpleEntry<Integer, Integer> key) {
        int n = 95;
        int a = key.getKey();
        int b = key.getValue();
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (c < 32 || c > 126) throw new IllegalArgumentException("Caracter '" + c + "' fuera del alfabeto ASCII imprimible.");
            int mi = c - 32;
            int y = (a * mi + b) % n;
            ciphertext.append((char) (y + 32));
        }
        return ciphertext.toString();
    }
    
    public static String affineDecipher(String ciphertext, SimpleEntry<Integer, Integer> key) {
        int n = 95;
        int a = key.getKey();
        int b = key.getValue();
        StringBuilder plaintext = new StringBuilder();
        
        
        int[] res = eea(n, a);
        int a_inv = res[2];
        for (char c : ciphertext.toCharArray()) {
            if (c < 32 || c > 126) throw new IllegalArgumentException("Caracter '" + c + "' fuera del alfabeto ASCII imprimible.");
            int mi = c - 32;
            int y = (a_inv * (mi - b + n)) % n;
            plaintext.append((char) (y + 32));
        }
        return plaintext.toString();
    }
}