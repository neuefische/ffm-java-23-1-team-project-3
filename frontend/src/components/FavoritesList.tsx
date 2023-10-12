import {Book} from "../Types.tsx";
import BookCard from "./BookCard.tsx";


type Props = {
    books: Book[]
    onItemChange: () => void
}

export default function FavoritesList(props: Props) {

    const favoriteBooks = props.books.filter(book => book.favorite)

    return (

        <div>
            <div className="BookList">
                {
                    favoriteBooks.map( book =>
                        <BookCard key={book.id} book={book} onItemChange={props.onItemChange}/>
                    )
                }
            </div>
        </div>
    )
}