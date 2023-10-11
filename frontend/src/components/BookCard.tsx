import {Book} from "../Types.tsx";
import {Link} from "react-router-dom";
import {useNavigate} from "react-router-dom";
import axios from "axios";

type Props = {
    book: Book
    onItemChange: () => void
}

export default function BookCard( props: Props ) {
    const navigate = useNavigate();

    function deleteCard() {
        axios.delete("/api/books/" + props.book.id)
            .then(props.onItemChange)
    }

    return (
        <div className="BookCard">
            <Link to={`/books/${props.book.id}`}>
            {/*<div>id     : {props.book.id     }</div>*/}
            <h3>{props.book.title  }</h3>
            <p>{props.book.author }</p>
            </Link>
            <button onClick={() => navigate("/books/"+props.book.id+"/edit")}>Edit</button>
            <button onClick={deleteCard}>✖</button>
        </div>
    )
}