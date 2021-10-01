# R2DBC demo

## Start docker
./start.sh

## Stop docker
./stop.sh

## Test api
```
curl --request PUT 'localhost:8088/api/1?name=hello'
curl --request GET 'localhost:8088/api/1'
```

## Run performance test
```
./gradlew gatlingRun-MySimulation --rerun-tasks
```