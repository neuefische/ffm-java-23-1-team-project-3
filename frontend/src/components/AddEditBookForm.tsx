import {Book} from "../Types.tsx";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

type Props = {
    book: Book
    saveBook: ( book: Book )=>void
    saveButtonTitle: string
}

export default function AddEditBookForm( props: Props ) {
    const [book, updateBook] = useState<Book>(props.book);
    useEffect(
        ()=> updateBook(props.book),
        [ props.book ]
    );
    const navigate = useNavigate();
    console.debug(`Rendering AddEditBookForm { id:"${props.book.id}" }`);

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
        props.saveBook(book);
    }

    return (
        <>
            <form onSubmit={saveChanges}>
                <label htmlFor="fld_title"       >Title       :</label><input    id="fld_title"       name="title"       value={book.title      } onChange={onChangeFcnI}/>
                <label htmlFor="fld_author"      >Author      :</label><input    id="fld_author"      name="author"      value={book.author     } onChange={onChangeFcnI}/>
                <label htmlFor="fld_description" >Description :</label><textarea id="fld_description" name="description" value={book.description} onChange={onChangeFcnI}/>
                <label htmlFor="fld_publisher"   >Publisher   :</label><input    id="fld_publisher"   name="publisher"   value={book.publisher  } onChange={onChangeFcnI}/>
                <label htmlFor="fld_isbn"        >ISBN        :</label><input    id="fld_isbn"        name="isbn"        value={book.isbn       } onChange={onChangeFcnI}/>
                <label htmlFor="fld_coverUrl"    >Cover URL   :</label><input    id="fld_coverUrl"    name="coverUrl"    value={book.coverUrl   } onChange={onChangeFcnI}/>
                {book.coverUrl && <img alt="Cover Image" src={book.coverUrl}/>}
                <div>
                    <button>{props.saveButtonTitle}</button>
                    <button type="button" onClick={() => navigate("/")}>Cancel</button>
                </div>
            </form>
        </>
    )
}
