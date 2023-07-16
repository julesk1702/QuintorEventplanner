import { useState } from "react"
import { toast } from "react-toastify"
import EventsRepository from "../../repositories/EventsRepository"
import TextInput from "../inputs/SmallTextInput"

export default function FeedbackInput(props) {
    const [feedback, setFeedback] = useState(props.feedback)

    const handleInputChange = (event) => {
        setFeedback(event.target.value)
    }

    const handleSendFeedback = () => {
        EventsRepository.createFeedback(props.eventId, props.userId, feedback)
            .then(response => {
                if (response.status === 200) {
                  toast.success("Feedback verstuurd")
                  props.handleDetailReload()
                } else {
                    if (response.status === 500) { toast.error("Er is iets misgegaan bij het versturen van de feedback") }
                    if (response.status === 404) { toast.error("Evenement niet gevonden") }
                    if (response.status === 400) { toast.error("De feedback is niet geldig") }
                    if (response.status === 401) { toast.error("Je moet ingeschreven zijn voor dit evenement om feedback te kunnen geven") }
                    if (response.status === 412) { toast.error("Feedback mag alleen gegeven worden binnen 2 weken na het evenement") }
                }
            })
    }


    return (
        <>
            <h4>Feedback</h4>
            <TextInput
                maxCharacters={500}
                handleInputChange={handleInputChange}
                name="feedback"
                placeholder="Geef feedback aan de evenementbeheerder over dit evenement. Deze feedback is niet openbaar"
                icon="bi-pencil-square"
                isTextArea={true}
                initialValue={feedback}
            />
            <div className="d-flex justify-content-end mt-2">
                <button className="btn btn-primary" onClick={handleSendFeedback}>Versturen</button>
            </div>
        </>
    )
}
