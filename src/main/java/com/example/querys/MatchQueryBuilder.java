package com.example.querys;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public final class MatchQueryBuilder {

    private MatchQueryBuilder() {
    }

    public static SearchRequest searchQueryRequest(final QueryFields queryFields, final SearchMeta searchMeta) {
        final SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(searchMeta.getIndex());

//        List<Query> mustQueries = new ArrayList<>();
        List<Query> filterQuery = new ArrayList<>();
        List<Query> matchQuery = new ArrayList<>();

        if (searchMeta.getQueryType() == QueryType.MATCH){

            for (Map.Entry<String,Object> entry : queryFields.getTerm().entrySet()){
                var key = entry.getKey();
                var value = entry.getValue();

                if (key.equalsIgnoreCase("address")){
                    filterQuery.add(Query.of(q -> q.term(f -> f.field(key + ".keyword").value(value.toString()))));
                }
                else {
                    matchQuery.add(Query.of(q->q.match(m->m.field(key).query(value.toString()).fuzziness("AUTO"))));
                }
            }
        }

//        if (searchMeta.getQueryType() == QueryType.MATCH) {
//            for (Map.Entry<String, Object> entry : queryFields.getTerm().entrySet()) {
//
//
//                try {
//                    mustQueries.add(Query.of(q -> q.match(
//                            m -> m.field(entry.getKey())
//                                    .query(entry.getValue().toString())
//                                    .fuzziness("AUTO")
//                    )));
//                } catch (Exception e) {
//                    mustQueries.add(Query.of(q -> q.term(
//                            t -> t.field(entry.getKey())
//                                    .value((FieldValue) entry.getValue())
//                    )));
//                }
//            }
//        }

        return builder.query(q -> q.bool(b -> b.filter(filterQuery).must(matchQuery))).build();
    }

}



/**
 * this is match query
 */


//package com.example.querys;
//
//import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
//import co.elastic.clients.elasticsearch.core.SearchRequest;
//import org.springframework.stereotype.Component;
//
//@Component
//public final class MatchQueryBuilder {
//
//    private MatchQueryBuilder(){
//
//    }
//
//    /**
//     * creating na query builder method which return search query request
//     */
//
//    public static SearchRequest searchQueryRequest(final QueryFields queryFields, final SearchMeta searchMeta){
//        final SearchRequest.Builder builder = new SearchRequest.Builder();
//        builder.index(searchMeta.getIndex());
//
//
//        final MatchQuery.Builder matcherQuery = new MatchQuery.Builder();
//        if (searchMeta.getQueryType().name().equals(QueryType.MATCH.name())){
//            for (String s: searchMeta.getFields()){
//                matcherQuery.field(s);
//            }
//        }
//
//
//       return builder.query(matcherQuery.build()).build();
//    }
//}




