import './EditBook.css';
import {Book} from "../Types.tsx";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import AddEditBookForm from "./AddEditBookForm.tsx";

type Props = {
    books: Book[]
    onItemChange: ()=>void
}

export default function EditBook(props: Props ) {
    const { id } = useParams();
    const navigate = useNavigate();
    console.debug(`Rendering EditBookForm { id:"${id}", props.books: ${props.books.length} books }`);

    const filteredBooks:Book[] = props.books.filter(e => e.id === id);

    if (filteredBooks.length < 1)
        return <>
            Can't find book with id "{id}"<br/>
            <button type="button" onClick={() => navigate("/")}>Back</button>
        </>

    function update( book: Book ) {
        axios
            .put('/api/books/'+book.id, book )
            .then(response => {
                if (response.status != 200)
                    throw new Error("Got wrong status on update book: " + response.status);
                props.onItemChange();
            })
            .catch(reason => {
                console.error(reason);
            });
        navigate("/");
    }

    return (
        <AddEditBookForm
            formCssClass="EditBookForm"
            book={filteredBooks[0]}
            saveBook={update}
            saveButtonTitle="Save"
        />
    )
}

