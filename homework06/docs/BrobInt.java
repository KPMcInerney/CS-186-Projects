/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  B.J. Johnson
 * Date       :  2017-04-04
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2017-04-04  B.J. Johnson  Initial writing and begin coding
 *  1.1.0  2017-04-13  B.J. Johnson  Completed addByt, addInt, compareTo, equals, toString, Constructor,
 *                                     validateDigits, two reversers, and valueOf methods; revamped equals
 *                                     and compareTo methods to use the Java String methods; ready to
 *                                     start work on subtractByte and subtractInt methods
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.lang.StringBuilder;
import java.lang.Math;

public class BrobInt {

   public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
   public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"
   public static final BrobInt TWO      = new BrobInt(  "2" );      /// Constant for "two"
   public static final BrobInt THREE    = new BrobInt(  "3" );      /// Constant for "three"
   public static final BrobInt FOUR     = new BrobInt(  "4" );      /// Constant for "four"
   public static final BrobInt FIVE     = new BrobInt(  "5" );      /// Constant for "five"
   public static final BrobInt SIX      = new BrobInt(  "6" );      /// Constant for "six"
   public static final BrobInt SEVEN    = new BrobInt(  "7" );      /// Constant for "seven"
   public static final BrobInt EIGHT    = new BrobInt(  "8" );      /// Constant for "eight"
   public static final BrobInt NINE     = new BrobInt(  "9" );      /// Constant for "nine"
   public static final BrobInt TEN      = new BrobInt( "10" );      /// Constant for "ten"

  /// Some constants for other intrinsic data types
  ///  these can help speed up the math if they fit into the proper memory space
   // public static final BrobInt MAX_INT  = new BrobInt( new Integer( Integer.MAX_VALUE ).toString() );
   // public static final BrobInt MIN_INT  = new BrobInt( new Integer( Integer.MIN_VALUE ).toString() );
   // public static final BrobInt MAX_LONG = new BrobInt( new Long( Long.MAX_VALUE ).toString() );
   // public static final BrobInt MIN_LONG = new BrobInt( new Long( Long.MIN_VALUE ).toString() );

  /// These are the internal fields
   private String internalValue = "";        // internal String representation of this BrobInt
   private int   sign          = 0;         // "0" is positive, "1" is negative
   private String reversed      = "";        // the backwards version of the internal String representation
   private int[] intVersion   = null;      // byte array for storing the string values; uses the reversed string

  /**
   *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
   *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
   *   for later use
   *  @param  value  String value to make into a BrobInt
   *  @throws  IllegalArgumentException if something is hinky
   */
   public BrobInt( String value ) {
      StringBuilder sb = new StringBuilder();
      StringBuilder reversedSB = new StringBuilder();
      if ( value == "" || value == null ){
         throw new IllegalArgumentException();
      }
      for ( int k = 0; k < value.length(); k++ ){
         validateDigits(value.charAt(k));
      }
      if ( value.charAt(0) == '-' ){
         sign = 1;
         intVersion = new int[value.length()-1];
         for ( int i = 1; i < value.length(); i++ ){
            sb.append( value.charAt(i) );
            reversedSB.append( value.charAt( value.length() - i) );
            intVersion[i-1] = value.charAt(i) - '0';
         }
         internalValue = sb.toString();
         reversed = reversedSB.toString();
      } else if ( value.charAt(0) == '+' ) {
         sign = 0;
         intVersion = new int[value.length()-1];
         for ( int i = 1; i < value.length(); i++ ){
            sb.append( value.charAt(i) );
            reversedSB.append( value.charAt( value.length() - i) );
            intVersion[i-1] = value.charAt(i) - '0';
         }
         internalValue = sb.toString();
         reversed = reversedSB.toString();
      } else {
         sign = 0;
         intVersion = new int[value.length()];
         internalValue = value;
         for ( int i = 0; i < value.length(); i++ ){
            reversedSB.append( value.charAt( (value.length() - 1) - i) );
            intVersion[i] = value.charAt(i) - '0';
         }
         reversed = reversedSB.toString();
      }
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to return the sign (+/-) of this BrobInt
    *  @return the integer value that is represents the sign(+/-) of this BrobInt
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int getSign(){
      return sign;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to set the sign of this BrobInt
    *  @return nothing (its a void method)
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public void setSign( int newSign ){
      sign = newSign;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to return the internalValue string of this BrobInt
    *  @return the internalValue string (version of this BrobInt without its sign) of this BrobInt
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public String getInternalValue(){
      return internalValue;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to return the integer array of this BrobInt
    *  @return the integer array (version of this BrobInt without its sign) of this BrobInt
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int[] getIntVersion(){
      return intVersion;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to validate that all the characters in the value are valid decimal digits
   *  @param   value       character value that will be checked for validity
   *  @return  boolean  true if all digits are good
   *  @throws  IllegalArgumentException if something is hinky
   *  note that there is no return false, because of throwing the exception
   *  note also that this must check for the '+' and '-' sign digits
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean validateDigits( Character value ) {
      String numbers = "-+1234567890";
      for ( int j = 0; j < numbers.length(); j++ ){
         if ( value == numbers.charAt(j) ){
            return true;
         }
      }
      throw new IllegalArgumentException();
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to reverse the value of this BrobInt
   *  @return BrobInt that is the reverse of the value of this BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt reverser() {
      StringBuilder reversedSB2 = new StringBuilder();
      for ( int i = 0; i < internalValue.length(); i++ ){
         reversedSB2.append( internalValue.charAt( (internalValue.length() - 1) - i) );
      }
      return new BrobInt( reversedSB2.toString() );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to reverse the value of a BrobIntk passed as argument
   *  Note: static method
   *  @param  gint         BrobInt to reverse its value
   *  @return BrobInt that is the reverse of the value of the BrobInt passed as argument
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static BrobInt reverser( BrobInt gint ) {
      return gint.reverser();
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to add the value of a BrobIntk passed as argument to this BrobInt using byte array
   *  @param  gint         BrobInt to add to this
   *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt addByte( BrobInt gint ) {
      int[] intVersion2 = gint.getIntVersion();
      int[] a = null;
      int[] b = null;
      int addSign = 0;
      int digit = 0;
      int rem = 0;
      StringBuilder result = new StringBuilder();
      if ( sign == 1 && gint.getSign() == 1 ){
         addSign = 1;
      }
      if ( sign == 0 && gint.getSign() == 1 ){
         return subtractByte( new BrobInt(gint.getInternalValue()) );
      }
      if ( sign == 1 && gint.getSign() == 0 ){
         return gint.subtractByte( new BrobInt( internalValue));
      }
      if ( intVersion.length >= intVersion2.length ){
         a = intVersion;
         b = intVersion2;
      } else {
         a = intVersion2;
         b = intVersion;
      }
      int bIndex = b.length - 1;
      for ( int i = a.length - 1; i > -1; i-- ){
         if ( a.length == b.length ){
            digit = ( a[i] + b[i] + rem );
         } else {
            if ( bIndex >= 0 ){
               digit = ( a[i] + b[bIndex] + rem );
               bIndex = bIndex - 1;
            } else {
               digit = a[i] + rem;
            }
         }
         if ( digit > 9 ) {
            rem = 1;
            digit = digit - 10;
         } else {
            rem = 0;
         }
         result.append(digit);
      }
      if ( rem == 1 ){
         result.append(1);
      }
      if ( addSign == 1 ){
         result.append('-');
      }
      BrobInt resultInt = new BrobInt( result.toString() );
      BrobInt resultInt2 = new BrobInt( resultInt.reverser().toString() );
      return (new BrobInt( resultInt2.toString().replaceFirst("^0+(?!$)", "") ) );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to add the value of a BrobIntk passed as argument to this BrobInt using int array
   *  @param  gint         BrobInt to add to this
   *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt addInt( BrobInt gint ) {
      String input = gint.getInternalValue();
      String a = "";
      String b = "";
      int addSign = 0;
      int digit = 0;
      int rem = 0;
      StringBuilder result = new StringBuilder();
      if ( sign == 1 && gint.getSign() == 1 ){
         addSign = 1;
      }
      if ( sign == 0 && gint.getSign() == 1 ){
         //result.append( subtractByte( new BrobInt(gint.getInternalValue()) ) );
         return subtractByte( new BrobInt(gint.getInternalValue()) );
      }
      if ( sign == 1 && gint.getSign() == 0 ){
         return gint.subtractByte( new BrobInt( internalValue));
      }
      if ( internalValue.length() >= input.length() ){
         a = internalValue;
         b = input;
      } else {
         a = input;
         b = internalValue;
      }
      int bIndex = b.length() - 1;
      for ( int i = a.length() - 1; i > -1; i-- ){
         if ( a.length() == b.length() ){
            digit = ( (a.charAt(i) - '0') + (b.charAt(i) - '0') + rem );
         } else {
            if ( bIndex >= 0 ){
               digit = ( (a.charAt(i) - '0') + (b.charAt(bIndex) - '0') + rem );
               bIndex = bIndex - 1;
            } else {
               digit = (a.charAt(i) - '0') + rem;
            }
         }
         if ( digit > 9 ) {
            rem = 1;
            digit = digit - 10;
         } else {
            rem = 0;
         }
         result.append(digit);
      }
      if ( rem == 1 ){
         result.append(1);
      }
      if ( addSign == 1 ){
         result.append('-');
      }
      BrobInt resultInt = new BrobInt( result.toString() );
      BrobInt resultInt2 = new BrobInt( resultInt.reverser().toString() );
      return (new BrobInt( resultInt2.toString().replaceFirst("^0+(?!$)", "") ) );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using bytes
   *  @param  gint         BrobInt to subtract from this
   *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt subtractByte( BrobInt gint ) {
      String input = gint.getInternalValue();
      String a = "";
      String b = "";
      int digit = 0;
      int borrower = 0;
      int subSign = 0;
      int switchOrder = 0;
      StringBuilder result = new StringBuilder();
      if ( sign == 0 && gint.getSign() == 1 ){
         return addInt( new BrobInt(gint.getInternalValue()) );
      }
      if ( sign == 1 && gint.getSign() == 0 ){
         result.append( '-' );
         BrobInt resultIntTemp = new BrobInt( internalValue );
         result.append( resultIntTemp.addInt(gint).toString() );
         return ( new BrobInt(result.toString()) );
      }
      if ( sign == 1 && gint.getSign() == 1 ){
         subSign = 1;
         switchOrder = 1;
      }
      if ( internalValue.length() >= input.length() ){
         a = internalValue;
         b = input;
      } else {
         a = input;
         b = internalValue;
         subSign = 1;
      }
      if ( internalValue.length() == input.length() ){
         if ( compareTo( new BrobInt(input) ) < 0 ){
            if ( switchOrder == 1 ){
               BrobInt resultInt = new BrobInt(input);
               return resultInt.subtractByte( new BrobInt(internalValue) );
            }
            if ( internalValue.length() == 1 && internalValue.charAt(0) == '0') {
               result.append('-' + input);
               return new BrobInt(result.toString());
            }
            subSign = 1;
         }
      }
      int bIndex = b.length() - 1;
      for ( int i = a.length() - 1; i > -1; i-- ){
         if ( a.length() == b.length() ){
            if ( ((a.charAt(i) - '0') - borrower) >= (b.charAt(i) - '0') ){
               digit = ((a.charAt(i) - '0') - borrower) - (b.charAt(i) - '0');
               borrower = 0;
            } else {
               digit = ((a.charAt(i) - '0') + 10) - (b.charAt(i) - '0');
               borrower = 1;
            }
         } else {
            if ( bIndex >= 0 ){
               if ( ((a.charAt(i) - '0') - borrower) >= (b.charAt(bIndex) - '0') ){
                  digit = ((a.charAt(i) - '0') - borrower) - (b.charAt(bIndex) - '0');
                  borrower = 0;
               } else {
                  digit = ((a.charAt(i) - '0') + 10 - borrower) - (b.charAt(bIndex) - '0');
                  borrower = 1;
               }
            } else {
               digit = (a.charAt(i) - '0') - borrower;
               borrower = 0;
            }
         }
         bIndex = bIndex - 1;
         result.append(digit);
      }
      if ( subSign == 1 ){
         result.append( '-' );
      }
      BrobInt resultInt = new BrobInt( result.toString() );
      BrobInt resultInt2 = new BrobInt( resultInt.reverser().toString() );
      return (new BrobInt( resultInt2.toString().replaceFirst("^0+(?!$)", "") ) );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using int array
   *  @param  gint         BrobInt to subtract from this
   *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt subtractInt( BrobInt gint ) {
      throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
   *  @param  gint         BrobInt to multiply by this
   *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt multiply( BrobInt gint ) {
      BrobInt resultBrobInt = new BrobInt( "0" );
      BrobInt a;
      BrobInt b;
      if ( internalValue.length() >= gint.getInternalValue().length() ){
         a = new BrobInt( internalValue );
         b = new BrobInt( gint.getInternalValue() );
      } else {
         a = new BrobInt( gint.getInternalValue() );
         b = new BrobInt( internalValue );
      }
      int numberOfInts = (b.getInternalValue().length()/9) + 1;
      int[] intArray = new int[numberOfInts];
      if ( b.getInternalValue().length() < 10 ){
         intArray[0] = Integer.parseInt(b.getInternalValue());
      } else {
         String tempString = "";
         for ( int j = 0; j < numberOfInts; j++ ){
            if ( j + 9 < b.getInternalValue().length() ){
               tempString = b.getInternalValue().substring(j*9, (j*9) + 9);
               intArray[j] = Integer.parseInt(tempString);
            } else {
               tempString = b.getInternalValue().substring(j*9, (j*9) + (b.getInternalValue().length() - j) );
               intArray[j] = Integer.parseInt(tempString);
            }
         }
      }
      for ( int i = 0; i < numberOfInts; i++ ){
         for (int j = 0; j < intArray[i]; j++ ){
            //System.out.println("resultBrobInt = " + resultBrobInt);
            resultBrobInt = resultBrobInt.addInt( new BrobInt(a.getInternalValue()) );
         }
      }
      if ( (sign == 1 && gint.getSign() == 0) || (sign == 0 && gint.getSign() == 1) ){
         resultBrobInt.setSign(1);
      } else {
         resultBrobInt.setSign(0);
      }
      return resultBrobInt;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
   *  @param  gint         BrobInt to divide this by
   *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt divide( BrobInt gint ) {
      BrobInt resultBrobInt = new BrobInt( "0" );
      BrobInt a = new BrobInt( "0" );
      String aString = a.getInternalValue();
      int divSign = 0;
      double multiplier = 0;
      if ( sign == 1 && gint.getSign() == 0 ){
         divSign = 1;
      }
      if ( sign == 0 && gint.getSign() == 1 ){
         divSign = 2;
         gint.setSign(0);
      }
      if ( sign == 1 && gint.getSign() == 1 ){
         divSign = 3;
         sign = 0;
         gint.setSign(0);
      }
      while ( internalValue.length() > aString.length() || (internalValue.length() == aString.length() && compareTo(a) >= 0) ){
            a = a.addInt(gint);
            aString = a.getInternalValue();
            resultBrobInt = resultBrobInt.addInt(ONE);
      }
      if ( divSign == 1 || divSign == 2 || divSign == 3 ){
         if ( divSign == 2 || divSign == 3 ){
            gint.setSign(1);
            if ( divSign == 3 ){
               sign = 1;
               return resultBrobInt.subtractByte(ONE);
            }
         }
         StringBuilder result = new StringBuilder( '-' + resultBrobInt.subtractByte(ONE).toString() );
         return new BrobInt(result.toString());
      }
      return resultBrobInt.subtractByte(ONE);
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to get the remainder of division of this BrobInt by the one passed as argument
   *  @param  gint         BrobInt to divide this one by
   *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt remainder( BrobInt gint ) {
      StringBuilder sb = new StringBuilder();
      StringBuilder resultSB = new StringBuilder();
      String resultString = subtractByte(divide(gint).multiply(gint)).toString();
      BrobInt resultBrobInt = new BrobInt( resultString );
      if ( resultString.charAt(0) == '-' ){
         for ( int i = 1; i < resultString.length(); i++ ){
            sb.append( resultString.charAt(i) );
         }
         return new BrobInt( resultSB.append( '-' + sb.toString().replaceFirst("^0+(?!$)", "") ).toString() );
      }
      return resultBrobInt;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to compare a BrobInt passed as argument to this BrobInt
   *  @param  gint  BrobInt to add to this
   *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
   *  NOTE: this method performs a lexicographical comparison using the java String "compareTo()" method
   *        THAT was easy.....
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int compareTo( BrobInt gint ) {
      return (internalValue.compareTo( gint.toString() ));
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to check if a BrobInt passed as argument is equal to this BrobInt
   *  @param  gint     BrobInt to compare to this
   *  @return boolean  that is true if they are equal and false otherwise
   *  NOTE: this method performs a similar lexicographical comparison as the "compareTo()" method above
   *        also using the java String "equals()" method -- THAT was easy, too..........
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean equals( BrobInt gint ) {
      return (internalValue.equals( gint.toString() ));
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to return a BrobInt given a long value passed as argument
   *  @param  value         long type number to make into a BrobInt
   *  @return BrobInt  which is the BrobInt representation of the long
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static BrobInt valueOf( long value ) throws NumberFormatException {
      BrobInt gi = null;
      try {
         gi = new BrobInt( new Long( value ).toString() );
      }
      catch( NumberFormatException nfe ) {
         System.out.println( "\n  Sorry, the value must be numeric of type long." );
      }
      return gi;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to return a String representation of this BrobInt
   *  @return String  which is the String representation of this BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public String toString() {
      StringBuilder sb2 = new StringBuilder();
      String intVersionOutput = "";
      for( int i = 0; i < intVersion.length; i++ ) {
         intVersionOutput = intVersionOutput.concat( Integer.toString( intVersion[i] ) );
      }
      intVersionOutput = new String( new StringBuffer( intVersionOutput ).reverse() );
      if ( sign == 1 ) {
         sb2.append('-' + internalValue);
         return sb2.toString();
      } else {
         return internalValue;
      }
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to display an Array representation of this BrobInt as its bytes
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public void toArray( int[] d ) {
      System.out.println( Arrays.toString( d ) );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  the main method redirects the user to the test class
   *  @param  args  String array which contains command line arguments
   *  note:  we don't really care about these
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static void main( String[] args ) {
      System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );
      System.out.println( "\n   You should run your tests from the BrobIntTester...\n" );
      // BrobInt g1 = new BrobInt("12345");
      // BrobInt g2 = new BrobInt("100000000");
      BrobInt g1 = new BrobInt("144127909719710664015092431502440849849506284148982076191826176553");
      BrobInt g2 = new BrobInt("1441279097197106640150924315024408498495062841089820783943215635");
      BrobInt g3 = new BrobInt("0");
      BrobInt g4 = new BrobInt("4");
      System.out.println( g1.toString() );
      // System.out.println( g2.toString() );
      System.out.println( "             " + g2.toString() );
      System.out.println( "g1  +  g2 = " + g1.addInt(g2) );
      // System.out.println( "expecting = 100012345" );
      // System.out.println( "g2  +  g1 = " + g2.addInt(g1) );
      System.out.println( "expecting = 145569188816907770655243355817465258348001346990071896975769392188" );
      // System.out.println(" ");
      // System.out.println( "g1  -  g2 = " + g1.subtractByte(g2) );
      // System.out.println( "g2  -  g1 = " + g2.subtractByte(g1) );
      // System.out.println( "expecting = 144127909719696251224120460436039340606356040063997125563415278346" );
      // System.out.println( " " );
      // System.out.println( "g1  /  g2 = " + g1.divide(g2) );
      // System.out.println( "expecting = 10000000000000" );
      // System.out.println( "g3 - g4 = " + g3.subtractByte(g4) );
      // System.out.println( "g4 - g3 = " + g4.subtractByte(g3) );
      System.exit( 0 );
   }
}
