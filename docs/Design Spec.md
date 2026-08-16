# SlotFinder UX Design Spec

## Product Personality

SlotFinder should feel:

- Modern
- Friendly
- Startup-like
- Simple and approachable
- Polished enough to feel like a real product rather than a developer prototype

Avoid overly corporate, formal, or engineering-focused language in the user-facing interface.

---

## Main Page

### Branding

**Product Name:**  
SlotFinder

**Tagline:**  
Advising appointments without refreshing all day.

The existing tagline will remain unchanged.

---

## Form Copy

### Fields

Keep the current straightforward field names:

- Email
- Appointment Type
- Advisor Preference
- Advisor (only shown when Specific Advisor is selected)

### Primary CTA

Replace:

`Start Monitoring`

With:

`Find Appointments`

Avoid exposing internal terminology such as "monitoring" or "watch requests" to users when simpler language works.

---

## Submission / Loading UX

The primary button should always display:

`Find Appointments`

Do **not** change the button text to `Submitting...` or `Searching...` while the request is processing.

When the user clicks **Find Appointments**:

1. Disable the button to prevent duplicate submissions.
2. Keep the button text as **Find Appointments**.
3. Display a small animated loading spinner underneath the button.
4. Remove the spinner when the backend request finishes.
5. If successful, transition to the success/confirmation view.
6. If unsuccessful, keep the form visible and display an appropriate error message.

The loading experience should feel subtle and polished rather than causing the button text to flicker.

---

## Implementation Philosophy

- Prioritize a clean, credible MVP rather than excessive visual complexity.
- Keep native dropdown functionality for now rather than building custom React dropdown components.
- Improve colors, typography, spacing, copy, feedback states, and overall visual hierarchy during the implementation pass.
- Avoid overengineering UI elements that provide little value to the internship-ready MVP.

---

## Success State

After a request is successfully submitted, replace the form with a confirmation view.

### Content

✓

**You’re all set!**

We’ll email you as soon as appointments open up.

We’ll notify you at **{email}**

**[ Submit Another Request ]**

### Behavior

- The email address shown should dynamically use the email from the submitted request.
- "Submit Another Request" returns the user to the request form.
- Keep the SlotFinder logo and tagline visible above the success state.
- Use a checkmark as a visual success indicator.

---

## Layout and Spacing

### Overall Layout

- Keep the interface centered horizontally.
- Keep the main content slightly above the vertical center of the page.
- Use generous whitespace so the interface feels calm and intentional.
- Avoid tightly stacking unrelated pieces of content.
- Keep the form relatively narrow and focused rather than stretching across the page.

---

## Main Form Layout

Structure the page in this order:

1. SlotFinder heading
2. Tagline
3. Form
4. Primary CTA
5. Loading indicator when submitting

Spacing rules:

- Use clear separation between the product heading and tagline.
- Use a larger gap between the tagline and the form.
- Treat each form field as a visual group:
  - label
  - small gap
  - input/select
- Use slightly more space between separate field groups.
- Add additional space before the primary CTA.
- Keep the main CTA aligned to the same width as the inputs.
- If loading, show the spinner below the CTA with a small amount of spacing.

---

## Success State Layout

Structure the success state in this order:

1. SlotFinder heading
2. Tagline
3. Success checkmark
4. “You’re all set!” heading
5. Confirmation sentence
6. Notification email
7. “Submit Another Request” button

Spacing rules:

- Use a larger gap between the tagline and the success content.
- Give the success checkmark its own breathing room.
- Make “You’re all set!” visually more prominent than the body text.
- Keep the confirmation sentence close enough to the heading to feel connected.
- Add slightly more space before the email section.
- Visually emphasize the email address.
- Add a larger gap before the secondary CTA.
- Keep the entire success block centered and compact.

---

## Typography

### Font

- Use Inter as the primary font.
- Keep typography clean, modern, friendly, and highly readable.
- Avoid decorative or overly stylized fonts.
- Use a small number of consistent font sizes and weights.

### Brand Heading

- "SlotFinder"
- Approximately 32–36px.
- Medium to semi-bold weight.
- Highest level of typography on the main page.

### Tagline

- Approximately 16px.
- Regular weight.
- Use a muted text color.
- Visually secondary to the SlotFinder heading.

### Form Labels

- Approximately 14–15px.
- Medium weight.
- Labels should be slightly smaller and less prominent than input text.

### Input and Select Text

- Approximately 16px.
- Regular weight.
- Should be easy to read without visually competing with headings.

### Primary Button

- Approximately 16px.
- Medium to semi-bold weight.
- Clear and prominent without using oversized text.

### Success State

#### Success Checkmark
- Visually prominent.
- Larger than surrounding body text.
- Should act as the first visual signal that the request succeeded.

#### Success Heading
- "You’re all set!"
- Approximately 26–28px.
- Semi-bold.
- Main focus of the success state.

#### Confirmation Text
- "We’ll email you as soon as appointments open up."
- Approximately 16px.
- Regular weight.

#### Notification Destination
- Display "We’ll notify you at" as smaller/muted supporting text.
- Display the user's email address separately underneath.
- Email approximately 16px with medium weight.
- The email should be visually emphasized so the user can quickly verify it.

#### Secondary Button
- "Submit Another Request"
- Approximately 16px.
- Medium weight.

### General Typography Rules

- Maintain clear hierarchy between headings, supporting text, labels, and interactive elements.
- Do not use excessive bold text.
- Do not use many different font sizes.
- Muted supporting text should remain easily readable.

---

## Visual Identity

### Overall Direction

- Use a light, airy, minimal interface rather than the current dark theme.
- Use a subtle cool off-white page background rather than pure white.
- Keep the visual style modern, friendly, and startup-like.
- Use color intentionally and sparingly.
- The interface should feel polished without becoming visually busy.

### Color System

Use **indigo** as SlotFinder's primary brand color.

Color hierarchy:

- Brand / primary actions → Indigo
- Success → Green
- Errors → Red
- Primary text → Near-black
- Secondary text → Muted gray
- Borders → Light gray
- Inputs / selects → White
- Page background → Cool off-white

Indigo should be the recognizable accent throughout the product without overwhelming the interface.

### Inputs and Dropdowns

- White backgrounds.
- Thin, subtle light-gray borders.
- Softly rounded corners, approximately 10px radius.
- Comfortable vertical padding, targeting roughly 44–48px total height.
- Avoid heavy shadows.
- Focused controls should use an indigo border with a subtle indigo focus ring.
- Placeholder text should use a muted gray.
- Keep native dropdown functionality for the MVP rather than building custom React dropdowns.

### Primary Button

- Indigo background.
- White text.
- Same width as the form controls.
- Approximately 10px border radius.
- Approximately 46–48px height.
- Avoid dramatic shadows.
- On hover, shift to a slightly darker indigo.
- Use a subtle transition between normal and hover states.
- When disabled, slightly fade the button while keeping the text readable.

### Loading State

- Keep the button text as **Find Appointments** while submitting.
- Disable the button while the request is processing.
- Do not change the button text to `Submitting...` or `Searching...`.
- Display a small animated indigo spinner underneath the button.
- Remove the spinner when the request finishes.
- Keep the loading treatment subtle and polished.

### Success Treatment

- Display the success checkmark inside a soft green circular treatment.
- The checkmark should provide immediate visual confirmation that the request succeeded.
- Green should be reserved for success and should not replace indigo as the primary brand color.

### Error Treatment

- Use a soft but clearly readable red for error messages.
- Keep error feedback close to the relevant form area.
- Error styling should be noticeable without feeling aggressive.
- Where appropriate, the relevant field may also receive a subtle red border.

### Interaction States

- Normal inputs/selects → subtle gray border.
- Focused inputs/selects → indigo border and subtle indigo focus ring.
- Primary button hover → slightly darker indigo.
- Disabled controls → slightly faded and clearly non-interactive.
- Avoid dramatic animations.
- Interactions should feel quick, subtle, and polished.

### Visual Identity Principle

The finished UI should feel intentionally designed but restrained: a bright, approachable appointment-finding product with a recognizable indigo accent, clear feedback states, soft rounded controls, and enough visual polish to feel like a real startup product rather than a developer prototype.

---

## Success and Error States

### Success State

After a request is successfully submitted:

- Replace the form with the success confirmation view.
- Display a soft green circular success icon with a checkmark.
- Show the heading **“You’re all set!”**
- Show the confirmation text: **“We’ll email you as soon as appointments open up.”**
- Display **“We’ll notify you at”** as supporting text.
- Display the submitted email address separately and visually emphasize it.
- Show a **“Submit Another Request”** button.
- Keep the SlotFinder heading and tagline visible.

The success state should feel reassuring, clear, and lightweight rather than technical.

### Error Copy

Use simple, friendly language rather than technical or backend-focused terminology.

#### Invalid Email

Display:

**“Enter a valid email address.”**

#### Missing Specific Advisor

If the user chooses **Specific Advisor** but does not select an advisor, display:

**“Choose an advisor to continue.”**

#### Server / Network Failure

If SlotFinder cannot complete the request because of a backend, API, or network problem, display:

**“Something went wrong. Please try again.”**

Do not expose raw backend errors or technical information to the user.

### Error Placement

- Invalid email errors should appear directly underneath the **Email** input.
- Missing advisor errors should appear directly underneath the **Advisor** dropdown.
- Server/network errors should appear underneath the **Find Appointments** button.
- Keep errors visually close to the action or field that caused them.

### Error Behavior

- Field-specific errors should disappear once the user corrects the relevant field.
- Clear stale feedback when the user begins a new submission attempt.
- A failed submission should keep the form visible so the user can retry.
- Do not erase valid form information after a failed request.
- Error messages should use the soft red error styling defined in the Visual Identity section.
- Errors should be noticeable without feeling aggressive or alarming.

### Feedback Principle

Success and error feedback should always tell the user what happened and, when necessary, what they should do next without exposing SlotFinder’s internal implementation details.

---

## Responsive UI

### Overall Responsive Behavior

- Keep SlotFinder as a single-column interface at all screen sizes.
- The interface should remain simple and focused rather than introducing different layouts for desktop and mobile.
- The page should never require horizontal scrolling.
- Content should remain centered and visually balanced across screen sizes.

### Desktop

- Keep the form and success content narrow and centered.
- Use a maximum content width of approximately 420–480px.
- Do not allow inputs or buttons to stretch excessively on large displays.
- Maintain the generous whitespace defined in the Layout and Spacing section.
- Keep the SlotFinder heading and tagline centered above the main content.

### Mobile

- Allow the form to expand close to the available screen width.
- Maintain comfortable horizontal page padding so controls never touch the edges of the screen.
- Inputs, dropdowns, and primary buttons should use the full available width of the form.
- Slightly reduce large outer gaps where necessary so the interface does not feel excessively tall on smaller screens.
- Preserve clear spacing between individual form fields.
- Keep controls large enough to tap comfortably on touch devices.
- Prevent text, email addresses, buttons, and form controls from overflowing the viewport.

### Success State

- Keep the success state centered on both desktop and mobile.
- The success checkmark, heading, confirmation copy, email address, and button should remain in a single vertical column.
- Allow long email addresses to wrap safely rather than overflow the screen.
- The **Submit Another Request** button should fit comfortably within the available mobile width.

### Dropdowns

- Continue using native dropdown behavior for the MVP.
- Do not build separate custom mobile or desktop dropdown components.
- Ensure the closed dropdown controls fit correctly within the available screen width.

### Breakpoints

- Keep responsive CSS simple.
- Use one primary mobile breakpoint if needed rather than creating a complex breakpoint system.
- The default layout should already be flexible enough to handle most screen sizes, with the mobile breakpoint used only for necessary spacing or sizing adjustments.

### Responsive Design Principle

SlotFinder should feel like the same product on desktop and mobile. Responsive behavior should preserve the existing visual hierarchy and simply adapt widths and spacing to the available screen size rather than redesigning the interface for each device.

---

