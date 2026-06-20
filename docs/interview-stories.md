# Interview Stories

## Story 1: Choosing API Integration Over HTML Scraping

While building SlotFinder, I initially assumed I would need to scrape the UBC advising appointment webpage HTML to detect available appointment slots.

After inspecting the booking flow using Chrome DevTools Network tab, I discovered that the webpage was actually loading appointment data from a third-party Comm100 backend API.

Instead of scraping the rendered HTML, I investigated the network requests and found endpoints related to routing, time zones, service agents, and available dates/times.

This changed the technical approach from fragile HTML scraping to a cleaner backend integration:

Browser → Comm100 API → JSON response → UI

SlotFinder could now potentially call the same availability API directly, parse JSON responses, and detect appointment openings more reliably.

The engineering lesson was that before scraping a webpage, it is important to inspect how the frontend actually gets its data. Many modern web applications display data from APIs, and calling those APIs directly can be more reliable than parsing HTML.