# Demo: Module 09 — Continuous Integration with GitHub Actions

**Duration:** 12 minutes  
**Prerequisite:** The Module 06 solution pushed to a public GitHub repository. GitHub Actions
is enabled on the repo (it is by default on all new repos).

---

## Part 1: What are we building? (2 min)

> "We have a working Spring Boot application with tests. Right now, tests only run when someone
> remembers to run them locally. CI means: every time code is pushed, the build and tests run
> automatically in the cloud. GitHub Actions is the CI system built directly into GitHub."

Show the GitHub Actions tab on a repo that has no workflows yet - it is empty.

> "A workflow is just a YAML file in .github/workflows/. GitHub detects it and runs it.
> No separate CI server to set up, no Jenkins to configure."

---

## Part 2: Walk through the workflow file (4 min)

Open `.github/workflows/ci.yml` from this demos folder:

```yaml
on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]
```

> "This is the trigger. The workflow runs on every push to main, and on every pull request
> targeting main. PRs are the important case - we want to know if a PR breaks the build
> BEFORE it is merged."

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
```

> "A job is a set of steps that run on the same machine. ubuntu-latest is a GitHub-hosted
> runner - a fresh virtual machine provisioned automatically for each run."

```yaml
    steps:
      - uses: actions/checkout@v4
```

> "uses: references a pre-built action. actions/checkout is the official action that clones
> your repository onto the runner. Without this, the runner has no code."

```yaml
      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: maven
```

> "This installs Java 21. cache: maven tells it to cache the ~/.m2 repository between runs.
> Without caching, Maven downloads all dependencies fresh every run - slow and wasteful."

```yaml
      - name: Build with Maven
        run: mvn -B package -DskipTests

      - name: Run tests
        run: mvn -B test
```

> "We separate build and test into two steps so the Actions UI shows them distinctly.
> -B means batch mode - no interactive prompts, cleaner log output."

---

## Part 3: Push and watch it run (4 min)

Copy the workflow file into the demo repository:

```bash
mkdir -p .github/workflows
cp demos/09-ci-github-actions/.github/workflows/ci.yml .github/workflows/
git add .github/workflows/ci.yml
git commit -m "Add CI workflow"
git push
```

Switch to the browser, open the Actions tab on the repo. Show:
- The workflow run appearing within seconds
- Clicking into the run to see the job
- Expanding each step to see the log output
- The green tick when it passes

Now introduce a deliberate test failure. Edit `StockServiceImpl` to make one test break,
commit and push:

> "Watch the Actions tab - it turns red. This is the point. CI catches the break immediately,
> before anyone else pulls the broken code."

Fix the code, push again, show it going green.

**Key message:** CI moves the feedback loop from 'someone finds it later' to 'the push is
rejected within minutes'. That is the entire value of CI.

---

## Part 4: Gradle callout (2 min)

Open `gradle-variant.yml`. Point out the differences:

```yaml
      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: gradle      # <-- gradle cache, not maven

      - name: Build with Gradle
        run: ./gradlew build -x test

      - name: Run tests
        run: ./gradlew test
```

> "The structure is identical. You swap the cache type, and swap mvn commands for gradlew.
> The Gradle wrapper (gradlew) is committed to the repo so the runner does not need Gradle installed."

---

## Instructor notes

- If the repo is private, Actions still works but the free tier has limited minutes for private repos
- The module 06 tests use Mockito and @WebMvcTest - they do not need a real database
- If you have integration tests that need MySQL, show the `services:` block (covered in slides)
- Build badge: Settings > Actions > General shows the badge markdown to paste into README
