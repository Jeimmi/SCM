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
import java.nio.file.Paths;

public class scm {
	
	
	
	public String CheckSum(File file) throws IOException{
		String content;
		String filesize = Long.toString(file.length());
		
		content = new String(Files.readAllBytes(Paths.get(file.getPath())));
		String s = content;
		int checkSumLength = 4;
		int checkSumTotal = 0;
		int[] checkSumWeights = new int[checkSumLength];
		checkSumWeights[0] = 1;
		checkSumWeights[1] = 3;
		checkSumWeights[2] = 11;
		checkSumWeights[3] = 17;

		for (int i = 0; i < s.length(); i++){
		    char c = s.charAt(i);
		    int asciiCharacter = (int)c;
		   
		    int index = i % checkSumLength;
		    
		    int checkSumValue = checkSumWeights[index]* asciiCharacter;
		    checkSumTotal += checkSumValue;
		   
		}
		String artifactName = Integer.toString(checkSumTotal)+"."+filesize;
		 //System.out.println("ChecksumTotal: "+checkSumTotal);
		 //System.out.println("FileSize: "+filesize);
		 //System.out.println("ArtifactName: "+artifactName);
		 return artifactName;
	}
	
    private void createRepo(File sourceFolder, File destinationFolder) throws IOException
    {
    	String artifactName = CheckSum(sourceFolder)+".txt";
    	
        destinationFolder.mkdir();
        if (sourceFolder.isDirectory())
        {
            // Returns an array of strings naming the files and directories in sourceFolder
            String files[] = sourceFolder.list();

            // Copies files & directories to destinationFolder
            for (String file : files)
            {
                // Creates a new File instance from a parent abstract pathname and a child pathname string.
                File sourceTemp = new File(sourceFolder, file);
                File destinationTemp = new File(destinationFolder, file);
                
                createRepo(sourceTemp, destinationTemp);
            }
        }
        else
        {
            // Creates directory with file name
            File leafDirectory = new File(destinationFolder.toString() + "/" + sourceFolder.getName());
            
            // Copies file into directory with its name
            Files.copy(sourceFolder.toPath(), leafDirectory.toPath());
            
            //Copies the file and renames it 
            File newFileName = new File(destinationFolder.toString() + "/" +artifactName );
            Files.copy(leafDirectory.toPath(),newFileName.toPath());
            
            
        }
    }
	

    public static void main(String[] args) throws IOException
    {
    	scm s = new scm();
        File sourceFolder = new File("/Users/Jeimmi/Desktop/test_source/h.txt");

        File destinationFolder = new File("/Users/Jeimmi/Desktop/test_destination" + sourceFolder.getName());

        s.createRepo(sourceFolder, destinationFolder);
    	s.CheckSum(sourceFolder);
    }


}
