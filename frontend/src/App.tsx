import './App.css';
import {useEffect, useState} from "react";
import {Book} from "./Types.tsx";
import axios from "axios";
import BookList from "./components/BookList.tsx";
import {Link, Route, Routes} from "react-router-dom";
import AddBook from "./components/AddBook.tsx";
import EditBook from "./components/EditBook.tsx";
import BookDetails from "./components/BookDetails.tsx";

export default function App() {
    const [books, setBooks] = useState<Book[]>([]);
    const [timestamp, setTimestamp] = useState<string>("");
    console.debug(`Rendering App { books: ${books.length} books in list, timestamp: "${timestamp}" }`);

    useEffect(loadAllBooks, []);
    useEffect(() => {
        const intervalID = setInterval(checkIfUpdateNeeded, 3000);
        return () => clearInterval(intervalID);
    }, [ timestamp ]);

    function loadAllBooks (){
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
                    loadAllBooks();
            })
            .catch((error)=>{
                console.error(error);
            })
    }

    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080': window.location.origin;
        window.open(host + '/oauth2/authorization/github', '_blank');
    }

    function me() {
        axios.get("/api/users/me")
            .then(response => {
                console.log(response.data)
            })
    }

    const favoriteBooks = books.filter(book => book.favorite)

    return (
        <>

            <Link to={`/`}><h1>Book Library</h1></Link>
            <header>
                <nav>
                    <Link to={`/`}>All Books</Link>
                    <Link to={`/favorites`}>My Favorites</Link>
                    <button onClick={login}>Login</button>
                    <button onClick={me}>me</button>
                </nav>
            </header>

            <Routes>
                <Route path="/books/:id"      element={<BookDetails />} />
                <Route path="/"               element={<BookList books={books} onItemChange={loadAllBooks}/>}/>
                <Route path="/books/add"      element={<AddBook onItemChange={loadAllBooks}/>}/>
                <Route path="/books/:id/edit" element={<EditBook books={books} onItemChange={loadAllBooks}/>}/>
                <Route path="/favorites"      element={<BookList books={favoriteBooks} onItemChange={loadAllBooks} headline={"My Favorites"}/>}/>
            </Routes>
        </>
    )
}
