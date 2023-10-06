import './App.css'
import {useEffect, useState} from "react";
import {Book} from "./Types.tsx";
import axios from "axios";

export default function App() {
    const [books, setBooks] = useState<Book[]>([])

    useEffect(()=>{


    }, [])

    function loadAllBooks (){
        axios.get("/api/books")
            .then((response) => {
                setBooks(response.data);
            })
            .catch((error)=>{
                console.error(error);
            })
    }


    return (
        <>

            <p>Hello</p>
        </>
    )
}
