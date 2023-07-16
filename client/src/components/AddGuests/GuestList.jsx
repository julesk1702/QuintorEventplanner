import React from "react";
import Button from "react-bootstrap/Button";
import Accordion from 'react-bootstrap/Accordion';
import GuestItem from "./GuestItem";
import useUuidv4 from "../../hooks/useUuidv4";

export default function GuestList(props) {
    const {eventId, userId, guests, setGuests, handleGuestDataChange} = props;
    const uuidv4 = useUuidv4;

    const addGuest = () => {
        const newGuest = {
            "guest": {
            "name": `Guest ${guests.length + 1}`,
            "event_id": eventId,
            "user_id": userId,
            "note": ``
            },
            "dietIds": []
        }
        setGuests([...guests, newGuest]);
    };

    return (
        <>
            <Button className="btn btn-light rounded d-flex gap-2 mb-3" style={{borderColor: '#D9D9D9'}} onClick={addGuest}>
                Voeg aanhang toe
                <i className="bi bi-plus-circle" style={{color: '#c23350'}}></i>
            </Button>
            <Accordion>
                {
                    guests && guests.map((guest, index) => (
                        <GuestItem guestData={guest} key={uuidv4()} index={index} handleGuestDataChange={handleGuestDataChange} />
                    ))
                }
            </Accordion>
        </>
    );
}
