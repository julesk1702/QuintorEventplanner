import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from 'react-toastify';
import SearchDiets from "../components/inputs/SearchDiets";
import DietsRepository from "../repositories/DietsRepository";
import Loader from "../components/loaders/Loader";
import TextInput from "../components/inputs/SmallTextInput";
import DietConfirmationModal from "../components/DietButtons/DietConfirmationModal.jsx";

export default function DietsPage() {

    const userId = localStorage.getItem("userId");
    const [selectedDietIds, setSelectedDietIds] = useState([]);
    const [initialSelectedOptions, setInitialSelectedOptions] = useState([]);
    const [otherDiets, setOtherDiets] = useState('');
    const [isLoading, setIsLoading] = useState(true);
    const [isSaving, setIsSaving] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        if (!userId) { window.location.replace("/login"); }

        DietsRepository.getDietsByUserId(userId)
            .then(request => {
                if (request.status === 0 || request.status === 500) {
                    toast.error("Er is iets misgegaan bij het ophalen van uw dieetwensen.");
                    navigate(`/error?code=${request.status}`);
                    return;
                }
                if (request.status === 200) {
                    setSelectedDietIds(request.data.map(diet => diet.dietId));
                    setInitialSelectedOptions(request.data.map(diet => diet.dietId));
                }
                DietsRepository.getCustomDietsByUserId(userId)
                    .then(request => {
                        if (request.status === 0 || request.status === 500) {
                            toast.error("Er is iets misgegaan bij het ophalen van uw dieetwensen.");
                            navigate(`/error?code=${request.status}`);
                            return;
                        }
                        if (request.status === 200) { setOtherDiets(request.data); }
                        setIsLoading(false);
                    });
            });
        return () => {}
    }, [userId, navigate]);

    const handleOptionSelected = (option) => {
        if (!selectedDietIds.includes(option.dietId)) {
            setSelectedDietIds([...selectedDietIds, option.dietId]);
        }
    }

    const handleOptionDeselected = (option) => {
        setSelectedDietIds(selectedDietIds.filter(id => id !== option.dietId));
    }

    const handleOtherDietsInputChange = (event) => {
        setOtherDiets(event.target.value);
    }

    const handleSave = () => {
        setIsSaving(true);
        DietsRepository.setDiets(userId, selectedDietIds, otherDiets)
            .then((response) => {
                setIsSaving(false);
                if (response.status === 0 || response.status === 500) {
                    toast.error("Er is iets misgegaan bij het opslaan van uw dieetwensen.");
                    navigate(`/error?code=${response.status}`);
                    return;
                }
                toast.success("Dieetwensen opgeslagen!");
                setShowModal(false);
            })
    }

    return ( !isLoading ?
        <div className="container h-100 text-center">
            <DietConfirmationModal
                handleSave={handleSave}
                show={showModal}
                handleClose={() => setShowModal(false)}
                isSaving={isSaving}
            />
            <div className="d-flex row justify-content-center min-height">
                {/* Title */}
                <div className="">
                    <h1 className="fw-semibold mt-4">Mijn Dieetwensen</h1>
                    <small className="text-secondary mt-1" style={{fontSize: '.7em'}}><em>Let op: Bij het inschrijven voor een evenement worden uw dieetwensen automatisch doorgegeven aan de evenementbeheerder en is deze informatie toegankelijk voor het managementteam.</em></small>
                </div>
                {/* Inputs */}
                <div className="d-flex gap-4 row">
                    <div style={{textAlign: "left"}}>
                        <SearchDiets
                            placeholder="Zoek een dieet"
                            handleOptionSelected={handleOptionSelected}
                            handleOptionDeselected={handleOptionDeselected}
                            initialSelectedOptions={initialSelectedOptions}
                        />
                    </div>
                    <div style={{textAlign: "left"}}>
                        <TextInput
                            maxCharacters={100}
                            handleInputChange={handleOtherDietsInputChange}
                            displayName="Overige Dieetwensen"
                            name="otherDiets"
                            placeholder="Indien u specifieke dieetwensen heeft die niet in ons systeem staan, kunt u deze hieronder invullen, gescheiden door een komma."
                            icon="bi-pencil-square"
                            isTextArea={true}
                            initialValue={otherDiets}
                        />
                    </div>
                </div>
                {/* Save button */}
                <div className="align-self-end mb-5">
                    <button className="btn btn-primary mt-3 mb-5 w-75" onClick={() => setShowModal(true)}>
                        Opslaan
                    </button>
                </div>
            </div>
        </div>
        : <div className="container h-100 text-center">
            <div className="d-flex min-height justify-content-center align-items-center">
                <Loader />
            </div>
        </div>
    );
}
