package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
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

    //!!!Update Student *********************************************
    public void updateStudent(Long id, StudentDTO studentDTO) {
        //ID li öğrenci var mı?
        Student student=findStudent(id);
        //!!!email unique mi?
        boolean emailExist=studentRepository.existsByEmail(studentDTO.getEmail());
        if(emailExist && studentDTO.getEmail().equals(student.getEmail())){
            throw  new ConflictException("Email is readly exist");
        }
        /*
        1)kendi email mrc ,yenisindede mrc gir-->(UPDATE OLUR)
        2)kendi email mrc, ahmet girdi(DB de zaten var)-->(CONFLICT)
        3)kendi email mrc,mhmet girdi(DB de yok)-->(UPDATE OLUR)
         */

        // !!! DTO --> POJO
        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());

        studentRepository.save(student);
    }
    }

