# Atipera-Task

The goal of this project was to create an endpoint that returns the user's GitHub repositories and their branches in the correct format.


## Getting Started

### Prerequisites
- Java Development Kit (JDK) 17 or above
- Maven

### Installation

Clone the repository

```bash
git clone https://github.com/monczall/atipera-task.git
```

Navigate to project directory and \
Use maven install to create application jar.

```bash
mvn clean install
```

### Starting the server

Execute command

```bash
java -jar target/atipera-task-1.0.jar
```
Server will run on localhost:8080

## API Endpoints

- 'GET /user/{userName} - Get User's repositories (that are not forks) and their branches

## API Response

### Example response from '/user/monczall'

```json
[
    {
        "repositoryName": "InzynieriaOprogramowania",
        "ownerLogin": "monczall",
        "branchList": [
            {
                "name": "main",
                "lastCommitSha": "f5766ff16e0043c906779a68634420b1a7e298c2"
            }
        ]
    },
    {
        "repositoryName": "monczall",
        "ownerLogin": "monczall",
        "branchList": [
            {
                "name": "main",
                "lastCommitSha": "cdcf4560f633679266477153ca34760c3dbd231b"
            }
        ]
    },
    {
        "repositoryName": "MyPokemonList",
        "ownerLogin": "monczall",
        "branchList": [
            {
                "name": "master",
                "lastCommitSha": "bff552a7d64d4a50a74a498241bc0e0b7dda3e41"
            }
        ]
    },
    {
        "repositoryName": "Projekt-Proseminarium",
        "ownerLogin": "monczall",
        "branchList": [
            {
                "name": "main",
                "lastCommitSha": "0744fbe3b69d8540d552347f86006f95cbbafb9f"
            }
        ]
    }
]
```

### Response for non-existing user

```json
{
    "status": 404,
    "message": "User not found"
}
```

### Response for not accepted header

```json
{
    "status": 406,
    "message": "Unsupported media type"
}
```
