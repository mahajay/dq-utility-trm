import React from "react";
import "./style.css";

function Container(props) {

    return (
        // <main className="Container">
            
        // </main>

        <div class="wrapper fadeInDown">
            <div id="formContent">
            {/* <!-- Tabs Titles --> */}

            {/* <!-- Icon --> */}
    <div class="fadeIn first">
      <img src="https://img.icons8.com/material/24/000000/gender-neutral-user.png" id="icon" alt="User Icon" />
    </div>

    {/* <!-- Login Form --> */}
    <form>
      <input type="text" id="login" class="fadeIn second" name="login" placeholder="Email Address"/>
      <input type="text" id="password" class="fadeIn third" name="login" placeholder="Password"/>
      <input type="submit" class="fadeIn fourth" value="Log In"/>
    </form>

    {/* <!-- Remind Passowrd --> */}
    <div id="formFooter">
      <a class="underlineHover" href="#">Forgot Password?</a>
    </div>

  </div>
</div>
    );
}

export default Container;