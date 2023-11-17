package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
        ***** SORU-1 :  @Controller yerine , @Component kullanirsam ne olur ??
        **    CEVAP-1 : Dispatcher , @Controller ile annote edilmis sınıfları tarar ve
        bunların içindeki @RequestMapping annotationlari algilamaya calisir. Dikkat :
        @Component ile annote edilen siniflar taranmayacaktir..

        Ayrica  @RequestMapping'i yalnızca sınıfları @Controller ile annote edilmis olan
        methodlar üzerinde/içinde kullanabiliriz ve @Component, @Service, @Repository vb.
        ile ÇALIŞMAZ…

        ***** SORU-2 : @RestController ile @Controller arasindaki fark nedir ??
        **   CEVAP-2 : @Controller, Spring MVC framework'ünün bir parçasıdır.genellikle HTML
        sayfalarının görüntülenmesi veya yönlendirilmesi gibi işlevleri gerçekleştirmek
        üzere kullanılır.
                       @RestController annotation'ı, @Controller'dan türetilmiştir ve RESTful
         web servisleri sağlamak için kullanılır.Bir sınıfın üzerine konulduğunda, tüm
         metodlarının HTTP taleplerine JSON gibi formatlarda cevap vermesini sağlar.

         ***** SORU-3 : Controller'dan direk Repo ya gecebilir miyim
         **   CEVAP-3: HAYIR, BusinessLogic ( kontrol ) katmani olan Service'i atlamamam gerekir.
 */

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Not: Get ALL STUDENTS
    @GetMapping // http://localhost:8080/students + GET
    public ResponseEntity<List<Student>> getAll(){

        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students); // 200 HTTP Status Code
        //return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Not: Create new Student
    @PostMapping  // http://localhost:8080/students + POST + JSON
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){
        // @Valid : parametreler valid mi kontrol eder, bu örenekte Student
        //objesi oluşturmak için  gönderilen fieldlar yani
        //name gibi özellikler düzgün set edilmiş mi ona bakar.
        // @RequestBody = gelen  requestin bodysindeki bilgiyi ,
        //Student objesine map edilmesini sağlıyor.
        studentService.createStudent(student);

        Map<String,String> map = new HashMap<>();
        map.put("message", "Student is created successfully");
        map.put("status","true");

        return new ResponseEntity<>(map, HttpStatus.CREATED); // map + 201 Http Status Kod
    }

    // Not: getStudentById RequestParam **********************
    @GetMapping("/query")  // http://localhost:8080/students/query?id=1   + GET
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    // Not: getStudentById PathVariable **********************
    @GetMapping("/{id}") //http://localhost:8080/students/1
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        return ResponseEntity.ok(studentService.findStudent(id));
    }

    // Not: DeleteStudentById ********************************
    @DeleteMapping("/{id}")  //http://localhost:8080/students/3
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);

        String message = "Student is deleted successfully";
        return new ResponseEntity<>(message, HttpStatus.OK); // return ResponseEntity.ok(message)
    }
    //Not: Update Student ************************************
    @PutMapping("/{id}")  //http://localhost:8080/students/1 + PUT + JSON
    public ResponseEntity<String> updateStudent(@PathVariable Long id,
                                                @Valid @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id, studentDTO);
        String message = "Student is updated Successfully";
        return new ResponseEntity<>(message, HttpStatus.OK); // 200 OK
    }

    //Not: getAllWithPage() ***********************************
    @GetMapping("/page") //http://localhost:8080/students/page?page=0&size=2&sort=name&direction=ASC  + GET
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page, // kacinci sayfa gelsin
            @RequestParam("size") int size, // safya basi kac ogrenci
            @RequestParam("sort") String prop, // siralama hangi degiskene gore yapilacak
            @RequestParam("direction") Sort.Direction direction // tersden mi yoksa dogal siralami mi yapilacak
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<Student> studentPage =  studentService.getAllWithPage(pageable);
        return ResponseEntity.ok(studentPage);
    }

    // Not: GetByLastName() ************************************
    @GetMapping("/querylastname") //http://localhost:8080/students/querylastname?lastName=Gocmek  + GET
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName) {
        List<Student> list = studentService.findStudentByLastName(lastName);
        return ResponseEntity.ok(list);
    }

    //Not: GetStudentByGrade( with JPQL ( Java Persistance Query Language) ) ******
    @GetMapping("/grade/{grade}") //http://localhost:8080/students/grade/70 + GET
    public ResponseEntity<List<Student>> getStudentsEqualsGrade(@PathVariable("grade") Integer grade){
        List<Student> list = studentService.getStudentsEqualsGrade(grade);
        return ResponseEntity.ok(list);
    }


}