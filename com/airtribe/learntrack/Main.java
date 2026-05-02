package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public Main() {
        this.scanner = new Scanner(System.in);

        StudentRepository studentRepo = new StudentRepository();
        CourseRepository courseRepo = new CourseRepository();
        EnrollmentRepository enrollmentRepo = new EnrollmentRepository();

        this.studentService = new StudentService(studentRepo);
        this.courseService = new CourseService(courseRepo);
        this.enrollmentService = new EnrollmentService(enrollmentRepo, studentService, courseService);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    private void run() {
        System.out.println("=========================================");
        System.out.println("  Welcome to " + AppConstants.APP_NAME + " v" + AppConstants.APP_VERSION);
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMenuChoice();
            switch (choice) {
                case MenuOptions.MAIN_STUDENT_MENU:
                    studentMenuLoop();
                    break;
                case MenuOptions.MAIN_COURSE_MENU:
                    courseMenuLoop();
                    break;
                case MenuOptions.MAIN_ENROLLMENT_MENU:
                    enrollmentMenuLoop();
                    break;
                case MenuOptions.MAIN_EXIT:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    // -------------------- Main Menu --------------------

    private void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println(MenuOptions.MAIN_STUDENT_MENU + ". Student Management");
        System.out.println(MenuOptions.MAIN_COURSE_MENU + ". Course Management");
        System.out.println(MenuOptions.MAIN_ENROLLMENT_MENU + ". Enrollment Management");
        System.out.println(MenuOptions.MAIN_EXIT + ". Exit");
        System.out.print("Enter choice: ");
    }

    // -------------------- Student Menu --------------------

    private void studentMenuLoop() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- Student Management ---");
            System.out.println(MenuOptions.STUDENT_ADD + ". Add Student");
            System.out.println(MenuOptions.STUDENT_VIEW_ALL + ". View All Students");
            System.out.println(MenuOptions.STUDENT_SEARCH_BY_ID + ". Search Student By ID");
            System.out.println(MenuOptions.STUDENT_UPDATE + ". Update Student");
            System.out.println(MenuOptions.STUDENT_DEACTIVATE + ". Deactivate Student");
            System.out.println(MenuOptions.STUDENT_BACK + ". Back");
            System.out.print("Enter choice: ");

            int choice = readMenuChoice();
            try {
                switch (choice) {
                    case MenuOptions.STUDENT_ADD:
                        handleAddStudent();
                        break;
                    case MenuOptions.STUDENT_VIEW_ALL:
                        handleViewAllStudents();
                        break;
                    case MenuOptions.STUDENT_SEARCH_BY_ID:
                        handleSearchStudent();
                        break;
                    case MenuOptions.STUDENT_UPDATE:
                        handleUpdateStudent();
                        break;
                    case MenuOptions.STUDENT_DEACTIVATE:
                        handleDeactivateStudent();
                        break;
                    case MenuOptions.STUDENT_BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (EntityNotFoundException | InvalidInputException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void handleAddStudent() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email (optional, press Enter to skip): ");
        String email = scanner.nextLine();
        System.out.print("Batch: ");
        String batch = scanner.nextLine();

        Student created;
        if (email == null || email.trim().isEmpty()) {
            created = studentService.addStudent(firstName, lastName, batch);
        } else {
            created = studentService.addStudent(firstName, lastName, email, batch);
        }
        System.out.println("Created: " + created);
    }

    private void handleViewAllStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private void handleSearchStudent() {
        int id = promptInt("Enter student ID: ");
        Student s = studentService.findById(id);
        System.out.println(s);
        System.out.println("Display name (polymorphic): " + s.getDisplayName());
    }

    private void handleUpdateStudent() {
        int id = promptInt("Enter student ID: ");
        System.out.print("New first name (Enter to skip): ");
        String firstName = scanner.nextLine();
        System.out.print("New last name (Enter to skip): ");
        String lastName = scanner.nextLine();
        System.out.print("New email (Enter to skip): ");
        String email = scanner.nextLine();

        Student updated = studentService.updateStudent(id, firstName, lastName, email);
        System.out.println("Updated: " + updated);
    }

    private void handleDeactivateStudent() {
        int id = promptInt("Enter student ID: ");
        studentService.deactivateStudent(id);
        System.out.println("Student " + id + " deactivated.");
    }

    // -------------------- Course Menu --------------------

    private void courseMenuLoop() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- Course Management ---");
            System.out.println(MenuOptions.COURSE_ADD + ". Add Course");
            System.out.println(MenuOptions.COURSE_VIEW_ALL + ". View All Courses");
            System.out.println(MenuOptions.COURSE_SEARCH_BY_ID + ". Search Course By ID");
            System.out.println(MenuOptions.COURSE_TOGGLE_STATUS + ". Activate/Deactivate Course");
            System.out.println(MenuOptions.COURSE_BACK + ". Back");
            System.out.print("Enter choice: ");

            int choice = readMenuChoice();
            try {
                switch (choice) {
                    case MenuOptions.COURSE_ADD:
                        handleAddCourse();
                        break;
                    case MenuOptions.COURSE_VIEW_ALL:
                        handleViewAllCourses();
                        break;
                    case MenuOptions.COURSE_SEARCH_BY_ID:
                        handleSearchCourse();
                        break;
                    case MenuOptions.COURSE_TOGGLE_STATUS:
                        handleToggleCourse();
                        break;
                    case MenuOptions.COURSE_BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (EntityNotFoundException | InvalidInputException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void handleAddCourse() {
        System.out.print("Course name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        int duration = promptInt("Duration (weeks): ");
        Course c = courseService.addCourse(name, description, duration);
        System.out.println("Created: " + c);
    }

    private void handleViewAllCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses yet.");
            return;
        }
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private void handleSearchCourse() {
        int id = promptInt("Enter course ID: ");
        System.out.println(courseService.findById(id));
    }

    private void handleToggleCourse() {
        int id = promptInt("Enter course ID: ");
        Course c = courseService.toggleStatus(id);
        System.out.println("Status updated: " + c);
    }

    // -------------------- Enrollment Menu --------------------

    private void enrollmentMenuLoop() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println(MenuOptions.ENROLLMENT_ENROLL + ". Enroll Student in Course");
            System.out.println(MenuOptions.ENROLLMENT_VIEW_BY_STUDENT + ". View Enrollments by Student");
            System.out.println(MenuOptions.ENROLLMENT_VIEW_ALL + ". View All Enrollments");
            System.out.println(MenuOptions.ENROLLMENT_COMPLETE + ". Mark Enrollment Completed");
            System.out.println(MenuOptions.ENROLLMENT_CANCEL + ". Mark Enrollment Cancelled");
            System.out.println(MenuOptions.ENROLLMENT_BACK + ". Back");
            System.out.print("Enter choice: ");

            int choice = readMenuChoice();
            try {
                switch (choice) {
                    case MenuOptions.ENROLLMENT_ENROLL:
                        handleEnroll();
                        break;
                    case MenuOptions.ENROLLMENT_VIEW_BY_STUDENT:
                        handleViewByStudent();
                        break;
                    case MenuOptions.ENROLLMENT_VIEW_ALL:
                        handleViewAllEnrollments();
                        break;
                    case MenuOptions.ENROLLMENT_COMPLETE:
                        handleCompleteEnrollment();
                        break;
                    case MenuOptions.ENROLLMENT_CANCEL:
                        handleCancelEnrollment();
                        break;
                    case MenuOptions.ENROLLMENT_BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (EntityNotFoundException | InvalidInputException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void handleEnroll() {
        int studentId = promptInt("Student ID: ");
        int courseId = promptInt("Course ID: ");
        Enrollment e = enrollmentService.enroll(studentId, courseId);
        System.out.println("Enrollment created: " + e);
    }

    private void handleViewByStudent() {
        int studentId = promptInt("Student ID: ");
        List<Enrollment> list = enrollmentService.listByStudent(studentId);
        if (list.isEmpty()) {
            System.out.println("No enrollments for student " + studentId + ".");
            return;
        }
        for (Enrollment e : list) {
            System.out.println(e);
        }
    }

    private void handleViewAllEnrollments() {
        List<Enrollment> list = enrollmentService.listAll();
        if (list.isEmpty()) {
            System.out.println("No enrollments yet.");
            return;
        }
        for (Enrollment e : list) {
            System.out.println(e);
        }
    }

    private void handleCompleteEnrollment() {
        int id = promptInt("Enrollment ID: ");
        Enrollment e = enrollmentService.markCompleted(id);
        System.out.println("Marked completed: " + e);
    }

    private void handleCancelEnrollment() {
        int id = promptInt("Enrollment ID: ");
        Enrollment e = enrollmentService.markCancelled(id);
        System.out.println("Marked cancelled: " + e);
    }

    // -------------------- Helpers --------------------

    private int readMenuChoice() {
        String line = scanner.nextLine();
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            return -1; // sentinel — falls into 'invalid option' branch
        }
    }

    private int promptInt(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        return InputValidator.parseInt(line, prompt.replace(":", "").trim());
    }
}
