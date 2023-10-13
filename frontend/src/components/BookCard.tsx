import {Book} from "../Types.tsx";
import {Link, useNavigate} from "react-router-dom";
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

    function favor() {
        axios.put("/api/books/" + props.book.id, {
            ...props.book,
            favorite: true
        })
            .then(props.onItemChange)
            .catch(reason => {
                console.error(reason)
            });
    }

    return (
        <div className="BookCard">
            <Link to={`/books/${props.book.id}`}>
            {/*<div>id     : {props.book.id     }</div>*/}
            <h3>{props.book.title  }</h3>
            <p>{props.book.author }</p>
            </Link>
            <button onClick={() => navigate("/books/"+props.book.id+"/edit")}>✎</button>
            <button onClick={deleteCard}>✖</button>
            <button className="star" onClick={favor}><svg width="24" height="24" viewBox="0 0 24 24" focusable="false" className="TYVfy NMm5M"><path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21 12 17.27z"></path></svg></button>
        </div>
    )
}