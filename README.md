# UBC SlotFinder

A deployed web application that monitors UBC academic-advising appointments and emails students when new appointment slots become available.

**Live Demo:** [UBC SlotFinder](https://main.d3khqm2y4ubb0n.amplifyapp.com)

UBC SlotFinder replaces constantly refreshing the appointments-page with automated monitoring.

![UBC SlotFinder appointment monitoring form](docs/Asstes/slotfinder-main.png)

## How It Works

1. A student creates a watch-request with their email, appointment type, and advisor preference.
2. The Spring Boot backend stores the request in PostgreSQL.
3. Every 60 seconds, a scheduled job checks the Comm100 booking API for matching appointment-slots.
4. When a matching slot is found, SlotFinder checks PostgreSQL to avoid sending a duplicate notification.
5. The user receives an email alert and can unsubscribe through a token-based link without creating an account.

## Architecture

SlotFinder is split into a separately deployed React frontend and Spring Boot backend. The frontend is hosted on AWS Amplify, while API requests are sent through CloudFront to the backend running on AWS Elastic Beanstalk. The backend persists watch requests and notification history in Amazon RDS for PostgreSQL, polls the Comm100 booking API for appointment availability, and sends email alerts through Gmail SMTP.

![SlotFinder architecture diagram](docs/Asstes/architecture-diagram.png)

## Tech Stack

| Area | Technologies |
| --- | --- |
| **Frontend** | React, Vite, JavaScript, HTML/CSS |
| **Backend** | Java, Spring Boot, Spring MVC, Spring Data JPA |
| **Database** | PostgreSQL, Hibernate |
| **Infrastructure** | AWS Amplify, CloudFront, Elastic Beanstalk, EC2, RDS |
| **Integrations** | Comm100 Booking API, Gmail SMTP |
| **Development** | Maven, Git, GitHub, Swagger / OpenAPI |


## Future Improvements

- **Automatic appointment booking** — Extend SlotFinder from just monitoring appointments to actually booking the appointment for the user.
- **Notification batching** — Combine multiple matching appointment slots into a single email rather than sending individual notifications.
- **Email infrastructure** — Move from Gmail SMTP to a transactional email provider as usage grows.

