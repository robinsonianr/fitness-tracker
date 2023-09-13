import "./dashboard.scss"
import Header from "../../components/header/Header";


export const Dashboard = () => {

    return (
        <div>
            <Header title="Fitness Tracker"/>
            <h1 className="home-title">Welcome to the Dashboard!</h1>
        </div>
    );
};

export default Dashboard;