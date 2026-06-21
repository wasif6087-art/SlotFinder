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

⸻