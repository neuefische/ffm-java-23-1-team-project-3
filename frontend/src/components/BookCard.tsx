import {Book} from "../Types.tsx";
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
            <div>id     : {props.book.id     }</div>
            <div>title  : {props.book.title  }</div>
            <div>author : {props.book.author }</div>
            <button onClick={() => navigate("/books/"+props.book.id+"/edit")}>Edit</button>
            <button onClick={deleteCard}>X</button>
        </div>
    )
}