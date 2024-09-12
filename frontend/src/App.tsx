import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";
import Dashboard from 'pages/Dashboard';
//import User from 'pages/User';
//import Loan from 'pages/Loan';
//import Book from 'pages/Book';
import Navbar from "components/Navbar";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Dashboard/>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;