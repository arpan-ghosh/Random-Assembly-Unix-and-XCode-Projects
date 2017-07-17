//hw5
//@author Arpan Ghosh
//problem 2, unhexing numbers
//my comments describe where in the sixtyfour.s algorithm this java is in

public class Unhex {

	int t2 = 8; //count down 8 digits worth
    int t3 = 0; //mask out lo 4 bits stored here
    String t4; //address of digit for 4 bits $t1 and $t3
    String output = ""; //$v0, the output of hex string
    String asciiDigits[] = {"0", "1", "2", "3", "4", "5", 
	"6", "7", "8", "9", "a", "b", "c", "d", "e", "f"}; //the ASCII digits
    

	public Static base16(int a) {
        
        for (int i = 0; i <= t2; i++) {
            a0 = Integer.rotateLeft(a0, 4); //rotateleft,hi 4bits at lo-end now
            t3 = a0 & 0xf; //mask out lo 4 bits, same as andi op code
            t4 = asciiDigits[t3]; //compute address of digit for 4 bits
            output = output + t4; //load and append digit step
        }

        return output;
	}

	public static void main(String[] args) {
        
        //store everything in variable for checkstyle magic number compliancy
		int x = 0;
		int y = -1
		int a = 65535;
		int b = 65536;

        System.out.println("The hexadecimal of 0 is: " + base16(x));
        System.out.println("The hexadecimal of -1 is: " + base16(y));
		System.out.println("The hexadecimal of 65535 is: " + base16(a));
		System.out.println("The hexadecimal of 65536 is: " + base16(b));

	}
}