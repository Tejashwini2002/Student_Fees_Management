/* Student fees management System in Java*/
import java.io.*;
import java.util.*;

class Student implements Serializable {
    String USN;
    String name;
    int total_fees;
    int remaining_fees;
    int amount_they_are_willing_to_pay_now;

    Student(String USN, String name, int total_fees, int remaining_fees) {
        this.name = name;
        this.USN = USN;
        this.total_fees = total_fees;
        this.remaining_fees = remaining_fees;
    }

    String get_student_Details() {
        return ("Name: " + name + "\nUSN: " + USN + "\nTotal fees: " + total_fees
                + "\nRemaining fees: " + remaining_fees);
    }

    void pay_fees(String USN) {
        System.out.println("Enter how much amount you want to pay now: ");
        this.amount_they_are_willing_to_pay_now = new Scanner(System.in).nextInt();
        this.remaining_fees = this.remaining_fees - this.amount_they_are_willing_to_pay_now;
        System.out.println("Total fees: " + total_fees + "\nRemaining fees: " + remaining_fees);
    }
}

public class Student_Fee {
    public static void main(String[] args) {
        Map<String, Student> map = loadStudentData(); // Load data from a file

        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("Enter your choice: ");
            System.out.println(
                    "1.Enter Student Details 2.Pay Student fees 3.Get Student Details 4. Display details of all Students 5.Delete any Student 6.Exit");
            choice = sc.nextInt();
            if (choice == 1) {
                String USN;
                String name;
                int total_fees;
                int remaining_fees;
                try {
                    System.out.println("Enter student USN: ");
                    USN = sc.next();
                    System.out.println("Enter Student Name: ");
                    name = sc.next();
                    System.out.println("Enter total fees of the student: ");
                    total_fees = sc.nextInt();
                    System.out.println("Enter remaining fees of the student: ");
                    remaining_fees = sc.nextInt();

                    Student obj = new Student(USN, name, total_fees, remaining_fees);
                    map.put(USN, obj);
                    saveStudentData(map); // Save data to a file
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (choice == 2) {
                System.out.println("Enter USN of the student whose fees are being paid: ");
                String USN = sc.next();
                if (map.containsKey(USN)) {
                    map.get(USN).pay_fees(USN);
                    saveStudentData(map); // Save data to a file
                } else {
                    System.out.println("Student with " + USN + " is not present.");
                }
            } else if (choice == 3) {
                System.out.println("Enter USN of the student whose details are to be displayed: ");
                String USN = sc.next();
                if (map.containsKey(USN)) {
                    System.out.println(map.get(USN).get_student_Details());
                } else {
                    System.out.println("Student with " + USN + " is not present.");
                }
            } else if (choice == 4) {
                for (Student student : map.values()) {
                    System.out.println(student.get_student_Details());
                }
            } else if (choice == 5) {
                System.out.println("Enter USN of the student who has to be removed from the list: ");
                String USN = sc.next();
                if (map.containsKey(USN)) {
                    Student removedVal = map.remove(USN);
                    System.out.println("Details of student removed:\n " + removedVal.get_student_Details());
                    saveStudentData(map); // Save data to a file
                } else {
                    System.out.println("Student with " + USN + " is not present.");
                }
            } else if (choice == 6) {
                saveStudentData(map); // Save data to a file before exiting
                System.exit(0);
            } else {
                System.out.println("Invalid choice!! Please enter a valid option");
            }
        }
    }

    private static void saveStudentData(Map<String, Student> map) {
        try (FileOutputStream fileOut = new FileOutputStream("studentData.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(map);
            System.out.println("Student data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    private static Map<String, Student> loadStudentData() {
        Map<String, Student> map = new HashMap<>();
        try (FileInputStream fileIn = new FileInputStream("studentData.ser");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            map = (Map<String, Student>) objectIn.readObject();
            System.out.println("Student data loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting with an empty database.");
        }
        return map;
    }
}
