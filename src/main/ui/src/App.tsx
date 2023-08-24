import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import Home from "./pages/home/Home";
import SignUp from "./pages/signup/SignUp";
import {Login} from "./pages/login/Login";

function App() {
  return (
    <div className="App">
      <Routes>
          <Route path="/" element={<Home />}/>
          <Route path="/signup" element={<SignUp />}/>
          <Route path="/login" element={<Login />}/>
      </Routes>
    </div>
  );
}

export default App;
