/**
 * Created by Jeimmi Gomez and William Lee
 *
 * Author: Jeimmi Gomez and William Lee
 *
 * The Manifest class is taking care of collecting information
 * to generate the manifest file
 *
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Manifest {

    private String mManifestTitle; //Title of namifest. --> Manifest_Date_Time
    private String mUserCommand; //Is createRepo for now
    private File mSource; //The Source path of folder to copy.
    private File mTarget; //The path of the repo

    /**
     * Manifest constructor
     * Create a manifest object
     * @param sourceFiles It contains stores the source and target directories and the files in the source directory (in that order)
     * @param userCom The command the user gives
     */
    public Manifest(List sourceFiles,String userCom) throws IOException{
        //Get the date and time of the manifest creation
        mSource = (File) sourceFiles.get(0);
        mTarget = (File) sourceFiles.get(1);
        mUserCommand = userCom;

        //Create the title by getting the date and time of when repo was created
        DateFormat manifestDate = new SimpleDateFormat("yyyyMMdd");
        DateFormat manifestTime = new SimpleDateFormat("HHmm");
        Date dateObj = new Date();
        mManifestTitle = "543-p1_GEL_" + manifestDate.format(dateObj) + "_" + manifestTime.format(dateObj) + "_" + userCom;

        //Create or update activity directory in the repository
        File activityDirectory, manifestTextFile;
        if (userCom != "checkOut") {
            activityDirectory = new File(mTarget.getPath(), "Activity");
            activityDirectory.mkdir();
            manifestTextFile = new File (activityDirectory, getmManifestTitle() + ".txt");
        }
        else {
            manifestTextFile = new File (mSource.getParent(), getmManifestTitle() + ".txt");
        }

        //Write target & source information to manifest
        writeToFile(manifestTextFile, userCom + "(sourceDirectory, targetDirectory)");
        writeToFile(manifestTextFile, "  source directory path: " + mSource.getPath());
        writeToFile(manifestTextFile, "  target directory path: " + mTarget.getPath());

        //Write file & artifact information to manifest
        File leaf, parent;
        for(int i = 2, manifestSize = sourceFiles.size(); i < manifestSize; i++) {
            leaf = (File) sourceFiles.get(i);
            if (leaf.isFile()) {
                parent = leaf.getParentFile();
                writeToFile(manifestTextFile, "    " + parent.getName() + ", " + leaf.getName() + ", " + leaf.getPath());
            }
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
        FileWriter fStreamWrite = new FileWriter(file,true);
        BufferedWriter out = new BufferedWriter(fStreamWrite);
        out.write(newFile.toString());
        out.newLine();
        out.close();
    }

    public String getmManifestTitle() {
        return mManifestTitle;
    }

}
