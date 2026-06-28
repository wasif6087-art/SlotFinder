User Story 4 Investigation Notes (June 2026)

Goal

Determine how UBCO appointment availability data is obtained so Slot Finder can detect newly available advising appointments.

Investigation Process

Used Chrome DevTools Network tab to inspect requests made by the UBCO advising appointment booking system (Comm100).

Key question:

Does the webpage contain appointment data directly in the HTML, or does it request appointment data from a backend API?

Findings

The booking page does NOT appear to contain appointment availability data directly in the HTML.

Instead, the page makes API requests to Comm100 backend services.

Observed requests included:

* route
* timezones
* serviceAgents
* availableDatesandtimes

Most important endpoint discovered:

availableDatesandtimes

Example request pattern:

GET /booking/services/{serviceId}/availableDatesandtimes

Parameters observed included:

* siteId
* timezone
* agentId

Responses Observed

When no appointment slots were available, the API returned empty JSON responses such as:

[]

or

{}

This matched the UI message:

“No time available”

This suggests that when appointments become available, this endpoint will likely return non-empty JSON containing available dates and times.

Architectural Insight

Rather than scraping HTML pages, a potentially better approach is:

1. Call the same backend API used by the booking webpage.
2. Parse the returned JSON.
3. Detect when available appointment slots exist.
4. Notify users by email.

This approach is likely more reliable and simpler than traditional HTML scraping.

Next Research Tasks

1. Determine the exact request format required by the availableDatesandtimes endpoint.
2. Identify which parameters are mandatory.
3. Confirm whether authentication is required.
4. Capture a response when appointment slots are actually available.
5. Reproduce the request in Postman or Java code.
6. Build an AppointmentCheckerService that polls this endpoint.

Important Lesson Learned

Modern websites often do not store important data in HTML.

Instead:

Browser → API Request → Backend Server → JSON Response → UI

For backend development and scraping tasks, inspecting network requests is often more useful than inspecting webpage HTML.



Design note: (what infor do we take from OUR users?)

We do not need to collect academic intake fields because Slot Finder only detects availability, not booking details.

We do need appointment mode because Phone/Zoom and In-person appear to have different availability pools, likely represented by different Comm100 serviceIds.

Next investigation:
Find In-person appointment serviceId by inspecting Network requests after clicking Book In-Person Appointment.


Advisor behavior:
- If user searches "All Available", appointmentSlot.advisorName may be "Unknown" because Comm100 availability response only returns dates/times.
- If user selects a specific advisor, inspect whether Comm100 request includes an advisor/agent parameter.
- If yes, set advisorName to the selected advisor.
- Future API idea:
  /appointments/check?mode=zoom&advisor=all
  /appointments/check?mode=zoom&advisor=Dia

  ---

  Remember before deploying -
  1. delete test email controller 
  2. replace Gmail SMTP with SendGrid
  3. Maybe improve data logging?