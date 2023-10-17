type Props = {
    login: ()=>void
}

export default function LoginPage( props:Props ) {
    return (
        <>
            Please login. <br/>
            <button onClick={props.login}>Login</button>
        </>
    )
}