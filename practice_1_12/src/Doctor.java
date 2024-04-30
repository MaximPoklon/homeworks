import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Doctor implements Externalizable {

    private String first_name;
    private String last_name;
    private String specialization;

    private int age;
    private int salary;

    public Doctor(){}

    public Doctor(String first_name, String last_name, String specialization, int age, int salary){
        this.first_name = first_name;
        this.last_name = last_name;
        this.specialization = specialization;
        this.age = age;
        this.salary = salary;
    }

    public String getFirst_name(){
        return first_name;
    }

    public String getLast_name(){
        return last_name;
    }

    public String getSpecialization(){
        return specialization;
    }

    public int getAge(){
        return age;
    }

    public int getSalary(){
        return salary;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getFirst_name());
        out.writeObject(this.getLast_name());
        out.writeObject(this.getSpecialization());
        out.writeObject(this.getAge());
        out.writeObject(this.getSalary());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.first_name = (String)in.readObject();
        this.last_name = (String)in.readObject();
        this.specialization = (String)in.readObject();
        this.age = (Integer)in.readObject();
        this.salary = (Integer)in.readObject();
    }

    @Override
    public String toString(){
        return "Doctor:{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
