import {Book} from "../Types.tsx";
import BookCard from "./BookCard.tsx";

type Props = {
    books: Book[]
}

export default function BookList( props: Props ) {

    return (
        <div className="BookList">
            {
                props.books.map( book =>
                    <BookCard key={book.id} book={book}/>
                )
            }
        </div>
    )
}