import React from "react";
import RegisterButton from "./RegisterButton";
import UnRegisterButton from "./UnRegisterButton";

export default function EnrollButton(props) {
  const {event, enrolled, handleDetailReload} = props

  return (
    <div className="text-start w-100">
      {enrolled ? <UnRegisterButton event={event} handleDetailReload={handleDetailReload} /> : <RegisterButton event={event} handleDetailReload={handleDetailReload} /> }
    </div>
  );
}
