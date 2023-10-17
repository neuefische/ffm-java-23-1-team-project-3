import {Book, UserInfos} from "../Types.tsx";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

type Props = {
    book: Book
    onItemChange: () => void
    user?: UserInfos
}

export default function BookCard( props: Props ) {
    const navigate = useNavigate();

    function deleteCard() {
        axios.delete("/api/books/" + props.book.id)
            .then(props.onItemChange)

        const popupOverlay = document.getElementById("popupOverlay");
        if(popupOverlay){
            popupOverlay.classList.remove("showPopup");
        }
    }

    function deleteQuestionPopUp(){
        const popup = document.getElementById(props.book.id);
        const popupOverlay = document.getElementById("popupOverlay");
        if(popup && popupOverlay) {
            popup.classList.toggle("showPopup");
            popupOverlay.classList.toggle("showPopup");
        }
    }

    function favor( favorite: boolean ) {
        axios.put("/api/books/" + props.book.id, {
            ...props.book,
            favorite: favorite
        })
            .then(props.onItemChange)
            .catch(reason => {
                console.error(reason)
            });
    }

    return (
        <div className="BookCard">
            <Link to={`/books/${props.book.id}`}>
            {/*<div>id     : {props.book.id     }</div>*/}
            <h3>{props.book.title}</h3>
            <p>{props.book.author}</p>
            </Link>

            {
                props.user?.isAuthenticated &&
                <>
                    <div id="popupOverlay" className="popup"></div>
                    <span className="popup" id={props.book.id}>
                        Really delete: <br/> {props.book.title}?
                        <button onClick={deleteCard}>
                            <svg width="20px" height="20px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path id="Vector" d="M6 12L10.2426 16.2426L18.727 7.75732" stroke="#000000" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/></svg>
                        </button>
                        <button onClick={deleteQuestionPopUp}>
                            <svg width="20px" height="20px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" clipRule="evenodd" d="M19.207 6.207a1 1 0 0 0-1.414-1.414L12 10.586 6.207 4.793a1 1 0 0 0-1.414 1.414L10.586 12l-5.793 5.793a1 1 0 1 0 1.414 1.414L12 13.414l5.793 5.793a1 1 0 0 0 1.414-1.414L13.414 12l5.793-5.793z" fill="#000000"/></svg>
                        </button>
                    </span>

                    <button onClick={() => navigate("/books/"+props.book.id+"/edit")}>
                        <svg width="20px" height="20px" viewBox="0 0 32 32" version="1.1" xmlns="http://www.w3.org/2000/svg">
                            <path d="M31.25 7.003c0-0 0-0.001 0-0.001 0-0.346-0.14-0.659-0.365-0.886l-5-5c-0.227-0.226-0.539-0.366-0.885-0.366s-0.658 0.14-0.885 0.366v0l-20.999 20.999c-0.146 0.146-0.256 0.329-0.316 0.532l-0.002 0.009-2 7c-0.030 0.102-0.048 0.22-0.048 0.342 0 0.691 0.559 1.251 1.25 1.252h0c0.126-0 0.248-0.019 0.363-0.053l-0.009 0.002 6.788-2c0.206-0.063 0.383-0.17 0.527-0.311l-0 0 21.211-21c0.229-0.226 0.37-0.539 0.371-0.886v-0zM8.133 26.891l-4.307 1.268 1.287-4.504 14.891-14.891 3.219 3.187zM25 10.191l-3.228-3.196 3.228-3.228 3.229 3.228z"></path>
                        </svg>
                    </button>
                    <button onClick={deleteQuestionPopUp}>
                        <svg width="20px" height="20px" viewBox="5 2 15 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path fillRule="evenodd" clipRule="evenodd" d="M14.5 3L15.5 4H19V6H5V4H8.5L9.5 3H14.5ZM12 12.59L14.12 10.47L15.53 11.88L13.41 14L15.53 16.12L14.12 17.53L12 15.41L9.88 17.53L8.47 16.12L10.59 14L8.46 11.88L9.87 10.47L12 12.59ZM6 19C6 20.1 6.9 21 8 21H16C17.1 21 18 20.1 18 19V7H6V19ZM16 9H8V19H16V9Z" fill="#000000"/>
                        </svg>
                    </button>
                    <button onClick={() => favor(!props.book.favorite)}>
                        <svg className={props.book.favorite ? "favorite" : "not_favorite"} width="20px" height="20px" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
                            <path d="M908.1 353.1l-253.9-36.9L540.7 86.1c-3.1-6.3-8.2-11.4-14.5-14.5-15.8-7.8-35-1.3-42.9 14.5L369.8 316.2l-253.9 36.9c-7 1-13.4 4.3-18.3 9.3a32.05 32.05 0 0 0 .6 45.3l183.7 179.1-43.4 252.9a31.95 31.95 0 0 0 46.4 33.7L512 754l227.1 119.4c6.2 3.3 13.4 4.4 20.3 3.2 17.4-3 29.1-19.5 26.1-36.9l-43.4-252.9 183.7-179.1c5-4.9 8.3-11.3 9.3-18.3 2.7-17.5-9.5-33.7-27-36.3z"/>
                        </svg>
                    </button>
                </>
            }
        </div>
    )
}