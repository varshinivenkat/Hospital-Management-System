package Users.Components;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {

  private String specialization;
  private String workingHours;
  private List<Integer> bookedPatients;
  private List<Integer> appointments;

  public Doctor(int id, String name, String username, String password, int age, String gender, long phn_no,
      String specialization, String workingHours) {
    super(id, name, username, password, age, gender, phn_no);
    this.specialization = specialization;
    this.workingHours = workingHours;
    this.bookedPatients = new ArrayList<>();
    this.appointments = new ArrayList<>();
  }

  public String getSpecialization() {
    return this.specialization;
  }

  public String getWorkingHours() {
    return this.workingHours;
  }

  public List<Integer> getBookedPatients() {
    return this.bookedPatients;
  }

  public List<Integer> getAppointments() {
    return this.appointments;
  }

  public void setBookedPatients(List<Integer> bookedPatients) {
    this.bookedPatients = bookedPatients;
  }

  public void setAppointments(List<Integer> appointments) {
    this.appointments = appointments;
  }

  public void addAppointmentId(int appointmentId) {
    this.appointments.add(appointmentId);
  }

  public void removeAppointmentId(int appointmentId) {
    this.appointments.remove(Integer.valueOf(appointmentId));
  }

  public void removeBookedPatientId(int bookedPatientId) {
    this.bookedPatients.remove(Integer.valueOf(bookedPatientId));
  }

  public void displayDetails() {
    System.out.printf("%-20s: %s%n", "\u001B[1;34mID\u001B[0m", this.getId());
    System.out.printf("%-20s: %s%n", "\u001B[1;34mName\u001B[0m", this.getName());
    System.out.printf("%-20s: %s%n", "\u001B[1;34mUsername\u001B[0m", this.getUsername());
    System.out.printf("%-20s: %d%n", "\u001B[1;34mAge\u001B[0m", this.getAge());
    System.out.printf("%-20s: %s%n", "\u001B[1;34mGender\u001B[0m", this.getGender());
    System.out.printf("%-20s: %d%n", "\u001B[1;34mPhone Number\u001B[0m", this.getPhn_no());
    System.out.printf("%-20s: %s%n", "\u001B[1;34mSpecialization\u001B[0m", this.specialization);
    System.out.printf("%-20s: %s%n", "\u001B[1;34mWorking Hours\u001B[0m", this.workingHours);

    System.out.println("\u001B[1;34m-------------------------------------\u001B[0m");
  }

}
