# Module 08 Lab — Introduction to Docker

## Objective
Write a multi-stage `Dockerfile` for the Stock Tracker Spring Boot application, build and run
it as a Docker container, then add a simple HTML/JS frontend served from inside the container.

## Prerequisites
- Docker Desktop installed and running (`docker --version` should respond)
- Java 21 and Maven available locally (to verify the app builds before containerising)
- The application code in this folder is complete — no Java changes are needed

## What is provided
The `labs/08-docker/` folder contains a complete Spring Boot application that uses an H2
in-memory database (no MySQL required). It exposes:
- `GET  /api/stocks`           — list all stocks (JSON)
- `GET  /api/stocks/{id}`      — get stock by id
- `POST /api/stocks`           — add a stock
- `GET  /api/stocks/{id}/prices` — price history

On startup it seeds three stocks (HSBA, BP, AAPL) automatically.

---

## Steps

### Step 1 — Verify the app builds locally
```bash
cd labs/08-docker
mvn package -DskipTests
java -jar target/*.jar
```
Visit `http://localhost:8080/api/stocks` — you should see a JSON array of three stocks.
Stop the app (`Ctrl+C`) before moving on.

---

### Step 2 — Write the Dockerfile
Create a file called `Dockerfile` (no extension) in the `labs/08-docker/` folder.
Complete each TODO below:

```dockerfile
# Stage 1: build the JAR
# TODO 1: Start FROM the maven:3.9-eclipse-temurin-21 image. Name this stage 'build'.
FROM ???

WORKDIR /app

# TODO 2: Copy pom.xml into the working directory
COPY ??? .

# TODO 3: Download all dependencies into the image layer cache
#         Hint: mvn dependency:go-offline -q
RUN ???

# TODO 4: Copy the src directory into the image
COPY ??? ./src

# TODO 5: Package the application, skipping tests
#         Hint: mvn package -DskipTests -q
RUN ???

# Stage 2: run the JAR in a minimal image
# TODO 6: Start FROM eclipse-temurin:21-jre-alpine
FROM ???

WORKDIR /app

# TODO 7: Copy the JAR from the build stage into this image as app.jar
#         Hint: use --from=build and /app/target/*.jar
COPY ??? app.jar

# TODO 8: Tell Docker which port the app listens on
EXPOSE ???

# TODO 9: Set the command to run the JAR
#         Hint: use ENTRYPOINT with JSON array syntax
ENTRYPOINT ???
```

---

### Step 3 — Build the image
```bash
docker build -t stock-app:v1 .
```

Watch the output. Notice each step creates a layer. Note how the FROM and dependency download
steps are the slowest — they only run once unless `pom.xml` changes.

---

### Step 4 — Run the container
```bash
docker run -d -p 8080:8080 --name stock-lab stock-app:v1
```

Verify it is running:
```bash
docker ps
docker logs stock-lab
```

Test the API:
```bash
curl http://localhost:8080/api/stocks
```

---

### Step 5 — Add a frontend
Create `src/main/resources/static/index.html` with an HTML page that:
1. Has a heading "Stock Tracker"
2. Uses `fetch('/api/stocks')` to load stocks from the API
3. Displays them in a `<table>` with columns: Symbol, Company, Sector, Exchange

Rebuild and re-run:
```bash
docker stop stock-lab && docker rm stock-lab
docker build -t stock-app:v2 .
docker run -d -p 8080:8080 --name stock-lab stock-app:v2
```

Open `http://localhost:8080` in a browser — your table should appear.

---

### Step 6 — Explore useful Docker commands
```bash
docker ps                          # list running containers
docker ps -a                       # include stopped containers
docker logs stock-lab              # view application output
docker exec -it stock-lab sh       # open a shell inside the container
docker stop stock-lab              # stop the container
docker rm stock-lab                # remove the container
docker images                      # list local images
docker rmi stock-app:v1            # remove an image
```

---

## Acceptance Criteria
- `docker build` completes without errors
- `docker run -p 8080:8080` starts the container and `GET /api/stocks` returns three stocks
- The second build (after `pom.xml` is unchanged) shows `CACHED` for the dependency layer
- `http://localhost:8080` serves your HTML page with the stocks table populated

## Key Questions
1. Why do we COPY `pom.xml` and run `mvn dependency:go-offline` *before* copying `src/`?
2. Why does the final image use `eclipse-temurin:21-jre-alpine` instead of the Maven image?
3. What is the difference between `EXPOSE` and `-p` in `docker run`?
4. What happens to the H2 data when the container is stopped and restarted?
5. How would you pass a different port to the app at runtime without rebuilding the image?
