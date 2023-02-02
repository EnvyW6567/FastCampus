package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.PostLike;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class PostLikeRepository {
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    final private String TABLE = "PostLike";

    final static private RowMapper<Timeline> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Timeline
            .builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .postId(resultSet.getLong("postId"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public PostLike save(PostLike postLike){
        if (postLike.getId() == null){
            return insert(postLike);
        }
        else{
            throw new UnsupportedOperationException("Timeline은 갱신을 지원하지 않습니다.");
        }
    }

    public void save(List<Timeline> timelines){
        bulkInsert(timelines);
    }

    public List<Timeline> findAllByMemberIdAndOrderByIdDesc(Long memberId, int size){
        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY id DESC
                LIMIT :size
                """, TABLE);

        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public List<Timeline> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, int size){
        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId and id < :id
                ORDER BY id DESC
                LIMIT :size
                """, TABLE);

        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("id", id)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }
    private PostLike insert(PostLike postLike){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(postLike);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return PostLike
                .builder()
                .id(id)
                .memberId(postLike.getMemberId())
                .postId(postLike.getPostId())
                .createdAt(postLike.getCreatedAt())
                .build();
    }

    public Long getCount(Long postId){
        var sql = String.format("""
                SELECT count(id)
                FROM %s
                WHERE postId = :postId
                """, TABLE);
        var params = new MapSqlParameterSource().addValue("postId", postId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }
}
