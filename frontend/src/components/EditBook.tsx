import './EditBook.css';
import {Book} from "../Types.tsx";
import {useNavigate, useParams} from "react-router-dom";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import axios from "axios";

type Props = {
    books: Book[]
    reload: ()=>void
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

    return <EditBookForm book={filteredBooks[0]} reload={props.reload}/>
}

type FormProps = {
    book: Book
    reload: ()=>void
}

function EditBookForm( props: FormProps ) {
    const [book, updateBook] = useState<Book>(props.book);
    useEffect(
        ()=> updateBook(props.book),
        [ props.book ]
    );
    const navigate = useNavigate();
    console.debug(`Rendering EditBookForm { id:"${props.book.id}" }`);

    function updateBookValue( name:string, value:string ) {
        updateBook( {
            ...book,
            [name]: value
        } );
    }

    function onChangeFcnI( event: ChangeEvent<HTMLInputElement> ) {
        updateBookValue( event.target.name, event.target.value );
    }

    function saveChanges( event: FormEvent<HTMLFormElement> ) {
        event.preventDefault();
        update(book);
        navigate("/");
    }

    function update( book: Book ) {
        axios
            .put('/api/books/'+book.id, book )
            .then(response => {
                if (response.status != 200)
                    throw {error: "Got wrong status on update book: " + response.status}
                props.reload()
            })
            .catch(reason => {
                console.error(reason)
            });
    }

    return (
        <>
            <form className="EditBookForm" onSubmit={saveChanges}>
                <label>id     : {book.id}</label>
                <label>title  : <input name="title"  value={book.title } onChange={onChangeFcnI}/></label>
                <label>author : <input name="author" value={book.author} onChange={onChangeFcnI}/></label>
                <div>
                    <button>Save</button>
                    <button type="button" onClick={() => navigate("/")}>Cancel</button>
                </div>
            </form>
        </>
    )
}
