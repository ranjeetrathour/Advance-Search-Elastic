package com.example.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.modle.Person;
import com.example.querys.MatchQueryBuilder;
import com.example.querys.QueryFields;
import com.example.querys.QueryType;
import com.example.querys.SearchMeta;
import com.example.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ElasticsearchClient elasticsearchClient;
    private final MatchQueryBuilder matchQueryBuilder;

    public void savePerson(Person person) {
        person.setUuid(UUID.randomUUID().toString());
        personRepository.save(person);
    }

    public Optional<Person> findPersonByName(String name) {
        return personRepository.findByName(name);
    }

    public List<Person> findAllMatchingPerson(QueryFields queryFields){
        try {
            SearchRequest request = MatchQueryBuilder.searchQueryRequest(queryFields, new SearchMeta(List.of("name","address"),"person", QueryType.MATCH));

            SearchResponse<Person> response = elasticsearchClient.search(request, Person.class);

         return response.hits().hits()
                    .stream()
                    .map(Hit::source)
                    .toList();

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }    }
}
