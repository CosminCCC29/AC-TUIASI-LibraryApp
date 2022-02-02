import LoginPage from "./components/LoginPage"
import RegisterPage from "./components/RegisterPage"

import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

function App() {
  return (
    <Router>
      <div className="content">
        <Switch>
          <Route exact path="/login">
            <LoginPage />
          </Route>
          <Route exact path="/register">
            <RegisterPage />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
