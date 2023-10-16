import {Book} from "../Types.tsx";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import AddEditBookForm, {NewCover, uploadCover} from "./AddEditBookForm.tsx";

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

    function updateBook(book: Book) {
        console.debug(`EditBook.updateBook { id:${book.id} }`);
        axios
            .put('/api/books/' + book.id, book)
            .then(response => {
                if (response.status != 200)
                    throw new Error("Got wrong status on update book: " + response.status);
                console.debug(`EditBook.updateBook { id:${book.id} } --> success`);
                props.onItemChange();
            })
            .catch(reason => {
                console.error("Error in EditBook.updateBook", reason);
            });
    }

    function save( book: Book, newCover?: NewCover ) {
        if (newCover)
            uploadCover(
                book.id, newCover,
                coverUrl => {
                    console.debug(`EditBook.save { id:${book.id} } --> setCoverURL( "${coverUrl}" )`);
                    book.coverUrl = coverUrl;
                    updateBook(book);
                },
                () => {
                    updateBook(book);
                }
            )
        else
            updateBook(book);
        navigate("/");
    }

    return (
        <AddEditBookForm
            book={filteredBooks[0]}
            saveBook={save}
            saveButtonTitle="Save"
        />
    )
}

