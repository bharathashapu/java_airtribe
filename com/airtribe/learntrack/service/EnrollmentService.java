package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.List;

public class EnrollmentService {

    private final EnrollmentRepository repository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository repository,
                             StudentService studentService,
                             CourseService courseService) {
        this.repository = repository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enroll(int studentId, int courseId) {
        Student student = studentService.findById(studentId);
        Course course = courseService.findById(courseId);

        if (!student.isActive()) {
            throw new InvalidInputException("Cannot enroll an inactive student.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("Cannot enroll into an inactive course.");
        }
        if (repository.existsForStudentAndCourse(studentId, courseId)) {
            throw new InvalidInputException(
                    "Student " + studentId + " is already enrolled in course " + courseId + ".");
        }

        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(), studentId, courseId);
        repository.save(enrollment);
        return enrollment;
    }

    public List<Enrollment> listAll() {
        return repository.findAll();
    }

    public List<Enrollment> listByStudent(int studentId) {
        // ensure student exists — throws if not
        studentService.findById(studentId);
        return repository.findByStudentId(studentId);
    }

    public Enrollment markCompleted(int enrollmentId) {
        return updateStatus(enrollmentId, EnrollmentStatus.COMPLETED);
    }

    public Enrollment markCancelled(int enrollmentId) {
        return updateStatus(enrollmentId, EnrollmentStatus.CANCELLED);
    }

    private Enrollment updateStatus(int enrollmentId, EnrollmentStatus newStatus) {
        Enrollment e = repository.findById(enrollmentId);
        if (e == null) {
            throw new EntityNotFoundException("Enrollment", enrollmentId);
        }
        if (e.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException(
                    "Enrollment " + enrollmentId + " is already " + e.getStatus() + ".");
        }
        e.setStatus(newStatus);
        return e;
    }
}
