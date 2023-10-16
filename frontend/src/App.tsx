import './App.css';
import {useEffect, useState} from "react";
import {Book} from "./Types.tsx";
import axios from "axios";
import BookList from "./components/BookList.tsx";
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import AddBook from "./components/AddBook.tsx";
import EditBook from "./components/EditBook.tsx";
import BookDetails from "./components/BookDetails.tsx";
import SearchBookByTitle from "./components/SearchBookByTitle.tsx";

export default function App() {
    const [books, setBooks] = useState<Book[]>([]);
    const [timestamp, setTimestamp] = useState<string>("");
    const [booksFromResearch, setBooksFromResearch] = useState<Book[]>([]);
    console.debug(`Rendering App { books: ${books.length} books in list, timestamp: "${timestamp}" }`);
    const navigate = useNavigate();

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

    function showBooksAfterSearch(title : string){
        axios.get("/api/books/search/"+ title)
            .then((response) => {
                if (response.status!==200)
                    throw new Error("Get wrong response status, when loading the books after searching: "+response.status);
                setBooksFromResearch(response.data)
                console.log(title)
                navigate("/books/search/title");
            })
            .catch((error)=>{
                console.error(error);
            })
    }

    return (
        <>
            <Link to={`/`}><h1 className="title">Book Library</h1></Link>
            <header>
                {}
            </header>

            <Routes>
                <Route path="/books/:id"                    element={<BookDetails showHomepage={true} />} />
                <Route path="/"                             element={<BookList books={books} showAdd={true} showHomepage={false} showSearch={true} onItemChange={loadAllBooks}/>}/>
                <Route path="/books/add"                    element={<AddBook onItemChange={loadAllBooks}/>}/>
                <Route path="/books/:id/edit"               element={<EditBook books={books} reload={loadAllBooks}/>}/>
                <Route path="/books/search/title"           element={<BookList books={booksFromResearch} showAdd={false} showHomepage={true} showSearch={false} onItemChange={loadAllBooks}/>}/>
                <Route path="/books/search"                 element={<SearchBookByTitle getBooksAfterSearch={showBooksAfterSearch}/>}/>
            </Routes>

        </>
    )
}
