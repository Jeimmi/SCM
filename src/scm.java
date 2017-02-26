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
	
	public String checkSum(File file) throws IOException{
        String temp = new String(Files.readAllBytes(Paths.get(file.getPath())));
        int tempLength = temp.length();
        int checkSumTotal = 0;
        int[] checkSumWeights = new int[] {1, 3, 11, 17};
        for (int i = 0; i < tempLength; i++){
            checkSumTotal +=  checkSumWeights[i % 4]* (int)temp.charAt(i);
        }
        temp = file.getName();
        return Integer.toString(checkSumTotal) + "." + Long.toString(file.length()) + temp.substring(temp.lastIndexOf("."));
    }
	
    private void createRepo(File sourceDirectory, File destinationDirectory, Manifest manifestObject) throws IOException {
        destinationDirectory.mkdir();
        if (sourceDirectory.isDirectory()) {
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
        else {
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
	

    public static void main(String[] args) throws IOException
    {
    	scm s = new scm();
        File sourceFolder = new File("/Users/Jeimmi/Desktop/test_source/");

        File destinationFolder = new File("/Users/Jeimmi/Desktop/test_destination" + sourceFolder.getName());

        File manifest = new File ("/Users/Jeimmi/Desktop/manifest.txt");
        
        Manifest mani = new Manifest("/Users/Jeimmi/Desktop/test_source/","/Users/Jeimmi/Desktop/test_destination","createRepo");

        s.createRepo(sourceFolder, destinationFolder,mani);
        
        //Creating archived
        File archiveFolder = new File(destinationFolder.getPath() + "/Archive");
        archiveFolder.mkdir();
        
        mani.userCommands(manifest, mani.getmUserCommand());
        
        for(int i = 0; i < mani.mArtifactFileNames.size() - 1; i++) {
	    	String sourceFileName = mani.mSourceFileNames.get(i);
	    	String artifactFileName = mani.mArtifactFileNames.get(i);
	    	String sourcePath = mani.mSourcePaths.get(i);

	    	mani.writeToFile(manifest, sourceFileName + ", " + artifactFileName + ", " + sourcePath+"\n");

	    	//mani.writeToFile(manifest, artifactFileName);
	    	//mani.writeToFile(manifest, sourceFileName);

	    	System.out.println(sourceFileName + ", " + artifactFileName + ", " + sourcePath);
	    }
    	
    }


}
