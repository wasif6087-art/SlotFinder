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

### Status : COMPLETE 

---

---

July 12, 2026

Completed the backend implementation of the user-facing unsubscribe / stop monitoring feature.

Completed:
- Added a unique `unsubscribeToken` to `WatchRequest`.
- Automatically generate a UUID unsubscribe token for every new watch request.
- Added `findByUnsubscribeToken()` to `WatchRequestRepository`.
- Added token-based cancellation to `WatchRequestService`.
- Created a public `GET /unsubscribe/{unsubscribeToken}` endpoint.
- Passed the unsubscribe token through:
  - `SlotMonitoringService`
  - `NotificationsService`
  - `EmailService`
- Added an unsubscribe link to every notification email.
- Verified the backend compiles successfully and the unsubscribe endpoint correctly handles requests.
- Fixed the existing manual stop endpoint after accidentally breaking its controller mapping.

Known Limitation:
- Full end-to-end testing is still pending because no real UBC appointments were available. The final production flow will be verified once the scheduler sends a real notification email containing a real unsubscribe token.

### Status: BACKEND IMPLEMENTATION COMPLETE

---


# August 3, 2026

## Frontend Setup and Initial UI

Completed the initial SlotFinder frontend setup.

### Completed

- Installed Node.js and npm.
- Created the React frontend using Vite.
- Started the Vite development server.
- Verified live updates through `App.jsx`.
- Learned the basic frontend project structure (`src`, `main.jsx`, and `App.jsx`).
- Built the first version of the Create Watch Request page with:
  - SlotFinder heading
  - Project description
  - Email input
  - Appointment Type dropdown
  - Advisor Preference dropdown
  - Conditional Advisor dropdown
  - Start Monitoring button
- Introduced basic React interactivity using `useState`.
- Implemented conditional rendering so the Advisor dropdown only appears when **Specific Advisor** is selected.

### Current Status

The frontend foundation and the initial Create Watch Request page are complete.

The form is currently a UI prototype. It is not yet connected to the Spring Boot backend and has not yet been styled with CSS.

### Next Step

Convert the static form into a fully functional frontend by:

- Managing the entire form using React state.
- Building the form submission flow.
- Connecting React to the Spring Boot backend.
- Displaying success and error feedback.
- Applying basic CSS styling to create a clean MVP interface.

**Status: FRONTEND FOUNDATION COMPLETE**

---

# August 11, 2026

## Frontend Integration and End-to-End Watch Request Flow

Continued development of the SlotFinder React frontend and moved the Create Watch Request page from a UI prototype into a working frontend-to-backend feature.

### Completed

#### Basic Frontend Layout
- Added basic CSS styling to the Create Watch Request page.
- Centered the form and improved spacing and alignment.
- Styled inputs, dropdowns, and the submit button.
- Made the page comfortable to use as a functional MVP interface.

#### Controlled Form State
- Added React state for:
  - Email
  - Appointment type
  - Advisor preference
  - Selected advisor
- Converted the form fields into controlled React components.
- React now always knows the current value of each field.
- Kept the Advisor dropdown conditional so it only appears when **Specific Advisor** is selected.

#### Form Submission Flow
- Wrapped the inputs inside a real HTML `<form>`.
- Added a `handleSubmit()` function.
- Prevented the default browser page refresh on form submission.
- Changed the Start Monitoring button to a submit button.
- Built the watch request object from React state before sending it to the backend.

#### Frontend-to-Backend Connection
- Connected the React frontend to the Spring Boot backend using `fetch()`.
- Added a POST request to:

  `POST /watchrequests`

- Converted the watch request JavaScript object into JSON using `JSON.stringify()`.
- Added the correct `Content-Type: application/json` header.

#### CORS Configuration
- Encountered and diagnosed a browser CORS error when React attempted to call the Spring Boot backend.
- Added Spring CORS support using `@CrossOrigin`.
- Allowed the React development server at:

  `http://localhost:5173`

  to communicate with the backend at:

  `http://localhost:8080`

- Verified frontend requests successfully reach Spring Boot.

#### Real Advisor Integration
- Connected the frontend to the existing backend advisor endpoint:

  `GET /advisors`

- Added `useEffect()` to automatically fetch advisor data when the React application loads.
- Added React state to store the returned advisor list.
- Verified the backend returns real advisor information including:
  - `displayName`
  - `id`
  - avatar metadata

#### Dynamic Advisor Dropdown
- Removed the temporary hardcoded Advisor 1 / Advisor 2 dropdown values.
- Used `.map()` to dynamically render the real advisor list returned from the backend.
- Displayed each advisor's `displayName` to the user.
- Stored the corresponding backend advisor `id` as the dropdown value.

The Specific Advisor dropdown now shows real UBC advisor names instead of placeholder values.

#### Watch Request Data Mapping
Fixed the frontend request body so it matches exactly what the backend expects.

For **Any Advisor**:

json
{
  "advisorPreference": "",
  "agentId": ""
}

Current Status: end-to-end frontend/backend watch-request flow is working, including real advisors and correct mapping.
    
Next Step: form validation + user feedback/loading state.

---
---

# August 12, 2026

## Frontend Validation, Feedback, and Submission State

Completed the next major frontend milestone for the Create Watch Request flow.

### Completed

- Added native email validation using `type="email"` and `required`.
- Added validation so a user cannot choose **Specific Advisor** without selecting an actual advisor.
- Updated the POST flow to inspect the backend response instead of treating every `fetch()` as successful.
- Added visible success and error feedback for watch request submissions.
- Added network error handling with `.catch()` so the UI can report when the backend is unavailable.
- Added submission/loading state with `isSubmitting`.
- Disabled the Start Monitoring button while a request is in progress to prevent duplicate submissions.
- Added temporary button feedback during submission.
- Verified the full frontend flow still works for both **Any Advisor** and **Specific Advisor** requests.

### Current Status

The Create Watch Request form is now functionally complete as a real user-facing frontend flow: it validates input, handles success/failure, prevents duplicate submissions, and communicates correctly with the Spring Boot backend.

### Next Step

Finish the Create Watch Request frontend MVP by adding a proper post-submission success state, a **Create Another Watch Request** flow, advisor loading/error states, small UX cleanup, responsive checks, and a final end-to-end frontend verification.

**Status: FORM VALIDATION & USER FEEDBACK COMPLETE**

Going Forward : Frontend MVP complete → unsubscribe frontend → deployment → reliability/testing → README/resume polish.

---

# August 15, 2026

## Create Watch Request Frontend MVP Complete

Completed the final Create Watch Request frontend milestone for SlotFinder.

### Completed

#### Proper Success State
- Replaced the old inline success message with a dedicated post-submission success view.
- Added a clear success confirmation:
  - **“You’re all set!”**
  - **“We’ll email you as soon as appointments open up.”**
- Display the email address that will receive notifications.
- Added a green success checkmark and polished success-state layout.

#### Submit Another Request Flow
- Added a **Submit Another Request** button.
- Built `handleCreateAnother()` to reset the frontend form state.
- Reset:
  - email
  - appointment type
  - advisor preference
  - selected advisor
  - success/error state
- Verified the user returns to a clean default form after a successful request.

#### Advisor Loading and Error State
- Added loading state for `GET /advisors`.
- Added advisor-fetch error handling.
- Display useful feedback when advisors cannot be loaded.
- Verified the frontend behaves correctly when the backend is unavailable.

#### UX and Visual Redesign
Expanded the original UX cleanup task into a full frontend design pass.

Updated the product to a cleaner modern startup-style interface with:

- User-facing name changed to **UBC SlotFinder**
- Indigo primary brand accent
- Light/off-white background
- Improved typography and text contrast
- Cleaner spacing and layout
- Redesigned form controls and buttons
- Improved success and error presentation
- More readable labels and supporting text
- Polished custom dropdown behavior
- Improved visual hierarchy across the form and success state

Created and documented a dedicated `Design Spec.md` covering:

- Product voice
- Layout and spacing
- Typography
- Visual identity
- Success and error states
- Responsive behavior

#### Responsive UI
Tested the frontend at desktop and mobile widths.

Verified on an iPhone-sized viewport:

- No horizontal overflow
- Form controls fit correctly
- Comfortable mobile padding
- Heading and tagline remain readable
- Success state fits cleanly
- Email display and buttons remain responsive
- Single-column layout works correctly

### Final End-to-End Verification

Completed the full frontend verification checklist.

Successfully tested:

1. Valid **Any Advisor** request
2. Valid **Specific Advisor** request
3. Invalid email
4. Missing specific advisor
5. Backend unavailable
6. Successful retry after backend recovery
7. **Submit Another Request** reset flow

All test cases passed.

### Current Status

The Create Watch Request frontend is now complete as a polished MVP feature.

The full flow now supports:

User enters request  
↓  
Frontend validates input  
↓  
Real advisors loaded from backend  
↓  
Watch request submitted to Spring Boot  
↓  
Errors handled gracefully  
↓  
Successful request shows confirmation state  
↓  
User can submit another request  
↓  
Layout works on desktop and mobile

✅ **Milestone: CREATE WATCH REQUEST FRONTEND COMPLETE**

### Next Step

Begin the next major frontend milestone:

**Unsubscribe Frontend**

After that, the planned roadmap is:

Frontend MVP complete → unsubscribe frontend → deployment → reliability/testing → README/resume polish.