import {ChangeEvent, FormEvent, useState} from "react";
import axios from "axios";

export default function TestUploadForm() {
    const [file, setFile] = useState<File>();
    const [message, setMessage] = useState<string>("");

    function handleFileChange( event: ChangeEvent<HTMLInputElement> ) {
        if (event?.target?.files) {
            const selectedFile = event.target.files[0];
            setFile(selectedFile);
        }
    }

    function handleFormSubmit( event: FormEvent<HTMLFormElement> ) {
        event.preventDefault();
        if (file) {
            uploadFile(file);
            console.log("upload file:", file);
        } else {
            alert('Please select a file to upload.')
        }
    }

    function uploadFile(file: File) {
        let data = new FormData();
        data.append("file", file);
        const config = {
            // headers: { 'content-type': 'multipart/form-data;boundary=gc0p4Jq0M2Yt08jU534c0p' }
            headers: { 'content-type': 'multipart/form-data' }
        };
        axios
            .post('/api/testupload', data, config)
            .then(response => {
                if (response.status != 200)
                    throw new Error("Got wrong status ["+response.status+"] on uploading file: " + response.data);
                setMessage("file "+file.name+" uploaded: "+response.data);
            })
            .catch(reason => {
                console.error("UploadError:",reason);
            });
    }

    return (
        <form onSubmit={handleFormSubmit}>
            <input type="file" onChange={handleFileChange}/>
            <span>Message: {message}</span>
            <button>Upload</button>
        </form>
    )
}