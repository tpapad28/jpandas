package com.tpapad.jpandas.controller;

import com.tpapad.jpandas.controller.dtos.CalculationResult;
import com.tpapad.jpandas.service.PythonContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("calculations")
@RequiredArgsConstructor
public class CalculationsController {

    private final PythonContextService pythonContextService;

    @GetMapping(value = "mean", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalculationResult> meanOfRandomNumbers(@RequestParam(required = true) int size) {
        if (size < 0) {
            return ResponseEntity.badRequest().build();
        }

        final long startTime = System.nanoTime();
        final Double mean = pythonContextService.calculateMean(size);
        final long endTime = System.nanoTime();
        final Duration duration = Duration.ofNanos(endTime - startTime);
        return ResponseEntity.ok(new CalculationResult(mean, duration));

    }

}
