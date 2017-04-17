import java.math.BigInteger;

public class cacheBlock {
	
	private int validBit;
	private int dirtyBit;
	private BigInteger tag;

	public cacheBlock() {
		validBit = 0;
		dirtyBit = 0;
		tag = BigInteger.ZERO;

	}	

	public cacheBlock(int v, int d, BigInteger t) {
		validBit = v;
		dirtyBit = d;
		tag = t;
	}

	public void setValid(int v) {
		validBit = v;
	}

	public void setDirty(int d) {
		dirtyBit = d;
	}

	public void setTag(BigInteger t) {
		tag = t;
	}

	public int getValid() {
		return validBit;
	}

	public int getDirty() {
		return dirtyBit;
	}

	public BigInteger getTag() {
		return tag;
	}




}