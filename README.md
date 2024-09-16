# University Manager Application

## Tools

Before you begin, ensure you have the following tools installed on your machine:

1. **Java 17** - The application is built with Java 17. You can download and install it from [here](https://jdk.java.net/17/).
2. **Apache Maven** - This is required for building the project. You can download it from [here](https://maven.apache.org/download.cgi).
3. **Docker & Docker Compose** - Docker is used to containerize the application and run the PostgreSQL database. You can download Docker [here](https://www.docker.com/products/docker-desktop).

## Getting Started


First, clone this repository to your local machine using the following command:

```bash
git clone https://github.com/Korbut-Dima/university-manager.git
cd university-manager
mvn clean install -DskipTests
docker-compose up
java -jar target/UniversityManager-0.0.1-SNAPSHOT.jar
