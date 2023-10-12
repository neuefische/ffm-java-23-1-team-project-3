import './EditBook.css';
import {Book} from "../Types.tsx";
import {useNavigate, useParams} from "react-router-dom";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import axios from "axios";

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

    return <EditBookForm book={filteredBooks[0]} onItemChange={props.onItemChange}/>
}

type FormProps = {
    book: Book
    onItemChange: ()=>void
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

    function onChangeFcnI( event: ChangeEvent<any> ) {
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
                    throw new Error("Got wrong status on update book: " + response.status);
                props.onItemChange();
            })
            .catch(reason => {
                console.error(reason);
            });
    }

    return (
        <>
            <form className="EditBookForm" onSubmit={saveChanges}>
                <label htmlFor="fld_title"       >Title       :</label><input    id="fld_title"       name="title"       value={book.title      } onChange={onChangeFcnI}/>
                <label htmlFor="fld_author"      >Author      :</label><input    id="fld_author"      name="author"      value={book.author     } onChange={onChangeFcnI}/>
                <label htmlFor="fld_description" >Description :</label><textarea id="fld_description" name="description" value={book.description} onChange={onChangeFcnI}/>
                <label htmlFor="fld_publisher"   >Publisher   :</label><input    id="fld_publisher"   name="publisher"   value={book.publisher  } onChange={onChangeFcnI}/>
                <label htmlFor="fld_isbn"        >ISBN        :</label><input    id="fld_isbn"        name="isbn"        value={book.isbn       } onChange={onChangeFcnI}/>
                <label htmlFor="fld_coverUrl"    >Cover URL   :</label><input    id="fld_coverUrl"    name="coverUrl"    value={book.coverUrl   } onChange={onChangeFcnI}/>
                {book.coverUrl && <img alt="Cover Image" src={book.coverUrl}/>}
                <div>
                    <button>Save</button>
                    <button type="button" onClick={() => navigate("/")}>Cancel</button>
                </div>
            </form>
        </>
    )
}
