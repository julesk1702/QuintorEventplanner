import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import ButtonLoader from "../loaders/ButtonLoader";

export default function DietConfirmationModal(props) {
    const { show, handleClose, handleSave, isSaving } = props;

    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Dieetwensen Opslaan</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>
                        Wilt u uw dieetwensen opslaan? Let op: Deze wijzigingen zullen uw eerdere dieetvoorkeuren overschrijven.
                    </p>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Annuleren
                    </Button>
                    <Button variant="primary" onClick={handleSave}>
                        {isSaving ? <ButtonLoader /> : "Opslaan"}
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}
