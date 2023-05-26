import { useState } from 'react';

const RegisterForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [registerSuccess, setRegisterSuccess] = useState(false);
    const [registerError, setRegisterError] = useState(false);

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch('http://localhost:5000/register', {
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
                    setRegisterSuccess(true);
                    setRegisterError(false);
                } else {
                    setRegisterSuccess(false);
                    setRegisterError(true);
                }
            })
            .catch((error) => {
                console.error(error);
                setRegisterSuccess(false);
                setRegisterError(true);
            });

        setUsername('');
        setPassword('');
    };

    return (
        <div>
            {registerSuccess && <div>Pomyślnie zarejestrowano.</div>}
            {registerError && <div>Błąd rejestracji. Konto już istnieje.</div>}
            {!registerSuccess && !registerError && (
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
                    <button type="submit">Zarejestruj</button>
                </form>
            )}
        </div>
    );
};

export default RegisterForm;
