import React, {useState} from "react";
import Accordion from 'react-bootstrap/Accordion';
import TextInput from "../inputs/SmallTextInput";
import SearchDiets from "../inputs/SearchDiets";

export default function GuestItem(props){
    const {guestData, index, handleGuestDataChange} = props;

    const [selectedGuestDiets, setSelectedGuestDiets] = useState(guestData.dietIds);

    const handleGuestNoteInputChange = (event) => {
        const newGuestData = {...guestData};
        newGuestData.guest.note = event.target.value;
        handleGuestDataChange(index, newGuestData);
    }

    const handleGuestDietInputChange = (newSelectedGuestDietArray) => {
        const newGuestData = {...guestData};
        newGuestData.dietIds = newSelectedGuestDietArray;
        handleGuestDataChange(index, newGuestData);
    }

    const handleGuestDietOptionSelected = (option) => {
        if (!selectedGuestDiets.includes(option.dietId)) {
            const newSelectedGuestDietArray = [...selectedGuestDiets, option.dietId];
            setSelectedGuestDiets(newSelectedGuestDietArray);
            handleGuestDietInputChange(newSelectedGuestDietArray);
        }
    }

    const handleGuestDietOptionDeselected = (option) => {
        const newSelectedGuestDietArray = selectedGuestDiets.filter(id => id !== option.dietId);
        setSelectedGuestDiets(newSelectedGuestDietArray);
        handleGuestDietInputChange(newSelectedGuestDietArray);
    }

    return (
        <>
            <Accordion.Item eventKey={guestData.guest.name}>
                <Accordion.Header>{guestData.guest.name}</Accordion.Header>
                <Accordion.Body>
                <div className="guestcommentcontainer">
                    <TextInput
                        maxCharacters={100}
                        handleInputChange={handleGuestNoteInputChange}
                        displayName="Beschrijving"
                        name="guestNote"
                        placeholder="Laat een opmerking achter voor de evenementbeheerder"
                        icon="bi-pencil-square"
                        isTextArea={true}
                        initialValue={guestData.guest.note}
                    />
                    <hr className="mt-2"/>
                </div>
                <div className="guestdiet">
                    <SearchDiets
                        placeholder="Zoek een dieet"
                        handleOptionSelected={handleGuestDietOptionSelected}
                        handleOptionDeselected={handleGuestDietOptionDeselected}
                        initialSelectedOptions={selectedGuestDiets}
                        title="Dieetwensen (optioneel)"
                    />
                </div>
                </Accordion.Body>
            </Accordion.Item>
        </>
    );
}
