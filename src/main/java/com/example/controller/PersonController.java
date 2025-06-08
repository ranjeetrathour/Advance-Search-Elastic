package com.example.controller;

import com.example.modle.Person;
import com.example.querys.QueryFields;
import com.example.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<String> createPerson(@RequestBody Person person) {
        personService.savePerson(person);
        return ResponseEntity.ok("Person saved successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<Person> getPersonByName(@RequestParam String name) {
        Optional<Person> person = personService.findPersonByName(name);
        return person.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/find-by-match")
    public ResponseEntity<List<Person>> getAllMatchingPerson(@RequestBody QueryFields queryFields){
        List<Person> results = personService.findAllMatchingPerson(queryFields);
        if (!results.isEmpty()) {
            return ResponseEntity.ok(results);
        }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
