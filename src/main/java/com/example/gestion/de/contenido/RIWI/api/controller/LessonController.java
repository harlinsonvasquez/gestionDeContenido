package com.example.gestion.de.contenido.RIWI.api.controller;

import com.example.gestion.de.contenido.RIWI.api.dto.request.LessonReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.LessonResp;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.ILessonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "lesson")
@AllArgsConstructor
public class LessonController {
    @Autowired
    private  final ILessonService lessonService;
    @PostMapping
    public ResponseEntity<LessonResp> insert(@RequestBody @Validated LessonReq request) {
        return ResponseEntity.ok(this.lessonService.create(request));
    }
}
