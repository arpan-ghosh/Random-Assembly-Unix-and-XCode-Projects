
import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.BigInteger;




public class csim {

	static int sets, blocks, bytes;
	static boolean writeAllocate, writeThrough, lru;
	static int blOffSize, setIndexSize, tagSize;
	static int totalLoads, totalStores, loadHits, loadMisses,
				storeHits, storeMisses, totalCycles;
	static cacheBlock[][] cache;


	public static void main(String[] args) { //need to add command line arguments

		sets = 256;  //take these out
		blocks = 4;
		bytes = 16;
		writeAllocate = true;
		writeThrough = false;
		lru = true;

		setSizes(bytes, sets);
		//System.out.println("offset " + blOffSize + "index " + setIndexSize + "tag" + tagSize);



		BigInteger tag;
		int setIndex;
		int blockOffset;

		cache = new cacheBlock[sets][blocks];
		for (int p = 0; p < sets; p++) {
			for (int q = 0; q < blocks; q++) {
				cache[p][q] = new cacheBlock();
			}
		}

		
		String address;    //clean these up a bit
		String a;
		boolean hit;
		boolean store;
		boolean load;
		int iter = 1;

		String type;

		try {
			Scanner s = new Scanner(new File("gcc.trace"));

			while (s.hasNextLine()) {

				hit = false;

				//System.out.println("Iteration: " + iter++);  //to watch the iterations
				type = s.next();

				if (type.equalsIgnoreCase("s")) {   //these determine store or load
					totalStores++;
					store = true;
					load = false;
				}
				else if (type.equalsIgnoreCase("l")) {
					totalLoads++;
					load = true;
					store = false;
				}
				else {
					store = false;
					load = false;
				}

				s.skip(" 0x");   //skip the prefix to the hex

				a = s.next();


				BigInteger dec = new BigInteger(a, 16);    //reads the hex to decimal big integer
				address = dec.toString(2);    //converts to binary string
				BigInteger dec2 = new BigInteger(address);     //now binary big integer
				
				address = String.format("%032d", dec2);    //makes sure padded with 0s on left for correct size

				int len = address.length();

					//this section cuts up the address into the right parts. probably needs to be cleaned up
				blockOffset = Integer.parseInt(address.substring(len - blOffSize));
				String temp = address.substring((len - blOffSize - setIndexSize),
							 (len- blOffSize));
				if (temp.length() == 0) setIndex = 0;    //if there is only one set, don't need a bit for set index
				else setIndex = Integer.parseInt(temp, 2);
			
				tag = new BigInteger(address.substring(0, (len - blOffSize - setIndexSize)));


				for (int i = 0; i < blocks; i++) { //searching through the actual cache set

						if (cache[setIndex][i].getValid() == 1) {    //first checks valid bit
							
							
							if (tag.equals(cache[setIndex][i].getTag())) {   //matches tags
								//System.out.println("found");
								hit = true;

								if (store) {
									storeHits++;
									totalCycles++;

									if (writeThrough) {
										totalCycles += 100;
									}
									else {
							
										cache[setIndex][i].setDirty(1);   //write back, set dirty bit
									}

								}

								else if (load) {
									loadHits++;
									totalCycles++;

								}

								if (lru) {   //moves all others in set down, puts this on top as least recently used
									cacheBlock move = cache[setIndex][i];
									for (int x = i; x > 0; x--) {
										cache[setIndex][x] = cache[setIndex][x-1];
										
									}
									cache[setIndex][0] = move;
								}

						}
					}

				}

				if (!(hit)) {   //MISS

					if (store) {
						storeMisses++;
						totalStores++;
						totalCycles += 100;  //stored to memory

						if (writeAllocate) {
							addOnMiss(setIndex, tag);    //allocates to cache after writing to memory
						}

					}

					else if (load) {
						loadMisses++;
						totalLoads++;
						totalCycles += 100;
						addOnMiss(setIndex, tag);   //loads from memory, writes to cache

					}
				}

				s.nextLine();

			}


		} catch (IOException e) {
			System.err.println(e);
		}

		System.out.println("Total loads: " + totalLoads);
		System.out.println("Total stores: " + totalStores);
		System.out.println("Load hits: " + loadHits);
		System.out.println("Load misses: " + loadMisses);
		System.out.println("Store hits: " + storeHits);
		System.out.println("Store misses: " + storeMisses);
		System.out.println("Total cycles: " + totalCycles);

	}

	public static void addOnMiss(int s, BigInteger t) {

		cacheBlock add = new cacheBlock(1, 0, t);

		if (cache[s][blocks-1].getDirty() == 1) {
			totalCycles += 100;  //write back to memory
		}

		for (int y = blocks-1; y > 0; y--) {
			cache[s][y] = cache[s][y-1];
		}
		cache[s][0] = add; //store at top of set

		totalCycles++;
	}

	public static void setSizes(int b, int s) {   //determines the size of each chunk of address

		double temp = (Math.log(b)) / (Math.log(2));
		blOffSize = (int) temp;
		temp = (Math.log(s)) / (Math.log(2));
		setIndexSize = (int) temp;
		temp = 32 - blOffSize - setIndexSize;      //need to change all 32s to constants
		tagSize = (int) temp;

	}
}