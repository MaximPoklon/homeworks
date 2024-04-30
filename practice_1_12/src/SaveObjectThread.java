import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveObjectThread extends Thread {

    private Doctor doctor;
    private String filePath;


    public SaveObjectThread(Doctor doctor, String filePath){

        this.doctor = doctor;
        this.filePath = filePath;
    }

    @Override
    public void run(){
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(doctor);
            out.close();
            fileOut.close();
            System.out.println("Object saved to file - " + filePath);
        } catch (IOException err) {
           System.out.printf("Error saving object: %s", err.getMessage());
        }
    }
}
