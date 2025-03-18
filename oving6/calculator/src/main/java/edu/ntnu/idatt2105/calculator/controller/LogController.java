package edu.ntnu.idatt2105.calculator.controller;

import edu.ntnu.idatt2105.calculator.dto.EquationDTO;
import edu.ntnu.idatt2105.calculator.dto.UserDTO;
import edu.ntnu.idatt2105.calculator.service.CalculationService;
import edu.ntnu.idatt2105.calculator.service.LogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/calculator/log")
@RestController
@RequiredArgsConstructor
public class LogController {

    private final Logger logger = LoggerFactory.getLogger(CalculationController.class);
    private final CalculationService calculationService;
    private final LogService logService;


    @CrossOrigin(origins = "*")
    @RequestMapping("/load")
    @ResponseBody
    public ResponseEntity<List<EquationDTO>> loadData(@RequestBody UserDTO userDTO) {
        try {
            List<EquationDTO> equations = calculationService.getEquations(userDTO);
            logger.info(String.format("%s has accessed their log", userDTO.getUsername()));
            return ResponseEntity.ok(equations);
        }
        catch (Exception e) {
            logger.error(String.format("Failed to load log for %s", userDTO.getUsername()));
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<Object> delete() {
        try {
            logService.deleteLog();
            logger.info("Log has been deleted");
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            logger.error("Failed to delete log");
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
}
