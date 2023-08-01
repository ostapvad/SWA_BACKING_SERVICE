## SWA Backing Service Implementation
### How to run
Create your docker image
```bash
mvn clean install -DskipTests=true
docker build -t=YOUR_CONT_NAME .
```
Run docker postgres container 
```
docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=main -e POSTGRES_DB=measurements --name=postgres_cont postgres
```
Run backing service container
```
docker run  YOUR_CONT_NAME 
```


## ToDo list

- [x] Basis functionality
- [x] Docker integration
- [ ] Resolve volume store for postgres 
- [ ] 

