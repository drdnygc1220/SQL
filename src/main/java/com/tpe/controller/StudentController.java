package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // !!! Get ALL STUDENTS
    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
         List<Student>students=studentService.getAll();
         return ResponseEntity.ok(students);

    }

    // !!! Create new Student
    //create yapabilmem için bana bazı bilgiler lazım.
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

    //!!!  getStudentById RequestParam *******************
    @GetMapping("/query")// http://localhost:8080/students/query?id=1 +GET
    public  ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student= studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    //!!! getStudentById PathVariable ***********************
    @GetMapping("/{id}")//http://localhost:8080/students/1
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id")Long id){
       // Student student=studentService.findStudent(id);
        return ResponseEntity.ok(studentService.findStudent(id));//ikinci olarak böyle yazabiliriz
    }

    //!!! DeleteStudentById ************************************
    //id değişken oldugu için süslü parantez kullanıyoruz.
    @DeleteMapping("/{id}")  //http://localhost:8080/students/3
    //student degilde string gönderecgiz birşey dönmüyor çünkü silinecek
    public ResponseEntity<String> deleteStudent(@PathVariable("id")Long id){
        studentService.deleteStudent(id);

        String message="Student is deleted successfuly";
        return new ResponseEntity<>(message,HttpStatus.OK);//return ResponseEntity.ok(message)

    }
    //!!! Update Student ******************************
    //post:sıfırdan bir veriyi güncellemek için
    //put:verilen bir veriyi güncellemek için
    @PutMapping("/{id}")//http://localhost:8080/student/1 +PUT +JSON
    public ResponseEntity<String>updateStudent(@PathVariable Long id,
                                               @Valid @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id,studentDTO);
        String message="Student is update Successfully";
        return new ResponseEntity<>(message,HttpStatus.OK);//200

    }

}