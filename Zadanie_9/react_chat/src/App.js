import React, { useState } from 'react';
import './App.css';

function App() {
  const [query, setQuery] = useState('');
  const [response, setResponse] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ message: query })
    };

    const response = await fetch('http://127.0.0.1:5000/', requestOptions);
    const data = await response.json();

    setResponse(data.response);
  };

  return (
      <>
        <form onSubmit={handleSubmit}>
          <label htmlFor="query">
            Zapytanie:
          </label>
          <input
              type="text"
              id="query"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
          />
          <button type="submit">Wyślij</button>
        </form>

        {response && (
            <div>
              <h3>Odpowiedź:</h3>
              <p>{response}</p>
            </div>
        )}
      </>
  );
}

export default App;
