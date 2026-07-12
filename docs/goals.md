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

### Status : INCONPLETE
---