import { useState } from "react"
import Modal from "react-bootstrap/Modal";

export default function NotesSummaryButton(props) {
    const {userNotes, userCustomDiets, guestNotes} = props;
    const [show, setShow] = useState(false);

    return (
        <>
            <button className="btn btn-primary" onClick={() => setShow(true)}>Alle Opmerkingen</button>

            <Modal show={show} onHide={() => setShow(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Alle Opmerkingen</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <b className="m-0">Evenement-specifieke gebruikeropmerkingen:</b>
                    {userNotes && userNotes.length >= 1 ? (
                        userNotes.map(note => (
                            <p className="m-0">{note}</p>
                        ))
                    ) : ' N/A'}
                    <hr />
                    <b className="mt-1">Additionele diëten van gebruikers:</b>
                    {userCustomDiets && userCustomDiets.length >= 1 ? (
                        userCustomDiets.map(diet => (
                            <p className="m-0">{diet}</p>
                        ))
                    ) : ' N/A'}
                    <hr />
                    <b className="mt-1">Evenement-specifieke gastopmerkingen:</b>
                    {guestNotes && guestNotes.length >= 1 ? (
                        guestNotes.map(note => (
                            <p className="m-0">{note}</p>
                        ))
                    ) : ' N/A'}
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
