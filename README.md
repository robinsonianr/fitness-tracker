# Fitness Tracker

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2da72f82744a4ef682f455cd080f427f)](https://app.codacy.com/gh/robinsonianr/fitness-tracker/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)![example workflow](https://github.com/robinsonianr/fitness-tracker/actions/workflows/backend-cd.yml/badge.svg) ![example workflow](https://github.com/robinsonianr/fitness-tracker/actions/workflows/frontend-cd.yml/badge.svg)

## Table of Contents

-   [Installation](#installation)
-   [Usage](#usage)
-   [Contributing](#contributing)


## Installation

To install this project, follow these steps:

Java 17 (Liberica or Zulu) compatible & uses Gradle 7.6.1

1. Clone the repository.
   
    ```sh
    git clone https://github.com/robinsonianr/fitness-tracker.git
    ```
   
2. Navigate to the project directory.
   
    -   cd `fit-track-ui/react`

3. Install dependencies.
   
    ```sh
    npm install
    ```
   
4. Database setup
   
   - Install Docker Desktop or setup Docker in terminal
     
   - Run `docker pull postgres` or search for 'postgres' on Docker Desktop and pull the image.
       
   ```sh
   docker network create db
   docker run --name (my-postgres-container) -p 5432:5432 --network=db -v dbdata:/var/lib/postgres/data -e POSTGRES_PASSWORD=root -e POSTGRES_DB=(postgres-db-name) -d postgres
   ```

   - Replace (my-postgres-container) and (postgres-db-name) with whatever name you like
      
   - Run `docker ps` to see if the container is running; if not, run `docker start (container-name)`
  
   - In fit-track/src/main/resources/application.yml, update/replace the password and end of the URL of `spring.datasource` with password when creating container and (postgres-db-name).


## Usage

Here's how you can use this project:
1. Build and run the application.
   - Build and run the application (recommend Intellij but can use your preferred IDE).
2. Run `npm run start` in `fit-track-ui/react`
   - Open a web browser and navigate to [http://localhost:5173](http://localhost:5173).

Dev Site:
- http://fitness-tracker-env.eba-3f5efq3k.us-east-1.elasticbeanstalk.com

Test Account Credentials:
- Email: test123@gmail.com
- Password: Test123


## Contributing

We welcome contributions from the community. To contribute to this project, please follow these guidelines:

1. Fork or clone the repository  to your local machine.
2. Create a new branch for your feature or bug fix.
3. Make your changes and test them thoroughly.
4. Submit a pull request and fill out the PR template with details of changes.
