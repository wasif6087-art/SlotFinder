# Interview Stories

## Story 1: Choosing API Integration Over HTML Scraping

While building SlotFinder, I initially assumed I would need to scrape the UBC advising appointment webpage HTML to detect available appointment slots.

After inspecting the booking flow using Chrome DevTools Network tab, I discovered that the webpage was actually loading appointment data from a third-party Comm100 backend API.

Instead of scraping the rendered HTML, I investigated the network requests and found endpoints related to routing, time zones, service agents, and available dates/times.

This changed the technical approach from fragile HTML scraping to a cleaner backend integration:

Browser → Comm100 API → JSON response → UI

SlotFinder could now potentially call the same availability API directly, parse JSON responses, and detect appointment openings more reliably.

The engineering lesson was that before scraping a webpage, it is important to inspect how the frontend actually gets its data. Many modern web applications display data from APIs, and calling those APIs directly can be more reliable than parsing HTML.


update - 

Date: June 20, 2026

Problem:
Initially assumed UBC appointment system required HTML scraping.

Investigation:
Used Chrome DevTools Network tab to inspect requests.
Discovered booking system was powered by Comm100 and communicated through JSON APIs.

Result:
Avoided building a brittle web scraper and instead integrated directly with the underlying API, reducing complexity and improving reliability.

Why it mattered:

The original plan was to build an HTML scraper.
If the site structure changed, the scraper could break.
Finding the underlying API could simplify implementation
and improve reliability.




You are starting to accumulate real stories already

Off the top of my head, you now have:

Story 1

* Choosing API integration over HTML scraping

Story 2

* Reverse engineering a third-party booking system using DevTools

Story 3

* Building and testing a REST endpoint with Swagger before implementing full functionality

Story 4

* Creating a domain model before integrating external data

You probably don’t realize it yet, but these are exactly the little engineering decisions that eventually become interview material.


---




Story Seed: Investigating a Data Discrepancy During API Integration

Date: June 21, 2026

Problem:

While building SlotFinder, I integrated with the Comm100 appointment availability API.

The UBC advising website displayed appointment times such as:

* 7:20 AM
* 7:40 AM
* 8:00 AM

However, my backend integration was returning:

* 2:20 PM
* 2:40 PM
* 3:00 PM

At first glance, it appeared that the backend and frontend were showing different appointment availability.

Investigation:

Compared the browser’s Network tab responses with the backend API responses.

Verified that both the browser and backend were calling the same Comm100 endpoint.

Observed that the appointment spacing and release pattern matched exactly:

* 20-minute intervals
* Same release timing behavior
* Same available dates

This suggested that the underlying availability data was likely identical and that the discrepancy was related to time representation rather than incorrect appointment retrieval.

Decision:

Documented the discrepancy as a future investigation item and continued development of the core appointment pipeline rather than blocking progress on a non-critical issue.

Result:

Successfully validated that the backend was retrieving real appointment availability from Comm100.

Avoided spending excessive time on a non-blocking issue and continued building the JSON → Java Map → AppointmentSlot pipeline.

Lessons Learned:

When debugging integrations, it is important to distinguish between:

* Incorrect data
* Correct data represented differently

Not every discrepancy requires immediate resolution. Sometimes the correct engineering decision is to validate assumptions, document the issue, and continue making progress on the primary objective.

---

## Story 2: Diagnosing and Resolving a Time Zone Parsing Bug During API Integration

Date: June 22, 2026

Problem

While building SlotFinder, I integrated with the Comm100 appointment availability API used by UBC Advising.

The UBC advising website displayed appointment times such as:

* 10:40 AM
* 10:50 AM
* 11:00 AM

However, my backend integration was returning:

* 5:40 PM
* 5:50 PM
* 6:00 PM

The difference was approximately seven hours, making it appear as though the backend was retrieving completely different appointment availability than what was shown on the website.

At first, I suspected that:

* The API endpoint might be returning different data to my backend than to the browser.
* The browser might be showing cached data.
* The Comm100 API might have separate time zone handling for frontend and backend requests.

Investigation

I approached the problem systematically rather than immediately changing the code.

First, I compared:

* The UBC advising webpage
* Chrome DevTools Network responses
* Direct API calls made in the browser
* Responses retrieved through Java RestTemplate

I verified that:

* The same endpoint was being called.
* The same appointment dates were being returned.
* The spacing between appointment slots was identical.
* The appointment release patterns matched exactly.

This evidence suggested that the underlying appointment availability data was actually the same.

The problem was not incorrect data retrieval.

The problem was how the data was being interpreted after retrieval.

I then inspected the raw JSON responses and discovered that Comm100 was returning appointment times in a format that Java was automatically interpreting incorrectly due to date/time parsing behavior.

Solution

Instead of relying on Java’s default parsing behavior, I implemented an explicit DateTimeFormatter and parsing strategy.

I created a formatter that matched the exact structure of the Comm100 date and time strings and used it when converting appointment data into LocalDateTime objects.

This ensured that:

* Appointment dates were parsed consistently.
* Time values matched the original Comm100 data.
* Time zone assumptions were no longer applied implicitly.

Result

After implementing the formatter:

* The backend appointment times matched the times displayed on the UBC advising website.
* The appointment pipeline became reliable and predictable.
* Appointment data could be exposed through Swagger and future APIs without ambiguity.

This also allowed me to complete the full data flow:

Comm100 API → JSON Response → Java Objects → AppointmentSlot Model → REST Endpoint

Lessons Learned

One of the biggest lessons was that many integration bugs are not caused by retrieving the wrong data.

Often the correct data is retrieved but interpreted incorrectly.

When debugging API integrations, it is important to separate:

* Data retrieval problems
* Data parsing problems
* Data representation problems

By validating assumptions at each stage of the pipeline, I was able to identify the actual root cause instead of making unnecessary changes to the API integration.

---

## Story 3: Extending an Existing API Design Without Duplicating Endpoints

Date: June 24, 2026

Problem

While building SlotFinder, I initially implemented appointment retrieval only for PHONE_ZOOM appointments.

The backend integration was working correctly, but after investigating the UBC advising system further, I discovered that the booking platform also supported IN_PERSON appointments through a different Comm100 service ID.

My first instinct was to create separate endpoints and separate service logic for each appointment type.

However, this would have duplicated a large amount of code because both appointment types followed the exact same workflow.

Investigation

Using Chrome DevTools and the Comm100 API responses, I compared the Phone/Zoom and In-Person booking flows.

I verified that:

* Both appointment types used the same Comm100 endpoint structure.
* Both returned appointment availability in the same JSON format.
* Both supported advisor-specific filtering through agent IDs.
* The only meaningful difference was the Comm100 service ID used in the request.

This suggested that the underlying retrieval logic was identical and that appointment type should be treated as input data rather than a separate implementation.

Solution

Instead of creating duplicate endpoints and service methods, I introduced an AppointmentType enum containing:

* PHONE_ZOOM
* IN_PERSON

I then refactored the appointment retrieval pipeline so that appointment type became a parameter that flowed through the application.

The implementation included:

* Creating centralized service ID selection logic.
* Passing AppointmentType through controller request parameters.
* Updating service methods to accept AppointmentType as an argument.
* Removing hardcoded Phone/Zoom behavior.
* Updating AppointmentSlot objects to record the selected appointment type.

I also verified the implementation through Swagger.

Because AppointmentType was implemented as an enum, Swagger automatically generated a dropdown menu containing the valid appointment types, reducing invalid input and improving API usability.

Result

The same API endpoint could now support both PHONE_ZOOM and IN_PERSON appointments without duplicating business logic.

The final design allowed requests such as:

* PHONE_ZOOM appointment retrieval
* IN_PERSON appointment retrieval
* Advisor-specific PHONE_ZOOM retrieval
* Advisor-specific IN_PERSON retrieval

all through the same underlying appointment retrieval pipeline.

This made the codebase simpler, easier to maintain, and easier to extend if additional appointment types are introduced in the future.

Lessons Learned

A useful API design principle is to parameterize behavior when the workflow is identical and only a small piece of data changes.

Instead of creating multiple nearly identical implementations, it is often cleaner to expose the difference as an input parameter and keep the business logic centralized.

This reduces duplication, improves maintainability, and makes future enhancements significantly easier.

---


