import {Navigate, Outlet} from "react-router-dom";
import {UserInfos} from "../Types.tsx";

type Props = {
    user?: UserInfos
}

export default function ProtectedRoutes(props: Props) {

    const isAuthenticated = props.user?.isAuthenticated;

    return(
        isAuthenticated ? <Outlet /> : <Navigate to="/login" />
    )
}