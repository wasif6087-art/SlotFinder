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