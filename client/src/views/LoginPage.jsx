import {useState} from "react";
import UserRepository from "../repositories/UsersRepository";
import TextInput from "../components/inputs/SmallTextInput";

export default function LoginPage() {
  const [alert, setAlert] = useState(null);
  const [values, setValues] = useState({
    email: "",
    password: "",
  });

  const handleInputChange = (event) => {
    const {name, value} = event.target;
    setValues(prevState => ({...prevState, [name]: value}));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await UserRepository.validateUserCredentials(values.email, values.password);

      if (response.status === 200) { handleValidCredentials(response.data.userId, response.data.role); }
    } catch (error) {
      if (error.response.status === 401) { handleInvalidCredentials(); }
      else { handleServerError(); }
    }
  }

  const handleValidCredentials = (id, role) => {
    localStorage.setItem("userId", id); // For now we store the user id in local storage, but this will change in the future to a more secure approach
    if (role.toUpperCase() === "ADMIN") { localStorage.setItem("isAdmin", true); } // For now we store whether the user is an admin in local storage, but this will change in the future to a more secure approach
    if (role.toUpperCase() === "GRADUATE") { localStorage.setItem("role", "graduate"); }
    window.location.replace("/account");
  }

  const handleInvalidCredentials = () => {
    setAlert("Je hebt onjuiste inloggegevens ingevoerd");
  }

  const handleServerError = () => {
    setAlert("Er is een fout opgetreden op de server");
  }

  const handleAlertClose = () => {
    setAlert(null);
  }

  return (
    <main className="container h-100">
      <div className="row justify-content-center min-height">
        <div className="col-md-6 col-lg-4">
          <div className="card mt-5 border-0 default-black-shadow">
            <div className="card-header bg-transparent border-0 border-sm-1">
              <h3 className="text-center font-weight-bold">Inloggen</h3>
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>

                <TextInput
                  handleInputChange={handleInputChange}
                  displayName="E-mailadres"
                  name="email"
                  placeholder="Typ je e-mailadres in"
                  icon="bi-envelope"
                  isInput={true}
                />

                <TextInput
                  handleInputChange={handleInputChange}
                  displayName="Wachtwoord"
                  name="password"
                  placeholder="Typ je wachtwoord in"
                  icon="bi-lock"
                  isInput={true}
                  isPassword={true}
                />

                <button type="submit" className="btn btn-primary w-100" aria-label="LoginPage">Log in</button>
              </form>
            </div>
          </div>
          {alert &&
            <div className="alert alert-danger mt-3 d-flex justify-content-between" role="alert" aria-live="assertive">
              <div>{alert}</div>
              <button type="button" className="btn-close" aria-label="Close alert" onClick={handleAlertClose}></button>
            </div>
          }
        </div>
      </div>
    </main>
  );
}
