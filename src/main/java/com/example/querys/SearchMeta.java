package com.example.querys;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SearchMeta {
    private List<String> fields;
    private String index;
    private QueryType queryType;
}
