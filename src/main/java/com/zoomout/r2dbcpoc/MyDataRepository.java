package com.zoomout.r2dbcpoc;

import io.r2dbc.spi.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Slf4j
public class MyDataRepository {

    private static final String INSERT_OR_UPDATE_TABLE_FOR_ID_AND_COLUMN = "INSERT INTO {0} ({1}, {2}) VALUES ($1, $2) ON CONFLICT ({1}) DO UPDATE SET {2} = EXCLUDED.{2}";
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<MyDataEntity> getMyData(String id) {
        return Mono.defer(() -> r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").is(id)), MyDataEntity.class));
    }

    public Mono<Long> upsertMyData(List<MyDataEntity> myDataEntities) {
        return Mono.defer(() -> r2dbcEntityTemplate.getDatabaseClient().inConnectionMany(connection -> {
                    var statement = connection.createStatement(MessageFormat.format(INSERT_OR_UPDATE_TABLE_FOR_ID_AND_COLUMN, "my_data", "id", "name"));
                    for (var data : myDataEntities) {
                        statement
                                .bind(0, data.getId())
                                .bind(1, data.getName())
                                .add();
                    }
                    return Flux.from(statement.execute()).flatMap(Result::getRowsUpdated);
                }).collectList().map(count())
        );
    }

    public static Function<List<Integer>, Long> count() {
        return quantities -> quantities.stream().reduce(0, Integer::sum).longValue();
    }

}
