package com.vytuto.mocmom.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vytuto.mocmom.engine.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
