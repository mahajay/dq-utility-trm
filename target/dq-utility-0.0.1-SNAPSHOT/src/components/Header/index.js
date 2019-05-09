import React from "react";
import "./style.css";

function Header() {
  return (
    // <header className="header">
    //     <a href="/">
    //     <img src="http://www.octoconsulting.com/wp-content/themes/octo-theme/images/Octo_logo.png"/>
    //     </a>
    //     <h1>DQ Automation Tool</h1>
    //     <h3>About Us</h3>
    // </header>
<nav className="navbar">
    <ul>
        <li className="brand">
            <a href="/">
            <img src="http://www.octoconsulting.com/wp-content/themes/octo-theme/images/Octo_logo.png"/>
            </a>
        </li>
        <li className="tool">DQ Automation Tool</li>
        <li className="info" href="/">About Us</li>
    </ul>
</nav>
  );
}

export default Header;