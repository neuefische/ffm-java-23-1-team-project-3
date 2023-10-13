import {Book} from "../Types.tsx";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";

type Props = {
    book: Book
    saveBook: ( book: Book, newCover?: NewCover )=>void
    saveButtonTitle: string
}

export type NewCover = {
    file?: File
    url?: string
}

const defaultCoverSourceType: string = "url";

export default function AddEditBookForm( props: Props ) {
    const [book, updateBook] = useState<Book>(props.book);
    const [displayedCoverImage, setDisplayedCoverImage] = useState<string>(props.book.coverUrl);
    const [coverSourceFile, setCoverSourceFile] = useState<File>();
    const [coverSourceURL, setCoverSourceURL] = useState<string>("");
    const [coverSourceType, setCoverSourceType] = useState<string>(defaultCoverSourceType);

    useEffect(
        ()=> {
            updateBook(props.book)
            setDisplayedCoverImage(props.book.coverUrl);
            setCoverSourceFile(undefined);
            setCoverSourceURL("");
            setCoverSourceType(defaultCoverSourceType)
        },
        [ props.book ]
    );
    const navigate = useNavigate();
    console.debug(`Rendering AddEditBookForm { id:"${props.book.id}" }`);

    function updateBookValue( name:string, value:string ) {
        updateBook( {
            ...book,
            [name]: value
        } );
    }

    function onChangeInputFcn( event: ChangeEvent<any> ) {
        updateBookValue( event.target.name, event.target.value );
    }

    function onSubmitForm( event: FormEvent<HTMLFormElement> ) {
        event.preventDefault();
        if      (coverSourceType==="file" && coverSourceFile) props.saveBook(book, { file: coverSourceFile });
        else if (coverSourceType==="url"  && coverSourceURL ) props.saveBook(book, { url : coverSourceURL  });
        else props.saveBook(book);
    }

    function onChangeSelectFcn( event: ChangeEvent<HTMLSelectElement> ) {
        setCoverSourceType(event.target.value);
        if (event.target.value==="url" && coverSourceURL)
            setDisplayedCoverImage(coverSourceURL); // as preview
        if (event.target.value==="file" && coverSourceFile)
            updateDisplayedCoverImageFromFile(coverSourceFile);
    }

    function onChangeCoverURL( event: ChangeEvent<HTMLInputElement> ) {
        setCoverSourceURL(event.target.value);
        setDisplayedCoverImage(event.target.value); // as preview
    }

    function onChangeCoverFile( event: ChangeEvent<HTMLInputElement> ) {
        if (event?.target?.files && event.target.files.length>=1) {
            const selectedFile: File = event.target.files[0];
            setCoverSourceFile(selectedFile);
            updateDisplayedCoverImageFromFile(selectedFile);
        }
    }

    function updateDisplayedCoverImageFromFile(selectedFile: File) {
        makeDataURL(selectedFile, dataURL => {
            setDisplayedCoverImage(dataURL);
        }); // as preview
    }

    function makeDataURL(file: File, processDataURL: (dataURL: string) => void): void {
        file.arrayBuffer()
            .then(value => {
                const uint8Array = new Uint8Array(value);
                const numArr = Array.from(uint8Array);
                const base64String = btoa( String.fromCharCode.apply(null, numArr) );
                processDataURL(
                    `data:${file.type};base64,${base64String}`
                );
            })
            .catch(error => {
                console.error("Error while converting a File into a DataURL", error);
            })
    }

    let cssClassFileInput = coverSourceType==="file" ? "" : "hidden";
    let cssClassUrlInput = coverSourceType==="url"  ? "" : "hidden";
    return (
        <>
            <form onSubmit={onSubmitForm}>
                <label htmlFor="fld_title"       >Title       :</label><input    id="fld_title"       name="title"       value={book.title      } onChange={onChangeInputFcn}/>
                <label htmlFor="fld_author"      >Author      :</label><input    id="fld_author"      name="author"      value={book.author     } onChange={onChangeInputFcn}/>
                <label htmlFor="fld_description" >Description :</label><textarea id="fld_description" name="description" value={book.description} onChange={onChangeInputFcn}/>
                <label htmlFor="fld_publisher"   >Publisher   :</label><input    id="fld_publisher"   name="publisher"   value={book.publisher  } onChange={onChangeInputFcn}/>
                <label htmlFor="fld_isbn"        >ISBN        :</label><input    id="fld_isbn"        name="isbn"        value={book.isbn       } onChange={onChangeInputFcn}/>
                {/*
                <label htmlFor="fld_coverUrl"    >Cover URL [DEBUG] :</label><input    id="fld_coverUrl"    name="coverUrl"    value={book.coverUrl   } onChange={onChangeInputFcn}/>
                */}
                <label>Cover :</label>
                <div>
                    <select value={coverSourceType} onChange={onChangeSelectFcn}>
                        <option value="url">from URL</option>
                        <option value="file">from local file</option>
                    </select>
                    <input className={cssClassFileInput} type="file" onChange={onChangeCoverFile}/>
                    <input className={cssClassUrlInput } type="text" onChange={onChangeCoverURL } value={coverSourceURL}/>
                </div>
                {displayedCoverImage && <img alt="Cover Image" src={displayedCoverImage}/>}
                <div>
                    <button>{props.saveButtonTitle}</button>
                    <button type="button" onClick={() => navigate("/")}>Cancel</button>
                </div>
            </form>
        </>
    )
}

export function uploadCover(
    id: string, newCover: NewCover,
    afterUpload?: (coverUrl: string)=>void,
    afterError?: ()=>void
) {
    if      (newCover.file) uploadCoverFromFile(id, newCover.file, afterUpload, afterError)
    else if (newCover.url ) uploadCoverFromUrl (id, newCover.url , afterUpload, afterError)
    else if (afterError) afterError();
}

function uploadCoverFromFile(
    id: string, file: File,
    afterUpload?: (coverUrl: string)=>void,
    afterError?: ()=>void
) {
    console.debug(`uploadCoverFromFile { id:${id} }`);
    const data = new FormData();
    data.append("file", file);
    const config = {
        headers: { 'content-type': 'multipart/form-data' }
    };
    axios
        .post('/api/books/'+id+'/setCoverByFile', data, config)
        .then( response => {
            if (response.status != 201)
                throw new Error(`Got wrong status [${response.status}] on uploading cover from file: ${response.data}`);
            console.debug(`uploadCoverFromFile { id:${id} } --> success`);
            if (afterUpload)
                afterUpload(response.data);
        })
        .catch(reason => {
            console.error("UploadError:",reason);
            if (afterError)
                afterError();
        });
}

function uploadCoverFromUrl(
    id: string, url: string,
    afterUpload?: (coverUrl: string)=>void,
    afterError?: ()=>void
) {
    console.debug(`uploadCoverFromUrl { id:${id} }`);
    const config = {
        headers: { 'content-type': 'text/plain' }
    };
    axios
        .post('/api/books/'+id+'/setCoverByURL', url, config)
        .then( response => {
            if (response.status != 201)
                throw new Error(`Got wrong status [${response.status}] on uploading cover from URL: ${response.data}`);
            console.debug(`uploadCoverFromUrl { id:${id} } --> success`);
            if (afterUpload)
                afterUpload(response.data);
        })
        .catch(reason => {
            console.error("UploadError:",reason);
            if (afterError)
                afterError();
        });
}
