import React, {useCallback, useEffect, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHeart, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import { toast } from 'react-toastify';
import IdeasRepository from "../../repositories/IdeasRepository";


export default function IdeasListItem(props){
    const {idea, handleIdeasReload} = props;
    const userId = parseInt(localStorage.getItem("userId"));
    const checkAdmin = localStorage.getItem("isAdmin");
    const [hasLiked, setHasLiked] = useState([]);
    const [color, setColor] = useState('#000000');

    const hasUserLiked = useCallback(() => IdeasRepository.checkUserHasLiked(idea.ideas_id, userId), [idea.ideas_id, userId]);

    useEffect(() => {
        hasUserLiked().then((data) => {
            const object = {
                "liked": data[2],
            };
            setHasLiked(object);
            if (data[2] === true) {
                setColor("#c23350");
            }
        });
    }, [hasUserLiked]);

    const handleLike = async () => {
        await IdeasRepository.likeIdea(idea.ideas_id, userId)
        .then((response) => {
            if(response.status === 200){
               setColor("#c23350");
               setHasLiked({liked: true});
               idea.likes = idea.likes + 1;
            }
        })
        .catch((error) => {
            toast.error("Er is iets misgegaan. Probeer het later opnieuw.");
        });
    }

    const handleDislike = async () => {
        await IdeasRepository.dislikeIdea(idea.ideas_id, userId)
        .then((response) => {
            if(response.status === 200){
                setColor("#000000");
                setHasLiked({liked: false});
                idea.likes = idea.likes - 1;
            }
        })
        .catch((error) => {
            toast.error("Er is iets misgegaan. Probeer het later opnieuw.");
        });
    }

    const deleteButton = () => {
        if(checkAdmin === "true" || checkAdmin === true) {
            return <FontAwesomeIcon className="p-0" onClick={handleDelete} icon={faTrashCan} style={{ cursor: "pointer" }}/>
        }
        if (idea.user_id === userId) {
            return <FontAwesomeIcon className="p-0" onClick={handleDelete} icon={faTrashCan} style={{ cursor: "pointer" }}/>
        }
    }

    const handleDelete = async () => {
        await IdeasRepository.deleteIdeaById(idea.ideas_id)
        .then((response) => {
            if(response.status === 200){
              toast.success("Het idee is verwijderd!");
              handleIdeasReload();
            }
          })
          .catch((error) => {
            toast.error("Er is iets misgegaan. Probeer het later opnieuw.");
          });
    }

    const likeButton = () => {
        if(hasLiked.liked) {
            return <FontAwesomeIcon className="" onClick={() => handleDislike()} icon={faHeart} style={{color: color, cursor: "pointer"}} size="xl" />
        }
        if(!hasLiked.liked) {
            return <FontAwesomeIcon className="" onClick={() => handleLike()} icon={faHeart} style={{color: color, cursor: "pointer"}} size="xl"/>
        }
    }

    return (
        <div className="col px-3">
            <div className="card h-100 border-0 default-black-shadow" style={{backgroundColor: "#fff"}}>
                <div className="card-body container p-3">
                    <div className="row">
                        <div className="col-10">
                            <p className="card-text fw-medium">{idea.idea}</p>
                        </div>
                        <div className="col-2">
                            {deleteButton()}
                        </div>
                    </div>
                    <div className="row mt-1">
                        <div className="col-12 d-flex gap-2">
                            {likeButton()}
                            <p className="card-text">{idea.likes}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
