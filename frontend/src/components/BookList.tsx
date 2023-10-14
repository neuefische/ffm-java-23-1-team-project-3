import {Book} from "../Types.tsx";
import BookCard from "./BookCard.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    books: Book[]
    onItemChange: () => void
    showAdd: boolean
    showHomepage: boolean
    showSearch: boolean
}

export default function BookList( props: Props ) {
    const navigate = useNavigate();
    return (
        <>
            {props.showSearch &&
                <button className="searchButton" onClick={()=>navigate("/books/search")}>
                    <h3>Search a book title</h3>
                    <p>click here</p>
                </button>
            }

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