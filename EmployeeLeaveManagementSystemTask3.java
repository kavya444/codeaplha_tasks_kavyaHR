// ✅ Employee Leave Management System - Single File Version
// You can run directly: javac EmployeeLeaveManagementSystem.java
// Then: java EmployeeLeaveManagementSystem

import java.util.*;
import java.io.*;

// ====================== Employee Class ======================
class Employee implements Serializable {
    private int id;
    private String name;
    private int totalLeaves;
    private int leaveBalance;

    public Employee(int id, String name, int totalLeaves) {
        this.id = id;
        this.name = name;
        this.totalLeaves = totalLeaves;
        this.leaveBalance = totalLeaves;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getLeaveBalance() { return leaveBalance; }

    public void deductLeave(int days) {
        this.leaveBalance -= days;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + totalLeaves + "," + leaveBalance;
    }

    public static Employee fromString(String line) {
        String[] parts = line.split(",");
        Employee emp = new Employee(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
        emp.leaveBalance = Integer.parseInt(parts[3]);
        return emp;
    }
}

// ====================== LeaveRequest Class ======================
class LeaveRequest implements Serializable {
    private int employeeId;
    private String employeeName;
    private String startDate;
    private String endDate;
    private String reason;
    private String status; // Pending, Approved, Rejected

    public LeaveRequest(int employeeId, String employeeName, String startDate, String endDate, String reason) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = "Pending";
    }

    public int getEmployeeId() { return employeeId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return employeeId + "," + employeeName + "," + startDate + "," + endDate + "," + reason + "," + status;
    }

    public static LeaveRequest fromString(String line) {
        String[] parts = line.split(",");
        LeaveRequest lr = new LeaveRequest(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]);
        lr.status = parts[5];
        return lr;
    }
}

// ====================== Admin Class ======================
class Admin {
    private final String LEAVE_FILE = "leaves.txt";

    public void viewAndApproveRequests() {
        try {
            File file = new File(LEAVE_FILE);
            if (!file.exists()) {
                System.out.println("No leave requests found.");
                return;
            }

            List<LeaveRequest> requests = new ArrayList<>();
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                requests.add(LeaveRequest.fromString(sc.nextLine()));
            }
            sc.close();

            if (requests.isEmpty()) {
                System.out.println("No leave requests available.");
                return;
            }

            for (int i = 0; i < requests.size(); i++) {
                LeaveRequest r = requests.get(i);
                System.out.println((i + 1) + ". " + r);
            }

            Scanner input = new Scanner(System.in);
            System.out.print("\nEnter request number to approve/reject: ");
            int choice = input.nextInt();
            input.nextLine();
            System.out.print("Approve or Reject? (A/R): ");
            String decision = input.nextLine();

            if (decision.equalsIgnoreCase("A")) {
                requests.get(choice - 1).setStatus("Approved");
            } else {
                requests.get(choice - 1).setStatus("Rejected");
            }

            PrintWriter pw = new PrintWriter(new FileWriter(LEAVE_FILE));
            for (LeaveRequest r : requests) {
                pw.println(r.toString());
            }
            pw.close();

            System.out.println("Request updated successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

// ====================== Main Class ======================
public class EmployeeLeaveManagementSystemTask3 {
    private static final String EMPLOYEE_FILE = "employees.txt";
    private static final String LEAVE_FILE = "leaves.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Admin admin = new Admin();

        while (true) {
            System.out.println("\n===== Employee Leave Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Apply for Leave");
            System.out.println("3. View Leave Requests (Admin)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addEmployee(sc);
                case 2 -> applyLeave(sc);
                case 3 -> admin.viewAndApproveRequests();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addEmployee(Scanner sc) {
        try (FileWriter fw = new FileWriter(EMPLOYEE_FILE, true)) {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Total Leaves: ");
            int totalLeaves = sc.nextInt();

            Employee emp = new Employee(id, name, totalLeaves);
            fw.write(emp.toString() + "\n");
            System.out.println("Employee added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    private static void applyLeave(Scanner sc) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            String start = sc.nextLine();
            System.out.print("Enter End Date (YYYY-MM-DD): ");
            String end = sc.nextLine();
            System.out.print("Enter Reason: ");
            String reason = sc.nextLine();

            LeaveRequest lr = new LeaveRequest(id, name, start, end, reason);
            FileWriter fw = new FileWriter(LEAVE_FILE, true);
            fw.write(lr.toString() + "\n");
            fw.close();

            System.out.println("Leave request submitted successfully!");
        } catch (Exception e) {
            System.out.println("Error applying leave: " + e.getMessage());
        }
    }
}
