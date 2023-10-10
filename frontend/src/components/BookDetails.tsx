import {Book} from "../Types.tsx";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";


export default function BookDetails(){

    const urlParams = useParams()

    const [book, setBook] = useState<Book>();
    const navigate = useNavigate();

    useEffect(loadAllBooks, [urlParams.id]);
    function loadAllBooks (){
        axios.get("/api/books/"+ urlParams.id)
            .then((response) => {
                if (response.status!==200)
                    throw "Get wrong response status, when loading the book: "+response.status;

             setBook(response.data)
            })
            .catch((error)=>{
                console.error(error);
            })
    }
    return (
        <>
            <div className={"BookCard"}>
                {book
                    ? <>
                        <h2>Book Details</h2>
                        <p>Title: {book.title}</p>
                        <p>Author: {book.author}</p>
                    </>
                    : <>
                        <p>Book not found</p>
                    </>
                }

            </div>
                <button onClick={()=>navigate("/")}>Home page</button>

        </>
    );

}