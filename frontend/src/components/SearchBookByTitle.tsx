import {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";

type Props = {
    getBooksAfterSearch: (title : string)=> void
}
export default function SearchBookByTitle(props : Props) {
    const [title, setTitle] = useState<string>("");
    const navigate = useNavigate();
    function SetTitleToSearch(title: ChangeEvent<HTMLInputElement>){
        setTitle(title.target.value);
    }
    function searchByTitle(event: FormEvent<HTMLFormElement>){
        event.preventDefault();
        props.getBooksAfterSearch(title);
    }

    return (
        <>
            <form className="searchBookForm" onSubmit={searchByTitle}>
                <label>Title:</label>
                <input name="Title" value={title} onChange={SetTitleToSearch}/>
                <div>
                    <button>Search</button>
                    <button type="button" onClick={()=>navigate("/")}>Cancel</button>
                </div>
            </form>
        </>
    )
}