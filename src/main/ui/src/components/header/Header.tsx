import React from "react";
import AuthService from "../../provider/AuthService";

// @ts-ignore
export const Header = ({ title }) => {
    return (
     <header>
         <h1>{title}</h1>
         <nav>
             <ul>
                 <li><a href="/">Home</a></li>
                 <li onClick={AuthService.logout}><a href="/login">Logout</a></li>
             </ul>
         </nav>
     </header>
    );
};

export default Header;