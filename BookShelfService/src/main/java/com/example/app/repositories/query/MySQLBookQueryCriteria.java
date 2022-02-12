package com.example.app.repositories.query;

import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;

import java.util.Map;

public class MySQLBookQueryCriteria extends BasicQueryCriteria {

    private final String title;
    private final String genre;
    private final String publisher;
    private final Integer year;
    private final Float price;
    private final String description;

    public MySQLBookQueryCriteria(Map<String, String> params) {
        super(params);

        title = params.get("title");
        genre = params.get("genre");
        publisher = params.get("publisher");
        year = Ints.tryParse(params.getOrDefault("year", ""));
        price = Floats.tryParse(params.getOrDefault("price", ""));
        description = params.get("description");

        if (title != null) this.queryParams.put("title", title);
        if (genre != null) this.queryParams.put("genre", genre);
        if (publisher != null) this.queryParams.put("genre", publisher);
        if (year != null) this.queryParams.put("year", year);
        if (price != null) this.queryParams.put("price", price);
        if (description != null) this.queryParams.put("description", description);
    }

    @Override
    public String createQuery() {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM books");

        // Building criteria for query
        StringBuilder criteriaBuilder = new StringBuilder();
        if (exactMatch) {
            for (Map.Entry<String, Object> paramEntry : this.queryParams.entrySet()) {
                criteriaBuilder.append(" AND ").append(paramEntry.getKey()).append("= ").append("?");
            }
        } else {
            for (Map.Entry<String, Object> paramEntry : this.queryParams.entrySet()) {
                criteriaBuilder.append(" AND ").append(paramEntry.getKey()).append(" LIKE ?");
                this.queryParams.put(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
            }
        }

        if (offset != null) {
            criteriaBuilder.append(" ORDER BY isbn LIMIT :items_per_page OFFSET :offset");
        }

        queryBuilder.append(criteriaBuilder);
        return queryBuilder.toString().replace("books AND", "books WHERE");
    }
}
