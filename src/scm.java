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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;

public class scm {

    public static void main(String[] args) throws IOException
    {
    	
        File sourceFolder = new File("C:\\Users\\wills\\Desktop\\test_source");

        File destinationFolder = new File("\\Users\\wills\\Desktop\\test_destination" + sourceFolder.getName());

        createRepo(sourceFolder, destinationFolder);
    }

    private static void createRepo(File sourceFolder, File destinationFolder) throws IOException
    {
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
            File leafDirectory = new File(destinationFolder.toString() + "\\" + sourceFolder.getName());
            // Copies file into directory with its name
            Files.copy(sourceFolder.toPath(), leafDirectory.toPath());
        }
    }
    public String checksum(File file) throws Exception{
    	MessageDigest md = MessageDigest.getInstance("SHA1");
    	FileInputStream fis = new FileInputStream(file.getName());
    	
        byte[] byteArray = new byte[1024];
        int byteCount = 0;
    	
      //Read file data and update in message digest
        while ((byteCount = fis.read(byteArray)) != -1) {
            md.update(byteArray, 0, byteCount);
        };
        
        fis.close();
        
        //Get the hash's bytes
        byte[] bytes = md.digest();
         
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        //return complete hash
       return sb.toString();
        
    }

}
