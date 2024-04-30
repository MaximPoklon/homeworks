import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadObjectThread extends Thread {

    private String filePath;

    public LoadObjectThread(String filePath){
        this.filePath = filePath;
    }
    @Override
    public void run(){
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Doctor doctor = (Doctor)in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Object loaded from file - " + filePath);
            System.out.println(doctor.toString());
        } catch (IOException | ClassNotFoundException err) {
            System.out.printf("Error loading object from file: %s", err.getMessage());
        }
    }
}
