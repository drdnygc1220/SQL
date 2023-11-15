package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
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

    //!!!  getStudentById RequestParam *******************
    public Student findStudent(Long id) {
       return studentRepository.findById(id).orElseThrow(()->
               new ResourceNotFoundException("Student not found with id: "+id));
    }

    //!!! DeleteStudentById ************************************
    public void deleteStudent(Long id) {
        //burada kontrol etmem gereken birşey var mı?student nesnem DB varmı?
        //olmayan bir öğrenciyi silmeye calışırsam nullpointerexception alırsın.
        Student student=findStudent(id);//kullanılmış methodu(yukarıdaki) bir daha çagırıp alttaki methodları yazmadım
        studentRepository.delete(student);
    }
}
