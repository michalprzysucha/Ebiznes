import { useState } from 'react';
import {Redirect} from "react-router-dom";


const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loginSuccess, setLoginSuccess] = useState(false);
    const [loginError, setLoginError] = useState(false);

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch('http://localhost:5000/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username,
                password,
            }),
        })
            .then((response) => {
                if (response.ok) {
                    setLoginSuccess(true);
                    setLoginError(false);
                } else {
                    setLoginSuccess(false);
                    setLoginError(true);
                }
            })
            .catch((error) => {
                console.error(error);
                setLoginSuccess(false);
                setLoginError(true);
            });

        setUsername('');
        setPassword('');
    };

    return (
        <div>
            {loginSuccess && <Redirect to="/loggedin" />}
            {loginError && <div>Błąd logowania. Sprawdź swoje dane logowania.</div>}
            {!loginSuccess && !loginError && (
                <form onSubmit={handleSubmit}>
                    <label htmlFor="username">Nazwa użytkownika:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={handleUsernameChange}
                    />
                    <label htmlFor="password">Hasło:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={handlePasswordChange}
                    />
                    <button type="submit">Zaloguj</button>
                </form>
            )}
        </div>
    );
};

export default LoginForm;
