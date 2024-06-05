package com.example.gestion.de.contenido.RIWI.api.controller;

import com.example.gestion.de.contenido.RIWI.api.dto.request.ClassReq;
import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IClassService;
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
@RequestMapping(path = "/clases")
@AllArgsConstructor
public class ClassController {
    @Autowired
    private final IClassService classService;
    @GetMapping
    public Page<ClassEntityBasic> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sort) {

        Page<ClassEntityBasic> ClassEntityBasicRespPage = classService.getAll(page, size, sort);
        return new PageImpl<>(ClassEntityBasicRespPage.getContent(), ClassEntityBasicRespPage.getPageable(), ClassEntityBasicRespPage.getTotalElements());
    }
    @GetMapping("/{id}/students")
    public ClassEntityResp getClassWithStudentsById(@PathVariable Long id) {
        return classService.getClassWithStudentsById(id);
    }
    @PostMapping
    public ResponseEntity<ClassEntityBasic> insert(@Validated @RequestBody ClassReq request){
        return ResponseEntity.ok(this.classService.create(request));
    }
    @PatchMapping("/{id}/{status}")
    public ResponseEntity<ClassEntityBasic> updateStudentStatus(@PathVariable Long id, @PathVariable String status) {
        try {
            Status newStatus = Status.valueOf(status.toUpperCase());
            ClassEntityBasic updatedClass = classService.patch(id, newStatus);
            return ResponseEntity.ok(updatedClass);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // status no válido
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // error interno del servidor
        }
    }
}
