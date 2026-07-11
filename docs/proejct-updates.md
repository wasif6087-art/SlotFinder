Slot Finder – Project Progress Update (June 25, 2026)

Overall Progress

Completed Features

Appointment Availability

* Built AppointmentCheckerService.
* Successfully retrieves appointment availability from the Comm100 backend.
* Supports:
    * Phone/Zoom appointments
    * In-Person appointments
    * Advisor-specific searches
    * All-advisor searches

⸻

Service Agents

* Built ServiceAgentService.
* Retrieves advisor information from the Comm100 API.
* Maps advisor IDs to advisor names.

⸻

Watch Requests

Implemented the complete Watch Request workflow.

Users can:

* Create watch requests
* View all watch requests
* Find appointment matches for a watch request
* Cancel (deactivate) watch requests

Each watch request stores:

* Email
* Appointment type
* Advisor preference
* Agent ID (optional)
* Created timestamp
* Active status

Added request validation using @Email to reject invalid email addresses before processing.

⸻

Matching Logic

Built the matching engine inside WatchRequestService.

The service:

* Finds the requested watch request
* Determines whether the user selected:
    * All advisors
    * A specific advisor
* Calls the appropriate appointment checker
* Returns matching appointment slots

⸻

Today’s Progress (Notification System)

Notification Model

Created the Notifications model.

Each notification stores:

* Recipient email
* Matching appointment slot
* Sent timestamp
* Sent status

⸻

Notification Service

Implemented NotificationsService.

Current responsibilities:

* Create notification objects
* Populate notification fields
* Store notifications in memory
* Expose all notifications through a getter
* Print notification creation messages for testing

The service currently acts as a simple notification factory and storage layer.

⸻

Slot Monitoring

Built SlotMonitoringService.

Current workflow:

1. Retrieve all active watch requests.
2. Find matching appointment slots.
3. Create notifications for every matching appointment.

This simulates the future background monitoring process.

⸻

Slot Monitoring Controller

Created a temporary testing endpoint:

GET /monitor/check

Calling this endpoint:

* Processes every watch request
* Finds matching appointments
* Creates notifications

This endpoint exists only for development and testing.

⸻

Notifications Controller

Added:

GET /notifications

Returns every notification currently stored in memory.

This makes it easy to verify that notifications are being generated correctly.

⸻

End-to-End Workflow Completed

The following workflow now works successfully:

User creates watch request
        ↓
Watch request stored
        ↓
/monitor/check called
        ↓
SlotMonitoringService checks appointments
        ↓
Matching appointments found
        ↓
Notifications created
        ↓
Notifications stored
        ↓
GET /notifications displays them

This represents the first complete end-to-end notification pipeline.

⸻

Current Limitations

Notifications currently:

* Exist only in memory
* Are not persisted to a database
* Do not send emails
* Can be duplicated if the monitoring endpoint is executed multiple times

These limitations are expected and will be addressed in later iterations.

⸻

Next Major Milestones

1. Integrate real email sending.
2. Replace the manual monitoring endpoint with a scheduled background task.
3. Prevent duplicate notifications from being sent.
4. Persist notifications and watch requests using a database.
5. Add frontend support for managing watch requests and notifications.

---

Project Update — Email Notifications (Spring Mail)

Session Goal

Implement the email notification system so that SlotFinder can send real appointment notifications to users via email.

What We Accomplished

1. Chose Email Technology

* Decided to use Spring Mail + Gmail SMTP instead of SendGrid for the MVP.
* Reasoning:
    * Simpler setup.
    * Less infrastructure.
    * Good enough for development and testing.
    * Can later swap Gmail SMTP for SendGrid without changing the application architecture.

2. Added Spring Mail

* Added the spring-boot-starter-mail dependency to pom.xml.

3. Configured Gmail SMTP

* Created a dedicated Gmail account for SlotFinder:
    * slotfinder.ubc@gmail.com
* Enabled Google 2-Step Verification.
* Generated a Gmail App Password for Spring Boot.
* Configured SMTP settings inside application.properties.

4. Implemented EmailService

Created a new EmailService responsible for sending appointment notification emails.

The service:

* receives the user’s email address
* receives an AppointmentSlot
* builds an email using SimpleMailMessage
* sends the email using Spring’s JavaMailSender

This keeps all email-related logic isolated inside one service.

5. Connected NotificationsService

Injected EmailService into NotificationsService.

Whenever a notification is created, the notification service now immediately calls:

emailService.sendAppointmentNotification(...)

This establishes the application flow:

Slot Monitoring
→ NotificationsService
→ EmailService
→ Gmail SMTP
→ User Inbox

6. Built Temporary Test Controller

Created a temporary EmailController exposing:

GET /test-email

This endpoint creates a fake appointment and sends a test email.

Purpose:

* verify SMTP configuration
* verify Spring Mail integration
* test email formatting

This controller is temporary and will be removed once the real notification pipeline is complete.

7. Successfully Sent First Email

Successfully sent the first real email through Gmail SMTP.

Verified:

* Spring Mail configuration
* Gmail authentication
* SMTP connection
* Email delivery
* End-to-end backend integration

This is the first feature that communicates with an external production service over the internet.

Architecture After This Session

WatchRequest

↓

SlotMonitoringService

↓

NotificationsService

↓

EmailService

↓

Spring Mail

↓

Gmail SMTP

↓

User Inbox

Engineering Concepts Learned

* Spring Mail
* SMTP
* Gmail App Passwords
* Dependency Injection
* Service-to-Service communication
* Spring configuration using application.properties
* External service integration
* Constructor injection
* Building service-layer architecture

Next Session Goals

## Next Session Goals (Target: ~4 hours)

### Phase 1 — Finish the Notification System (≈2 hours)

#### 1. Improve email formatting
- Format appointment date/time using `DateTimeFormatter`
- Convert enum values (e.g. `PHONE_ZOOM`) into user-friendly text
- Improve email layout and wording
- Make notification emails look polished and production-ready

#### 2. Prevent duplicate notifications
- Ensure the same appointment cannot trigger multiple emails
- Design and implement duplicate detection
- Skip notifications that have already been sent

#### 3. Improve backend logging
- Replace simple print statements with meaningful logs
- Log watch request processing
- Log appointment matching
- Log email sending success/failure
- Log notification creation

#### 4. Clean up the notification flow
- Remove the temporary `EmailController`
- Remove all testing-only code
- Ensure emails are only sent through the real SlotMonitoringService workflow

✅ Milestone:
**Notification System Complete**

---

### Phase 2 — Start the Scheduling System (≈2 hours)

#### 5. Build the scheduling infrastructure
- Learn Spring Scheduling (`@EnableScheduling`, `@Scheduled`)
- Create a scheduler that runs automatically
- Execute `SlotMonitoringService.checkAllWatchRequests()` on a fixed interval
- Verify the monitoring loop runs without manual intervention

#### 6. End-to-end testing
- Confirm the scheduler automatically checks watch requests
- Confirm matching appointments trigger notifications
- Confirm emails are sent without manually calling an endpoint

✅ Milestone:
**Automatic Appointment Monitoring Complete**

---

June 28, 2026

Completed automatic monitoring system.

- Added Spring Scheduling (@EnableScheduling)
- Built ScheduledMonitoringService
- Monitoring now runs automatically every 60 seconds
- Prevented duplicate notifications
- Improved email formatting
- Successfully tested end-to-end

---
Next Session Goals (Target: ~4 hours)

Phase 0 — Finish Today’s Work (≈30–45 min)

1. Set up GitHub Copilot Student

* Apply for GitHub Student Developer Pack
* Connect GitHub account to VS Code
* Restore ghost auto-completion
* Verify inline AI suggestions are working

2. Code Comprehension — NotificationsService.java

* Walk through the entire NotificationsService line by line
* Understand:
    * Notification creation flow
    * Duplicate prevention logic
    * HashSet-based notification keys
    * buildNotificationKey()
    * Why the previous implementation failed
    * Why the final implementation works
* Ensure complete understanding before moving on

⸻

Phase 1 — Database Foundation (≈60 min)

3. Configure PostgreSQL + Spring Data JPA

* Add Spring Data JPA dependency
* Add PostgreSQL driver
* Configure database connection
* Verify Spring Boot successfully connects to PostgreSQL

✅ Milestone:
Database connected to the backend

⸻

Phase 2 — Persist Watch Requests (≈90–120 min)

4. Replace in-memory WatchRequest storage

* Convert WatchRequest into a JPA entity
* Create WatchRequestRepository
* Replace the in-memory List with database persistence
* Update create/view/cancel watch request operations to use the repository

✅ Milestone:
Watch requests stored in PostgreSQL

⸻

Phase 3 — End-to-End Database Testing (≈45–60 min)

5. Verify persistence through Swagger

* Create a watch request
* Restart the backend
* Confirm the watch request still exists
* Cancel a watch request
* Verify the active status persists correctly
* Confirm automatic monitoring continues to work with database-backed watch requests

✅ Milestone:
Watch requests survive backend 

### Status: COMPLETE

---

Next Session Goals (Target: ~4 hours)

Session Theme:
Understand the database work from today, clean up the Swagger request body issue, then start persisting Notifications to PostgreSQL.

Phase 1 — Code Comprehension (≈45–60 min)

1. WatchRequestRepository.java
- Understand why the file is so small
- Understand `JpaRepository<WatchRequest, Long>`
- Understand where `save()`, `findAll()`, and `findById()` come from
- Understand how Spring creates the repository automatically

2. WatchRequestService.java
- Understand how the service changed from in-memory storage to database-backed storage
- Walk through:
  - `createWatchRequest()`
  - `getAllWatchRequests()`
  - `cancelWatchRequest()`
  - `findMatches(Long id)`
  - `findMatches(WatchRequest request)`
- Understand why manual `nextId` and `createdAt` were removed

✅ Milestone:
Understand database-backed WatchRequest flow



Phase 2 — Quick Swagger Cleanup (≈15–25 min)

Goal:
Fix or document why Swagger shows database-owned fields in the POST request body.

Tasks:
- Understand why Swagger shows `id`, `active`, and `createdAt`
- Decide on quick cleanup approach:
  - Swagger/OpenAPI annotations, or
  - leave as known technical debt for future DTO cleanup
- Make the smallest useful fix or write a clear project note

✅ Milestone:
No confusion around POST request body



Phase 3 — Build: Persist Notifications to PostgreSQL (≈90–120 min)

Goal:
Move Notifications from in-memory storage to database-backed persistence.

Tasks:
- Convert `Notification` into a JPA entity
- Add required annotations:
  - `@Entity`
  - `@Id`
  - `@GeneratedValue`
  - `@Column`
  - enum/status handling if needed
- Create `NotificationRepository`
- Inject `NotificationRepository` into `NotificationsService`
- Replace in-memory notification storage with repository-backed storage
- Update `getAllNotifications()` to load from PostgreSQL
- Keep duplicate prevention working

✅ Milestone:
Notifications stored in PostgreSQL



Phase 4 — End-to-End Notification Persistence Testing (≈45–60 min)

Test:
- Create a watch request
- Let the scheduler find matching appointments
- Confirm notifications are created
- Run `GET /notifications`
- Restart backend
- Run `GET /notifications` again
- Confirm notifications still exist after restart
- Confirm duplicate notifications are still skipped

✅ Milestone:
Notifications survive backend restarts


Phase 5 — Project Update + Next Roadmap (≈15–20 min)

Document:
- WatchRequest persistence completed
- Notification persistence completed
- Database-backed monitoring status
- Any known technical debt:
  - Swagger request DTO cleanup
  - Gmail SMTP → SendGrid later
  - Temporary EmailController removal before deployment
  - Logging/observability later

Next likely milestone:
Persist AppointmentSlots or add stop-monitoring/unsubscribe link.

✅ Milestone:
Project state documented clearly

---

July 11, 2026

Completed notification persistence by migrating the Notification system from in-memory storage to PostgreSQL.

Completed:
- Converted `Notification` into a JPA entity.
- Embedded `AppointmentSlot` using `@Embedded`.
- Created `NotificationsRepository`.
- Refactored `NotificationsService` to use the repository instead of in-memory storage.
- Replaced HashSet-based duplicate persistence with database-backed duplicate detection using `existsByNotificationKey()`.
- Updated `GET /notifications` to load notifications from PostgreSQL.
- Built a temporary testing endpoint to create fake notifications without relying on real appointment availability.
- Successfully verified:
  - Notifications are persisted in PostgreSQL.
  - Duplicate notifications are prevented.
  - Duplicate detection still works after restarting the backend.
  - Notification persistence survives backend restarts.

Architecture now:

WatchRequest
↓
SlotMonitoringService
↓
NotificationsService
↓
NotificationsRepository
↓
PostgreSQL

### Status: COMPLETE

---

Next Session Goals (Target: ~4 hours)

Session Theme:
Build the user-facing unsubscribe / stop monitoring flow.

Phase 1 — Design the Unsubscribe Flow
- Decide how users should securely stop monitoring from an email.
- Design the unsubscribe token/link architecture.
- Determine how EmailService will identify the correct WatchRequest.

Phase 2 — Backend Implementation
- Add unsubscribe token support to WatchRequest.
- Persist the token in PostgreSQL.
- Create repository/service methods to find a watch request by token.
- Build an unsubscribe endpoint that deactivates the matching watch request.

Phase 3 — Email Integration
- Include an unsubscribe link in notification emails.
- Connect the link to the backend unsubscribe endpoint.

Phase 4 — End-to-End Testing
- Verify clicking the unsubscribe link deactivates the watch request.
- Confirm the scheduler no longer processes the watch request.
- Test invalid and duplicate unsubscribe requests.

✅ Milestone:
Users can stop monitoring directly from the notification email without using Swagger.

### Status : INCOMPLETE 



