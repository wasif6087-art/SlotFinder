# SlotFinder

## Problem

At UBC the demand for Academic Advising Appointments skyrockets during the course registration period. 

There are only 11 advisors for the enitre university. Advising slots are are limited and released on a rolling basis. This creates frustration - students repeatedly refresh the booking page throughout the day hoping to book a slot before some else takes it.

Students are forced to manually check the page many times throughout the day - most checks result in no available appointments. Student dont know when exactly new slots will appear, if they appeared at all or whether they missed newly opened slots because someone else took them: ANXIETY.

SlotFinder monitors appointment availability and sends alerts to users exactly when new slots drop.

### Current User Pain

1. Student needs advising appointment.
2. Student checks the booking page.
3. No slot is available.
4. Student keeps refreshing throughout the day.
5. Slot may appear, but student might miss it.
6. Student feels stressed and wastes time manually checking


## Solution

SlotFinder continuously monitors advising appointment availability. Users can create a watchlist based on preferred advisors. When appointment becomes available, SlotFinder immediately notifies the user so they can book before the slot is taken.



## User Stories

1. As a student, I want to enter my email address so that SlotFinder knows where to send appointment availability notifications.

2. As a student, I want to choose whether I am looking for a virtual appointment or an in-person appointment, so that I only receive alerts for the appointment type I care about.

3. As a student, I want to choose whether I am open to ANY advisor or only a specific advisor so that the alerts match my advising preference.

4. As a student, I want SlotFinder to monitor appointment availability automatically so that I do not have to repeatedly refresh the advising website myself.

5. As a student, I want to receive an email notification when an appointment becomes available so that I can manually book the appointment before it is taken.

6. As a student, I want a simple way to stop receiving notifications once I no longer need an appointment so that SlotFinder does not keep emailing me unnecessarily.

## MVP Scope

The first version of SlotFinder will allow users to:

- Enter an email address
- Select appointment type (virtual or in-person)
- Select advisor preference (specific advisor or any advisor)
- Submit a watch request
- Receive email notifications when matching appointments become available
- Stop notifications when they no longer need an appointment

The MVP will not include:

- User accounts
- Login/logout
- Passwords
- Dashboards
- Analytics
- Date/time filtering
- Mobile application
- Automatic appointment booking

## Data Models

### WatchRequest

- id
- email
- appointmentType
- advisorPreference
- active
- createdAt

### AppointmentSlot

- id
- advisorName
- appointmentType
- appointmentDateTime
- detectedAt
- source

### Notification

- id
- watchRequestId
- appointmentSlotId
- email
- sentAt
- status


## User Flow

Student visits SlotFinder

↓

Student enters email address

↓

Student selects appointment type
(Virtual or In-Person)

↓

Student selects advisor preference
(Specific Advisor or Any Advisor)

↓

Student submits watch request

↓

SlotFinder stores the request

↓

SlotFinder monitors appointment availability

↓

Matching appointment becomes available

↓

SlotFinder sends email notification

↓

Student receives notification

↓

Student manually books appointment

↓

Student stops monitoring

## System Flow

Watch Request Submitted

↓

Store WatchRequest in Database

↓

Scheduler Runs Periodically

↓

Check Advising Website

↓

Extract Available Appointments

↓

Create AppointmentSlot Records

↓

Compare Appointments Against Watch Requests

↓

Matching Appointment Found

↓

Create Notification Record

↓

Send Email

↓

Update Notification Status

## MVP Screens

### 1. Landing Page


SlotFinder

Find advising appointments without refreshing all day.

[ Email ]

Appointment Type:
( ) Virtual
( ) In-Person

Advisor Preference:
( ) Any Advisor
( ) Specific Advisor

If Specific:
[ Advisor Name ]

[ Start Monitoring ]

---

### 2. Success Page

Success!

SlotFinder is now monitoring appointments for you.

Notifications will be sent to:

wasif@email.com

[ Create Another Watch Request ]

---

### 3. Email Sent (Not a page, user just recieves this email)

Subject:
Appointment Available

Hi,

A matching advising appointment has been found.

Advisor: Jane Doe
Date: June 14
Time: 2:30 PM
Type: Virtual

Book now at:
[ UBC Advising Link ]

No longer need notifications?

[ Stop Monitoring ]

---

### 4. Stop Monitoring Page

Stop Monitoring?

You will no longer receive
appointment notifications.

Email:
wasif@gmail.com

[ Stop Monitoring ]

[ Keep Monitoring ]

----

### 5. Stop Confirmation Page

Monitoring Stopped

You will no longer receive
appointment notifications.

Thank you for using SlotFinder.


## Tech Stack

### Frontend

* React
* JavaScript
* HTML
* CSS

### Backend

* Java
* Spring Boot

### Database

* PostgreSQL

### Version Control

* Git
* GitHub

### Email Notifications

* SendGrid (TBD)

### Deployment

* Render or Railway (TBD)
* AWS (Future Enhancement)

## Architecture

Frontend (React) ->
Backend (Spring Boot) ->
Database (PostgreSQL) 

The frontend communicates with the backend through REST APIs. The backend handles logic, appointment monitoring, notification processing, and data persistence. PostgreSQL stores application data such as: watch requests, appointment slots, and notification records.



## Technical Roadmap / Depth Plan

SlotFinder is not intended to remain a simple CRUD application.

The project should evolve into a real appointment monitoring system with the following technical layers:

### 1. Core Backend Logic

- Retrieve live appointment availability from Comm100
- Support virtual and in-person appointment types
- Support all-advisor and advisor-specific availability
- Match active WatchRequests against available AppointmentSlots

### 2. Monitoring System

- Add a scheduler that runs periodically
- Check all active watch requests automatically
- Detect when matching appointments become available
- Handle external API failures gracefully

### 3. Notification System

- Create a Notification model
- Send email alerts when matching appointments are found
- Record notification status
- Prevent duplicate notifications for the same appointment slot

### 4. Persistence

- Store WatchRequests in PostgreSQL
- Store detected AppointmentSlots in PostgreSQL
- Store sent Notifications in PostgreSQL
- Replace in-memory storage with database-backed repositories

### 5. User Control

- Allow users to stop monitoring
- Add unsubscribe/stop-monitoring links in emails
- Ensure stopped watch requests no longer trigger notifications

### 6. Deployment

- Deploy backend service
- Deploy frontend service
- Connect deployed app to PostgreSQL
- Configure environment variables for external API and email credentials

### 7. Optional Future Enhancements

- Authentication and user accounts
- Dashboard for managing watch requests
- Date/time preference filtering
- Observability/logging for monitoring cycles
- Cloud deployment on AWS














