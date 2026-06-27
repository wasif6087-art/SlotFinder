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