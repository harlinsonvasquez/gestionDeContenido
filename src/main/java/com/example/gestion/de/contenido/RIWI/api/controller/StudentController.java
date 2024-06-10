package com.example.gestion.de.contenido.RIWI.api.controller;

import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IStudentService;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "student")
@AllArgsConstructor
public class StudentController {
    @Autowired
    private final IStudentService studentService;

    @GetMapping
    public ResponseEntity<Page<StudentBasicResp>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") SortType sort,
            @RequestParam(required = false) String name) {

        Page<StudentBasicResp> students = studentService.getAllBasic(page, size, sort, name);
        return ResponseEntity.ok(students);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.studentService.get(id));
    }

    @PostMapping
    public ResponseEntity<StudentResp>insert(@Validated @RequestBody StudentReq request){
        return ResponseEntity.ok(this.studentService.create(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<StudentResp>updateupdate(
            @Validated @RequestBody StudentReq request,
            @PathVariable Long id){
        return ResponseEntity.ok(this.studentService.update(request, id));
    }
    @PatchMapping("/{id}/{status}")
    public ResponseEntity<StudentResp> updateStudentStatus(@PathVariable Long id, @PathVariable String status) {
        try {
            Status newStatus = Status.valueOf(status.toUpperCase());
            StudentResp updatedStudent = studentService.patch(id, newStatus);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // status no válido
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // error interno del servidor
        }
    }

}
