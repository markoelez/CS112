package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}

	private static String removeLeadingZeros(String s) {
		int i = 0;
		while (i<s.length()-1 && s.charAt(i) == '0') {
			i++;
		}
		System.out.println(s.substring(i));
		return s.substring(i);
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		String str = integer.trim();
		BigInteger bigInt = new BigInteger();

		// get rid of zeros in front and check format
		if (str.charAt(0) == '-' || str.charAt(0) == '+') {
			char sign = str.charAt(0);
			if (sign == '-') {
				bigInt.negative = true;
			} else if (sign == '+'){
				bigInt.negative = false;
			}
			// CHANGE METHOD FOR REMOVING ZEROS FROM FRONT
			str = removeLeadingZeros(str.substring(1));
//			str = String.valueOf(Integer.parseInt(str.substring(1)));
		} else if (Character.isDigit(str.charAt(0))){
			str = removeLeadingZeros(str);
//			str = String.valueOf(Integer.parseInt(str));
		} else {
			throw new IllegalArgumentException("Incorrect format");
		}

		bigInt.numDigits = str.length();

		// create linked list root
		int start_idx = str.length()-1;
		bigInt.front = new DigitNode(Character.getNumericValue(str.charAt(start_idx)), null);
//		System.out.println("FRONT: \n" + str.charAt(str.length()-1));
		// build the rest
		DigitNode ptr = bigInt.front;
		for (int i = start_idx-1; i>-1; i--) {
			if (Character.isDigit(str.charAt(i))) {
//				System.out.println(Character.getNumericValue(str.charAt(i)));
				ptr.next = new DigitNode(Character.getNumericValue(str.charAt(i)), null);
			} else {
				throw new IllegalArgumentException("Incorrect format");
			}
			ptr = ptr.next;
		}

		return bigInt;
	}

	private static DigitNode padNode(DigitNode snode, int diff) {
		DigitNode ptr = snode;

		while (ptr.next != null) {
			ptr = ptr.next;
		}

		while ((diff--) != 0) {
			ptr.next = new DigitNode(0, null);
			ptr = ptr.next;
		}
		return snode;
	}

	private static void traverseNodes(DigitNode node) {
		// utility function to traverse and print nodes of a linked list
		System.out.println("----------------------------------");
		DigitNode n = node;
		while (n != null) {
			System.out.print(n.digit + " ");
			n = n.next;
		}
		System.out.println("\n----------------------------------");
	}

	private static DigitNode reverse(DigitNode node) {
		DigitNode prev = null;
		DigitNode current = node;
		DigitNode next = null;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		node = prev;
		return node;
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {

		boolean negative;

		if ((first.negative && second.negative) || (!first.negative && !second.negative)) {
			// ADDITION
			DigitNode result = null;
			DigitNode prev = null;
			DigitNode temp = null;
			int carry = 0;
			int sum = 0;


			BigInteger res = new BigInteger();

			negative = first.negative;
			DigitNode root1 = first.front;
			DigitNode root2 = second.front;
			while (root1 != null || root2 != null) {
				// get sum of digits
				sum = carry + (root1 != null ? root1.digit : 0) + (root2 != null ? root2.digit : 0);
				// if sum >= 10, carry 1
				carry = (sum >= 10) ? 1 : 0;
				// account for possible carrying
				sum = sum % 10;
				// create node for sum
				temp = new DigitNode(sum, null);
				// if this is the root, set root as the sum
				if (result == null) {
					result = temp;
					// otherwise add as next node
				} else {
					prev.next = temp;
				}
				// update previous pointer
				prev = temp;
				// move to next digits
				if (root1 != null) {
					root1 = root1.next;
				}
				if (root2 != null) {
					root2 = root2.next;
				}
			}
			// if we had to carry, add it to the end of the result
			if (carry > 0) {
				temp.next = new DigitNode(carry, null);
			}
			res.front = result;
			if (negative) {
				res.negative = true;
			}
			// ADD NUM DIGITS
			return res;
		} else {
			// SUBTRACTION

			// first figure out which is greater
			DigitNode temp1 = first.front;
			DigitNode temp2 = second.front;

			DigitNode lnode = null;
			DigitNode snode = null;

			if (first.numDigits > second.numDigits) {
				lnode = first.front;
				snode = second.front;
				negative = first.negative;
			} else if (first.numDigits < second.numDigits) {
				lnode = second.front;
				snode = first.front;
				negative = second.negative;
			} else {
				System.out.println("SAME LENGTH");
				DigitNode t1 = temp1;//reverse(temp1);
				DigitNode t2 = temp2;//reverse(temp2);
				int d1 = 0;
				int d2 = 0;
				while (t1 != null && t2!=null) {

//					if (t1.digit != t2.digit) {
//						System.out.println("HERE::::: " + t1.digit);
//						lnode = t1.digit > t2.digit ? temp1 : temp2;
//						snode = t1.digit > t2.digit ? temp2 : temp1;
//						break;
//					}
					if (t1.digit != t2.digit) {
						d1 = t1.digit;
						d2 = t2.digit;
					}
					t1 = t1.next;
					t2 = t2.next;
				}
				if (d1 == 0 && d2==0) {
					return new BigInteger();
				}
				lnode = d1 > d2 ? temp1 : temp2;
				snode = d1 > d2 ? temp2 : temp1;
//				traverseNodes(lnode);
//				traverseNodes(snode);

				negative = first.front == lnode ? first.negative : second.negative;
			}

			int diff = Math.abs(first.numDigits - second.numDigits);
			if (diff != 0) {
				snode = padNode(snode, diff);
			}

			traverseNodes(lnode);
			traverseNodes(snode);

			BigInteger ans = new BigInteger();
			ans.front = new DigitNode(0, null);
			DigitNode ansPtr = ans.front;

			boolean borrow = false;

			while (lnode != null && snode != null) {
				int d1 = lnode.digit;
				int d2 = snode.digit;
				int resDig = 0;
				// if we had to borrow previously, decrement digit
				if (borrow) {
					d1 --;
					borrow = false;
				}

				if (d1 > d2) {
					// no borrow, just subtract
					resDig = d1 - d2;
				} else if (d1 < d2){
					// we need to borrow
					borrow = true;
					resDig = 10 + d1 - d2;
				}
				// set current digit to sum
				ansPtr.digit = resDig;
				// check if we have more sums to calc before creating next node
				if (lnode.next != null || snode.next != null){
					ansPtr.next = new DigitNode(resDig, null);
				}
				// move pointers along
				ansPtr = ansPtr.next;
				lnode = lnode.next;
				snode = snode.next;
				// debug print
				System.out.println("RESDIG :::::::::::::::::::::::::::::::::::::: " + resDig);
			}
			ans.negative = negative;
			// debug
			traverseNodes(ans.front);

			// REMOVE TRAILING ZEROS
			 int last_idx = 0;
			 int i = 0;
			 DigitNode ref = ans.front;
			 while (ref != null) {
			 	if (ref.digit != 0) {
			 		last_idx = i;
				}
			 	i++;
			 	ref = ref.next;
			 }
			 int n = 0;
			 DigitNode temp = ans.front;
			 while (n != last_idx) {
			 	temp = temp.next;
			 	n++;
			 }
			 temp.next = null;

			return ans;
		}
	}


	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		
		
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}


}
