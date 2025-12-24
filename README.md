# Java Chat Application

A multi-user chat application with direct messaging built with Java Swing and PostgreSQL.

## Prerequisites

- Java JDK 8 or higher
- PostgreSQL database
- PostgreSQL JDBC driver (included in `lib/postgresql.jar`)

## Project Structure

```
JavaChatApp/
├── src/
│   ├── ClientServerNetwork/  # Server and client networking code
│   └── GUI/                   # Chat UI components
├── lib/                       # External dependencies
│   └── postgresql.jar         # PostgreSQL JDBC driver
├── out/                       # Compiled class files
└── *.jar                      # Pre-built executables
```

## Compilation

### Compile Server
```bash
javac -d out -cp lib/postgresql.jar src/ClientServerNetwork/Server.java src/ClientServerNetwork/DatabaseHelper.java
```

### Compile Client
```bash
javac -d out -cp lib/postgresql.jar src/ClientServerNetwork/*.java src/GUI/*.java
```

### Build JARs (optional)
```bash
# Server JAR
jar cvf Server.jar -C out .

# Client JAR  
jar cvf JavaChatApp.jar -C out .
```

## Running the Application

### Start the Server
```bash
java -cp "out:lib/postgresql.jar" ClientServerNetwork.Server
```

Or using the pre-built JAR:
```bash
java -cp "Server.jar:lib/postgresql.jar" ClientServerNetwork.Server
```

### Start the Client
```bash
java -cp "out:lib/postgresql.jar" GUI.ChatRoom
```

Or using the pre-built JAR:
```bash
java -cp "JavaChatApp.jar:lib/postgresql.jar" GUI.ChatRoom
```

**Note for Windows users:** Replace `:` with `;` in classpath arguments.

## Database Setup

1. Ensure PostgreSQL is running
2. Create the database and tables:
   ```bash
   psql -U postgres
   CREATE DATABASE chatappdb;
   \c chatappdb
   \i schema.sql
   ```
3. Update database credentials in `src/ClientServerNetwork/DatabaseHelper.java` if needed:
   - Default database: `chatappdb`
   - Default user: `daltongorham`
   - Default password: (empty)
