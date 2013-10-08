package rsa.main;

import java.math.BigInteger;
import java.security.SecureRandom;

import rsa.util.RSAKeys;
import rsa.util.RSAUtils;

/**
 * @author roberto
 * 
 */
public class RSA {

	/**
	 * Secure random object to generate the primes
	 */
	SecureRandom random = new SecureRandom();
	byte[] key = new byte[16];

	/**
	 * Bit length of each prime
	 */
	int primeSize;

	/**
	 * Prime numbers p and q.
	 */
	private BigInteger p;
	private BigInteger q;

	/**
	 * Object that contains, module N, exponent E and D
	 */
	private RSAKeys keys;

	public RSAKeys getKeys() {
		return keys;
	}

	/**
	 * Constructor.
	 * 
	 * @param primeSize
	 *            Bit length of each prime
	 */
	public RSA(int primeSize) {
		this.primeSize = primeSize;

		// Generate two distinct large prime numbers p and q.
		random.nextBytes(key);

		p = new BigInteger(primeSize, 15, random);

		do {
			random.nextBytes(key);
			q = new BigInteger(primeSize, 15, random);
		} while (q.compareTo(p) == 0);

		// Generate Keys
		keys = RSAUtils.generateKeys(p, q, key, primeSize);
	}

	/**
	 * Encrypts the plaintext
	 * 
	 * @param message
	 *            String containing the plaintext message to be encrypted.
	 * @return The ciphertext as a BigInteger array.
	 */
	public BigInteger[] encrypt(String message) {
		int i;
		byte[] aux = new byte[1];

		byte[] dig = message.getBytes();
		BigInteger[] digBigint = new BigInteger[dig.length];

		for (i = 0; i < digBigint.length; i++) {

			aux[0] = dig[i];
			digBigint[i] = new BigInteger(aux);

		}

		BigInteger[] encrypted = new BigInteger[digBigint.length];

		for (i = 0; i < digBigint.length; i++) {
			encrypted[i] = digBigint[i].modPow(keys.getE(), keys.getN());
		}

		return encrypted;
	}

	/**
	 * Decrypts the ciphertex
	 * 
	 * @param encrypted
	 *            BigInteger array containing the ciphertext to be decrypted.
	 * @return The decrypted plaintext.
	 */
	public String decrypt(BigInteger[] encrypted, BigInteger D, BigInteger N) {
		int i;

		BigInteger[] decrypted = new BigInteger[encrypted.length];

		for (i = 0; i < decrypted.length; i++) {
			decrypted[i] = encrypted[i].modPow(D, N);

		}
		char[] aux = new char[decrypted.length];

		for (i = 0; i < aux.length; i++)
			aux[i] = (char) (decrypted[i].intValue());

		return new String(aux);
	}

	public BigInteger getP() {
		return p;
	}

	public BigInteger getQ() {
		return q;
	}

}
