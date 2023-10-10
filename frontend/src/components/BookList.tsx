import {Book} from "../Types.tsx";
import BookCard from "./BookCard.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    books: Book[]
    onItemChange: () => void
}

export default function BookList( props: Props ) {
    const navigate = useNavigate();
    return (
        <>
            <nav>
                <button onClick={()=>navigate("/books/add")}>Add</button>
            </nav>

        <div className="BookList">
            {
                props.books.map( book =>
                    <BookCard key={book.id} book={book} onItemChange={props.onItemChange}/>
                )
            }
        </div>
        </>
    )
}