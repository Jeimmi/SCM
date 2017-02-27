/**
 * Created by LeonardoDaVinci on 2/21/2017.
 *
 * Author: Govinda Escobar
 *
 * This program copies the following to the target directory: the source directory (including the root),
 * all of its directories, & all of its files. For files, it creates a new directory with the file name &
 * stores the file in that directory
 *
 * Note: This works for at most one file per directory. Must implement checksum to handle multiple files.
 *
 */

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.Scanner;
import java.nio.file.Paths;

public class scm {
	
	public void userCommands(File manifest){
		System.out.println("1. Create new repo");
    	Scanner s = new Scanner(System. in);
    	int input = s.nextInt();
		int userIn = input;
		switch(userIn){
		case 1:
			break;
		}
	}
	
	/**
	 * CheckSum method to create the checksum given any file
	 * Algorithm used is prime weights multiplying by ASCII value and summing total
	 * @param file - Any file to find checksum of
	 * @return The check sum value and string of file name
	 * @throws IOException
	 */
	public String checkSum(File file) throws IOException{
        String temp = new String(Files.readAllBytes(Paths.get(file.getPath())));
        int tempLength = temp.length();
        int checkSumTotal = 0;
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
	 * Recursive function to loop through tree of directory. 
	 * @param sourceDirectory
	 * @param destinationDirectory
	 * @param manifestObject
	 * @throws IOException
	 */
    private void createRepo(File sourceDirectory, File destinationDirectory, Manifest manifestObject) throws IOException {
        destinationDirectory.mkdir();
        if (sourceDirectory.isDirectory()) { //When it is a folder
            // Returns an array of strings naming the files and directories in sourceDirectory
            String files[] = sourceDirectory.list();

            // Copies files & directories to destinationDirectory
            for (String file : files)
            {
                // Creates a new File instance from a parent abstract pathname and a child pathname string.
                File sourceTemp = new File(sourceDirectory, file);
                File destinationTemp = new File(destinationDirectory, file);
                createRepo(sourceTemp, destinationTemp, manifestObject);
            }
        }
        else { //When it is a file
            // Creates directory with file name
            File leafDirectory = new File(destinationDirectory.toString(), checkSum(sourceDirectory));
            // Copies file into directory with its name
            Files.copy(sourceDirectory.toPath(), leafDirectory.toPath());
            
            /*
             * Code to pass file name, artifact file name, and original path back to manifest
             */
            String sourceFileName = sourceDirectory.getName();
            String artifactFileName = leafDirectory.getName();
            String sourcePath = sourceDirectory.getPath();
            
            manifestObject.addFileNames(sourceFileName, artifactFileName, sourcePath);
        }
    }
	
    /**
     * Write to a text file
     * @param file - The file to write to
     * @param content - The string to write to given file
     * @throws IOException
     */
	public static void writeToFile(File file, String content) throws IOException
	{
	    StringBuilder newFile = new StringBuilder();
	    String edited = content;
	    newFile.append(edited);
	    FileWriter fstreamWrite = new FileWriter(file,true);
	    BufferedWriter out = new BufferedWriter(fstreamWrite);
	    out.write(newFile.toString());
	    out.newLine();
	    out.close();
	}
    

    public static void main(String[] args) throws IOException
    {
    	//Get input source and target path from user
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input path for Windows using \\ and Apple using /");
		System.out.println("windows ex: C:\\Users\\wills\\Desktop");
		System.out.println("Apple ex: /Users/wills/Desktop");
		System.out.println("Enter Path for Source Folder");
		String sourcePath = scanner.nextLine();
		System.out.println("Enter Path for Target Repo Folder");
		String targetPath = scanner.nextLine();   	
    	scm s = new scm();   	
    	
    	//sourcePath = "C:\\Users\\wills\\Desktop\\test_source";
   
        File sourceFolder = new File(sourcePath);

        //targetPath = "C:\\Users\\wills\\Desktop\\test_destination" + sourceFolder.getName();
        
        File destinationFolder = new File(targetPath + sourceFolder.getName());
        
        
        Manifest manifestObject = new Manifest(sourceFolder.getPath(), destinationFolder.getPath(),"createRepo");

        s.createRepo(sourceFolder, destinationFolder,manifestObject);
        
        //Creating archived folder in the repository
        File archiveFolder = new File(destinationFolder.getPath(), "Archive");
        archiveFolder.mkdir();
        
        //Put manifest file into archive folder
        File manifestTextFile = new File (archiveFolder, manifestObject.getmManifestTitle() + ".txt");
        
        manifestObject.userCommands(manifestTextFile, manifestObject.getmUserCommand());
        writeToFile(manifestTextFile, "createRepo");
        
        
        for(int i = 0; i < manifestObject.mArtifactFileNames.size() - 1; i++) {
	    	String sourceFileName = manifestObject.mSourceFileNames.get(i);
	    	String artifactFileName = manifestObject.mArtifactFileNames.get(i);
	    	String sourcePathName = manifestObject.mSourcePaths.get(i);

	    	writeToFile(manifestTextFile, sourceFileName + ", " + artifactFileName + ", " + sourcePathName);

	    	//mani.writeToFile(manifest, artifactFileName);
	    	//mani.writeToFile(manifest, sourceFileName);

	    	System.out.println(sourceFileName + ", " + artifactFileName + ", " + sourcePathName);
	    }
    	
    }


}
