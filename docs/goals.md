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