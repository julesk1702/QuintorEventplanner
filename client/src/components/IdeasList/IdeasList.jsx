import React from "react";
import IdeasListItem from "./IdeasListItem";

export default function IdeasList(props){
    const {ideas, handleIdeasReload} = props;

    return (
        <div className="row row-cols-1 row-cols-md-2 row-cols-xl-3 g-4 mb-5 pb-5 mb-lg-0 pb-lg-0">
            {ideas && ideas.map(idea => <IdeasListItem key={idea.ideas_id} idea={idea} handleIdeasReload={handleIdeasReload} />)}
        </div>
    );
}
