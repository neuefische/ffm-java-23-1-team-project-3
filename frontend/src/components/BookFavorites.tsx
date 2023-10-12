import {useEffect, useState} from "react";
import {Book} from "../Types.tsx";
import {useParams} from "react-router-dom";
import axios from "axios";
import FavoritesList from "./FavoritesList.tsx";


export default function BookFavorites() {

    const [books, setBooks] = useState<Book[]>([]);

    useEffect(loadBooks, []);
    function loadBooks () {
        axios.get("/api/books/")
            .then((response) => {
                if (response.status !== 200)
                    throw new Error("Get wrong response status, when loading the book: " + response.status);
                setBooks(response.data)
            })
            .catch((error) => {
                console.error(error);
            })
    }

    return (
        <div>
            <h1>My Favorites</h1>
            <FavoritesList books={books} onItemChange={loadBooks}/>
        </div>
    )
}