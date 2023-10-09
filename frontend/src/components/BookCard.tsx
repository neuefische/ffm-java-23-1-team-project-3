import {Book} from "../Types.tsx";
import axios from "axios";

type Props = {
    book: Book
    onItemChange: () => void
}

export default function BookCard( props: Props ) {

    function deleteCard() {
        axios.delete("/api/books/" + props.book.id)
            .then(props.onItemChange)
    }

    return (
        <div className="BookCard">
            <div>id     : {props.book.id     }</div>
            <div>title  : {props.book.title  }</div>
            <div>author : {props.book.author }</div>
            <button onClick={deleteCard}>X</button>
        </div>
    )
}