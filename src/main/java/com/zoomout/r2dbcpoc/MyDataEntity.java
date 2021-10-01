package com.zoomout.r2dbcpoc;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Builder
@Table(value = MyDataEntity.TABLE_NAME)
public class MyDataEntity {

    public static final String TABLE_NAME = "my_data";
    @Id
    String id;
    String name;

}
