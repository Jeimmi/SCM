/**
 * Created by LeonardoDaVinci on 2/21/2017.
 *
 * Author: Govinda Escobar, Jeimmi Gomez, and William Lee
 *
 * This program copies the following to the target directory: the source directory (including the root),
 * all of its directories, & all of its files. For files, it creates a new directory with the file name &
 * stores the file in that directory
 *
 *  Example user input for createRepo and checkIn:
 *   source = C:\Users\LeonardoDaVinci\Desktop\test_source
 *   target = C:\Users\LeonardoDaVinci\Desktop\target
 *
 * Example user input for checkOut:
 *   source = C:\Users\LeonardoDaVinci\Desktop\target\test_source
 *   target = C:\Users\LeonardoDaVinci\Desktop\test_source
 *   timeStamp = 20170508_1110
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.io.File;


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
     * file name stores the file in that directory.\
     * @param sourceDirectory
     * @param targetDirectory
     * @throws IOException
     */
    private void checkIn(File sourceDirectory, File targetDirectory) throws IOException {
        createRepo(sourceDirectory, targetDirectory);
    }

    private void createRepo(File sourceDirectory, File targetDirectory) throws IOException {
        targetDirectory.mkdir();
        if (sourceDirectory.isDirectory()) {
            // Returns an array of strings naming the files and directories in sourceDirectory
            String files[] = sourceDirectory.list();

            // Copies files & directories to targetDirectory
            for (String file : files)
            {
                    // Creates a new File instance from a parent abstract pathname and a child pathname string.
                    File targetTemp = new File(targetDirectory, file);
                    File sourceTemp = new File(sourceDirectory, file);
                    createRepo(sourceTemp, targetTemp);
                    this.sourceFiles.add(targetTemp);
            }
        }
        else {
            String tFiles[] = targetDirectory.list();
            File csSource = new File(checkSum(sourceDirectory));
            File leafDirectory = new File(targetDirectory.toString(), csSource.getName());
            int duplicateIndex = -1;
            if(tFiles != null) Arrays.sort(tFiles);
            if(tFiles != null) duplicateIndex = Arrays.binarySearch(tFiles, csSource.getName());
            if (duplicateIndex < 0) {
                Files.copy(sourceDirectory.toPath(), leafDirectory.toPath());
            }
            this.sourceFiles.add(leafDirectory);
        }
    }


    private void checkOut(File repoName, File projectName, String timeStamp) throws IOException {
        File manFile = new File(repoName.getPath(), "Activity");


        this.sourceFiles.add(manFile);
        this.sourceFiles.add(projectName);

        File manFiles[] = manFile.listFiles();
        for (File file : manFiles) {
            if (file.getName().matches("(.*)" + timeStamp + "(.*)")) {
                manFile = file;
                break;
            }
        }

        try {

            List<String> manLines = Files.readAllLines(manFile.toPath(), StandardCharsets.UTF_8);
            String manLine = manLines.get(2);
            manLine = manLine.substring(manLine.lastIndexOf(" ") + 1);
            File rFile = new File(manLine);
            manLine = rFile.getParent();
            int repoFileLength = manLine.length();
            for (int i = 3, len = manLines.size(); i < len; i++) {
                manLine = manLines.get(i);
                manLine = manLine.substring(manLine.lastIndexOf(" ") + 1);
                rFile = new File(manLine);
                this.sourceFiles.add(rFile);
                manLine = rFile.getParent();
                if (manLine != null) {
                    manLine = manLine.substring(repoFileLength + 1);
                    File target = new File(projectName.getParent(), manLine);
                    target.mkdirs();
                    Files.copy(rFile.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        catch(NullPointerException e)
        {
            System.out.print("NullPointerException Caught");
        }
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        scm s = new scm();
        System.out.println("Please select an option: 1. CreateRepo, 2. CheckIn, 3. CheckOut");
        int selection = Integer.parseInt(scanner.nextLine());
        //Get input source and target path from user

        System.out.println("Please enter the path of the source directory");
        String sourceStr = scanner.nextLine();
        System.out.println("Please enter the path of the target directory");
        String targetStr = scanner.nextLine();

        if (selection == 1 || selection == 2) {
            File sourceDirectory = new File(sourceStr);
            File targetDirectory = new File(targetStr, sourceDirectory.getName());
            s.sourceFiles.add(sourceDirectory);
            s.sourceFiles.add(targetDirectory);
            if (selection == 1) {
                s.createRepo(sourceDirectory, targetDirectory);
                Manifest manifestCreate = new Manifest(s.sourceFiles,"createRepo");
            }
            else {
                s.checkIn(sourceDirectory, targetDirectory);
                Manifest manifestIn = new Manifest(s.sourceFiles,"checkIn");
            }
        }
        else {
            File targetDirectory = new File(targetStr);
            File sourceDirectory = new File(sourceStr);
            System.out.println("Please enter the time stamp (YYYYMMDD_HHMM)");
            String timeStamp = scanner.nextLine();
            s.checkOut(sourceDirectory, targetDirectory, timeStamp);
            Manifest manifestOut = new Manifest(s.sourceFiles,"checkOut");
        }
    }
}
