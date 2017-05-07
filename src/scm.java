/**
 * Created by LeonardoDaVinci on 2/21/2017.
 *
 * Author: Govinda Escobar, Jeimmi Gomez, and William Lee
 *
 * This program copies the following to the target directory: the source directory (including the root),
 * all of its directories, & all of its files. For files, it creates a new directory with the file name &
 * stores the file in that directory
 *
 */
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.*;

public class scm {
    //It contains stores the source and target directories and the files in the source directory (in that order)
    public List<File> sourceFiles = new ArrayList();

    /**
     * CheckSum method to create the checksum given any file
     * Algorithm used is prime weights multiplying by ASCII value and summing total
     * @param file - Any file to find checksum of
     * @return The check sum value and string of file name
     * @throws IOException
     */

    public String checkSum(File file) throws IOException{
        int checkSumTotal = 0;
        String temp = new String(Files.readAllBytes(Paths.get(file.getPath())));
        int tempLength = temp.length();
        int[] checkSumWeights = new int[] {1, 3, 11, 17}; //The prime weights for the checksum

        //Loop through every character in text file
        for (int i = 0; i < tempLength; i++){
            //Cast each character into an int for the value. Mod the interator by 4 to figure out the prime weight
            checkSumTotal +=  checkSumWeights[i % 4]* (int)temp.charAt(i);
        }
        temp = file.getName(); //Get the file name
        return Integer.toString(checkSumTotal) + "." + Long.toString(file.length()) + temp.substring(temp.lastIndexOf("."));
    }

    /**
     * Copies the source directory to the target directory. For files, it creates a new directory with the
     * file name stores the file in that directory.
     * @param sourceDirectory
     * @param targetDirectory
     * @throws IOException
     */
    private void createRepo(File sourceDirectory, File targetDirectory) throws IOException {
        targetDirectory.mkdir();
        if (sourceDirectory.isDirectory()) {
            // Returns an array of strings naming the files and directories in sourceDirectory
            String files[] = sourceDirectory.list();

            // Copies files & directories to targetDirectory
            for (String file : files)
            {
                // Creates a new File instance from a parent abstract pathname and a child pathname string.
                File sourceTemp = new File(sourceDirectory, file);
                File targetTemp = new File(targetDirectory, file);
                createRepo(sourceTemp, targetTemp);
            }
        }
        else {
            // Creates directory with file name
            File leafDirectory = new File(targetDirectory.toString(), checkSum(sourceDirectory));
            // Copies file into directory with its name
            Files.copy(sourceDirectory.toPath(), leafDirectory.toPath());

            /*
             * Code to pass file name, artifact file name, and original path back to manifest
             */
            this.sourceFiles.add(leafDirectory);
        }
    }


    public static void main(String[] args) throws IOException
    {
        //Get input source and target path from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Path for Source Folder");
        File sourceDirectory = new File(scanner.nextLine());
        System.out.println("Enter Path for Target Repo Folder");
        File targetDirectory = new File(scanner.nextLine(), sourceDirectory.getName());

        scm s = new scm();
        s.sourceFiles.add(sourceDirectory);
        s.sourceFiles.add(targetDirectory);

        s.createRepo(sourceDirectory, targetDirectory);

        Manifest manifestObject = new Manifest(s.sourceFiles,"createRepo");

    }