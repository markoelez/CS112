package bigint;

import java.util.concurrent.ThreadLocalRandom;

public class Driver {

    public static void main(String args[]) throws Exception {
//        test();
        multTest();
        addTest();
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("\t\t\t\t\t\tALL TESTS PASSED");
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
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

    private static void test() throws Exception {
        String t1 = "95465";
        String t2 = "20802";

        try {
            BigInteger one = BigInteger.parse(t1);
            BigInteger two = BigInteger.parse(t2);

            System.out.println(t1);
            System.out.println(t2);

            BigInteger ans = BigInteger.multiply(one, two);

            System.out.println("----------------------------------------------------------------------------");
            traverseNodes(ans.front);
            System.out.println("----------------------------------------------------------------------------");
            System.out.println(ans.numDigits);
            System.out.println("----------------------------------------------------------------------------");
            System.out.println(ans.negative);
            System.out.println("----------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(t1);
            System.out.println(t2);
            throw new Exception();
        }

    }

    private static void multTest() throws Exception{
        int i = 0;
        while (i < 100000) {
            String t1 = Integer.toString(ThreadLocalRandom.current().nextInt(0, 10000));
            String t2 = Integer.toString(ThreadLocalRandom.current().nextInt(0, 10000));

            try {
                BigInteger one = BigInteger.parse(t1);
                BigInteger two = BigInteger.parse(t2);

                System.out.println(t1);
                System.out.println(t2);

                BigInteger ans = BigInteger.multiply(one, two);

                int test = Integer.parseInt(ans.toString());
                int actual = Integer.parseInt(t1) * Integer.parseInt(t2);

                int len1 = ans.numDigits;
                int len2 = String.valueOf(actual).length();

                if (test != actual) {
                    System.out.println("Test: " + test + " :::: " + "Actual: " + actual);
                    throw new Exception("Failed answer!");
                } else if (len1 != len2){
                    System.out.println("Test: " + test + " :::: " + "Actual: " + actual);
                    System.out.println("Test Digits: " + len1 + " :::: " + "Actual Digits: " + len2);
                    throw new Exception("Failed Num Digits");
                } else {
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("Passed!");
                    System.out.println("----------------------------------------------------------------------------");
                }
            } catch (Exception e) {
                System.out.println(t1);
                System.out.println(t2);
                throw new Exception();
            }

            i++;
        }
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("Multiply Test Successful!");
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    }

    private static void addTest() throws Exception{
        int i = 0;
        while (i < 100000) {
            String t1 = Integer.toString(ThreadLocalRandom.current().nextInt(0, 100000));
            String t2 = Integer.toString(ThreadLocalRandom.current().nextInt(0, 100000));

            try {
                BigInteger one = BigInteger.parse(t1);
                BigInteger two = BigInteger.parse(t2);

                System.out.println(t1);
                System.out.println(t2);

                BigInteger ans = BigInteger.add(one, two);

                int test = Integer.parseInt(ans.toString());
                int actual = Integer.parseInt(t1) + Integer.parseInt(t2);

                int len1 = ans.numDigits;
                int len2 = String.valueOf(actual).length();

                if (test != actual) {
                    System.out.println("Test: " + test + " :::: " + "Actual: " + actual);
                    throw new Exception("Failed answer!");
                } else if (len1 != len2){
                    System.out.println("Test Digits: " + len1 + " :::: " + "Actual Digits: " + len2);
                    throw new Exception("Failed Num Digits");
                } else {
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("Passed!");
                    System.out.println("----------------------------------------------------------------------------");
                }
            } catch (Exception e) {
                System.out.println(t1);
                System.out.println(t2);
                throw new Exception();
            }

            i++;
        }
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("Add Test Successful!");
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    }
}
