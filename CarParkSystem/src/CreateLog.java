import java.io.BufferedWriter;
import java.io.File; // Import the File class 
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors
import java.io.Writer;

public class CreateLog {

    private String FileName;

    public CreateLog(String Fname){
        FileName = Fname;
    }
    //public static void main(String[] args) {
    public void CFileRun(){
      try {
        FileName = FileName.concat("ServerLog.txt");
        File myFil = new File(FileName);
        if (myFil.createNewFile()) {
          System.out.println("Log File created: " + myFil.getName());
        } else {
          System.out.println("File already exists.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }

    public void WriteFile(String Entry) {
        try {
          Writer output;
          output = new BufferedWriter(new FileWriter(FileName, true));  
          output.append(Entry);
          output.close();
          //System.out.println("Log Updated.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
      }
  } 