package com.example.gestion.de.contenido.RIWI.api.controller;

import com.example.gestion.de.contenido.RIWI.api.dto.request.ClassReq;
import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IClassService;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "courses")
public class ClassController {
    @Autowired
    private final IClassService classService;
    @Operation(
            summary = "list all courses with pagination and ordering",
            description = "este empoint es para ver todos los cursos  devuelve un classEntityResp "
    )
    @GetMapping
    public ResponseEntity<Page<ClassEntityBasic>> getAllClasses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") SortType sort,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {

        Page<ClassEntityBasic> classes = classService.getAllBasic(page, size, sort, name, description);
        return ResponseEntity.ok().body(classes);
    }
    @Operation(
            summary = "list one course for the given id", description = "If the id is not null or empty, it is filtered by the id"
    )
    @GetMapping("/{id}/students")
    public ClassEntityResp getClassWithStudentsById(@PathVariable Long id) {
        return classService.getClassWithStudentsById(id);
    }

    @Operation(
            summary = "insert a new course",
            description = "you must send the request with the course is information"
    )
    @PostMapping
    public ResponseEntity<ClassEntityBasic> insert(@Validated @RequestBody ClassReq request){
        return ResponseEntity.ok(this.classService.create(request));
    }

    @Operation(
            summary = "list one course for the given id", description = "If the id is not null or empty, it is filtered by the id"
    )
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
