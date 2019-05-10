import React, { Component } from "react";
import Header from "../components/Header";
import "./Connection.css"

class Connection extends Component {



    render () {
        return(
            <div>
                <Header />
                <div className="d-md-flex h-md-100 align-items-center">


                    <div className="col-md-6 p-0 bg-White h-md-100 firsthalf">
                        <div className="d-md-flex align-items-center h-100 p-5 justify-content-center">
                            <form>
                            <div className="form-row">
                                <div class="form-group col-md-4">
                                    <label for="inputState">Select Database</label>
                                    <select id="inputState" class="form-control">
                                    <option selected>Choose...</option>
                                        <option value="Oracle">Oracle</option>
                                        <option value="MySql">MySql</option>
                                        <option value="SQL Server">SQL Server</option>
                                        <option value="Postgres">Postgres</option>
                                        <option value="MongoDB">MongoDB</option>
                                        <option value="Cassandra">Cassandra</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-row">
                                <div class="form-group col-md-6">
                                <label for="inputEmail4">UserName</label>
                                <input type="email" class="form-control" id="inputEmail4" placeholder="UserName"/>
                                </div>
                                <div class="form-group col-md-6">
                                <label for="inputPassword4">Password</label>
                                <input type="password" class="form-control" id="inputPassword4" placeholder="Password"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputAddress">Host Name</label>
                                <input type="text" class="form-control" id="inputAddress" placeholder="Host Address"/>
                            </div>
                            {/* <div class="form-group">
                                <label for="inputAddress2">Address 2</label>
                                <input type="text" class="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor"/>
                            </div> */}
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                <label for="inputCity">Port No</label>
                                <input type="text" class="form-control" id="inputCity" placeholder="3000"/>
                                </div>
                                <div class="form-group col-md-6">
                                <label for="inputZip">Service Name</label>
                                <input type="text" class="form-control" id="inputZip" placeholder=" "/>
                                </div>
                            </div>
                        </form>
                        </div>
                    </div>

                    <div className="col-md-6 p-0 bg-white h-md-100 loginarea">
                        <div className="d-md-flex align-items-center h-md-100 p-5 justify-content-center">
                        <form>
                            <div className="form-row">
                                <div class="form-group col-md-4">
                                    <label for="inputState">Select Database</label>
                                    <select id="inputState" class="form-control">
                                    <option selected>Choose...</option>
                                        <option value="Oracle">Oracle</option>
                                        <option value="MySql">MySql</option>
                                        <option value="SQL Server">SQL Server</option>
                                        <option value="Postgres">Postgres</option>
                                        <option value="MongoDB">MongoDB</option>
                                        <option value="Cassandra">Cassandra</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-row">
                                <div class="form-group col-md-6">
                                <label for="inputEmail4">UserName</label>
                                <input type="email" class="form-control" id="inputEmail4" placeholder="UserName"/>
                                </div>
                                <div class="form-group col-md-6">
                                <label for="inputPassword4">Password</label>
                                <input type="password" class="form-control" id="inputPassword4" placeholder="Password"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputAddress">Host Name</label>
                                <input type="text" class="form-control" id="inputAddress" placeholder="Host Address"/>
                            </div>
                            {/* <div class="form-group">
                                <label for="inputAddress2">Address 2</label>
                                <input type="text" class="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor"/>
                            </div> */}
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                <label for="inputCity">Port No</label>
                                <input type="text" class="form-control" id="inputCity" placeholder="3000"/>
                                </div>
                                <div class="form-group col-md-6">
                                <label for="inputZip">Service Name</label>
                                <input type="text" class="form-control" id="inputZip" placeholder=" "/>
                                </div>
                            </div>
                        </form>
                        
                        </div>
                    </div>
                        
                </div>
                <br/>
                <div className="connection">
                    <button type="button" className="btn btn-info">Connect</button>
                </div>
            </div>
        );
    }
}

export default Connection;