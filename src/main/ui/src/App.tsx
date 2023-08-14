import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import Home from "./pages/home/Home";
import SignUp from "./pages/signup/SignUp";

function App() {
  return (
    <div className="App">
      <Routes>
          <Route path="/" element={<Home />}/>
          <Route path="/signup" element={<SignUp />}/>
      </Routes>
    </div>
  );
}

export default App;
