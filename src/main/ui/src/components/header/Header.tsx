import React from "react";

// @ts-ignore
export const Header = ({ title }) => {
    return (
     <header>
         <h1>{title}</h1>
         <nav>
             <ul>
                 <li><a href="/">Home</a></li>
                 <li><a href="/signup">Sign Up</a></li>
                 <li><a href="/login">Login</a></li>
             </ul>
         </nav>
     </header>
    );
};

export default Header;