import java.util.Random;
public class MyClass {
    public static void main(String args[]) {
        
        final String nextLine = "bctf{abcdefghijklmnop}";
        if (nextLine.length() != 22) {
            System.out.println("Not the flag :(");
            return;
        }
        char[] array2 = new char[nextLine.length()];
        for (int i = 0; i < nextLine.length(); ++i) {
            array2[i] = nextLine.charAt(i);
        }
        // STEP 1
        for (int j = 0; j < nextLine.length() / 2; ++j) {
            final char c = array2[nextLine.length() - j - 1];
            array2[nextLine.length() - j - 1] = array2[j];
            array2[j] = c;
        }        
        // STEP 1
        
        //PRINTING
        System.out.println("STEP 1 ===============================");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print(array2[i]);
        }
        System.out.println();
        //PRINTING
        
        // STEP 2
        int[] array3 = { 19, 17, 15, 6, 9, 4, 18, 8, 16, 13, 21, 11, 7, 0, 12, 3, 5, 2, 20, 14, 10, 1 };
        int[] array4 = new int[array2.length];
        for (int k = array3.length - 1; k >= 0; --k) {
            array4[k] = array2[array3[k]];
        }
        // STEP 2
        
        //PRINTING
        System.out.println("STEP 2 ===============================");
        System.out.print("A2:  ");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print((int)(array2[i]) + ".");
        }
        System.out.println();
        System.out.print("A4:  ");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print(array4[i] + ".");
        }
        System.out.println();
        //PRINTING
        
        // STEP 3
        final Random random = new Random();
        random.setSeed(431289L);
        final int[] array5 = new int[nextLine.length()];
        for (int l = 0; l < nextLine.length(); ++l) {
            array5[l] = (array4[l] ^ random.nextInt(l + 1));
        }
        // STEP 3
        
        //PRINTING
        System.out.println("STEP 3 ===============================");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print(array5[i] + ".");
        }
        System.out.println();
        //PRINTING
        
        
        
        
        Object s = "";
        for (int i = 0; i < array5.length; ++i) {
            s = (String)s + array5[i] + ".";
        }
        System.out.println("\nYOUR FLAG: " + (String)s);
        
        System.out.println("\nTRUE FLAG: 116.122.54.50.93.66.98.117.75.51.97.78.104.119.90.53.94.36.105.84.40.69.");
        if (((String)s).equals("116.122.54.50.93.66.98.117.75.51.97.78.104.119.90.53.94.36.105.84.40.69.")) {
            System.out.println("Congrats! You got the flag!");
        } else {
            System.out.println("Not the flag :(");
        }
        
        // REV STEP 3
        final Random revrandom = new Random();
        revrandom.setSeed(431289L);
        array4 = new int[nextLine.length()];
        for (int l = 0; l < nextLine.length(); ++l) {
            array4[l] = (array5[l] ^ revrandom.nextInt(l + 1));
        }
        // REV STEP 3
        
        //PRINTING
        System.out.println("REVSTEP 3 ===============================");
        System.out.print("A5:  ");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print(array5[i] + ".");
        }
        System.out.println();
        System.out.print("A4:  ");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print(array4[i] + ".");
        }
        System.out.println();
        //PRINTING


        
        // REV STEP 2
        array2 = new char[array2.length];
        for (int k = array3.length - 1; k >= 0; --k) {
            array2[array3[k]] = (char)array4[k];
        }
        // REV STEP 2
        
        //PRINTING
        System.out.println("REVSTEP 2 ===============================");
        System.out.print("A4:  ");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print(array4[i] + ".");
        }
        System.out.println();
        System.out.print("A2:  ");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print((int)(array2[i]) + ".");
        }
        System.out.println();
        //PRINTING
        
        // REV STEP 1
        for (int j = 0; j < nextLine.length() / 2; ++j) {
            final char c = array2[nextLine.length() - j - 1];
            array2[nextLine.length() - j - 1] = array2[j];
            array2[j] = c;
        }        
        // REV STEP 1
        
        //PRINTING
        System.out.println("REVSTEP 1 ===============================");
        for (int i = 0; i < nextLine.length(); ++i) {
            System.out.print(array2[i]);
        }
        System.out.println();
        //PRINTING
        
        //Random random = new Random();
        //random.setSeed(431289L);
        //for (int  i = 0 ; i<=22; i++) {
        //    System.out.println(random.nextInt(i+1));
        //}

        
    }
}