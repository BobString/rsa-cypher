package rsa.util;

import java.math.BigInteger;

public class RSAKeys {
	BigInteger n;
	BigInteger e;
	BigInteger d;

	public RSAKeys(BigInteger n, BigInteger e, BigInteger d) {
		this.n = n;
		this.e = e;
		this.d = d;
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getD() {
		return d;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}

	@Override
	public String toString() {
		//TODO: put variables
		String res="<RSAKeyPair>" +
				"  <Modulus></Modulus>" +
				"  <Exponent></Exponent>" +
				"  <P></P>" +
				"  <Q></Q>" +
				"  <DP></DP>" +
				"  <DQ></DQ>" +
				"  <InverseQ></InverseQ>" +
				"  <D></D>" +
				"</RSAKeyPair>"; 
		return res;
	}
}
