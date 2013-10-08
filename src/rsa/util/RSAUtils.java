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

	public static BigInteger euclAlgBigInt(BigInteger u, BigInteger r,
			BigInteger c, BigInteger d, BigInteger e, BigInteger f) {

		if (r.equals(BigInteger.ZERO)) {
			BigInteger ret = BigInteger.ZERO;
			ret = e;
			return ret;
		}

		else {
			return euclAlgBigInt(r, u.mod(r),
					e.subtract((u.divide(r)).multiply(c)),
					f.subtract((u.divide(r)).multiply(d)), c, d);
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

		BigInteger n = p.multiply(q);
		BigInteger r = p.subtract(BigInteger.valueOf(1)).multiply(
				q.subtract(BigInteger.valueOf(1)));
		// r = r.multiply(q.subtract(BigInteger.valueOf(1)));

		// Choose E, prime to phi(n) and less than r
		BigInteger e;
		do {
			random.nextBytes(key);
			e = new BigInteger(2 * primeSize, random);
		} while ((e.compareTo(r) != -1)
				|| (gcdBI(e, r).compareTo(BigInteger.valueOf(1)) != 0));

		BigInteger d = euclAlgBigInt(e, r, BigInteger.ZERO, BigInteger.ONE,
				BigInteger.ONE, BigInteger.ZERO);

		return new RSAKeys(n, e, d);

	}

}
