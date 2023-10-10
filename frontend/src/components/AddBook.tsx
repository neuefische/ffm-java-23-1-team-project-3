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
            <form onSubmit={saveNewBook}>
                <input value={title} onChange={addTitle}/>
                <input value={author} onChange={addAuthor}/>
                <button>Add New Book</button>
            </form>
        </>
    )
}