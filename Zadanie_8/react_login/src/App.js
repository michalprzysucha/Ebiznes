import React from 'react';
import { BrowserRouter, Link, Route } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import LoggedIn from './components/LoggedIn';
import RegistrationForm from './components/RegistrationForm';
import './App.css';

function App() {
  return (
      <div className="App">
          <h1>Aplikacja do logowania i rejestracji</h1>
          <BrowserRouter forceRefresh={true}>
              <nav>
                  <ul className = "hidden-bullets-ul">
                      <li>
                          <Link to="/login">Logowanie</Link>
                      </li>
                      <li>
                          <Link to="/register">Rejestracja</Link>
                      </li>
                  </ul>
              </nav>
              <Route path="/login" component={LoginForm} />
              <Route path="/loggedin" component={LoggedIn}/>
              <Route path="/register" component={RegistrationForm} />
        </BrowserRouter>
      </div>
  );
}

export default App;
