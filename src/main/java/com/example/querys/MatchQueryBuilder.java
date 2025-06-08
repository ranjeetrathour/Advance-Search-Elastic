package com.example.querys;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import org.springframework.stereotype.Component;

@Component
public final class MatchQueryBuilder {

    private MatchQueryBuilder(){

    }

    /**
     * creating na query builder method which return search query request
     */

    public static SearchRequest searchQueryRequest(final QueryFields queryFields, final SearchMeta searchMeta){
        final SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(searchMeta.getIndex());


        final MatchQuery.Builder matcherQuery = new MatchQuery.Builder();
        if (searchMeta.getQueryType().name().equals(QueryType.MATCH.name())){
            for (String s: searchMeta.getFields()){
                matcherQuery.field(s);
            }
            matcherQuery.query(queryFields.getTerm());
        }


       return builder.query(matcherQuery.build()).build();
    }
}
