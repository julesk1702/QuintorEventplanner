import { useState } from "react"
import Modal from "react-bootstrap/Modal";

export default function DietsSummaryButton(props) {
    const {diets} = props;
    const [show, setShow] = useState(false);

    return (
        <>
            <button className="btn btn-primary" onClick={() => setShow(true)}>Samenvatting Dieetwensen</button>

            <Modal show={show} onHide={() => setShow(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Dieetwensen Samenvatting</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {diets.length >=1 ?
                        (diets.map(diet => <p className="m-0"><b>{diet.name}:</b> {diet.totalCount}</p>))
                    : 'N/A'}
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
