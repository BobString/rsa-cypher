package rsa.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAUtils {

	/**
	 * Greatest Common Divisor for BigIntegers
	 */

	public static BigInteger gcdBI(BigInteger a, BigInteger b) {
		if (b.equals(BigInteger.ZERO))
			return a;
		else
			return gcdBI(b, a.mod(b));
	}

	/**
	 * Extended Euclides Algorithm for BigIntegers
	 * */

	public static BigInteger extEuclAlgBI(BigInteger E, BigInteger r,
			BigInteger c, BigInteger d, BigInteger e, BigInteger f) {

		if (r.equals(BigInteger.ZERO)) {
			BigInteger ret = BigInteger.ZERO;
			ret = e; // coefficient of E which is d
			return ret;
		}

		else {
			return extEuclAlgBI(r, E.mod(r),
					e.subtract((E.divide(r)).multiply(c)),
					f.subtract((E.divide(r)).multiply(d)), c, d);
		}
	}

	/**
	 * Generate Public and Private Keys.
	 * 
	 * @return
	 */
	public static RSAKeys generateKeys(BigInteger p, BigInteger q, byte[] key,
			int primeSize) {
		SecureRandom random = new SecureRandom();

		// N = p * q
		BigInteger n = p.multiply(q);

		// r = ( p - 1 ) * ( q - 1 )
		BigInteger r = p.subtract(BigInteger.valueOf(1));
		r = r.multiply(q.subtract(BigInteger.valueOf(1)));

		// Choose E, prime to phi(n) and less than r
		BigInteger e;
		do {
			random.nextBytes(key);
			e = new BigInteger(2 * primeSize, random);
			// uses Euclides Algorithm(gcd(E, r )) for calculating Greatest
			// Common
			// Divisor
		} while ((e.compareTo(r) != -1)
				|| (gcdBI(e, r).compareTo(BigInteger.valueOf(1)) != 0));

		BigInteger d = extEuclAlgBI(e, r, BigInteger.ZERO, BigInteger.ONE,
				BigInteger.ONE, BigInteger.ZERO);

		return new RSAKeys(n, e, d);

	}

}
