package edu.ntnu.idatt2105.calculator.repository;

import edu.ntnu.idatt2105.calculator.model.User;
import edu.ntnu.idatt2105.calculator.model.Equation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface EquationRepository extends JpaRepository<Equation, Long> {
    Optional<List<Equation>> findAllByUser(User user);
    Optional<List<Equation>> findAllByUser_Username(String username);
}
