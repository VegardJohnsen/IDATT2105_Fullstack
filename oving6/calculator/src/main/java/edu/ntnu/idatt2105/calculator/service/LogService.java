package edu.ntnu.idatt2105.calculator.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ntnu.idatt2105.calculator.repository.EquationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {

    private final EquationRepository equationRepository;
    private final Logger logger = LoggerFactory.getLogger(LogService.class);

    public void deleteLog() {
        equationRepository.deleteAll();
        logger.info("Log has been deleted");
    }

    
}
