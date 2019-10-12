package test;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class test_samples {
    private static Runtime RT = Runtime.getRuntime();
    private static String JOTT_EXAMPLE_DIR = "./jottExamples/";
    private static String CURRENT = ".";
    private static String OUT = "./compiled/";

    /**
     * Concat two File[], used by walk
     *
     * @param first  first File[]
     * @param second second File[]
     * @return  File[] containing elements of both first and second
     */
    private static File[] concat(File[] first, File[] second) {
        File[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * Recursively explore a director for files
     * @param dir the directory to explore
     * @return  File[] containing all files (no directories)
     *          in dir and subsequent subdirs
     */
    private static File[] walk(File dir) {
        File[] result = new File[0];
        if (dir.isFile()) result = new File[] {dir};
        else {
            File[] contents = dir.listFiles();
            if (contents != null)
            for (File f : contents) {
                result = concat(walk(f), result);
            }
        }
        return result;
    }

    /**
     * Compile all .java files from in and subdirectories.
     * If out directory is specified, the .class files will be stored there.
     *
     * @param in directory to traverse for .java files
     * @param out directory to store .class files after compilation, may be null
     * @return whether or not the compilation was successful
     */
    private static boolean compile(String in, String out) {
        String command = "javac" + (out != null ? " -d " + out : "");
        for (File f : walk(new File(in))) {
            String filename = f.toString();
            if (filename.endsWith(".java")) command += " " + filename;
        }
        try {
            Process proc = RT.exec(command);
            System.out.println("Compiling...");
            proc.waitFor();
            System.out.println("Done");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Delete a directory
     * @param dir the directory to delete
     */
    private static void deleteDir(File dir) {
        File[] contents = dir.listFiles();
        if (contents != null)
        for (File f : contents) {
            deleteDir(f);
        }
        dir.delete();
    }

    /**
     * Clean the directory which will store .class files for compilation.
     * If the directory does not exist, create the empty directory.
     * @param out the directory to clean/make
     */
    private static void clean(String out) {
        File file = new File(out);
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print(file.getCanonicalPath() + " will be deleted. Are you sure? (Y/n) ");
            if (!"Y".equals(sc.next())) {
                sc.close();
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("IOException");
            sc.close();
            System.exit(-1);
        }

        File[] contents = file.listFiles();
        if (contents == null) {
            file.mkdir();
            return;
        }
        for (File f : contents) {
            deleteDir(f);
        }
    }

    /**
     * Test all .j files in a specified directory and subdirectories
     * @param dir       the directory to traverse for .j files
     * @param classPath the location of the main class file
     */
    private static void test(String dir, String classPath) {
        File[] contents = walk(new File(dir));
        for (File f : contents) {
            if (!f.toString().endsWith(".j")) continue;
            System.out.println("\n\n\t\t\t====<" + f.getName() + ">====");
            String command = "java -cp " + classPath + " Jott " + f.toString();
            try {
                Process proc = RT.exec(command);
                BufferedReader stdin = new BufferedReader(new
                        InputStreamReader(proc.getInputStream()));
                BufferedReader stderr = new BufferedReader(new
                        InputStreamReader(proc.getErrorStream()));

                String s = null;
                while ((s = stdin.readLine()) != null) {
                    System.out.println(s);
                }
                while ((s = stderr.readLine()) != null) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //File curr = new File(CURRENT);
        //System.out.println(curr.getCanonicalPath());
        //System.exit(0);
        clean(OUT);
        if (compile(CURRENT, OUT)) {
            test(JOTT_EXAMPLE_DIR, OUT);
        } else {
            System.out.println("Compile failed");
        }
    }
}
