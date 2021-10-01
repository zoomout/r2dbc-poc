package com.zoomout.r2dbcpoc;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
class MyData {
    String id;
    String name;
}
