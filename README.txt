Online Voting System

An online voting application built using Java Spring Boot, H2 Database, and HTML/CSS/JavaScript. The project provides voter registration, candidate management, online voting, and result viewing, along with a simple in-memory blockchain demonstration.

✨ Features

- 👤 Voter registration
- 🧑‍💼 Candidate management
- 🗳️ Online vote casting
- 📊 Election results
- 🔗 Basic blockchain demonstration
- 🗄️ H2 in-memory database
- 🌐 RESTful APIs

🛠️ Tech Stack

- Java 17
- Spring Boot 3.2.2
- Spring Data JPA
- H2 Database
- Maven
- HTML, CSS & JavaScript

🚀 How to Run

Backend

git clone https://github.com/SadafGhameria/OnlineVotingSystem.git
cd OnlineVotingSystem
mvn clean package
mvn spring-boot:run

Backend runs at:

http://localhost:8080

Frontend

Open "frontend/index.html" in a browser after starting the backend.

🔗 Main APIs

POST /api/voters/register
GET  /api/candidates
POST /api/candidates
POST /api/votes/cast
GET  /api/votes/results

🔗 Blockchain

The project includes a simple in-memory blockchain to demonstrate a tamper-evident sequence of vote records.

«⚠️ This is an educational project and is not intended for real-world elections.»

👩‍💻 Author

Sadaf Ghameria
