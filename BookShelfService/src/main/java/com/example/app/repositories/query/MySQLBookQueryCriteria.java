package com.example.app.repositories.query;

import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class MySQLBookQueryCriteria extends BasicQueryCriteria {

    private final String title;
    private final String genre;
    private final String publisher;
    private final Integer year;
    private final Float price;
    private final String description;

    public MySQLBookQueryCriteria(Map<String, String> params)
    {
        super(params);

        title = params.get("title");
        genre = params.get("genre");
        publisher = params.get("publisher");
        year = Ints.tryParse(params.getOrDefault("year", ""));
        price = Floats.tryParse(params.getOrDefault("price", ""));
        description = params.get("description");

        if(title != null) this.params.put("title", title);
        if(genre != null) this.params.put("genre", genre);
        if(publisher != null) this.params.put("genre", publisher);
        if(year != null) this.params.put("year", year);
        if(price != null) this.params.put("price", price);
        if(description != null) this.params.put("description", description);
    }

    @Override
    public String createQuery() {
        StringBuilder queryBuilder = new StringBuilder();

        if(verbose)
            queryBuilder.append("SELECT isbn, title, genre FROM books");
        else
            queryBuilder.append("SELECT * FROM books");

        // Building criteria for query
        StringBuilder criteriaBuilder = new StringBuilder();
        if (exactMatch)
        {
            for(Map.Entry<String, Object> e : this.params.entrySet())
            {
                if(e.getKey() == "offset" || e.getKey() == "items_per_page")
                    continue;
                criteriaBuilder.append(" AND " + e.getKey() + "=:" + e.getKey());
            }
        }
        else
        {
            for(Map.Entry<String, Object> e : this.params.entrySet())
            {
                if(e.getKey() == "offset" || e.getKey() == "items_per_page")
                    continue;
                criteriaBuilder.append(" AND " + e.getKey() + " LIKE '%:"+e.getKey()+"%'");
            }
        }

        if(offset != null)
        {
            criteriaBuilder.append(" ORDER BY isbn LIMIT :items_per_page OFFSET :offset");
        }

        /*
         todo
          match=true problema query
          verbose=true arunca exceptie
        */

        queryBuilder.append(criteriaBuilder);
        String res = queryBuilder.toString().replace("books AND", "books WHERE");
        return res;
    }
}
