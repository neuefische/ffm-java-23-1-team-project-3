import {Book} from "../Types.tsx";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

type Props={
   /* showBack:boolean*/
    showHomepage:boolean
}
export default function BookDetails(props:Props ){

    const urlParams = useParams()

    const [book, setBook] = useState<Book>();
    const navigate = useNavigate();

    useEffect(loadBook, [urlParams.id]);
    function loadBook (){
        axios.get("/api/books/"+ urlParams.id)
            .then((response) => {
                if (response.status!==200)
                    throw new Error("Get wrong response status, when loading the book: "+response.status);
             setBook(response.data)
            })
            .catch((error)=>{
                console.error(error);
            })
    }
    return (
        <>
            <div className="bookDetails">
                {book
                    ? <>
                        <h3>{book.title}</h3>
                        <p>Author: <br/>{book.author}</p>
                    </>
                    : <>
                        <p>Book not found</p>
                    </>
                }

            </div>
            {props.showHomepage &&
                <button className="backHomeBTN" onClick={()=>navigate("/")}>Back to Homepage</button>
            }
           {/* {props.showBack &&
                <button className="backHomeBTN" onClick={()=>navigate("/books/search/:title")}>Back</button>
            }*/}


        </>
    );

}