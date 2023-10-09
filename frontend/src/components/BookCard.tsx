import {Book} from "../Types.tsx";
import {Link} from "react-router-dom";

type Props = {
    book: Book
}

export default function BookCard( props: Props ) {

    return (
        <div className="BookCard">
            <Link to={`/books/${props.book.id}`}>
            <div>id     : {props.book.id     }</div>
            <div>title  : {props.book.title  }</div>
            <div>author : {props.book.author }</div>
            </Link>
        </div>

    )
}