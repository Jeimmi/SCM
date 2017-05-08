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
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

    private void checkOut(File manifest, File targetDirectory) throws IOException {
        List<String> manLines = Files.readAllLines(manifest.toPath(), StandardCharsets.UTF_8);
        String file = manLines.get(2);
        file = file.substring(file.lastIndexOf(" ") + 1);
        File rFile = new File(file);
        file = rFile.getParent();
        int repoFileLength = file.length();
        for (int i = 3, len = manLines.size(); i < len; i++) {
            file = manLines.get(i);
            file = file.substring(file.lastIndexOf(" ") + 1);
            rFile = new File(file);
            this.sourceFiles.add(rFile);
            file = rFile.getParent();
            if (file != null) {
                file = file.substring(repoFileLength + 1);
                File target = new File(targetDirectory, file);
                target.mkdirs();
                Files.copy(rFile.toPath(),target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static void main(String[] args) throws IOException {


/*
Scanner scanner = new Scanner(System.in);

        //Get input source and target path from user
        System.out.println("Enter Path for Source Folder");
        File sourceDirectory = new File(scanner.nextLine());
        System.out.println("Enter Path for Target Repo Folder");
        File targetDirectory = new File(scanner.nextLine());
 */
        Scanner scanner = new Scanner(System.in);

        scm s = new scm();

/*
        File sourceDirectory = new File("C:\\Users\\LeonardoDaVinci\\Desktop\\test_source");
        File targetDirectory = new File("C:\\Users\\LeonardoDaVinci\\Desktop\\target", sourceDirectory.getName());
        s.sourceFiles.add(sourceDirectory);
        s.sourceFiles.add(targetDirectory);
        s.createRepo(sourceDirectory, targetDirectory);
        Manifest manifestCreate = new Manifest(s.sourceFiles,"createRepo");


C:\Users\LeonardoDaVinci\Desktop\target\test_source\Activity\543-p1_GEL_20170507_1632_createRepo.txt
C:\Users\LeonardoDaVinci\Desktop\checkOut
*/


        System.out.println("Enter Path for Source Folder");
        File manFile = new File(scanner.nextLine());
        System.out.println("Enter Path for Target Repo Folder");
        File target = new File(scanner.nextLine());
 //       File target = new File("C:\\Users\\LeonardoDaVinci\\Desktop\\checkOut");
   //     File manFile = new File("C:\\Users\\LeonardoDaVinci\\Desktop\\target\\test_source\\Activity\\543-p1_GEL_20170507_0305_createRepo.txt");
        s.sourceFiles.add(manFile);
        s.sourceFiles.add(target);
        s.checkOut(manFile, target);
        Manifest manifestOut = new Manifest(s.sourceFiles,"checkOut");


        /*
        Scanner scanner = new Scanner(System.in);
        scm s = new scm();
        System.out.println("Please select an option: 1. CreateRepo, 2. CheckIn, 3. CheckOut");
        int selection = scanner.nextInt();
        //Get input source and target path from user
        System.out.println("Enter Path for Source Folder");
        File sourceDirectory = new File(scanner.nextLine());
        System.out.println("Enter Path for Target Repo Folder");
        File targetDirectory = new File(scanner.nextLine(), sourceDirectory.getName());
        s.sourceFiles.add(sourceDirectory);
        s.sourceFiles.add(targetDirectory);
        switch(selection) {
            case 1:
                s.createRepo(sourceDirectory, targetDirectory);
                Manifest manifestCreate = new Manifest(s.sourceFiles,"createRepo");
                break;
            case 2:
                s.checkIn(sourceDirectory, targetDirectory);
                Manifest manifestIn = new Manifest(s.sourceFiles,"checkIn");
                break;
            case 3:
                s.checkOut(sourceDirectory, targetDirectory);
     //           Manifest manifestOut = new Manifest(s.sourceFiles,"checkOut");
                break;
        }
*/
    }
}
