package com.gamesys.testtask;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public ItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createItemTable();
    }

    private void createItemTable() {
        String sql = "drop table if exists ITEM; " +
                "create table if not exists ITEM (" +
                "id bigint auto_increment primary key, " +
                "guid varchar, " +
                "pubDate varchar, " +
                "title varchar, " +
                "link varchar, " +
                "description varchar, " +
                "category varchar," +
                "created timestamp default current_timestamp )";
        jdbcTemplate.execute(sql);
    }

    @Transactional
    public int[] save(List<Item> items) {
        String sql = "insert into ITEM (guid, pubDate, title, link, description, category) values (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, items.get(i).getGuid());
                ps.setString(2, items.get(i).getPubDate());
                ps.setString(3, items.get(i).getTitle());
                ps.setString(4, items.get(i).getLink());
                ps.setString(5, items.get(i).getDescription());
                ps.setString(6, items.get(i).getCategory());
            }
            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }

    @Transactional
    public List<Item> findLastTen() {
        String sql = "select guid, pubDate, title, link, description, category from ITEM order by id desc limit 10";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Item(
                    rs.getString("guid"),
                    rs.getString("pubDate"),
                    rs.getString("title"),
                    rs.getString("link"),
                    rs.getString("description"),
                    rs.getString("category")
                    )
        );
    }
}
