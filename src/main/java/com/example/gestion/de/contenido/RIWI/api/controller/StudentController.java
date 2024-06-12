package com.example.gestion.de.contenido.RIWI.api.controller;

import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IStudentService;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import com.example.gestion.de.contenido.RIWI.utils.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "student")//anotacion para la documentacion y cambiar el nombre en swagger
public class StudentController {
    @Autowired
    private final IStudentService studentService;

    @Operation(
            summary = "list all students with pagination and ordering",
            description = "If the name is not null or empty, it is filtered by the name and status " +
                    "otherwise it is only filtered by status"
    )
    @GetMapping
    public ResponseEntity<Page<StudentBasicResp>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") SortType sort,
            @RequestParam(required = false) String name) {

        Page<StudentBasicResp> students = studentService.getAllBasic(page, size, sort, name);
        return ResponseEntity.ok(students);
    }
    @Operation(
            summary = "list one student for the given id",
            description = "If the id is not null or empty, it is filtered by the id"
    )
    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentResp>get(@PathVariable Long id){
        return ResponseEntity.ok(this.studentService.get(id));
    }

    @Operation(
            summary = "insert a new student",
            description = "you must send the request with the student's information"
    )
    @PostMapping
    public ResponseEntity<StudentResp>insert(@Validated @RequestBody StudentReq request){
        return ResponseEntity.ok(this.studentService.create(request));
    }
    @ApiResponse(responseCode = "400", description = "id not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BadRequestException.class)))//debo crear los dtos para responder los errores
    @Operation(
            summary = "update an existing student",
            description = "you must send the request with the student's information and the id  the student to update"
    )
    @PutMapping("/{id}")
    public ResponseEntity<StudentResp>updateupdate(
            @Validated @RequestBody StudentReq request,
            @PathVariable Long id){
        return ResponseEntity.ok(this.studentService.update(request, id));
    }

    @Operation(summary = "update the status of an existing student", description = "you must send the request with the new status and the id of the student to update")
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
