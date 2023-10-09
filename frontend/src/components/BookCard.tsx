import {Book} from "../Types.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    book: Book
}

export default function BookCard( props: Props ) {
    const navigate = useNavigate();

    return (
        <div className="BookCard">
            <div>id     : {props.book.id     }</div>
            <div>title  : {props.book.title  }</div>
            <div>author : {props.book.author }</div>
            <button onClick={() => navigate("/books/"+props.book.id+"/edit")}>Edit</button>
        </div>
    )
}