package com.tgc.challenge.flagpicker.repository;

import com.tgc.challenge.flagpicker.model.Continet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagPickerRepository extends JpaRepository<Continet, String> {
    Continet findByContinent(String continent);
}
