import {Book} from "../Types.tsx";
import BookCard from "./BookCard.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    books: Book[]
    onItemChange: () => void
    showAdd: boolean
    showHomepage: boolean
}

export default function BookList( props: Props ) {
    const navigate = useNavigate();
    return (
        <>
        <div className="BookList">
            {props.showAdd &&
            <button className="BookCard" onClick={()=>navigate("/books/add")}>
                <h3>Add a new Book</h3>
                <p>click here</p>
            </button>
            }
            {
                props.books.map( book =>
                    <BookCard key={book.id} book={book} onItemChange={props.onItemChange}/>
                )
            }
            {
                props.showHomepage &&
                <button type="button" onClick={()=>navigate("/")}>Back to Homepage</button>
            }

        </div>
        </>
    )
}