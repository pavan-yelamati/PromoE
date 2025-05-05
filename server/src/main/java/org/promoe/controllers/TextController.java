package org.promoe.controllers;


import org.promoe.domain.Result;
import org.promoe.domain.TextService;
import org.promoe.model.Text;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/generate")
public class TextController {
    private final TextService service;

    public TextController(TextService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> getText(@RequestBody String text) {
        Result<Text> result = service.getText(text);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

}

