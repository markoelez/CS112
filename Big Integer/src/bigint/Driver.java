package bigint;

import java.util.concurrent.ThreadLocalRandom;

public class Driver {

    public static void main(String args[]) throws Exception {

        int i = 0;
        while (i < 100000) {
            String t1 = Integer.toString(ThreadLocalRandom.current().nextInt(-10000, 10000));
            String t2 = Integer.toString(ThreadLocalRandom.current().nextInt(-10000, 10000));

            try {
                BigInteger one = BigInteger.parse(t1);
                BigInteger two = BigInteger.parse(t2);

                System.out.println(t1);
                System.out.println(t2);

                int test = Integer.parseInt(BigInteger.add(one, two).toString());
                int actual = Integer.parseInt(t1) + Integer.parseInt(t2);

                if (test != actual) {
                    System.out.println("Test: " + test + " :::: " + "Actual: " + actual);
                    throw new Exception("Failed");
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
        System.out.println("Success!");
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");


//        -2244
//        2186

    }
}