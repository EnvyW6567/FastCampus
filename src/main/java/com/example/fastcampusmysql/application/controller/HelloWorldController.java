package com.example.fastcampusmysql.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HelloWorldController {

    final private NamedParameterJdbcTemplate jdbcTemplate;

    @GetMapping
    public Integer helloWorld(){
        return jdbcTemplate.queryForObject("SELECT 1", new MapSqlParameterSource(), Integer.class);
    }

}
