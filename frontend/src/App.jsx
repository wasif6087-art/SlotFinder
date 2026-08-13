import { useEffect, useState} from 'react'
import './App.css'




function App() {
  const [AdvisorPreference, setAdvisorPreference] = useState('ANY_ADVISOR')
  const [email, setEmail] = useState('')
  const [appointmentType, setAppointmentType] = useState('PHONE_ZOOM')
  const [advisor, setAdvisor] = useState('')
  const [advisors, setAdvisors] = useState([])
  const [successMessage, setSuccessMessage] = useState('')
  const [errorMessage, setErrorMessage] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)

  useEffect(() => {
    fetch('http://localhost:8080/advisors')
      .then(response => response.json())
      .then(data => setAdvisors(data))
  }, [])

  function handleSubmit(event) {
    event.preventDefault()
    setIsSubmitting(true)

    const selectedAdvisor = advisors.find((item) => item.id === advisor)
    const watchRequest = {
      email: email,
      appointmentType: appointmentType,
      advisorPreference: 
        AdvisorPreference === 'ANY_ADVISOR'
        ? ''
        : selectedAdvisor?.displayName,
      agentId:
        AdvisorPreference === 'ANY_ADVISOR'
        ? ''
        : advisor
    }
    console.log(watchRequest)
    fetch('http://localhost:8080/watchrequests', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(watchRequest)
    })
    .then((response) => {
      console.log(response)
      if (response.ok) {
        setSuccessMessage('Watch request submitted successfully!')
        setErrorMessage('')
      } else {
        setErrorMessage('Something went wrong. Please try again.')
        setSuccessMessage('')
      }
    })
    .catch((error) => {
      console.log(error)
      setErrorMessage('Could not connect to the server.')
      setSuccessMessage('')
    })
    .finally(() => {
      setIsSubmitting(false)
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
        required
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
      required
      >
        <option value="">Select an Advisor</option>
        {advisors.map((advisor) => (
          <option key={advisor.id} value={advisor.id}>
            {advisor.displayName}
          </option>
        ))}
      </select>
      </>

      )}

      <button type="submit" disabled={isSubmitting}>
        {isSubmitting ? 'Submitting...' : 'Start Monitoring'}
      </button>

      {successMessage && <p>{successMessage}</p>}
      {errorMessage && <p>{errorMessage}</p>}

    </form>
    </main>
  )
}

export default App