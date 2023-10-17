import {Book, UserInfos} from "../Types.tsx";
import BookCard from "./BookCard.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    user?: UserInfos;
    headline?: string
    books: Book[]
    onItemChange: () => void
    showAdd: boolean
}

const showButtonsAlways = true;

export default function BookList( props: Props ) {
    const navigate = useNavigate();
    return (
        <>
        {props.headline && <h3>{props.headline}</h3>}

        <div className="BookList">
            {
                props.showAdd && (showButtonsAlways || props.user?.isAuthenticated) &&
                <button className="BookCard" onClick={()=>navigate("/books/add")}>
                    <h3>Add a new Book</h3>
                    <p>click here</p>
                </button>
            }
            {
                props.books.map( book =>
                    <BookCard key={book.id} book={book} user={props.user} onItemChange={props.onItemChange}/>
                )
            }
        </div>
        </>
    )
}