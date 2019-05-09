import React from 'react';
import { BrowserRouter as Router, Route } from "react-router-dom";
// import Navbar from "./components/Navbar";
// import Footer from "./components/Footer";
import Home from "./pages/Home";
import Connection from "./pages/Connection";

import './App.css';

function App() {
  return (
    <Router>
      <div>
        {/* <Navbar /> */}
        <Route exact path="/" component={Home} />
        <Route exact path="/connection" component={Connection} />
        {/* <Route exact path="/about" component={About} /> */}
        {/* <Footer /> */}
      </div>
    </Router>
  );
}

export default App;
