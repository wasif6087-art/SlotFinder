import { useEffect, useState, useRef } from 'react'
import './App.css'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

// Custom Dropdown Component
function Dropdown({ id, value, onChange, options, placeholder, disabled, className }) {
  const [isOpen, setIsOpen] = useState(false)
  const dropdownRef = useRef(null)

  useEffect(() => {
    function handleClickOutside(event) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false)
      }
    }

    if (isOpen) {
      document.addEventListener('mousedown', handleClickOutside)
      return () => document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [isOpen])

  const selectedOption = options.find(opt => opt.value === value)
  const displayText = selectedOption?.label || placeholder

  return (
    <div className={`dropdown ${className || ''}`} ref={dropdownRef}>
      <button
        type="button"
        className="dropdown-trigger"
        onClick={() => !disabled && setIsOpen(!isOpen)}
        disabled={disabled}
        aria-haspopup="listbox"
        aria-expanded={isOpen}
      >
        <span className="dropdown-text">{displayText}</span>
        <svg className={`dropdown-arrow ${isOpen ? 'open' : ''}`} width="12" height="8" viewBox="0 0 12 8" fill="none">
          <path d="M1 1L6 6L11 1" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" />
        </svg>
      </button>
      
      {isOpen && (
        <div className="dropdown-menu">
          {options.map((option) => (
            <button
              key={option.value}
              type="button"
              className={`dropdown-option ${value === option.value ? 'selected' : ''}`}
              onClick={() => {
                onChange(option.value)
                setIsOpen(false)
              }}
              role="option"
              aria-selected={value === option.value}
            >
              {option.label}
            </button>
          ))}
        </div>
      )}
    </div>
  )
}

function App() {
  const [AdvisorPreference, setAdvisorPreference] = useState('ANY_ADVISOR')
  const [email, setEmail] = useState('')
  const [appointmentType, setAppointmentType] = useState('PHONE_ZOOM')
  const [advisor, setAdvisor] = useState('')
  const [advisors, setAdvisors] = useState([])
  const [errorMessage, setErrorMessage] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [submitted, setSubmitted] = useState(false)
  const [advisorsLoading, setAdvisorsLoading] = useState(true)
  const [advisorsError, setAdvisorsError] = useState('')
  const [emailError, setEmailError] = useState('')
  const [advisorError, setAdvisorError] = useState('')

  const pathParts = window.location.pathname.split('/')
  const unsubscribeToken = pathParts[1] === 'unsubscribe' ? pathParts[2] : null
  const [unsubscribed, setUnsubscribed] = useState(false)
  const [keepMonitoring, setKeepMonitoring] = useState(false)

  
  

  useEffect(() => {
    fetch(`${API_BASE_URL}/advisors`)
      .then(response => response.json())
      .then(data => {
        setAdvisors(data)
      })
      .catch(error => {
        setAdvisorsError('Failed to fetch advisors.')
      })
      .finally(() => {
        setAdvisorsLoading(false)
      })
  }, [])

  function handleSubmit(event) {
    event.preventDefault()
    
    // Reset errors
    setEmailError('')
    setAdvisorError('')
    setErrorMessage('')
    
    // Validate email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailRegex.test(email)) {
      setEmailError('Enter a valid email address.')
      return
    }
    
    // Validate advisor selection if specific advisor is chosen
    if (AdvisorPreference === 'SPECIFIC_ADVISOR' && !advisor) {
      setAdvisorError('Choose an advisor to continue.')
      return
    }
    
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
    fetch(`${API_BASE_URL}/watchrequests`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(watchRequest)
    })
    .then((response) => {
      console.log(response)
      if (response.ok) {
        setErrorMessage('')
        setSubmitted(true)
      } else {
        setErrorMessage('Something went wrong. Please try again.')
      }
    })
    .catch((error) => {
      console.log(error)
      setErrorMessage('Something went wrong. Please try again.')
    })
    .finally(() => {
      setIsSubmitting(false)
    })

  }

  function handleCreateAnother() {
    setSubmitted(false)
    setErrorMessage('')
    setEmail('')
    setAppointmentType('PHONE_ZOOM')
    setAdvisorPreference('ANY_ADVISOR')
    setAdvisor('')
    setEmailError('')
    setAdvisorError('')
  }

  function handleUnsubscribe() {
    fetch(`${API_BASE_URL}/unsubscribe/${unsubscribeToken}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to unsubscribe.')
        }
        return response.text()
      })
      .then((data) => {
        console.log(data)
        setUnsubscribed(true)
      })
      .catch((error) => {
        console.error(error)
      })
  }

  if (unsubscribeToken) {

    if (unsubscribed) {
      return (
        <main className="app">
          <div className="container">
            <h1 className="brand-heading">UBC SlotFinder</h1>
            <p className="tagline">Advising appointments without refreshing all day.</p>

            <div className="success-state">
              <div className="success-checkmark">✓</div>
              <h2 className="success-heading">Monitoring Cancelled</h2>
              <p className="success-message">You will no longer receive notifications for this request.</p>
            </div>
          </div>
        </main>
      )
    }

    if (keepMonitoring) {
      return (
        <main className="app">
          <div className="container">
            <h1 className="brand-heading">UBC SlotFinder</h1>
            <p className="tagline">Advising appointments without refreshing all day.</p>

            <div className="success-state">
              <div className="success-checkmark">✓</div>
              <h2 className="success-heading">We'll Keep Looking</h2>
              <p className="success-message">We'll email you when appointment slots are available.</p>
            </div>
          </div>
        </main>
      )
    }

    return (
      <main className="app">
        <div className="container">
          <h1 className="brand-heading">UBC SlotFinder</h1>
          <p className="tagline">Advising appointments without refreshing all day.</p>

          <div className="unsubscribe-confirmation">
            <h2 className="success-heading">Stop appointment alerts?</h2>
            <p className="success-message">You will no longer receive appointment notifications.</p>

            <div className="unsubscribe-actions">
              <button className="primary-button" onClick={handleUnsubscribe}>
                Stop Monitoring
              </button>

              <button className="secondary-button" onClick={() => setKeepMonitoring(true)}>
                Keep Monitoring
              </button>
            </div>
          </div>
        </div>
      </main>
    )
  }  


  return (
    <main className="app">
      <div className="container">
        <h1 className="brand-heading">UBC SlotFinder</h1>
        <p className="tagline">Advising appointments without refreshing all day.</p>

        {!submitted && (
          <form className="form-container" onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                id="email"
                type="email"
                placeholder="you@example.com"
                value={email}
                onChange={(event) => {
                  setEmail(event.target.value)
                  if (emailError) setEmailError('')
                }}
                className={emailError ? 'error' : ''}
              />
              {emailError && <span className="error-message">{emailError}</span>}
            </div>

            <div className="form-group">
              <label htmlFor="appointmentType">Appointment Type</label>
              <Dropdown
                id="appointmentType"
                value={appointmentType}
                onChange={setAppointmentType}
                options={[
                  { value: 'PHONE_ZOOM', label: 'Phone / Zoom' },
                  { value: 'IN_PERSON', label: 'In Person' }
                ]}
                placeholder="Select appointment type"
              />
            </div>

            <div className="form-group">
              <label htmlFor="advisorPreference">Advisor Preference</label>
              <Dropdown
                id="advisorPreference"
                value={AdvisorPreference}
                onChange={setAdvisorPreference}
                options={[
                  { value: 'ANY_ADVISOR', label: 'Any Advisor' },
                  { value: 'SPECIFIC_ADVISOR', label: 'Specific Advisor' }
                ]}
                placeholder="Select preference"
              />
            </div>

            {AdvisorPreference === 'SPECIFIC_ADVISOR' && (
              <div className="form-group">
                <label htmlFor="advisor">Advisor</label>
                {advisorsLoading && <p className="loading-text">Loading advisors...</p>}
                {advisorsError && <p className="error-message">{advisorsError}</p>}
                {!advisorsLoading && !advisorsError && (
                  <>
                    <Dropdown
                      id="advisor"
                      value={advisor}
                      onChange={(value) => {
                        setAdvisor(value)
                        if (advisorError) setAdvisorError('')
                      }}
                      options={[
                        { value: '', label: 'Select an Advisor' },
                        ...advisors.map((adv) => ({
                          value: adv.id,
                          label: adv.displayName
                        }))
                      ]}
                      placeholder="Select an advisor"
                      className={advisorError ? 'error' : ''}
                    />
                    {advisorError && <span className="error-message">{advisorError}</span>}
                  </>
                )}
              </div>
            )}

            <button type="submit" disabled={isSubmitting} className="primary-button">
              Find Appointments
            </button>

            {isSubmitting && <div className="spinner"></div>}

            {errorMessage && <p className="error-message error-server">{errorMessage}</p>}
          </form>
        )}

        {submitted && (
          <div className="success-state">
            <div className="success-checkmark">✓</div>
            <h2 className="success-heading">You're all set!</h2>
            <p className="success-message">We'll email you as soon as appointments open up.</p>
            <div className="success-email-section">
              <p className="success-email-label">We'll notify you at</p>
              <p className="success-email">{email}</p>
            </div>
            <button onClick={handleCreateAnother} className="secondary-button">
              Submit Another Request
            </button>
          </div>
        )}
      </div>
    </main>
  )
}

export default App