import {useEffect, useState} from "react";
import {Book} from "../Types.tsx";
import axios from "axios";
import FavoritesList from "./FavoritesList.tsx";


export default function BookFavorites() {

    const [books, setBooks] = useState<Book[]>([]);
    const [timestamp, setTimestamp] = useState<string>("");
    console.debug(`Rendering App { books: ${books.length} books in list, timestamp: "${timestamp}" }`);

    useEffect(loadBooks, []);
    useEffect(() => {
        const intervalID = setInterval(checkIfUpdateNeeded, 3000);
        return () => clearInterval(intervalID);
    }, [ timestamp ]);

    function loadBooks () {
        axios.get("/api/books")
            .then((response) => {
                if (response.status!==200)
                    throw new Error("Get wrong response status, when loading all books: "+response.status);
                setBooks(response.data.books);
                if (!response.data.timestamp) setTimestamp("");
                else setTimestamp(response.data.timestamp.timestamp);
            })
            .catch((error)=>{
                console.error(error);
            })
    }

    function checkIfUpdateNeeded() {
        axios.get("/api/books/state")
            .then((response) => {
                if (response.status!==200)
                    throw new Error("Get wrong response status, when getting database timestamp: "+response.status);
                if (response.data && timestamp!==response.data.timestamp)
                    loadBooks();
            })
            .catch((error)=>{
                console.error(error);
            })
    }

    return (
        <div>
            <h3>My Favorites</h3>
            <FavoritesList books={books} onItemChange={loadBooks}/>
        </div>
    )
}