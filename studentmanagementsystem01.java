import java.io.*;
import java.util.*;

class Student {
    private String Name;
    private int RollNumber;
    private String Grade;

    public Student(String Name, int RollNumber, String Grade) {
        this.Name = Name;
        this.RollNumber = RollNumber;
        this.Grade = Grade;
    }

    public String getName() {
        return Name;
    }

    public int getRollNumber() {
        return RollNumber;
    }

    public String getGrade() {
        return Grade;
    }

    public String toString() {
        return "NAME: " + Name + ", ROLL NUMBER: " + RollNumber + ", GRADE: " + Grade;
    }
}

class StudentManagementSystem01 {
    private ArrayList<Student> students = new ArrayList<>();
    private static final String DATA_FILE = "student_data.txt";

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int RollNumber) {
        students.removeIf(student -> student.getRollNumber() == RollNumber);
    }

    public Student searchStudent(int RollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == RollNumber) {
                return student;
            }
        }
        return null;
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public void saveStudentData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            outputStream.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadStudentData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (ArrayList<Student>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem01 system = new StudentManagementSystem01();

        system.loadStudentData();

        while (true) {
            System.out.println("\nSTUDENT MANAGEMENT SYSTEM");
            System.out.println("1. ADD A STUDENT");
            System.out.println("2. REMOVE A STUDENT");
            System.out.println("3. SEARCH FOR A STUDENT");
            System.out.println("4. DISPLAY ALL STUDENTS");
            System.out.println("5. SAVE AND EXIT");

            System.out.print("ENTER YOUR CHOICE: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("ENTER STUDENT NAME : ");
                    String name = scanner.nextLine();
                    System.out.print("ENTER STUDENTS ROLL NUMBER : ");
                    int RollNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("ENTER STUDENTS GRADE : ");
                    String grade = scanner.nextLine();

                    if (RollNumber > 0) {
                        Student newStudent = new Student(name, RollNumber, grade);
                        system.addStudent(newStudent);
                        System.out.println("STUDENT ADDED SUCCESSFULLY.");
                    } else {
                        System.out.println("INVALID ROLL NUMBER.PLEASE ENTER A VALID INTEGER.");
                    }
                    break;

                case 2:
                    System.out.print("ENTER THE ROLL NUMBER OF THE STUDENT TO REMOVE FROM SYSTEM : ");
                    int rollToRemove = scanner.nextInt();
                    scanner.nextLine();
                    system.removeStudent(rollToRemove);
                    System.out.println("STUDENT REMOVED SUCCESSFULLY.");
                    break;

                case 3:
                    System.out.print("ENTER THE ROLL NUMBER OF THE STUNDENT TO SEARCH : ");
                    int rollToSearch = scanner.nextInt();
                    scanner.nextLine();
                    Student foundStudent = system.searchStudent(rollToSearch);
                    if (foundStudent != null) {
                        System.out.println("STUDENT FOUND : " + foundStudent);
                    } else {
                        System.out.println("STUDENT NOT FOUND.");
                    }
                    break;

                case 4:
                    ArrayList<Student> allStudents = system.getAllStudents();
                    if (allStudents.isEmpty()) {
                        System.out.println("NO STUDENTS IN THE SYSTEM.");
                    } else {
                        System.out.println("ALL STUDENTS : ");
                        for (Student student : allStudents) {
                            System.out.println(student);
                        }
                    }
                    break;

                case 5:
                    system.saveStudentData();
                    System.out.println("STUDENT DATA SAVED. EXITING THE PROGRAM.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("INVALID CHOICE .PLEASE TRY AGAIN.");
            }
        }
    }
}
