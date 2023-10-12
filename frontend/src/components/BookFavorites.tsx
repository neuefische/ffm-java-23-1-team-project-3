import {Book} from "../Types.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import BookCard from "./BookCard.tsx";


type Props = {
    books: Book[]
    onItemChange: () => void
}

export default function BookFavorites(props: Props) {

    return (

        <div>
            <h1>My Favorites</h1>
            <div className="BookList">
                {
                    props.books.map( book =>
                        <BookCard key={book.id} book={book} onItemChange={props.onItemChange}/>
                    )
                }
            </div>
        </div>
    )
}