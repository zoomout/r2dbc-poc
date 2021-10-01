package com.zoomout.r2dbcpoc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class MyController {

    private final MyDataRepository myDataRepository;

    public MyController(MyDataRepository myDataRepository) {
        this.myDataRepository = myDataRepository;
    }

    @GetMapping(path = "/api/{id}")
    public Mono<MyData> getMyData(@PathVariable String id) {
        return myDataRepository.getMyData(id).map(myDataEntity -> MyData.builder().id(myDataEntity.getId()).name(myDataEntity.getName()).build());
    }

    @PutMapping(path = "/api/{id}")
    public Mono<Long> postMyData(@PathVariable String id, @RequestParam(name = "name") String name) {
        return myDataRepository.upsertMyData(List.of(MyDataEntity.builder().id(id).name(name).build()));
    }

}
