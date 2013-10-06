package rsa.main;

import java.math.BigInteger;
import java.security.SecureRandom;

import rsa.util.RSAKeys;
import rsa.util.RSAUtils;

public class RSA {


		/**
		 * Secure random object to generate the primes
		 */
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16]; // 16 bytes = 128 bits

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
		 * Encrypts the plaintext (Using Public Key).
		 * 
		 * @param message
		 *            String containing the plaintext message to be encrypted.
		 * @return The ciphertext as a BigInteger array.
		 */
		public BigInteger[] encrypt(String message) {
			int i;
			byte[] temp = new byte[1];

			byte[] digits = message.getBytes();

			BigInteger[] bigdigits = new BigInteger[digits.length];

			for (i = 0; i < bigdigits.length; i++) {

				temp[0] = digits[i];
				bigdigits[i] = new BigInteger(temp);

			}

			BigInteger[] encrypted = new BigInteger[bigdigits.length];

			for (i = 0; i < bigdigits.length; i++) {
				encrypted[i] = bigdigits[i].modPow(keys.getE(),keys.getN());
				// System.out.print( encrypted[i].toString( 16 ) ) ;
			}

			return (encrypted);
		}

		/**
		 * Decrypts the ciphertext (Using Private Key).
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
			char[] charArray = new char[decrypted.length];

			for (i = 0; i < charArray.length; i++)
				charArray[i] = (char) (decrypted[i].intValue());

			return (new String(charArray));
		}

		public BigInteger getP() {
			return p;
		}

		public BigInteger getQ() {
			return q;
		}

	}
