package com.example.repository;

import com.example.modle.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends ElasticsearchRepository<Person, String> {

    Optional<Person> findByName(String name);
}
