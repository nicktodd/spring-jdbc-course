# Demo: Module 08 — Introduction to Docker

**Duration:** 10 minutes  
**Prerequisite:** Java 21 and Maven installed locally; Docker Desktop running; demo app in this folder builds cleanly with `mvn package -DskipTests`

---

## Part 1: The problem Docker solves (2 min)

Open a terminal and show the app running locally without Docker:

```bash
cd demos/08-docker
mvn spring-boot:run
```

While it starts, narrate:

> "This works on my machine because I have Java 21, Maven, and the right environment. If I hand this JAR to a colleague or a server, they need exactly the same setup. Docker packages the app *and* its environment together into an image."

Stop the app (`Ctrl+C`).

**Key talking point:** An image is the recipe; a container is the running instance — like a class vs. an object in Java.

---

## Part 2: Inspect the Dockerfile (3 min)

Open `Dockerfile` in the editor and walk through it line by line:

```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS build
```
> "We start FROM an existing image that already has Maven and Java 21. The AS build gives this stage a name so we can reference it later."

```dockerfile
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
```
> "We copy the pom.xml *before* the source code. Docker caches each layer. If only the source changes, Docker reuses this cached dependency-download layer — much faster rebuilds."

```dockerfile
COPY src ./src
RUN mvn package -DskipTests -q
```
> "Now we copy source and compile. Skipping tests here because CI will run them separately."

```dockerfile
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```
> "The second FROM starts a brand-new, much smaller image — just a JRE, no Maven, no source code. We COPY only the JAR from the build stage. This is called a multi-stage build. The final image is typically 10x smaller than if we used the Maven image."

---

## Part 3: Build and run (4 min)

Build the image:

```bash
docker build -t stock-app:demo .
```

While building, point out the layer output — especially the cache hits on a second build:

```bash
docker build -t stock-app:demo .
```

> "Notice CACHED next to the dependency-download step — that layer didn't run again because pom.xml didn't change."

Run the container:

```bash
docker run -d -p 8080:8080 --name stock-demo stock-app:demo
```

Explain the flags:
- `-d` — detached (runs in background)
- `-p 8080:8080` — maps host port 8080 to container port 8080
- `--name` — gives the container a friendly name

Open `http://localhost:8080` in a browser — the stock tracker frontend appears.

Show useful CLI commands:

```bash
docker ps                        # running containers
docker logs stock-demo           # app log output
docker exec -it stock-demo sh    # shell inside the container
docker stop stock-demo           # stop
docker rm stock-demo             # remove
```

**Key message:** Docker makes the environment part of the deliverable. The same image runs identically on any machine with Docker installed.
