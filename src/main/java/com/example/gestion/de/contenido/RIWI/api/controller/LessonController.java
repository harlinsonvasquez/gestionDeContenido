package com.example.gestion.de.contenido.RIWI.api.controller;

import com.example.gestion.de.contenido.RIWI.api.dto.request.LessonReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.LessonResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.ILessonService;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lessons")
@AllArgsConstructor
public class LessonController {

    @Autowired
    private final ILessonService lessonService;

    @PostMapping
    public LessonResp create(@RequestBody LessonReq lessonReq) {
        return lessonService.create(lessonReq);
    }

    @GetMapping("/{id}")
    public LessonResp get(@PathVariable Long id) {
        return lessonService.get(id);
    }

    @PutMapping("/{id}")
    public LessonResp update(@RequestBody LessonReq lessonReq, @PathVariable Long id) {
        return lessonService.update(lessonReq, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LessonResp> patchLessonStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        try {
            Status newStatus = Status.valueOf(status.toUpperCase());
            LessonResp updatedLesson = lessonService.patch(id, newStatus);
            return ResponseEntity.ok(updatedLesson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // status no válido
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // error interno del servidor
        }
    }

    @GetMapping
    public ResponseEntity<Page<LessonResp>> getAllLessons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") SortType sort)
    {

        Page<LessonResp> lessons = lessonService.getAll(page, size, sort);
        return ResponseEntity.ok(lessons);
    }


}
