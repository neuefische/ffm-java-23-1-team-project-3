import {Book} from "../Types.tsx";
import BookCard from "./BookCard.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    headline?: string
    books: Book[]
    onItemChange: () => void
}

export default function BookList( props: Props ) {
    const navigate = useNavigate();
    return (
        <>
        {props.headline && <h3>{props.headline}</h3>}

        <div className="BookList">
            <button className="BookCard" onClick={()=>navigate("/books/add")}>
                <h3>Add a new Book</h3>
                <p>click here</p>
            </button>
            {
                props.books.map( book =>
                    <BookCard key={book.id} book={book} onItemChange={props.onItemChange}/>
                )
            }
        </div>
        </>
    )
}