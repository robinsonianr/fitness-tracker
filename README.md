# FitnessTracker

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2da72f82744a4ef682f455cd080f427f)](https://app.codacy.com/gh/robinsonianr/fitness-tracker/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade) ![example workflow](https://github.com/robinsonianr/fitness-tracker/actions/workflows/cd-deploy.yml/badge.svg)

## Table of Contents

-   [Installation](#installation)
-   [Usage](#usage)
-   [Contributing](#contributing)


## Installation
To install this project, follow these steps:

Java 17 compatible & uses Gradle

1.  Clone the repository.
   ```sh
   git clone https://github.com/robinsonianr/fitness-tracker.git
   ```
   
2.  Navigate to the project directory.
   - cd src/main/ui

5.  Install dependencies.
   ```sh
   run npm install
   ```
   
7.  Database setup
   -   Install Docker Desktop or setup Docker in terminal
  
   -   run docker pull postgres or search postgres in docker desktop and pull image
    
   ```sh
   run docker network create db
   run docker run --name (my-postgres-container) -p 5432:5432 --network=db -v dbdata:/var/lib/postgres/data -e POSTGRES_PASSWORD=root -e POSTGRES_DB=robinsonir -d postgres
   ```
   
   replace (my-postgres-container) with whatever name you like
      
   run docker ps to see if container is running if not, run docker start (container-name)


## Usage

Here's how you can use this project:

1. Build and run the application.
   -   build and run springboot application. use prefered IDE

2. run npm start in src/main/ui
   -   Open a web browser and navigate to [http://localhost:3000](http://localhost:3000).


## Contributing

We welcome contributions from the community. To contribute to this project, please follow these guidelines:

1.  Fork the repository and clone it to your local machine.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes and test them thoroughly.
4.  Submit a pull request with a clear description of your changes.
