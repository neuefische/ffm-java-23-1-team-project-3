import './App.css';
import {useEffect, useState} from "react";
import {Book, UserInfos} from "./Types.tsx";
import axios from "axios";
import BookList from "./components/BookList.tsx";
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import AddBook from "./components/AddBook.tsx";
import EditBook from "./components/EditBook.tsx";
import BookDetails from "./components/BookDetails.tsx";
import SearchBookByTitle from "./components/SearchBookByTitle.tsx";
import ProtectedRoutes from "./components/ProtectedRoutes.tsx";

export default function App() {
    const [books, setBooks] = useState<Book[]>([]);
    const [timestamp, setTimestamp] = useState<string>("");
    const [booksFromResearch, setBooksFromResearch] = useState<Book[]>([]);
    const [title, setTitle] = useState<string>("");
    const [user, setUser] = useState<UserInfos>();
    console.debug(`Rendering App { books: ${books.length} books in list, timestamp: "${timestamp}" }`);
    const navigate = useNavigate();

    useEffect(loadAllBooks, []);
    useEffect(() => {
        const intervalID = setInterval(checkIfUpdateNeeded, 3000);
        return () => clearInterval(intervalID);
    }, [ timestamp ]);

    useEffect(determineCurrentUser, []);

    function updateBookList(){
        loadAllBooks();
        showBooksAfterSearch(title,false);
    }
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
        window.open(host + '/oauth2/authorization/github', '_self');
    }

    function logout() {
        axios.post("/api/logout")
            .then(() => {
                setUser(undefined)
            })
            .catch(error => {
                console.error(error)
            })
    }

    function determineCurrentUser() {
        axios.get("/api/users/me")
            .then(response => {
                console.log(response.data);
                setUser(response.data);
            })
    }

    const favoriteBooks = books.filter(book => book.favorite)

    function setSearch(title : string){
        setTitle(title);
        showBooksAfterSearch(title,true);
    }
    function showBooksAfterSearch(title : string, jumpToList : boolean){
        axios.get("/api/books/search/"+ title)
            .then((response) => {
                if (response.status!==200)
                    throw new Error("Get wrong response status, when loading the books after searching: "+response.status);
                setBooksFromResearch(response.data)
                console.log(title)
                if(jumpToList)
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
                <nav>
                    <Link to={`/`}>All Books</Link>
                    <Link to={`/favorites`}>My Favorites</Link>
                    {!user?.isAuthenticated && <button onClick={login}>Login</button>}
                    { user?.isAuthenticated && <button onClick={logout}>Logout</button>}
                    <button onClick={determineCurrentUser}>me</button>
                    {
                        user?.isAuthenticated &&
                        <span className="current_user">
                            Current user:
                            <a href={user.url}>
                                {user.avatar_url && <img alt="user avatar image" src={user.avatar_url}/>} {user.name} [{user.id}]
                            </a>
                        </span>
                    }
                </nav>
            </header>

            <Routes>
                <Route path="/"                      element={<BookList books={books} user={user} showAdd={true} showHomepage={false} showSearch={true} onItemChange={loadAllBooks}/>}/>
                <Route path="/favorites"             element={<BookList books={favoriteBooks} user={user} showAdd={false} showHomepage={false} showSearch={false} onItemChange={loadAllBooks} headline={"My Favorites"}/>}/>
                <Route path="/books/:id"             element={<BookDetails showHomepage={true} />} />
                <Route path="/books/search"          element={<SearchBookByTitle getBooksAfterSearch={setSearch}/>}/>
                <Route path="/books/search/title"    element={<BookList books={booksFromResearch} user={user} showAdd={false} showHomepage={true} showSearch={false} onItemChange={updateBookList}/>}/>
                <Route element={<ProtectedRoutes user={user} />}>
                    <Route path="/books/add"         element={<AddBook onItemChange={loadAllBooks}/>}/>
                    <Route path="/books/:id/edit"    element={<EditBook books={books} onItemChange={loadAllBooks}/>}/>
                </Route>

            </Routes>
        </>
    )
}
