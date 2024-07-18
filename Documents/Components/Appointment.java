package Documents.Components;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
  private int appointmentId;
  private LocalDate date;
  private LocalTime time;
  private String doctorName;
  private int doctorId;
  private String patientName;
  private int patientId;

  public Appointment(int appointmentId, LocalDate date, LocalTime time, String doctorName, int doctorId,
      String patientName, int patientId) {
    this.appointmentId = appointmentId;
    this.date = date;
    this.time = time;
    this.doctorName = doctorName;
    this.doctorId = doctorId;
    this.patientName = patientName;
    this.patientId = patientId;
  }

  public int getId() {
    return appointmentId;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getTime() {
    return time;
  }

  public String getDoctorName() {
    return doctorName;
  }

  public int getDoctorId() {
    return doctorId;
  }

  public String getPatientName() {
    return patientName;
  }

  public int getPatientId() {
    return patientId;
  }

  public void displayDetails() {
    System.out.println("-----------------------------------");
    System.out.printf("\u001B[93m%-20s: \u001B[96m%d\u001B[0m\n", "Appointment ID", appointmentId);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m\n", "Date", date);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m\n", "Time", time);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m\n", "Doctor Name", doctorName);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%d\u001B[0m\n", "Doctor ID", doctorId);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m\n", "Patient Name", patientName);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%d\u001B[0m\n", "Patient ID", patientId);
    System.out.println("-----------------------------------");
  }
}
