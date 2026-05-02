package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.enums.CourseStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course addCourse(String name, String description, int durationInWeeks) {
        InputValidator.validateNonEmpty(name, "Course name");
        InputValidator.validateCourseDuration(durationInWeeks);

        Course course = new Course(IdGenerator.getNextCourseId(), name, description, durationInWeeks);
        repository.save(course);
        return course;
    }

    public List<Course> listCourses() {
        return repository.findAll();
    }

    public Course findById(int id) {
        Course c = repository.findById(id);
        if (c == null) {
            throw new EntityNotFoundException("Course", id);
        }
        return c;
    }

    public Course toggleStatus(int id) {
        Course c = findById(id);
        c.setStatus(c.isActive() ? CourseStatus.INACTIVE : CourseStatus.ACTIVE);
        return c;
    }
}
