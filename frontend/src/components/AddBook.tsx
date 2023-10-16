import {Book} from "../Types.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import AddEditBookForm, {NewCover, uploadCover} from "./AddEditBookForm.tsx";

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
        favorite   : false
    };
    const navigate = useNavigate();
    console.debug(`Rendering AddBook {}`);

    function updateBook(book: Book) {
        console.debug(`AddBook.updateBook { id:${book.id} }`);
        axios
            .put('/api/books/' + book.id, book)
            .then(response => {
                if (response.status != 200)
                    throw new Error("Got wrong status on update book: " + response.status);
                console.debug(`AddBook.updateBook { id:${book.id} } --> success`);
                props.onItemChange();
            })
            .catch(reason => {
                console.error("Error in AddBook.updateBook", reason);
            });
    }

    function addBook(book: Book, afterAdd: (saved: Book) => void) {
        console.debug(`AddBook.addBook { title:${book.title} }`);
        axios
            .post("/api/books", book)
            .then(response => {
                if (response.status != 201)
                    throw new Error("Got wrong status on add book: " + response.status);
                props.onItemChange();
                console.debug(`AddBook.addBook { title:${book.title} } --> success`);
                afterAdd( response.data )
            })
            .catch(reason => {
                console.error("Error in AddBook.addBook", reason);
            });
    }

    function save(book: Book, newCover?: NewCover) {
        addBook(
            book,
            savedBook => {
                if (newCover)
                    uploadCover(
                        savedBook.id, newCover,
                        coverUrl => {
                            console.debug("AddBook.save { title:"+book.title+" } --> setCoverURL( \""+coverUrl+"\" )");
                            savedBook.coverUrl = coverUrl;
                            updateBook(savedBook);
                        }
                    )
            }
        );
        navigate("/");
    }

    return (
        <AddEditBookForm
            book={emptyBook}
            saveBook={save}
            saveButtonTitle="Add New Book"
        />
    )
}