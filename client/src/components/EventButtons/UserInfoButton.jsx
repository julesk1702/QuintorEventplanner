import { useState } from "react";
import Modal from "react-bootstrap/Modal";

export default function UserInfoButton(props) {

    const {user, guest} = props;
    const [show, setShow] = useState(false);
    const person = user || guest;

    console.log(person)

    return (
        <>
            {person.note === "" && person.customDiets === null && person.diets === null ? ''
            :
              <button className="bg-transparent border-0 mx-2" onClick={() => setShow(true)}>
                  <i class="bi bi-info-circle" style={{color: "var(--quintor-red)"}}></i>
              </button>
            }

            <Modal show={show} onHide={() => setShow(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>{person.email || person.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {person && person.note !== null && person.note.length > 0  ? <p className="m-0"><b>Opmerking:</b> {person.note}</p> : ''}
                    {user && user.diets !== null && user.diets.length > 0 ? <p className="m-0"><b>Dieetwensen:</b> {user.diets.map(diet => diet.name).join(', ')}</p> : ''}
                    {guest && guest.diets !== null && guest.diets.length > 0 ? <p className="m-0"><b>Dieetwensen:</b> {guest.diets.map(diet => diet.name).join(', ')}</p> : ''}
                    {user && user.customDiets !== null ? <p className="m-0"><b>Additionele Dieetwensen:</b> {user.customDiets}</p> : ''}
                </Modal.Body>
                <Modal.Footer>
                    <button className="btn btn-secondary" onClick={() => setShow(false)}>
                        Sluiten
                    </button>
                </Modal.Footer>
            </Modal>
        </>
    )
}
