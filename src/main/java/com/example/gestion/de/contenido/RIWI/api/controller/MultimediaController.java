package com.example.gestion.de.contenido.RIWI.api.controller;

import com.example.gestion.de.contenido.RIWI.api.dto.request.MultimediaReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.MultimediaBasicResp;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IMultimediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "multimedia")
@AllArgsConstructor
@Tag(name = "multimedia")
public class MultimediaController {


    @Autowired
    private  final IMultimediaService multimediaService;

    @Operation(
            summary = "insert a new content multimedia",
            description = "you must send the request with the multimedia information"
    )
    @PostMapping
    public ResponseEntity<MultimediaBasicResp> createMultimedia(@RequestBody MultimediaReq request) {
        MultimediaBasicResp response = multimediaService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
