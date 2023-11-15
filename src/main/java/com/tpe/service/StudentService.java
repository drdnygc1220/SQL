package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {//service içinde repository ile görüşecegiz.
    @Autowired
    private StudentRepository studentRepository;

    //!!! Get ALL STUDENTS*****************************
    public List<Student> getAll() {

       return studentRepository.findAll();//findAll JpaRepositoryden geliyor.
    }

    // !!! Create new Student***************************
    public void createStudent(Student student) {
        //kontrol etmem gereken birşey var mı? önce buna bakılmalı...
        //unique olan degerden ögrenci önceden kaydedilmiş mi? Ama önce unique deger varmı buna bakmalıyız.

    //!!! email unique mi kontrolü
        if(studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictException("Email is already exist ");
        }
        studentRepository.save(student);


    }
}
