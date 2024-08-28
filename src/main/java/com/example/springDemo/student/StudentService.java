package com.example.springDemo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(!optionalStudent.isPresent()){
            throw new IllegalStateException("The student with id: " + studentId + " doesn't exist");
        }
        return optionalStudent;
    }

    public void addNewStudent(Student newStudent) {
        Optional<Student> studentOptional = studentRepository.findStudentsByEmail(newStudent.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("Email is alredy taken");
        }
        studentRepository.save(newStudent);
    }

    public void deleteStudent(Long studentId) {
        boolean exist = studentRepository.existsById(studentId);
        if(!exist){
            throw new IllegalStateException("The student with id: " + studentId + " doesn't exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("The student with id: " + studentId + " doesn't exist"));

        if( name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if( email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){
            Optional<Student> optionalStudent = studentRepository.findStudentsByEmail(email);
            if(optionalStudent.isPresent()){
                throw new IllegalStateException("Email is alredy taken");
            }
            student.setEmail(email);
        }
    }
}
