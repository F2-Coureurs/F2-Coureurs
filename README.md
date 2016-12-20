# F2 Coureurs
[![Build Status](https://travis-ci.org/F2-Coureurs/F2-Coureurs.svg?branch=master)](https://travis-ci.org/F2-Coureurs/F2-Coureurs)

## Goal of project
Through this project, we want to improve our programming skills using the knowledge we have gained. We are aiming to build a fully-functional, well-working software system from a “vague” assignment, make a beneficial use of version control, set up a proper UML class diagram and realistic planning. We aspire to be “lazy” in a smart and safe way.

The end-result of this project is a game called ‘F1 Manager’. This game allow the player to build his/her own Formula 1 team with a budget. Examples of things the player can change in this team are the driver, the strategist, the aerodynamics and the engine. During the game the team will compete in a Formula 1 season. The team (that can be edited before every race) gets money for every scored point in a race. The player wins if at the end of a season the team has scored the most points.

## Installing
### Prerequisites
- Java
- Maven

### Installation and contribution procedures
1. Fork the project
2. Get the project from GitHub
`git clone https://github.com/[USERNAME]/F2-Coureurs.git`
3. Add remote upstream
`git remote add upstream https://github.com/F2-Coureurs/F2-Coureurs.git`
4. Create new feature branch
`git checkout -b [BRANCH-NAME]`
5. Contribute. Please read [this](https://https://github.com/F2-Coureurs/F2-Coureurs/wiki/How-to-contribute) before contributing
6. Push changes to your own fork
`git push origin [BRANCH-NAME]`
7. Create pull request
8. Ask someone to merge your changes

## Tests
Tests can be run using: 
`mvn test`

### Project reports
You can create a project report using:
`mvn site`

You can check your code for checkstyle errors with:
`mvn checkstyle:check -Dcheckstyle.config.location=TI1216.checkstyle.xml -Dcheckstyle.violationSeverity=warning`

Project reports can be found here [here](https://github.com/F2-Coureurs/F2-Coureurs/tree/master/project/reports).

## Team members

Stephan Tromer (stromer) 4177398

![Image of Stephan](team-photos/stephan.jpg)

Matthijs van Niekerk (mrsvanniekerk) 4608976

![Image of Matthijs](team-photos/matthijs.jpg)

Alex Settels (asettels) 4455622

![Image of Alex] (team-photos/alex.jpg)

Yin Wu (jiayinysj) 4530454

![Image of Yin] (team-photos/yin.jpg)