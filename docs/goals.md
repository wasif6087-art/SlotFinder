### July 13, 2026

### Session Theme

### Transition SlotFinder from a backend-only API into a real user-facing web application.


### Phase 0 — Final Unsubscribe Verification (≈15–30 min)

 Goal
Perform one final production-style test of the unsubscribe feature.

Tasks:
- Create a real watch request once UBC releases appointments.
- Let the scheduler send a real notification email.
- Click the unsubscribe link from the email.
- Verify the watch request becomes inactive.
- Confirm the scheduler ignores the watch request on future monitoring cycles.

✅ Milestone:
**Unsubscribe flow fully verified end-to-end.**



### Phase 1 — Frontend Architecture (≈45–60 min)

 Goal
Plan the frontend before writing code.

Tasks:
- Decide the overall frontend structure.
- Decide what pages the application will have.
- Map each page to the backend endpoints it will use.
- Design the primary user flow through the application.
- Keep the first version intentionally simple.

✅ Milestone:
**Frontend architecture finalized.**



 ### Phase 2 — Bootstrap the Frontend (≈2 hours)

 Goal
Create the frontend project and connect it to the existing backend.

Tasks:
- Create the frontend application.
- Configure communication with the Spring Boot backend.
- Set up basic routing.
- Build the initial page structure.
- Verify the frontend can successfully communicate with the backend.

✅ Milestone:
**Frontend successfully connected to the backend.**


 ### Phase 3 — Build the First User Workflow (≈1–1.5 hours)

 Goal
Replace Swagger for the primary user action.

Tasks:
- Build a "Create Watch Request" page.
- Allow users to:
  - Enter their email.
  - Select appointment type.
  - Choose an advisor (or all advisors).
- Submit the request to the backend.
- Display a success confirmation after the watch request is created.

✅ Milestone:
**Users can create watch requests entirely through the frontend.**


End-of-Day Goal

By the end of today's session, SlotFinder should no longer feel like just a backend API. It should have its first real user interface capable of creating watch requests and communicating with the backend without relying on Swagger.

My own thoughts - why did backend take so long but we do the whole frontend in one day? is there anything like swagger for frontend that i dont know?

### Status : INCONPLETE, but made progress
---

# August 4, 2026 – Frontend Becomes Functional

## 1. Basic Layout (15–20 min)

- Improve spacing and alignment with CSS.
- Stack the form vertically.
- Make the page comfortable to work with (not necessarily pretty).

**Success Criteria:** The UI no longer looks like raw HTML.



## 2. Finish Form Interactivity

- Add React state for every input.
- Make every field a controlled component.
- Keep the Advisor dropdown conditional on **Specific Advisor**.

**Success Criteria:** React always knows the current value of every field.



## 3. Submit Handler

- Wrap everything inside a `<form>`.
- Create a `handleSubmit()` function.
- Prevent the default page refresh.
- Build the request body in the format expected by the backend.

**Success Criteria:** Clicking **Start Monitoring** prepares a complete watch request.



## 4. Connect Frontend to Backend

- Send a POST request from React to Spring Boot.
- Configure CORS if necessary.
- Verify the watch request is actually created.

**Success Criteria:** You no longer need Swagger to create watch requests.



## 5. User Feedback

- Display a success message after submission.
- Display an error message if something fails.
- Disable the button while submitting (if time permits).

**Success Criteria:** The user clearly knows whether the request succeeded or failed.



# End-of-Day Goal

A user should be able to:

- Enter an email.
- Select an appointment type.
- Choose **Any Advisor** or **Specific Advisor**.
- Select an advisor (if needed).
- Click **Start Monitoring**.
- Successfully create a watch request through the frontend.
- Receive a confirmation message.



**Estimated Session Length:** 2–4 hours

**Primary Objective:** Complete the first fully functional frontend-to-backend feature for SlotFinder. After tomorrow, users should be able to create watch requests directly from the web interface without using Swagger.

---

# August 7th Goals

prompt - “Continue SlotFinder. We’re working on Phase 1 of the frontend. The next task is loading advisors from the /advisors endpoint into the React dropdown. Here’s my current App.jsx.”

## 1. Load Advisors from the Backend
- Fetch the advisor list from `GET /advisors`.
- Store the returned advisors in React state.
- Verify the response contains `displayName` and `id`.

**Success Criteria:** React has the real advisor data from the backend.

---

## 2. Populate the Advisor Dropdown
- Replace the temporary hardcoded advisors.
- Render the dropdown using the advisors fetched from the backend.
- Display `displayName` to the user while keeping the corresponding `id`.

**Success Criteria:** The dropdown displays the real advisors from the backend.

---

## 3. Fix Watch Request Data Mapping
- If **Any Advisor** is selected:
  - `advisorPreference = ""`
  - `agentId = ""`
- If **Specific Advisor** is selected:
  - `advisorPreference = selectedAdvisor.displayName`
  - `agentId = selectedAdvisor.id`
- Verify the request body matches exactly what the backend expects.

**Success Criteria:** Both Any Advisor and Specific Advisor create the correct request body.

---

## 4. Verify End-to-End Submission
- Submit a watch request using **Any Advisor**.
- Submit a watch request using **Specific Advisor**.
- Verify both requests reach the backend correctly.
- Confirm the watch requests are successfully stored in the database.

**Success Criteria:** The entire frontend → backend → database flow works for both scenarios.

---

## 5. User Feedback
- Display a success message after a successful submission.
- Display an error message if the request fails.
- (Optional) Disable the submit button while the request is being sent.

**Success Criteria:** Users receive clear feedback after submitting a watch request.

---

# End-of-Day Goal

A user should be able to:

- Enter an email.
- Select an appointment type.
- Choose **Any Advisor** or **Specific Advisor**.
- Select a real advisor loaded from the backend.
- Click **Start Monitoring**.
- Successfully create a watch request.
- Receive a success or error message after submission.

---

# August 12th — Form Validation & User Feedback

## Current Starting Point

The core Create Watch Request form now works end-to-end.

Completed:
- React fetches the real advisor list from `GET /advisors`.
- Advisors are stored in React state.
- The Specific Advisor dropdown is populated dynamically using `displayName`.
- The selected advisor's real `id` is stored in React state.
- Any Advisor correctly submits:
  - `advisorPreference: ""`
  - `agentId: ""`
- Specific Advisor correctly submits:
  - `advisorPreference: selectedAdvisor.displayName`
  - `agentId: selectedAdvisor.id`
- Both scenarios successfully reach the backend through `POST /watchrequests`.

## Goal: Form Validation & User Feedback

### 1. Email Validation
- Prevent submission if the email field is empty.
- Prevent submission if the email address has an invalid format.
- Start with native HTML validation (`required` + `type="email"`).

Success Criteria:
The form cannot be submitted with an empty or invalid email address.

### 2. Specific Advisor Validation
- If `ANY_ADVISOR` is selected, no advisor needs to be chosen.
- If `SPECIFIC_ADVISOR` is selected, require the user to select an advisor.
- Prevent an invalid request from being submitted.

Success Criteria:
A Specific Advisor request cannot be submitted without an actual advisor selected.

### 3. Detect POST Success / Failure
- Inspect the response returned by `POST /watchrequests`.
- Determine whether the backend accepted or rejected the request.
- Do not treat every fetch as automatically successful.

Success Criteria:
React knows whether creation of the watch request succeeded or failed.

### 4. Success Feedback
- Show the user a confirmation after successful submission.
- Example:
  "Monitoring started. We'll email you when a matching appointment becomes available."

Success Criteria:
The user no longer needs DevTools to know their watch request was created.

### 5. Error Feedback
- Show the user an understandable message if the request fails.
- Do not silently fail.

Success Criteria:
The user knows when their watch request was not created.

### 6. Submission / Loading State
- Track when the request is being submitted.
- Disable the Start Monitoring button while the request is in progress.
- Give the user clear feedback that something is happening.

Success Criteria:
The user cannot accidentally submit the same form repeatedly while waiting for the backend.

## End Goal

The Create Watch Request form should behave like a real user-facing form:

Fill form
→ Validate input
→ Submit request
→ Wait for backend
→ Show success or failure feedback

## Prompt to start

"Continue SlotFinder. We're working on the React frontend Create Watch Request form.

Last session we completed the real advisor integration and verified the full frontend → backend flow for both Any Advisor and Specific Advisor.

Today we're working on form validation and user feedback. The six tasks are:
1. Email validation
2. Specific Advisor validation
3. Detect POST success/failure
4. Success feedback
5. Error feedback
6. Submission/loading state

Start with #1 only.

Keep using our teaching approach: explain WHY before WHAT, make one small change at a time, explain the syntax I write, and don't jump ahead or dump a bunch of code on me."

---

# August 13th 

Continue SlotFinder.

We are working on the React frontend, specifically finishing the Create Watch Request frontend MVP.

Current project status:
- Spring Boot backend is already built and working.
- PostgreSQL persistence is working.
- Scheduled appointment monitoring is working.
- Real email notifications are working.
- Duplicate notification prevention is working.
- Backend unsubscribe / stop-monitoring flow is implemented with unsubscribe tokens.
- React frontend can already:
  - fetch real advisors from GET /advisors
  - submit watch requests to POST /watchrequests
  - handle Any Advisor and Specific Advisor correctly
  - validate email
  - require an advisor when Specific Advisor is selected
  - detect POST success/failure
  - show success and error messages
  - handle network failure with catch()
  - track submission state
  - disable the submit button while submitting
  - briefly show “Submitting...”
- The six Form Validation & User Feedback tasks are complete.

Tomorrow’s goal is:

# Session Goals

1. Build Proper Success State
- After a successful watch request, replace the form with a clear confirmation view.
- Show that monitoring has started.
- Display the email address where notifications will be sent.

2. Create Another Watch Request
- Add a “Create Another Watch Request” button.
- Reset the form state when clicked.
- Return the user to the Create Watch Request form.

3. Advisor Loading / Error State
- Track whether advisors are loading.
- Show useful feedback while advisors are loading.
- Show an understandable error if GET /advisors fails.

# August 14th: just start from task 4. No need to re-promt or restart, just jump in
4. UX Cleanup
- Improve success/error presentation.
- Review submission/loading behavior.
- Clear stale success/error messages when appropriate.

5. Responsive UI Check
- Test desktop and narrow/mobile widths.
- Fix obvious spacing, layout, or overflow problems.

6. Final End-to-End Verification
Test:
- Valid Any Advisor request
- Valid Specific Advisor request
- Invalid email
- Missing specific advisor
- Backend unavailable
- Successful submission
- Create Another Watch Request

Milestone:
CREATE WATCH REQUEST FRONTEND COMPLETE

Important teaching instructions:
- Start with Task 1 only.
- Explain WHY before WHAT.
- Make one small code change at a time.
- Do not dump a full solution.
- Explain every new syntax I write.
- Wait for me to confirm before moving to the next step.
- Keep the implementation simple and MVP-focused; do not overengineer.

---

# August 16th

1. Figure out unsubcribe frontend + how to link to backend (we do currently have an unsubscribe endpoint)

# August 17th — Unsubscribe Frontend

## Goal
Build the frontend unsubscribe flow and connect it to the existing backend unsubscribe system.

## BEGIN HERE : Start the backend. Start the frontend. Play with the app. Read the last prompt and response on 'SlotFinder Backend' chat. Then just jump into the work from there.

## Stage 1 — Verify Existing Backend Unsubscribe
- Inspect the existing unsubscribe endpoint in Swagger.
- Determine what token/parameter it expects.
- Test the endpoint manually.
- Confirm the correct WatchRequest becomes inactive.

**Success Criteria:** Backend unsubscribe flow is understood and verified.

## Stage 2 — Understand the Email Unsubscribe Link
- Inspect how the unsubscribe token is generated and stored.
- Inspect how the unsubscribe URL is added to notification emails.
- Determine how the email link should route users to the React frontend instead of directly to the backend.

**Success Criteria:** Complete email → frontend → backend flow is planned.

## Stage 3 — Design Unsubscribe UX
- Design the Stop Monitoring confirmation state.
- Design the Monitoring Stopped success state.
- Decide button actions and user-facing text.
- Keep the design consistent with the existing SlotFinder frontend.

**Success Criteria:** Unsubscribe frontend behavior and UI are finalized before implementation.

## End Goal

Email notification  
→ Click unsubscribe link  
→ Open SlotFinder unsubscribe page  
→ Confirm stop monitoring  
→ Backend deactivates WatchRequest  
→ Show confirmation to user

---

# August 21st 

Handle these two issues - 

  1) What if someone forgets to unsubscribe (they dont use that email anymore etc). Do we just keep monitoring for them forever?

  2) Unsubscribe link is sort of hidden in Gmail issue (hidden after 3 dots)

  3) Start/Look into Deployment

  ---

# August 22nd

1) Scope deployment - what is it? how to do irt? what software to use? costs and benefits?
(just get a general idea of the whole deployment thing cuz you've never done it before)

2) what changes do i need to make to my whole project (a lot of it is configured to only run on my device)

3) do i use AWS or not? what do student usually use to deploy projects like this? 


  
