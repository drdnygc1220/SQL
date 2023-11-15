package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {//service içinde repository ile görüşecegiz.
    @Autowired
    private StudentRepository studentRepository;

    //!!! Get ALL STUDENTS
    public List<Student> getAll() {

       return studentRepository.findAll();//findAll JpaRepositoryden geliyor.
    }
}
