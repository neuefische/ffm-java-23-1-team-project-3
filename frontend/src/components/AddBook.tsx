import {Book} from "../Types.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import AddEditBookForm from "./AddEditBookForm.tsx";

type Props ={
    onItemChange: () => void
}
export default function AddBook(props: Props) {
    const emptyBook: Book = {
        id         : "",
        title      : "",
        author     : "",
        description: "",
        publisher  : "",
        isbn       : "",
        coverUrl   : "",
    };
    const navigate = useNavigate();
    console.debug(`Rendering AddBook {}`);

    function saveNewBook(book: Book) {
        axios
            .post("/api/books", book)
            .then(()=>{
                props.onItemChange();
            })
        navigate("/");
    }

    return (
        <AddEditBookForm
            formCssClass="addBookForm"
            book={emptyBook}
            saveBook={saveNewBook}
            saveButtonTitle="Add New Book"
        />
    )
}