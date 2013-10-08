package rsa.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import rsa.main.RSA;
import rsa.util.RSAKeys;

public class UI {

	public static void main(String[] args) {
		int primeSize;

		System.out.println("Please enter block size (better 1024):");
		String blocksize = "";
		try {
			blocksize = (new BufferedReader(new InputStreamReader(System.in)))
					.readLine();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		System.out.println("");

		if (blocksize.isEmpty()) {
			primeSize = 1024;
		} else {
			primeSize = Integer.parseInt(blocksize);
		}

		// Generate Public and Private Keys

		RSA rsa = new RSA(primeSize);
		RSAKeys keys = rsa.getKeys();
		System.out
				.println("===================================================");
		System.out.println("Prime size: " + primeSize);
		System.out.println("");

		System.out.println("Prime numbers p and q");
		System.out.println("p: " + rsa.getP().toString(16).toUpperCase());
		System.out.println("q: " + rsa.getQ().toString(16).toUpperCase());
		System.out.println("");
		System.out.println("The public key is: ");
		System.out.println("N: " + keys.getN().toString(16).toUpperCase());
		System.out.println("E: " + keys.getE().toString(16).toUpperCase());
		System.out.println("");
		System.out.println("The private key is:");
		System.out.println("N: " + keys.getN().toString(16).toUpperCase());
		System.out.println("D: " + keys.getD().toString(16).toUpperCase());
		System.out
				.println("===================================================");
		System.out.println("");

		// Get message (plaintext) from user
		System.out.println("Please enter plaintext:");
		String plaintext = "";
		try {
			plaintext = (new BufferedReader(new InputStreamReader(System.in)))
					.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Encrypt Message
		BigInteger[] ciphertext = rsa.encrypt(plaintext);

		System.out.print("Ciphertext: [");
		for (int i = 0; i < ciphertext.length; i++) {
			System.out.print(ciphertext[i].toString(2));

			if (i != ciphertext.length - 1) {
				System.out.print(" ");
			}
		}
		System.out.println("]");
		System.out.println("");

		// Get message (plaintext) from user
		System.out.println("");
		System.out.println("Please enter ciphertext:");

		BigInteger[] array = new BigInteger[1];
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String[] strs = br.readLine().split(" ");
			if (strs.length == 0) {
				throw new RuntimeException(
						"Please don't enter an empty cyphertext");
			}
			array = new BigInteger[strs.length];
			for (int j = 0; j < strs.length; j++) {
				BigInteger bi = new BigInteger(strs[j], 2);
				array[j] = bi;

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Decrypting... ");
		RSA rsaaux = new RSA(primeSize);

		String recoveredPlaintext = rsaaux.decrypt(array, keys.getD(),
				keys.getN());

		System.out.println("Recovered plaintext: [" + recoveredPlaintext + "]");
	}

}
