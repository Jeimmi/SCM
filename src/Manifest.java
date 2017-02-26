import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Manifest {
	
	private String mManifestTitle;
	private String mUserCommand;
	private String mSourcePath;
	private String mTargetPath;
	
	public Manifest(String source,String target,String userCom){
		//Get the date and time of the manifest creation
		 mSourcePath = source;
		 mTargetPath = target;
		 mUserCommand = userCom;
		DateFormat manifestDate = new SimpleDateFormat("yyyyMMdd");
		DateFormat manifestTime = new SimpleDateFormat("HHmm");
		Date dateobj = new Date();
		
		String mManifestTitle = "Manifest_" + manifestDate + "_" + manifestTime;
		
	}
	public void writeToFile(File file, String content){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(content);

			// no need to close it.
			//bw.close();
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void userCommands(File manifest,String userCom){
    	String command = null;
		switch(userCom){
		case "createRepo":
			command = userCom+": "+this.getmSourcePath()+" "+this.getmTargetPath();
			writeToFile(manifest, command);
			System.out.println("end user com");
			break;	
		}

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
