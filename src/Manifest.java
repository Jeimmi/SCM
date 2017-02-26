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
	
	private String mManifestTitle;
	private String mUserCommand;
	private String mSourcePath;
	private String mTargetPath;
	public ArrayList<String> mSourceFileNames;
	public ArrayList<String> mArtifactFileNames;
	public ArrayList<String> mSourcePaths;
	
	public Manifest(String source,String target,String userCom){
		//Get the date and time of the manifest creation
		 mSourcePath = source;
		 mTargetPath = target;
		 mUserCommand = userCom;
		DateFormat manifestDate = new SimpleDateFormat("yyyyMMdd");
		DateFormat manifestTime = new SimpleDateFormat("HHmm");
		Date dateobj = new Date();
		mSourceFileNames = new ArrayList<String>();
		mArtifactFileNames = new ArrayList<String>();
		mSourcePaths = new ArrayList<String>();
		
		String mManifestTitle = "Manifest_" + manifestDate + "_" + manifestTime;
		
	}
	
	public void writeToFile(File file, String content) throws IOException
	{
	    StringBuilder newFile = new StringBuilder();
	    String edited = content;
	    newFile.append(edited);
	    newFile.append("\n");
	    FileWriter fstreamWrite = new FileWriter(file,true);
	    BufferedWriter out = new BufferedWriter(fstreamWrite);
	    out.write(newFile.toString());
	    out.close();
	}
	
	
	
	

	public void userCommands(File manifest,String userCom) throws IOException{
    	String command = null;
		switch(userCom){
		case "createRepo":
			command = userCom+": "+this.getmSourcePath()+" "+this.getmTargetPath()+"\n";
			writeToFile(manifest, command);
			break;	
		}

	}
	
	public void addFileNames(String sourcefileName, String artifactFileName, String sourcePath) {
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
