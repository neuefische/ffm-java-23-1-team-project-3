import {ChangeEvent, FormEvent, useState} from "react";
import {Book} from "../Types.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";

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
    const [book, updateBook] = useState<Book>(emptyBook);
    const navigate = useNavigate();
    console.debug(`Rendering AddBook {}`);

    function updateBookValue( name:string, value:string ) {
        updateBook( {
            ...book,
            [name]: value
        } );
    }

    function onChangeFcnI( event: ChangeEvent<any> ) {
        updateBookValue( event.target.name, event.target.value );
    }

    function saveNewBook(event: FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios
            .post("/api/books", book)
            .then(()=>{
                updateBook(emptyBook);
                props.onItemChange();
            })
        navigate("/");
    }

    return (
        <>
            <form className="addBookForm" onSubmit={saveNewBook}>
                <label htmlFor="fld_title"       >Title       :</label><input    id="fld_title"       name="title"       value={book.title      } onChange={onChangeFcnI}/>
                <label htmlFor="fld_author"      >Author      :</label><input    id="fld_author"      name="author"      value={book.author     } onChange={onChangeFcnI}/>
                <label htmlFor="fld_description" >Description :</label><textarea id="fld_description" name="description" value={book.description} onChange={onChangeFcnI}/>
                <label htmlFor="fld_publisher"   >Publisher   :</label><input    id="fld_publisher"   name="publisher"   value={book.publisher  } onChange={onChangeFcnI}/>
                <label htmlFor="fld_isbn"        >ISBN        :</label><input    id="fld_isbn"        name="isbn"        value={book.isbn       } onChange={onChangeFcnI}/>
                <label htmlFor="fld_coverUrl"    >Cover URL   :</label><input    id="fld_coverUrl"    name="coverUrl"    value={book.coverUrl   } onChange={onChangeFcnI}/>
                {book.coverUrl && <img alt="Cover Image" src={book.coverUrl}/>}
                <div>
                    <button>Add New Book</button>
                    <button type="button" onClick={()=>navigate("/")}>Cancel</button>
                </div>
            </form>
        </>
    )
}