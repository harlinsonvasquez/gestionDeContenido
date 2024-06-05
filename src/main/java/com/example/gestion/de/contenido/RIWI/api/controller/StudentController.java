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
    public Page<StudentResp> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sort) {

        Page<StudentResp> userBasicRespPage = studentService.getAll(page, size, sort);
        return new PageImpl<>(userBasicRespPage.getContent(), userBasicRespPage.getPageable(), userBasicRespPage.getTotalElements());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.studentService.get(id));
    }

    @PostMapping
    public ResponseEntity<StudentResp>insert(@Validated @RequestBody StudentReq request){
        return ResponseEntity.ok(this.studentService.create(request));
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
