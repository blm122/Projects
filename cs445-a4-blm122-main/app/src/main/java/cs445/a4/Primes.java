package cs445.a4;

import java.util.Random;

/**
 * This class provides primality tests and associated helper methods.
 */
public class Primes {

    // Source of randomness for probabilistic primality test
    private static Random r = new Random();

    /**
     * Returns the largest prime number that does not exceed (i.e., is less than
     * or equal to) n. That is, if n is prime, it is returned. Otherwise, the
     * next smaller prime is returned.
     * @param n the upper limit
     * @return the largest prime not exceeding n
     * @throws IllegalArgumentException if n is less than 2
     */
    public static int primeNoMoreThan(int n) {
        if (n < 2) throw new IllegalArgumentException();
        else if (n == 2) return 2; // 2 is the only even prime
        if (n % 2 == 0) n--; // subtract one if n is even
        while (!isPrime(n)) {
            n -= 2;
            assert n >= 2; // n should never get this small
        }
        return n;
    }

    /**
     * Returns the smallest prime number that is not less than (i.e., is greater
     * than or equal to) n. That is, if n is prime, it is returned. Otherwise,
     * the next smaller prime is returned.
     * @param n the lower limit
     * @return the smallest prime no less than n
     */
    public static int primeNoLessThan(int n) {
        if (n <= 2) return 2;
        if (n % 2 == 0) n++; // add one if n is even
        while (!isPrime(n)) {
            n += 2;
        }
        return n;
    }

    /**
     * Uses Miller-Rabin probabilistic primality test to determine if n is
     * (probably) prime. If this method returns true, n is 1/2^100 likely to be
     * composite. If this method returns false, n is definitely composite.
     * @param n the integer of which to test the primality
     * @return whether n is (probably) prime
     */
    public static boolean isPrime(int n) {
        return isPrime(n, 100);
    }

    /**
     * Uses Miller-Rabin probabilistic primality test to determine if n is
     * (probably) prime. Parameter k is the number of iterations and thus
     * represents a confidence value. If this method returns true, n is 1/2^k
     * likely to be composite. If this method returns false, n is definitely
     * composite.
     * @param n the integer of which to test the primality
     * @param k the confidence parameter
     * @return whether n is (probably) prime
     * @throws IllegalArgumentException if n is less than 2
     */
    public static boolean isPrime(int n, int k) {
        int d, s;

        if(n < 2) {
            throw new IllegalArgumentException("Prime test on number too low");
        } else if (n == 2 || n == 3) {
            return true;
        }

        if(n % 2 != 1) {
            return false;
        }

        d = n - 1;
        s = 0;
        while(d % 2 == 0) {
            d /= 2;
            s++;
        }

        for(int i = 0; i < k; i++) {
            if(!subPrime(d, s, n)) {
                return false;
            }
        }

        return true;
    }

    // Miller-Rabin subprime test
    private static boolean subPrime(int d, int s, int n) {
        int a = r.nextInt(n - 3) + 2;
        int x = modExp(a, d, n);
        if(x == 1 | x == n - 1) {
            return true;
        }
        for(int r = 1; r < s; r++) {
            x = (x * x) % n;
            if(x == 1) {
                return false;
            } else if(x == n - 1) {
                return true;
            }
        }
        return false;
    }

    // Modular exponentiation; computes (base^power) % modulus
    private static int modExp(int base, int power, int modulus) {
        long result = 1;
        for (int i = 31; i >= 0; i--) {
            result = (result * result) % modulus;
            if((power & (1 << i)) != 0) {
                result = (result*base) % modulus;
            }
        }
        return (int)result;
    }

}

