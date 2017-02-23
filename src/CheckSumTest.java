/**
 * 
 */

/**
 * @author wills
 *
 */
public class CheckSumTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = "HELLO WORLD";
		int checkSumLength = 4;
		int checkSumTotal = 0;
		int[] checkSumWeights = new int[checkSumLength];
		checkSumWeights[0] = 1;
		checkSumWeights[1] = 3;
		checkSumWeights[2] = 11;
		checkSumWeights[3] = 17;

		for (int i = 0; i < s.length(); i++){
		    char c = s.charAt(i);
		    int asciiCharacter = (int)c;
		   
		    int index = i % checkSumLength;
		    
		    int checkSumValue = checkSumWeights[index]* asciiCharacter;
		    checkSumTotal += checkSumValue;
		    
		    //Process char
		   
		}
		 System.out.println(checkSumTotal);
		
	}

}
