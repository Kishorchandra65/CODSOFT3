import java.io.*;
import java.util.*;

class Student implements Serializable {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private static final String DATA_FILE = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudents();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    public boolean removeStudent(String rollNumber) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getRollNumber().equals(rollNumber)) {
                iterator.remove();
                saveStudents();
                return true;
            }
        }
        return false;
    }

    public Student searchStudent(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
         
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data: " + e.getMessage());
        }
    }
}

public class StudentManagementApp {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManagementSystem sms = new StudentManagementSystem();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nStudent Management System Menu:");
        System.out.println("1. Add New Student");
        System.out.println("2. Edit Student Information");
        System.out.println("3. Search for a Student");
        System.out.println("4. Display All Students");
        System.out.println("5. Exit");
    }

    private static int getUserChoice() {
        System.out.print("Please select an option: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number between 1 and 5: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void addNewStudent() {
        scanner.nextLine(); 
        System.out.print("Enter Roll Number: ");
        String rollNumber = scanner.nextLine().trim();
        if (rollNumber.isEmpty()) {
            System.out.println("Roll Number cannot be empty.");
            return;
        }
        if (sms.searchStudent(rollNumber) != null) {
            System.out.println("A student with this roll number already exists.");
            return;
        }
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        System.out.print("Enter Grade: ");
        String grade = scanner.nextLine().trim();
        if (grade.isEmpty()) {
            System.out.println("Grade cannot be empty.");
            return;
        }
        Student student = new Student(name, rollNumber, grade);
        sms.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void editStudent() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Roll Number of the student to edit: ");
        String rollNumber = scanner.nextLine().trim();
        Student student = sms.searchStudent(rollNumber);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter new Name (leave blank to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            student.setName(name);
        }
        System.out.print("Enter new Grade (leave blank to keep current): ");
        String grade = scanner.nextLine().trim();
        if (!grade.isEmpty()) {
            student.setGrade(grade);
        }
        sms.addStudent(student); 
        System.out.println("Student information updated successfully.");
    }

    private static void searchStudent() {
        scanner.nextLine(); 
        System.out.print("Enter Roll Number of the student to search: ");
        String rollNumber = scanner.nextLine().trim();
        Student student = sms.searchStudent(rollNumber);
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("Student Details:");
            System.out.println(student);
        }
    }
    private static void displayAllStudents() {
        List<Student> students = sms.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("List of All Students:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
}

 
