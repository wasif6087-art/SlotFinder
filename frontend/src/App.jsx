import { useState } from 'react'
import './App.css'




function App() {
  const [AdvisorPreference, setAdvisorPreference] = useState('ANY_ADVISOR')
  const [email, setEmail] = useState('')
  const [appointmentType, setAppointmentType] = useState('PHONE_ZOOM')
  const [advisor, setAdvisor] = useState('')

  function handleSubmit(event) {
    event.preventDefault()
    const watchRequest = {
      email: email,
      appointmentType: appointmentType,
      advisorPreference: AdvisorPreference,
      agentId: advisor
    }
    fetch('http://localhost:8080/watchrequests', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(watchRequest)
    })
  }

  return (
    <main className="app">
      <h1>SlotFinder</h1>

      <p>
        Advising appointments without refreshing all day.
      </p>

      <form className="form-container" onSubmit={handleSubmit}>


      <label htmlFor="email">Email</label>
      
      <input
        id="email"
        type="email"
        placeholder="you@example.com"
        value={email}
        onChange={(event) => setEmail(event.target.value)}
      />

      <label htmlFor="appointmentType">Appointment Type</label>
      
      <select 
      id="appointmentType" 
      value={appointmentType} 
      onChange={(event) => setAppointmentType(event.target.value)}
      >
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

      <select 
      id="advisor"
      value={advisor}
      onChange={(event) => setAdvisor(event.target.value)}
      >
        <option value="">Select an Advisor</option>
        <option value="ADVISOR_1">Advisor 1</option>
        <option value="ADVISOR_2">Advisor 2</option>
      </select>
      </>

      )}

      <button type="submit">
        Start Monitoring
      </button>

    </form>
    </main>
  )
}

export default App