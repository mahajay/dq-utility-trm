import React, { Component } from "react";
import Header from "../components/Header";
import Container from "../components/Container";
// import API from "../utils/API";
import users from "../user.json";
import "../components/Container/style.css"

class Home extends Component {

state = {
    user: users,
    Email: "",
    Passowrd:"",
    ValidUser:0
}

handleInputChange = event => {
    event.preventDefault();
    const { name, value } = event.target;
    this.setState({
      [name]: value
    });
  };

redirect = link=>{
     window.location = link+"connection";
    
};  

getuser = e =>{
    e.preventDefault();
    
    const user = this.state.user;

    var obj = {};
    obj = user.find(o => o.EMail_ID === this.state.Email);

    // if(Object.entries(obj).length===0){
    //     console.log("Pass");
    // }

    // console.log(Object.entries(obj).length);

    // for (var i=0;i<user.length;i++){
        
    //     if(user[i].EMail_ID === this.state.Email && user[i].Password === this.state.Passowrd){
    //         break;
    //     }else{
    //         this.setState({InValidUser:1})
    //     }
    // }

    if(!obj){
        alert("Invalid UserName or Password");
    }else if(obj.Password===this.state.Passowrd){
        // window.location.reload();
        this.redirect(window.location.href);
        // window.location("/connection");
    }else{
        alert("Invalid UserName or Password");
    };
    
};


render() {
    return(
        <div>
            <Header  />
            <div className="wrapper fadeInDown">
            <div id="formContent">
            {/* <!-- Tabs Titles --> */}

            {/* <!-- Icon --> */}
            <div className="fadeIn first">
            <img src="https://img.icons8.com/material/24/000000/gender-neutral-user.png" id="icon" alt="User Icon" />
            </div>

            {/* <!-- Login Form --> */}
            <form>
            <input type="text" id="login" className="fadeIn second" name="Email" placeholder="Email Address" onChange={this.handleInputChange}/>
            <input type="text" id="password" className="fadeIn third" name="Passowrd" placeholder="Password" onChange={this.handleInputChange}/>
            <input type="submit" href="" className="fadeIn fourth" value="Log In" onClick={this.getuser}/>
            </form>

            {/* <!-- Remind Passowrd --> */}
            <div id="formFooter">
            <a className="underlineHover" href="#">Forgot Password?</a>
            </div>

            </div>
            </div>
        </div>
    );
}


}

export default Home;

