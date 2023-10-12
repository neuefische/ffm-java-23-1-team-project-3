import {ChangeEvent, FormEvent, useState} from "react";
import {Book} from "../Types.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";

type Props ={
    onItemChange: () => void
}
export default function AddBook(props: Props) {
    const [title, setTitle] = useState<string>("");
    const [author, setAuthor] = useState<string>("");
    const navigate = useNavigate();
    function addTitle(title: ChangeEvent<HTMLInputElement>){
        setTitle(title.target.value);
    }
    function addAuthor(author: ChangeEvent<HTMLInputElement>){
        setAuthor(author.target.value);
    }
    function saveNewBook(event: FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.post("/api/books",{
            title: title,
            author: author
        } as Book)
            .then(()=>{
                setTitle("");
                setAuthor("");
                props.onItemChange();
            })
        navigate("/");
    }

    return (
        <>
            <form className="addBookForm" onSubmit={saveNewBook}>
                <label>Title:</label>
                <input name="Title" value={title} onChange={addTitle}/>
                <label>Author:</label>
                <input name="Author" value={author} onChange={addAuthor}/>
                <div>
                    <button>Add New Book</button>
                    <button type="button" onClick={()=>navigate("/")}>Cancel</button>
                </div>
            </form>
        </>
    )
}