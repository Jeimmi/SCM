import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Manifest {
	
	private String mManifestTitle; //Title of namifest. --> Manifest_Date_Time
	private String mUserCommand; //Is createRepo for now
	private String mSourcePath; //The Source path of folder to copy.
	private String mTargetPath; //The path of the repo
	public ArrayList<String> mSourceFileNames; //Arraylist will hold all source file names in Repo
	public ArrayList<String> mArtifactFileNames; //Arraylist will hold all artifact file names in Repo
	public ArrayList<String> mSourcePaths; //Arraylist will hold all sourcepaths in repo
	
	/**
	 * Manifest constructor
	 * Create a manifest object
	 * @param source The source folder path
	 * @param target The target repo path
	 * @param userCom The command the user gives
	 */
	public Manifest(String source,String target,String userCom){
		//Get the date and time of the manifest creation
		 mSourcePath = source;
		 mTargetPath = target;
		 mUserCommand = userCom;

		//Instantiate arraylists to put sourcefiles, artifacts, and source paths in
		mSourceFileNames = new ArrayList<String>();
		mArtifactFileNames = new ArrayList<String>();
		mSourcePaths = new ArrayList<String>();
		
		//Create the title by getting the date and time of when repo was created
		DateFormat manifestDate = new SimpleDateFormat("yyyyMMdd");
		DateFormat manifestTime = new SimpleDateFormat("HHmm");
		Date dateObj = new Date();
		mManifestTitle = "Manifest_" + manifestDate.format(dateObj) + "_" + manifestTime.format(dateObj);
		
	}


	public void userCommands(File manifest,String userCom) throws IOException{
    	String command = null;
		switch(userCom){
		case "createRepo":
			command = userCom+": "+this.getmSourcePath()+" "+this.getmTargetPath()+"\n";
			break;	
		}

	}
	
	/**
	 * addFileNames to member arraylists that will hold each file name in Manifest.
	 * Will be called in the rescusive createRepo method in class SCM
	 * @param sourcefileName
	 * @param artifactFileName
	 * @param sourcePath
	 */
	public void addFileNames(String sourcefileName, String artifactFileName, String sourcePath) {
		//Add souce file, artifact file, and source path into arraylists
		mSourceFileNames.add(sourcefileName);
		mArtifactFileNames.add(artifactFileName);
		mSourcePaths.add(sourcePath);
	}
	
	public String getmManifestTitle() {
		return mManifestTitle;
	}
	public void setmManifestTitle(String mManifestTitle) {
		this.mManifestTitle = mManifestTitle;
	}
	public String getmUserCommand() {
		return mUserCommand;
	}
	public void setmUserCommand(String mUserCommand) {
		this.mUserCommand = mUserCommand;
	}
	public String getmSourcePath() {
		return mSourcePath;
	}
	public void setmSourcePath(String mSourcePath) {
		this.mSourcePath = mSourcePath;
	}
	public String getmTargetPath() {
		return mTargetPath;
	}
	public void setmTargetPath(String mTargetPath) {
		this.mTargetPath = mTargetPath;
	}
	
	
}
