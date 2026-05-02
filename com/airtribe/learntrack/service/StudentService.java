package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // Overloaded: without email
    public Student addStudent(String firstName, String lastName, String batch) {
        InputValidator.validateNonEmpty(firstName, "First name");
        InputValidator.validateNonEmpty(lastName, "Last name");
        InputValidator.validateNonEmpty(batch, "Batch");

        Student student = new Student(IdGenerator.getNextStudentId(), firstName, lastName, batch);
        repository.save(student);
        return student;
    }

    // Overloaded: with email
    public Student addStudent(String firstName, String lastName, String email, String batch) {
        InputValidator.validateNonEmpty(firstName, "First name");
        InputValidator.validateNonEmpty(lastName, "Last name");
        InputValidator.validateEmail(email);
        InputValidator.validateNonEmpty(batch, "Batch");

        Student student = new Student(IdGenerator.getNextStudentId(),
                firstName, lastName, email, batch);
        repository.save(student);
        return student;
    }

    public List<Student> listStudents() {
        return repository.findAll();
    }

    public Student findById(int id) {
        Student s = repository.findById(id);
        if (s == null) {
            throw new EntityNotFoundException("Student", id);
        }
        return s;
    }

    public Student updateStudent(int id, String firstName, String lastName, String email) {
        Student s = findById(id);
        if (firstName != null && !firstName.trim().isEmpty()) {
            s.setFirstName(firstName);
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            s.setLastName(lastName);
        }
        if (email != null && !email.trim().isEmpty()) {
            InputValidator.validateEmail(email);
            s.setEmail(email);
        }
        return s;
    }

    public void deactivateStudent(int id) {
        Student s = findById(id);
        s.setActive(false);
    }
}
