# Role based server auth
Sample Spring boot auth server application based on user privileges
## 1. Local usage

### 1.1 Usage

#### Build and run server
Build and start application as docker container in test vis-network
 ```zsh
./build.sh
 ```

Application starts at port 8081 (see `application.properties` file) and expose it outside docker network on host machine.

#### Stop server
For stopping application container you have to run script:
```zsh
./stop.sh
```

#### Log in credentials
username: admin
password: admin