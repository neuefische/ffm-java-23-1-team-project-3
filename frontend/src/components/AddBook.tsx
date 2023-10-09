import {ChangeEvent, FormEvent, useState} from "react";
import {Book} from "../Types.tsx";
import axios from "axios";


export default function AddBook() {

    const [title, setTitle] = useState<string>();
    const [author, setAuthor] = useState<string>();


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