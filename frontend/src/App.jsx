import { useState } from 'react'




function App() {
  const [AdvisorPreference, setAdvisorPreference] = useState('ANY_ADVISOR')
  return (
    <main>
      <h1>SlotFinder</h1>

      <p>
        Advising appointments without refreshing all day.
      </p>

      <label htmlFor="email">Email</label>
      
      <input
        id="email"
        type="email"
        placeholder="you@example.com"
      />

      <label htmlFor="appointmentType">Appointment Type</label>
      
      <select id="appointmentType">
        <option value="PHONE_ZOOM">Phone / Zoom</option>
        <option value="IN_PERSON">In Person</option>
      </select>

      <label htmlFor="advisorPreference">Advisor Preference</label>

      <select
        id="advisorPreference"
        value={AdvisorPreference}
        onChange={(event) => setAdvisorPreference(event.target.value)}
      >
        <option value="ANY_ADVISOR">Any Advisor</option>
        <option value="SPECIFIC_ADVISOR">Specific Advisor</option>
      </select>

      {AdvisorPreference === 'SPECIFIC_ADVISOR' && (
        <>

      <label htmlFor="advisor">Advisor</label>

      <select id="advisor">
        <option value="">Select an Advisor</option>
        <option value="ADVISOR_1">Advisor 1</option>
        <option value="ADVISOR_2">Advisor 2</option>
      </select>
      </>

      )}

      <button type="button">
        Start Monitoring
      </button>

      
    </main>
  )
}

export default App