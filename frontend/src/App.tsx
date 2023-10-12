import './App.css';
import {useEffect, useState} from "react";
import {Book} from "./Types.tsx";
import axios from "axios";
import BookList from "./components/BookList.tsx";
import {Routes, Route, Link} from "react-router-dom";
import AddBook from "./components/AddBook.tsx";
import EditBook from "./components/EditBook.tsx";
import BookDetails from "./components/BookDetails.tsx";

export default function App() {
    const [books, setBooks] = useState<Book[]>([]);


    useEffect(loadAllBooks, []);

    function loadAllBooks (){
        axios.get("/api/books")
            .then((response) => {
                if (response.status!==200)
                    throw "Get wrong response status, when loading all books: "+response.status;
                setBooks(response.data);
            })
            .catch((error)=>{
                console.error(error);
            })
    }


    return (
        <>

            <Link to={`/`}><h1>Book Library</h1></Link>
            <header>
                {}
            </header>

            <Routes>
                <Route path="/books/:id"      element={<BookDetails />} />
                <Route path="/"               element={<BookList books={books} onItemChange={loadAllBooks}/>}/>
                <Route path="/books/add"      element={<AddBook onItemChange={loadAllBooks}/>}/>
                <Route path="/books/:id/edit" element={<EditBook books={books} reload={loadAllBooks}/>}/>
            </Routes>
        </>
    )
}
