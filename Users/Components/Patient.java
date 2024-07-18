package Users.Components;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
  private double height;
  private double weight;
  private List<Integer> appointmentIds;
  private List<Integer> prescriptionIds;

  public Patient(int id, String name, String username, String password, int age, String gender, long phn_no,
      double height, double weight) {
    super(id, name, username, password, age, gender, phn_no);
    this.height = height;
    this.weight = weight;
    this.appointmentIds = new ArrayList<>();
    this.prescriptionIds = new ArrayList<>();
  }

  public double getHeight() {
    return this.height;
  }

  public double getWeight() {
    return this.weight;
  }

  public void setAppointmentIds(List<Integer> appointmentIds) {
    this.appointmentIds = appointmentIds;
  }

  public void setPrescriptionIds(List<Integer> prescriptionIds) {
    this.prescriptionIds = prescriptionIds;
  }

  public List<Integer> getAppointmentIds() {
    return appointmentIds;
  }

  public List<Integer> getPrescriptionIds() {
    return prescriptionIds;
  }

  public void addAppointmentId(int appointmentId) {
    this.appointmentIds.add(appointmentId);
  }

  public void addPrescriptionId(int prescriptionId) {
    this.prescriptionIds.add(prescriptionId);
  }

  public void removeAppointmentId(int appointmentId) {
    this.appointmentIds.remove(Integer.valueOf(appointmentId));
  }

  public void displayDetails() {
    System.out.println("-------------------------------------------------------");
    System.out.printf("%-20s: %s%n", "\u001B[1;34mID\u001B[0m", this.getId());
    System.out.printf("%-20s: %s%n", "\u001B[1;34mName\u001B[0m", this.getName());
    System.out.printf("%-20s: %s%n", "\u001B[1;34mUsername\u001B[0m", this.getUsername());
    System.out.printf("%-20s: %d%n", "\u001B[1;34mAge\u001B[0m", this.getAge());
    System.out.printf("%-20s: %s%n", "\u001B[1;34mGender\u001B[0m", this.getGender());
    System.out.printf("%-20s: %d%n", "\u001B[1;34mPhone Number\u001B[0m", this.getPhn_no());
    System.out.println("-------------------------------------------------------");
  }

}
