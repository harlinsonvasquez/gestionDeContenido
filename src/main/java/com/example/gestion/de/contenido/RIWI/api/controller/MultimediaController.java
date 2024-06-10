package com.example.gestion.de.contenido.RIWI.api.controller;

import com.example.gestion.de.contenido.RIWI.api.dto.request.MultimediaReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.MultimediaBasicResp;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IMultimediaService;
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
public class MultimediaController {


    @Autowired
    private  final IMultimediaService multimediaService;
    @PostMapping
    public ResponseEntity<MultimediaBasicResp> createMultimedia(@RequestBody MultimediaReq request) {
        MultimediaBasicResp response = multimediaService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
